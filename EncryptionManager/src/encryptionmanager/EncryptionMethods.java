package encryptionmanager;

import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;

public class EncryptionMethods {

    public static SecureRandom generator = new SecureRandom();

    //Generates a secure random byte array.
    public static byte[] generateBytes(int Size) {
        byte[] secureBytes = new byte[Size];
        generator.nextBytes(secureBytes);
        return secureBytes;
    }

    //Encryptes data using AES with a specific key.
    public static void AESEncrypt(byte[] key, byte[] IV) {
        try {
            //We need to create an initizalization vector.
            IvParameterSpec IVSpec = new IvParameterSpec(IV);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            //Use CBC so each block is different.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");            
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, IVSpec);            
        } catch (Exception e) {

        }
    }

    //Decrypts data using AES with a speciic key.
    public static void AESDecrypt(byte[] key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Sorry, that encryption algorithm does not exist.");
        } catch (NoSuchPaddingException ex) {
            System.out.println("Sorry, padding algorithm does not exist.");
        } catch (InvalidKeyException ex) {
            System.out.println("Sorry, that is an invalid key.");
        } catch (Exception ex){
            System.out.println("An exception occured.");
        }
    }

    //Hashes and salts a string. Generates 32 bytes.
    public static byte[] hash(char[] data, byte[] salt) {
        try {
            //We cannot do more than 320,000 iterations until it hurts performance.
            PBEKeySpec keySpec = new PBEKeySpec(data, salt, 320000, 256);
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

    //Generates a password of a certain length.
    public static void generatePassword(int length) {
        byte[] passwordBytes = generateBytes(length);
        System.out.println(Arrays.toString(passwordBytes));
    }

}
