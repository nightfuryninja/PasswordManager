
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
        byte[] IV = {-124, -124, -93, 115, -55, 50, 120, 6, 93, -4, -23, -32, 20, -117, 78, -73};
        byte[] data = "This Data Must Be Encrypted".getBytes();
        byte[] expected = {-124, -124, -93, 115, -55, 50, 120, 6, 93, -4, -23, -32, 20, -117, 78, -73, 83, -30, -36,-58, -24, -45,-34, -98,69, 37,-9, -107,-78, -88,-113, -125,87, 7, -102,-104, -63,124, 126,-47, -108,-77, 34,1, -40,67, 63,33};

        byte[] encryptedData = EncryptionMethods.AESEncrypt(key, IV, data);
        assertArrayEquals(expected, encryptedData);
    }

    @Test
    public void decryptData() {
        byte[] key = "ThisKeyIsThirtyTwoBytesLong(32).".getBytes();
        byte[] IV = EncryptionMethods.generateBytes(16);
        byte[] data = "This Data Must Be Encrypted".getBytes();

        byte[] encrypted = EncryptionMethods.AESEncrypt(key, IV, data);
        byte[] actualEncrypted = new byte[encrypted.length - 16];

        System.arraycopy(encrypted, 16, actualEncrypted, 0, actualEncrypted.length);
        byte[] decrytped = EncryptionMethods.AESDecrypt(key, IV, actualEncrypted);
        assertArrayEquals(data, decrytped);
    }

    @Test
    public void decryptDataWithoutPregeneratedIV() {
        byte[] key = "ThisKeyIsThirtyTwoBytesLong(32).".getBytes();
        byte[] IV = new byte[16];
        byte[] data = "This Data Must Be Encrypted".getBytes();

        byte[] encrypted = EncryptionMethods.AESEncrypt(key, null, data);
        byte[] actualEncrypted = new byte[encrypted.length - 16];
        System.arraycopy(encrypted, 0, IV, 0, 16);
        System.arraycopy(encrypted, 16, actualEncrypted, 0, actualEncrypted.length);
        byte[] decrytped = EncryptionMethods.AESDecrypt(key, IV, actualEncrypted);
        assertArrayEquals(data, decrytped);
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
