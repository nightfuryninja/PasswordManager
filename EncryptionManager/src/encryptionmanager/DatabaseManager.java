package encryptionmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    
    private Connection conn = null;

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
    
}
