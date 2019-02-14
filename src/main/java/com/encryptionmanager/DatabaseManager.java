package com.encryptionmanager;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class DatabaseManager {

    private Connection conn = null;

    public DatabaseManager() {
        try {
            //Plain text database is stored in memory as it is volatile.
            conn = DriverManager.getConnection("jdbc:sqlite::memory:");
            System.out.println("Connection Sucessful.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
        String sql = "CREATE TABLE IF NOT EXISTS users (id integer PRIMARY KEY,email VARCHAR,password VARBINARY, salt VARBINARY)";
        String sql2 = "INSERT INTO users(email,password,salt) VALUES(?,?,?)";

        byte[] salt = EncryptionMethods.generateBytes(64);
        byte[] hashedPassword = EncryptionMethods.hash(password, salt);

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            PreparedStatement pstmt = conn.prepareStatement(sql2);
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
