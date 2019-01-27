package encryptionmanager;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionMethods {

    public static SecureRandom generator = new SecureRandom();

    //Generates a secure random salt (256 bits / 32 bytes in length).
    public static byte[] GenerateSalt(int Size) {
        byte[] salt = new byte[Size];
        generator.nextBytes(salt);
        return salt;
    }

    //Encryptes data using AES with a specific key.
    public static void AESEncrypt(byte[] key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        } catch (Exception e) {

        }
    }

    //Decrypts data using AES with a speciic key.
    public static void AESDecrypt(byte[] key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
        } catch (Exception e) {

        }
    }

    //Hashes and salts a string. Generates 32 bytes.
    public static byte[] Hash(String data) {
        try {
            byte[] salt = GenerateSalt(64);
            //We cannot do more than 320,000 iterations until it hurts performance.
            PBEKeySpec keySpec = new PBEKeySpec(data.toCharArray(), salt, 320000, 256);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return keyFactory.generateSecret(keySpec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Sorry, that encryption algorith doesn't exist.");
            return null;
        } catch (InvalidKeySpecException ex) {
            System.out.println("Sorry, that is an invalid key specification.");
            return null;
        }
    }
    
    public static void connect() {        
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:test.db";
            String sql = "CREATE TABLE IF NOT EXISTS passwords (id integer PRIMARY KEY,website VARCHAR2,username BINARY,password BINARY)";
            //String sql2 = "INSERT INTO passwords(website,username,password) VALUES(?,?,?)";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            /*
            PreparedStatement pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, "Test");
            pstmt.setString(2, "Test");
            pstmt.setString(3, "Test");
            pstmt.executeUpdate();
            */
            
            System.out.println("Connection Sucessful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
}