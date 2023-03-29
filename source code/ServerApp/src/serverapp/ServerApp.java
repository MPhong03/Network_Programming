/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.io.*;

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
            // Initialize server socket
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started");

            // Connect to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            while (true) {
                // Wait for client connection
                Socket clientSocket = serverSocket.accept();

                // Read input from client
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                String request = in.readLine();

                // Process request
                if (request.equals("register")) {
                    String username = in.readLine();
                    String encryptedPassword = in.readLine();
                    if (checkUsername(connection, username)) {
                        out.println("Username already exists");
                    } else {
                        registerUser(connection, username, encryptedPassword);
                        out.println("Registration successful");
                    }
                } else if (request.equals("login")) {
                    String username = in.readLine();
                    String encryptedPassword = in.readLine();
                    if (checkCredentials(connection, username, encryptedPassword)) {
                        out.println("success");
                    } else {
                        out.println("failure");
                    }
                } else if (request.equals("excuteData")) {
                    String encryptedText = in.readLine();
                    String secretKey = in.readLine();
                    String user = in.readLine();
                    
                    String output = excuteMessage(connection, encryptedText, secretKey);
                    out.println(output);
                    String sql = "INSERT INTO message (username, messagetext) VALUES (?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, user);
                    statement.setString(2, output);
                    statement.executeUpdate();
                }

                // Close connection with client
                clientSocket.close();
                System.out.println("Client disconnected");
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static boolean checkUsername(Connection connection, String username) throws SQLException {
        String sql = "SELECT * FROM client WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    private static boolean checkCredentials(Connection connection, String username, String encryptedPassword) throws SQLException {
        String sql = "SELECT * FROM client WHERE username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, encryptedPassword);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    private static void registerUser(Connection connection, String username, String encryptedPassword) throws SQLException {
        String sql = "INSERT INTO client (username, password) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, encryptedPassword);
        statement.executeUpdate();
    }
    
    private static String excuteMessage(Connection connection, String encryptedtext, String secretkey) throws SQLException {
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
                String temp = ((char) ('a' + i)) + ":" + count[i] + "\n";
                output += temp;
            }
        }
        return output;
    }
}

