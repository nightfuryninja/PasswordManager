import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import encryptionmanager.EncryptionMethods;


public class EncryptionMethodsTest {
    
    public EncryptionMethodsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void encryptData(){
        byte[] key = "ThisKeyIsThirtyTwoBytesLong(32).".getBytes();
        byte[] data = "This Data Must Be Encrypted".getBytes();
        byte[] encryptedData = EncryptionMethods.AESEncrypt(key, data);
        if(new String(data).equalsIgnoreCase(new String(encryptedData))){
            fail("AESEncrypt failed to encrypt the data.");
        }
    }
    
    public void generateRandomBytes(){
        byte[] generatedBytes = EncryptionMethods.generateBytes(16);
        if(generatedBytes == null || generatedBytes.length != 16){
            fail("Something went wrong when generating random bytes.");
        }
    }

}
