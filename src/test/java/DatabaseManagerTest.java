import org.junit.BeforeClass;
import org.junit.Test;
import com.encryptionmanager.DatabaseManager;

public class DatabaseManagerTest {
    
    public static DatabaseManager database;
    
    public DatabaseManagerTest() {
    }
    
    @BeforeClass
    public static void createNewDatabase() {
        database = new DatabaseManager();
    }
    
    
    
}
