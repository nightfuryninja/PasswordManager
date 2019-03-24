package com.encryptionmanager;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Users {
    
    private Connection conn = null;

    public Users() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            System.out.println("Connection Sucessful.");
            
            String sql = "CREATE TABLE IF NOT EXISTS users (id integer PRIMARY KEY AUTOINCREMENT,email VARCHAR,password VARBINARY,salt VARBINARY)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void register(String email, char[] password) {
        String sql = "INSERT INTO users(email,password,salt) VALUES(?,?,?)";

        byte[] salt = EncryptionMethods.generateBytes(64);
        byte[] hashedPassword = EncryptionMethods.hash(password, salt);

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setBytes(2, hashedPassword);
            pstmt.setBytes(3, salt);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public boolean login(String email, char[] password) {
        boolean success = false;
        String sql = "SELECT password,salt FROM users WHERE email=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] hashedPassword = rs.getBytes("password");
                byte[] salt = rs.getBytes("salt");
                if (Arrays.equals(EncryptionMethods.hash(password, salt), hashedPassword)) {
                    success = true;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return success;
    }
    
    public byte[] getKey(String email) {
        byte[] key = null;
        
        String sql = "SELECT password,salt FROM users WHERE email=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] passwordBytes = rs.getBytes("password");
                byte[] salt = rs.getBytes("salt");
                String passwordString;
                try {
                    passwordString = new String(passwordBytes, "UTF-8");
                    char[] password = passwordString.toCharArray();
                    key = EncryptionMethods.hash(password, salt);
                } catch (UnsupportedEncodingException ex) {
                    System.out.println(ex);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return key;
    }
    
}
