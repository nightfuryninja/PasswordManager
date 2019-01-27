package encryptionmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseMethods {
    
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
