package com.encryptionmanager;



import java.security.InvalidAlgorithmParameterException;
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
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncryptionMethods {

    //!!!!!!!!! HMAC ENCRYPTION !!!!!!!!!
    
    public static SecureRandom generator = new SecureRandom();

    //Generates a secure random byte array.
    public static byte[] generateBytes(int Size) {
        byte[] secureBytes = new byte[Size];
        generator.nextBytes(secureBytes);
        return secureBytes;
    }

    //Encryptes data using AES with a specific key.
    public static byte[] AESEncrypt(byte[] key, byte[] data) {
        try {
            //We need to create an initizalization vector.
            IvParameterSpec IVSpec = new IvParameterSpec(generateBytes(16));
            //First 16 bytes of file will be IV.
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            //Use CBC so each block is different.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");            
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, IVSpec);
            //First 16 bytes must be IV
            return cipher.doFinal(data);
        } catch(NoSuchAlgorithmException ex){
            System.out.println("That algorithm doesn't exist.");
        } catch(NoSuchPaddingException ex){
            System.out.println("That padding doesn't exist.");
        } catch (InvalidKeyException ex){
            System.out.println("Invalid key.");
        } catch (InvalidAlgorithmParameterException ex){
            System.out.println("Invalid algorithm");
        } catch(IllegalBlockSizeException ex){
            System.out.println("That is an illegal block size.");
        } catch (BadPaddingException ex){
            System.out.println("Bad padding. Please try again.");
        }
        return null;
    }

    //Decrypts data using AES with a speciic key.
    public static byte[] AESDecrypt(byte[] key, byte[] IV,  byte[] data) {
        //We should only ever decrypt to memory (seeing as it is volatile).
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        //The IV parameter is first 16 bytes in the file.
        IvParameterSpec IVSpect = new IvParameterSpec(IV);
        try{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, IVSpect);
        //Everything after position 15 in data.
        return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException ex){
            System.out.println("That encryption algorithm does not exist.");
        } catch (NoSuchPaddingException ex){
            System.out.println("That padding does not exist.");
        } catch(InvalidKeyException ex){
            System.out.println("That is an invalid key.");
        } catch (InvalidAlgorithmParameterException ex){
            System.out.println("Invalid algorith parameter.");
        } catch (IllegalBlockSizeException ex){
            System.out.println("Block size invalid. Something went wrong.");
        } catch (BadPaddingException ex){
            System.out.println("Bad padding exception.");
        }
        return null;
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
