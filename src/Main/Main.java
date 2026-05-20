package Main;

import View.LoginView;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Jalankan GUI di Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Set look and feel sesuai sistem
                    javax.swing.UIManager.setLookAndFeel(
                        javax.swing.UIManager.getSystemLookAndFeelClassName()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                // Tampilkan form login
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
            }
        });
    }
}