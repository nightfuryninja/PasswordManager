
import org.junit.Test;
import static org.junit.Assert.*;
import com.encryptionmanager.EncryptionMethods;
import java.util.Arrays;

public class EncryptionMethodsTest {

    public EncryptionMethodsTest() {
    }

    @Test
    public void encryptData() {
        byte[] key = "ThisKeyIsThirtyTwoBytesLong(32).".getBytes();
        byte[] data = "This Data Must Be Encrypted".getBytes();
        byte[] encryptedData = EncryptionMethods.AESEncrypt(key, data, null);
        if (new String(data).equalsIgnoreCase(new String(encryptedData))) {
            fail("AESEncrypt failed to encrypt the data.");
        }
    }

    @Test
    public void decryptData() {
        byte[] key = "ThisKeyIsThirtyTwoBytesLong(32).".getBytes();
        byte[] data = "This Data Must Be Encrypted".getBytes();
        byte[] encryptedData = EncryptionMethods.AESEncrypt(key, data, null);
        System.out.println(encryptedData.length);
        byte[] IV = Arrays.copyOf(encryptedData, 16);
        byte[] dataToDecrypt = Arrays.copyOfRange(encryptedData, 16, encryptedData.length);
        byte[] decryptedData = EncryptionMethods.AESDecrypt(key, IV, dataToDecrypt);
        //Broken
    }

    @Test
    public void generateRandomBytes() {
        byte[] generatedBytes = EncryptionMethods.generateBytes(16);
        if (generatedBytes == null || generatedBytes.length != 16) {
            fail("Something went wrong when generating random bytes.");
        }
    }

    @Test
    public void hashString() {
        byte[] expectedOutput = {118, -20, -28, 65, -45, -23, -8, -69, 127, -21, 21, -112, 111, 114, 97, -96, -97, 65, 70, 98, -55, 39, 115, -81, -10, -61, 90, -19, -6, 0, -32, -65};
        char[] data = "ThisIsOurDataToHash".toCharArray();
        byte[] salt = "ThisIsOurSalt".getBytes();
        byte[] actualOutput = EncryptionMethods.hash(data, salt);

        if (actualOutput.equals(expectedOutput)) {
            fail("Something went wrong when hashing our data.");
        }
    }

}
