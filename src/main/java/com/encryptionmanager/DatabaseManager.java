package com.encryptionmanager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseManager {

    private Connection conn = null;

    public DatabaseManager() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:SampleDatabase.db");
            System.out.println("Connection Sucessful.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public final String getDatabaseURL(String email) {
        String URL = System.getenv("APPDATA") + "\\PasswordManager";
        File file = new File(URL);
        file.mkdirs();
        String dbName = EncryptionMethods.hashEmail(email);
        URL = URL + "\\" + dbName + ".db";
        return URL;
    }

    //Creates a new table to store the users passwords.
    public byte[] register(String email, char[] password) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS passwords(ID INTEGER Primary Key AUTOINCREMENT, name VARCHAT(255), url VARCHAR(255),username BLOB, password BLOB)");
            pstmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        byte[] salt = email.getBytes();
        return EncryptionMethods.hash(password, salt);
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

    //Gets all the websites stored in the database and returns a list of Websites (object).
    public ArrayList<Website> getWebsites() {
        ArrayList<Website> websites = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT name,url,username,password FROM passwords");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String websiteName = rs.getString("name");
                String url = rs.getString("url");
                byte[] username = rs.getBytes("username");
                byte[] password = rs.getBytes("password");
                Website website = new Website(websiteName, url, username, password);
                websites.add(website);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return websites;
    }

    //Adds a website to the database. 
    public void addWebsite(String websiteName, String url, byte[] username, byte[] password) {
        try {
            PreparedStatement pst = conn.prepareStatement("INSERT INTO passwords (name, url, username, password) VALUES(?, ?, ?, ?)");
            pst.setString(1, websiteName);
            pst.setString(2, url);
            pst.setBytes(3, username);
            pst.setBytes(4, password);
            pst.executeUpdate();
            System.out.println("Successfully added to database.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Closes the connection to the database.
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Updates an existing row (using ID) in the database.
    public void updateRow(String[] args) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE accounts SET ? = ? WHERE id  = ?");
            pstmt.execute();
            System.out.println("Updated");
        } catch (SQLException ex) {
            System.out.println("An SQL exception occured.");
        }
    }

}
