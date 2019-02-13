package encryptionmanager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author night
 */
public class FileHandler {

    //Read a binary file and returns the bytes.
    public static byte[] readBinaryFile(String path) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(path);
            data = new byte[inputStream.available()];
            inputStream.read(data);
        } catch (FileNotFoundException ex) {
            System.out.println("The file could not be found.");
        } catch (IOException ex) {
            System.out.println("There was a problem reading from the file.");
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                System.out.println("There was a problem closing the stream.");
            } catch (NullPointerException ex) {
                System.out.println("We cannot close an input stream that was never initizalised.");
            }
            return data;
        }
    }

    //Writes bytes to a binary file.
    public static void writeBinaryFile(String path, byte[] data) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(path);
            outputStream.write(data);
        } catch (FileNotFoundException ex) {
            System.out.println("The file could not be found.");
        } catch (IOException ex) {
            System.out.println("There was an IO exception.");
        } finally {
            try {
                outputStream.close();
            } catch (IOException ex) {
                System.out.println("There was an IO exception when trying to close the stream.");
            }
        }
    }

    public static OutputStream writeBinaryFile(String path) {
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            return outputStream;
        } catch (FileNotFoundException ex) {
            System.out.println("Sorry, we could not find that file.");
            return null;
        }
    }
}
