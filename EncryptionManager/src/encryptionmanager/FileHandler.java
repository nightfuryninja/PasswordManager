package encryptionmanager;

import java.io.File;
import java.nio.file.Files;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class FileHandler {

    private static FileOutputStream outputStream;

    //Writes to a binary file.
    public static void WriteToFile(String pathName, byte[] data) {
        try {
            outputStream = new FileOutputStream(pathName);
            outputStream.write(data);
        } catch (FileNotFoundException ex) {
            System.out.println("The file could be found.");
        } catch (IOException ex) {
            System.out.println("There was an error with the file. IO Exception.");
        } finally {
            try {
                outputStream.close();
            } catch (IOException ex) {
                System.out.println("There was an error with the file. IO Exception.");
            }
        }
    }

    //Reads from a binary file.
    public static byte[] ReadFromFile(String pathName) {
        try {
            byte[] data = Files.readAllBytes(new File(pathName).toPath());
            return data;
        } catch (FileNotFoundException ex) {
            System.out.println("The file was not found.");
            return null;
        } catch (IOException ex) {
            System.out.println("There was an IO Exception.");
            return null;
        }
    }

}
