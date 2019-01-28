package encryptionmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
  
    private Connection conn;

    public DatabaseManager(String url) {
        connectToDatabase();
    }

    //Connects to the database.
    private void connectToDatabase() {
        try {
            String url = "jdbc:sqlite:test.db";
            String sql = "CREATE TABLE IF NOT EXISTS accounts (id integer PRIMARY KEY,website VARCHAR2,username BINARY,password BINARY)";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
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

    public void execute(String sql) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
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

    public void executePreparedStatement(PreparedStatement pstmt) {
        try {
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);;
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
