package emailclient;

import java.io.*;
import java.net.Socket;

/**
 * A basic SMTP client that connects to an SMTP server
 * and sends a plain-text email using socket communication.
 */
public class Client {
    private String host;
    private int port;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private boolean connected = false;

    /**
     * Constructor - connects to the given SMTP server and port.
     * @param host SMTP server host (e.g. "localhost")
     * @param port SMTP server port (e.g. 25 or 1025)
     */
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        connect(); // try to open the connection immediately
    }

    /**
     * Establishes a connection to the SMTP server
     * and waits for a 220 greeting from the server.
     */
    private void connect() {
        try {
            // Open connection to server
            socket = new Socket(host, port);

            // Setup input/output communication
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Wait for server greeting (220)
            checkServerResponse(220);
            connected = true;
        } catch (IOException e) {
            showError("Failed to connect to SMTP server: " + e.getMessage());
        }
    }

    /**
     * Sends the full email (sender, recipient, body)
     * using SMTP protocol commands.
     */
    public void sendEmail(String from, String to, String message) {
        if (!connected) {
            showError("Not connected to SMTP server.");
            return;
        }

        try {
            // Send greeting and commands according to SMTP rules
            sendCommand("HELLO ", 250);//input customizable
            sendCommand("MAIL FROM:<" + from + ">", 250);
            sendCommand("RCPT TO:<" + to + ">", 250);
            sendCommand("DATA", 354);

            writer.write("Subject: SMTP Test\r\n");
            writer.write("From: " + from + "\r\n");
            writer.write("To: " + to + "\r\n");
            writer.write("\r\n");

            String formattedMessage = message.replaceAll("(?<!\r)\n", "\r\n");
            writer.write(formattedMessage);
            writer.write("\r\n.\r\n");
            writer.write("\r\n.\r\n");
            writer.flush();


            // Expect success message after sending body
            checkServerResponse(250);

            // Close the connection properly
            close();
        } catch (IOException e) {
            
        }
    }

    /**
     * Sends a command to the SMTP server and checks the response code.
     * @param command The SMTP command.
     * @param expectedCode The expected response code from the server.
     */
    private void sendCommand(String command, int expectedCode) {
        try {
            writer.write(command + "\r\n");
            writer.flush();

            String response = reader.readLine();

            // If server response is missing or wrong code
            if (response == null || !response.startsWith("" + expectedCode)) {
                showError("Unexpected response to '" + command + "': " + response);
            }
        } catch (IOException e) {
            showError("Error sending command: " + command + " → " + e.getMessage());
        }
    }

    /**
     * Reads the server response and checks that it starts with the expected code.
     * @param expectedCode The expected 3-digit SMTP response code.
     */
    private void checkServerResponse(int expectedCode) {
        try {
            String response = reader.readLine();

            if (response == null || !response.startsWith("" + expectedCode)) {
                showError("Expected code " + expectedCode + ", got: " + response);
            }
        } catch (IOException e) {
            showError("Error reading server response: " + e.getMessage());
        }
    }

    /**
     * Closes the connection and all input/output streams.
     */
    private void close() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            showError("Error closing connection: " + e.getMessage());
        }
    }

    /**
     * Displays an error message to the console.
     * @param message The message to show
     */
    private void showError(String message) {
        System.err.println("SMTP ERROR " + message);
    }
}

