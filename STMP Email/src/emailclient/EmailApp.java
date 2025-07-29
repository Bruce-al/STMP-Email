package emailclient;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * A simple JavaFX GUI for composing and sending emails
 * using a custom SMTP client (SmtpClient).
 */
public class EmailApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SMTP Email Client");

        // Labels and text fields for host and port
        Label hostLabel = new Label("Host:");
        TextField hostField = new TextField(" "); // Default: localhost

        Label portLabel = new Label("Port:");
        TextField portField = new TextField(" "); // Default SMTP port

        // Labels and text fields for sender and recipient
        Label senderLabel = new Label("Sender:");
        TextField senderField = new TextField(" ");//enter custom

        Label recipientLabel = new Label("Recipient:");
        TextField recipientField = new TextField(" "); enter custom

        // Label and text area for the message content
        Label messageLabel = new Label("Message:");
        TextArea messageArea = new TextArea();

        // Button to trigger the email sending
        Button sendButton = new Button("Send");
        
        // Bonus >> attachment button 
        Button attachButton = new Button("Attach File");
        Label attachmentLabel = new Label("No file selected");
        File[] attachedFile = new File[1]; // Holder for selected file
        
        // ----- EVENT HANDLER -----
        attachButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose File to Attach");
            fileChooser.getExtensionFilters().add(
            	    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            	);
            File selected = fileChooser.showOpenDialog(primaryStage);

            if (selected != null) {
                attachedFile[0] = selected;
                attachmentLabel.setText("Attached: " + selected.getName());
            } else {
                attachmentLabel.setText("No file selected");
            }
        });

     // This runs when the user clicks the "Send" button
        sendButton.setOnAction(e -> {
            // Collect input from text fields
            String host = hostField.getText();
            int port = Integer.parseInt(portField.getText());
            String sender = senderField.getText();
            String recipient = recipientField.getText();
            String message = messageArea.getText();
         
            
            if (attachedFile[0] != null) {
                try {
                    String fileContent = Files.readString(attachedFile[0].toPath(), StandardCharsets.UTF_8);
                    message += "\n\n--- Attachment: " + attachedFile[0].getName() + " ---\n" + fileContent;
                } catch (Exception ex) {
                    message += "\n\n[Attachment failed to load: " + ex.getMessage() + "]";
                }
            }
            
            try {
                // Create and use the SMTP client
                Client client = new Client(host, port);
                client.sendEmail(sender, recipient, message);

                // Show confirmation message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Email sent successfully!");
                successAlert.showAndWait();

            } catch (Exception ex) {
                // Show error message if something fails
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Failed to send email: " + ex.getMessage());
                errorAlert.showAndWait();
            }
        });

        // ----- LAYOUT SETUP -----

        GridPane grid = new GridPane();
        grid.setVgap(10); // Vertical spacing between rows
        grid.setHgap(10); // Horizontal spacing between columns

        // Add all labels and input fields to the grid layout
        grid.add(hostLabel, 0, 0);
        grid.add(hostField, 1, 0);

        grid.add(portLabel, 0, 1);
        grid.add(portField, 1, 1);

        grid.add(senderLabel, 0, 2);
        grid.add(senderField, 1, 2);

        grid.add(recipientLabel, 0, 3);
        grid.add(recipientField, 1, 3);

        grid.add(messageLabel, 0, 4);
        grid.add(messageArea, 1, 4);

        grid.add(sendButton, 1, 5); // Add Send button
        grid.add(attachButton, 0, 5);
        grid.add(attachmentLabel, 3, 5);

        // ----- DISPLAY -----

        Scene scene = new Scene(grid, 400, 400); // Create window
        primaryStage.setScene(scene);           // Attach scene to stage
        primaryStage.show();                    // Show the GUI
    }
}

