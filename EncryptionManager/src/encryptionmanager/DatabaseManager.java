package encryptionmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
<<<<<<< HEAD

    private Connection conn = null;
=======
  
    private Connection conn;
>>>>>>> 637a9c703dc515631cfe275a09d06a33cf809ce7

    public DatabaseManager(String url) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + url);
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
    public void InsertRow(String[] args) {
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
    public void UpdateRow(String[] args) {
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