package com.encryptionmanager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void register(String email, char[] password) {

        byte[] salt = email.getBytes();
        byte[] hashedPassword = EncryptionMethods.hash(password, salt);
        try {
            PreparedStatement pstmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS passwords(ID INTEGER Primary Key AUTOINCREMENT, name VARCHAT(255), url VARCHAR(255),username VARCHAR(255),password VARCHAR(255))");
            pstmt.execute();
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

    public ArrayList<Website> getWebsites() {
        ArrayList<Website> websites = new ArrayList<>();
        String sql = "SELECT name,url,username,password FROM passwords";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String websiteName = rs.getString("name");
                String url = rs.getString("url");
                String username = rs.getString("username");
                String password = rs.getString("password");

                Website website = new Website(websiteName,url, username, password);
                websites.add(website);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return websites;
    }

    //Adds a website to the database. 
    public void addWebsite(String websiteName, String url, String username, String password) {
        try {
            PreparedStatement pst = conn.prepareStatement("INSERT INTO passwords (name, url, username, password) VALUES(?, ?, ?, ?)");
            pst.setString(1, websiteName);
            pst.setString(2, url);
            pst.setString(3, username);
            pst.setString(4, password);
            pst.executeUpdate();
            System.out.println("Successfully added to database.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

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
        String command = String.format("UPDATE accounts SET {0} = {1} WHERE id  = {3};", args);
        try {
            Statement statement = conn.createStatement();
            statement.execute(command);
            System.out.println("Updated");
        } catch (SQLException ex) {
            System.out.println("An SQL exception occured.");
        }
    }

}
