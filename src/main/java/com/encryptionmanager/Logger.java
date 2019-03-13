package com.encryptionmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public static void debug(String debugData) {
        System.out.println(debugData);
        writeToLog(debugData);
    }

    public static void error(String errorData, Exception e) {
        System.out.println(errorData);
        writeToLog(errorData);
    }
    
    //Writes data to a log file.
    private static void writeToLog(String data) {
        try {
            //All log files will be stored in the users temporary directory.
            File logFile = new File(System.getProperty("java.io.tmpdir") + "\\PasswordManager\\");
            logFile.mkdir(); //Makes directory if it doesn't exist.
            FileOutputStream out = new FileOutputStream(logFile + "\\" + getDateTime() + ".txt", false);
            out.write(data.getBytes());
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println("That file could not be found.");
        } catch(IOException ex){
            System.out.println("An IO exception occured when trying to write a crash dump.");
        }
    }

    //Returns the current date and time as a string.
    private static String getDateTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        return formatter.format(date);
    }

}
