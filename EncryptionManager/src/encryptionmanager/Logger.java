package encryptionmanager;

public class Logger {
    
    public void debug(String debugData){
        System.out.println(debugData);
        
    }
    
    public void error(String errorData, Exception e){
        System.out.println(errorData);
    }
    
    private void writeToLog(){
        
    }
    
}