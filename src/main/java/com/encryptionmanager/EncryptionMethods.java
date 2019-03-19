package com.encryptionmanager;

import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.NoSuchAlgorithmException;

public class EncryptionMethods {

    public static SecureRandom generator = new SecureRandom();

    //Generates a secure random byte array.
    public static byte[] generateBytes(int Size) {
        byte[] secureBytes = new byte[Size];
        generator.nextBytes(secureBytes);
        return secureBytes;
    }

    //AES Encrypts provided data via a key. Does not require an IV, 
    //which will be generated and returned with the data.
    public static byte[] AESEncrypt(byte[] key, byte[] initVector, byte[] data) {
        try {
            if (initVector == null) {
                initVector = generateBytes(16);
            }
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(data);
            byte[] returnData = new byte[iv.getIV().length + encrypted.length];
            System.arraycopy(iv.getIV(), 0, returnData, 0, iv.getIV().length);
            System.arraycopy(encrypted, 0, returnData, iv.getIV().length, encrypted.length);
            return returnData;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //AES Decrypts provided data via a key. Does not require initVector parameter,
    //assuming that the first 16 bytes in the data represents the IV.
    public static byte[] AESDecrypt(byte[] key, byte[] initVector, byte[] encrypted) {
        try {
            byte[] data;
            if (initVector == null) {
                initVector = new byte[16];
                System.arraycopy(encrypted, 0, initVector, 0, 16);
                data = new byte[encrypted.length - 16];
                System.arraycopy(encrypted, 16, data, 0, encrypted.length - 16);
            }
            else{
                data = encrypted;
            }

            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(data);
            return original;
        } catch (Exception ex) {
            ex.printStackTrace();
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
            System.out.println("Sorry, that encryption algorithm doesn't exist.");
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

    //Converts bytes to hexadecimal.
    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        String hexString = builder.toString();
        return hexString;
    }

    public static String hashEmail(String email) {
        String[] emailArray = email.split("@");
        char[] username = emailArray[0].toCharArray();
        byte[] domain = emailArray[1].getBytes();
        byte[] hashedEmailBytes = hash(username, domain);
        String hashedEmailHex = bytesToHex(hashedEmailBytes);

        return hashedEmailHex;
    }

}
