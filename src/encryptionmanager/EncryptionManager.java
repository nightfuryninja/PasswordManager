package encryptionmanager;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionManager {

    //TODO LIST:
    //1) Create option for password to contain different characters.
    //2) Store passwords within an encrypted database.
    //3) Create a pretty GUI that looks all fancy and modern.
    public static Scanner input = new Scanner(System.in);
    private static SecureRandom generator = new SecureRandom();

    public static void main(String[] args) {
        
    }

    //Generates a secure random salt (256 bits / 32 bytes in length).
    public static byte[] GenerateSalt(int Size) {
        byte[] salt = new byte[Size];
        generator.nextBytes(salt);
        return salt;
    }

    //Encryptes data using AES with a specific key.
    public static void AESEncrypt(byte[] key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        } catch (Exception e) {

        }
    }

    //Decrypts data using AES with a speciic key.
    public static void AESDecrypt(byte[] key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
        } catch (Exception e) {
            
        }
    }

    //Hashes and salts a string. Generates 32 bytes.
    public static byte[] Hash(String data) {
        try {
            byte[] salt = GenerateSalt(64);
            //We cannot do more than 320,000 iterations until it hurts performance.
            PBEKeySpec keySpec = new PBEKeySpec(data.toCharArray(), salt, 320000, 256);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return keyFactory.generateSecret(keySpec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Sorry, that encryption algorith doesn't exist.");
            return null;
        } catch (InvalidKeySpecException ex) {
            System.out.println("Sorry, that is an invalid key specification.");
            return null;
        }
    }
}
