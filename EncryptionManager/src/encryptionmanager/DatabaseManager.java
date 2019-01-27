package encryptionmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private Connection conn;
    
    public DatabaseManager(){
        ConnectToDatabase();
    }
    
    //Connects to the database.
    private void ConnectToDatabase() {
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

}
