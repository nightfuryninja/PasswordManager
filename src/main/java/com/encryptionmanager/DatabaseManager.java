package com.encryptionmanager;



import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class DatabaseManager {

    private Connection conn = null;

    public DatabaseManager(String email) {
        try {
            //Plain text database is stored in memory as it is volatile.
            String url = getDatabaseURL(email);
            conn = DriverManager.getConnection("jdbc:sqlite:" + url);
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

    public void execute(String sql) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void backupDatabase() {
        try {
            Statement statement = conn.createStatement();
            statement.execute("backup to backup.db");
        } catch (SQLException ex) {
            System.out.println("There is an SQL exception when backing up database.");
        }
    }

    public void register(String email, char[] password) {
        String sql = "CREATE TABLE IF NOT EXISTS passwords (id integer PRIMARY KEY,name VARCHAR,username VARCHAR,password VARCHAR)";

        byte[] salt = email.getBytes();
        byte[] hashedPassword = EncryptionMethods.hash(password, salt);
        
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
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

    public PreparedStatement createPreparedStatement(String sql) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return pstmt;
    }

    public void preparedStatementSetString(PreparedStatement pstmt, int index, String value) {
        try {
            pstmt.setString(index, value);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void preparedStatementSetBytes(PreparedStatement pstmt, int index, byte[] value) {
        try {
            pstmt.setBytes(index, value);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void executePreparedStatement(PreparedStatement pstmt) {
        try {
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
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

    //Inserts a new row of account data into the database.
    public void insertRow(String[] args) {
        String command = String.format("INSERT INTO accounts(website, username, password) "
                + "VALUES({0}, {1}, {2})", args);
        try {
            Statement statement = conn.createStatement();
            statement.execute(command);
            System.out.println("Inserted new data into the database.");
        } catch (SQLException ex) {
            System.out.println("A problem with SQL occured.");
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
