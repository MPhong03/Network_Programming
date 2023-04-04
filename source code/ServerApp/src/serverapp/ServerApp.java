/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Minh Phong
 */

public class ServerApp {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ltm";
    private static final String DB_USER = "mphong";
    private static final String DB_PASS = "123456";

    public static void main(String[] args) {
        try {
            // Khởi tạo một Server Socket
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started");

            // Kết nối đến cơ sở dữ liệu
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            while (true) {
                // Đợi kết nối từ Client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");

                // Tạo luồng để nhiều Client có thể kết nối dến Server và sử dụng các dịch vụ của Server
                ServerApp server = new ServerApp();
                ClientHandler clientHandler = server.new ClientHandler(clientSocket, connection);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public class ClientHandler implements Runnable {
        private Socket clientSocket;
        private Connection connection;

        public ClientHandler(Socket clientSocket, Connection connection) {
            this.clientSocket = clientSocket;
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                // Tạo các input output để lấy dữ liệu từ Client
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                
                // Yêu cầu được gửi từ Client
                /* Trong request có thể sẽ là các tín hiệu sau:
                - "register" : Cho phép người dùng đăng ký tài khoản
                - "login" : Cho phép người dùng đăng nhập bằng tài khoản đã có
                = "excuteData" : Thực hiện xử lý dữ liệu theo yêu cầu của đề tài
                */
                String request = in.readLine();

                // Các dịch vụ của Server mà Client có thể đọc
                
                if (request.equals("register")) { // Đăng ký
                    String username = in.readLine();
                    String encryptedPassword = in.readLine();
                    if (checkUsername(connection, username)) {
                        out.println("Username already exists");
                    } else {
                        registerUser(connection, username, encryptedPassword);
                        out.println("Registration successful");
                    }
                } else if (request.equals("login")) { // Đăng nhập
                    String username = in.readLine();
                    String encryptedPassword = in.readLine();
                    if (checkCredentials(connection, username, encryptedPassword)) {
                        out.println("success");
                    } else {
                        out.println("failure");
                    }
                } else if (request.equals("excuteData")) { // Xử lý dữ liệu được gửi từ Client
                    String encryptedText = in.readLine();
                    String secretKey = in.readLine();
                    String user = in.readLine();
                    String time = in.readLine();

                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    String formattedDateTime = formatter.format(date);
                    
                    String output = excuteMessage(encryptedText, secretKey);
                    String messageToClient = formattedDateTime + " [Server]: " + output;
                    out.println(messageToClient);
                    
                    /* VD: messageToClient là
                        01/01/2023 12:00:00 [Server]: I'm Server
                    */
                    
                    // Lưu tin nhắn đã được mã hóa và khóa vào database
                    String sql = "INSERT INTO message (username, messagetext, secretKey, timemessage) VALUES (?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, user);
                    statement.setString(2, AES.encrypt(output, secretKey));
                    statement.setString(3, secretKey);
                    statement.setString(4, time);
                    statement.executeUpdate();
                } else if (request.equals("excuteFile")) {
                    String encryptedText = in.readLine();
                    String key = in.readLine();
                    String username = in.readLine();

                    String output = excuteMessage(encryptedText, key); // Thay "hello" bằng từ cần đếm số lần xuất hiện

                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    String formattedDateTime = formatter.format(date);
                    
                    String messageToClient = formattedDateTime + " [Server]: " + output;
                    out.println(messageToClient);

                    // Lưu file vào ổ D:/
                    String filename = username + "_" + key + "_" + System.currentTimeMillis() + ".txt";
                    String outputPath = "D:/fromclient/";
                    File file = new File(outputPath + filename);
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(encryptedText);
                    }
                }

                // Đóng kết nối của Client
                clientSocket.close();
                System.out.println("Client disconnected");
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        }

        // Kiểm tra username đã tồn tại hay không
        private boolean checkUsername(Connection connection, String username) throws SQLException {
            String sql = "SELECT * FROM client WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }

        // Kiểm tra đăng nhập
        private boolean checkCredentials(Connection connection, String username, String encryptedPassword) throws SQLException {
            String sql = "SELECT * FROM client WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, encryptedPassword);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }

        // Đăng ký tài khoản
        private void registerUser(Connection connection, String username, String encryptedPassword) throws SQLException {
            String sql = "INSERT INTO client (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, encryptedPassword);
            statement.executeUpdate();
        }

        // Xử lý dữ liệu
        private static String excuteMessage(String encryptedtext, String secretkey) throws SQLException {
            // Giải mã văn bản được gửi từ client
            String plaintext = AES.decrypt(encryptedtext, secretkey);

            // Đếm số lượng xuất hiện của các chữ cái trong bản rõ
            int[] count = new int[26]; // mảng để lưu số lần xuất hiện của từng chữ cái
            for (int i = 0; i < plaintext.length(); i++) {
                char c = Character.toLowerCase(plaintext.charAt(i)); // Lấy ký tự và chuyển thành chữ thường
                if (c >= 'a' && c <= 'z') { // Nếu là chữ cái, tăng giá trị tương ứng trong mảng lên 1
                    count[c - 'a']++;
                }
            }
            // In ra kết quả
            String output = "";
            for (int i = 0; i < 26; i++) {
                if (count[i] != 0) {
                    String temp = ((char) ('a' + i)) + ":" + count[i] + " ";
                    output += temp;
                }
            }
            return output;
        }
    }
}


