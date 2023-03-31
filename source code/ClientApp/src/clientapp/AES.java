/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientapp;
/**
 *
 * @author Minh Phong
 */
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {
    private static final String INIT_VECTOR = "RandomInitVector"; // Đây là giá trị khởi tạo IV, được sử dụng để tăng cường tính ngẫu nhiên cho quá trình mã hóa.

    /**
     * Mã hóa một chuỗi văn bản sử dụng thuật toán AES.
     *
     * @param value Chuỗi văn bản cần mã hóa.
     * @param key Khóa mã hóa.
     * @return Chuỗi base64 biểu diễn kết quả sau khi mã hóa.
     */
    public static String encrypt(String value, String key) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8")); // Khởi tạo đối tượng IvParameterSpec với giá trị IV được mã hóa theo phương thức UTF-8.
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES"); // Khởi tạo đối tượng SecretKeySpec với khóa đã cho và thuật toán mã hóa AES.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); // Tạo đối tượng Cipher với thuật toán AES/CBC/PKCS5PADDING.
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv); // Thiết lập chế độ mã hóa và khóa mã hóa sử dụng trong thuật toán.
            byte[] encrypted = cipher.doFinal(value.getBytes()); // Mã hóa chuỗi đầu vào và lưu kết quả vào một mảng byte.
            return Base64.getEncoder().encodeToString(encrypted); // Trả về chuỗi base64 biểu diễn kết quả sau khi mã hóa.
        } catch (Exception ex) {
            ex.printStackTrace(); // In ra thông tin lỗi nếu có lỗi xảy ra.
        }
        return null; // Nếu có lỗi xảy ra, trả về giá trị null.
    }

    /**
     * Giải mã một chuỗi đã được mã hóa sử dụng thuật toán AES.
     *
     * @param encrypted Chuỗi base64 biểu diễn chuỗi đã được mã hóa.
     * @param key Khóa giải mã.
     * @return Chuỗi ban đầu trước khi bị mã hóa.
     */
    public static String decrypt(String encrypted, String key) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8")); // Khởi tạo đối tượng IvParameterSpec với giá trị IV được mã hóa theo phương thức UTF-8.
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES"); // Khởi tạo đối tượng SecretKeySpec với khóa đã cho và thuật toán mã hóa AES.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); // Tạo đối tượng Cipher với thuật toán AES/CBC/PKCS5PADDING.
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv); // Thiết lập chế độ giải mã và khóa giải mã sử dụng trong thuật toán.
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted)); // Giải mã chuỗi đầu vào và lưu kết quả vào một mảng byte.
            return new String(original); // Trả về chuỗi ban đầu trước khi bị mã hóa.
        } catch (Exception ex) {
            ex.printStackTrace();  // In ra thông tin lỗi nếu có lỗi xảy ra.
        }
        return null; // Nếu có lỗi xảy ra, trả về giá trị null.
    }
}
