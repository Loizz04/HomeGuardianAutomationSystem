package homeguardian.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Author: Rawan Genina
 * Student Number: 1196208
 * 
 * Class: User
 * 
 * Description:
 * This class represents a test client that connects to the server
 * and sends commands to control smart home devices (e.g., turn on/off a smart light).
 * Includes proper exception handling to manage connection errors.
 */

public class User {

    public static void main(String[] args) {

        Socket socket = null;
        PrintWriter out = null;

        try {
            // Attempt to connect to the server running on localhost:12345
            socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);

            // ---- Send command to turn on the smart light ----
            out.println("TURN_ON:SmartLight1"); // Example device ID
            System.out.println("Command sent to server: TURN_ON:SmartLight1");

        } catch (UnknownHostException e) {
            System.err.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } finally {
            // ---- Close resources ----
            try {
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
