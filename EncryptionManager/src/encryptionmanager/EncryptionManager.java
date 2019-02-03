package encryptionmanager;

import java.util.Scanner;

public class EncryptionManager {

    //TODO LIST:
    //1) Create option for password to contain different characters.
    //2) Store passwords within an encrypted database.
    //3) Create a pretty GUI that looks all fancy and modern.
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EncryptionManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EncryptionManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EncryptionManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EncryptionManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //Keep the GUI in another thread to stop blocking.
        Thread GUIThread = new Thread(){
            @Override
            public void run(){
                GUI gui = new GUI();
                gui.setVisible(true);
            }
        };
        GUIThread.run();
        
    }
    
}
