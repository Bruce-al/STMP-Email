import emailclient.EmailApp;

/**
 * Entry point for the SMTP Email Client application.
 * Launches the JavaFX GUI defined in the EmailApp class.
 * 
 */
public class Main {
    /**
     * The main method that starts the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        EmailApp.launch(EmailApp.class);
    }
}

