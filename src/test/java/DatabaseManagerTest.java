import org.junit.BeforeClass;
import org.junit.Test;
import com.encryptionmanager.DatabaseManager;

/**
 * Please note: These tests will fail unless the SQLite library is installed.
 * 
 */
public class DatabaseManagerTest {
    
    public static DatabaseManager database;
    
    public DatabaseManagerTest() {
    }
    
    @BeforeClass
    public static void createNewDatabase() {
        database = new DatabaseManager();
    }
        
    @Test
    public void registerInDatabase(){
        String email = "email@email.com";
        char[] password = "password".toCharArray();
        database.register(email, password);
    }
    
}
