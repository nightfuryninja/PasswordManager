package encryptionmanager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationMethods {
    
    public static boolean isEmailValid(String email) {
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        boolean isValid = matcher.matches();
        
        return isValid;
    }
    
    public static boolean isPasswordValid(char[] password) {
        String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!\"£$€%^&*()@#/?]).{8,})"; // Password must contain at least one digit, at least one lower case letter, at least one upper case letter, at least one special character and must be at least 8 characters long.
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(new String(password));
        boolean isValid = matcher.matches();
        
        return isValid;
    }
    
}
