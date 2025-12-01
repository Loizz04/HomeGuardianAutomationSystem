package homeguardian.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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

/*public class User {

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
}*/


/**
 * Base User Class
 * Represents any user in the Home Guardian system.
 * Extended by HomeAdmin and HomeGuest.
 */
public abstract class User {

    // -------------------------
    // ATTRIBUTES
    // -------------------------
    protected String name;
    protected String username;
    protected String email;
    protected String passwordHash;

    // Each user has a personal activity log list
    protected List<ActivityLog> userLogs;

    // -------------------------
       // CONSTRUCTOR
    // -------------------------
    public User(String name, String username, String email, String passwordHash) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userLogs = new ArrayList<>();
    }

    // -------------------------
    // CORE METHODS
    // -------------------------

    public boolean login(String enteredUsername, String enteredPasswordHash) {
        if (this.username.equals(enteredUsername) &&
            this.passwordHash.equals(enteredPasswordHash)) {

            addUserLog("Login successful.");
            return true;
        }
        addUserLog("Login failed.");
        return false;
    }

    public void logout() {
        addUserLog("User logged out.");
    }

    public boolean signup(HGController controller) {
        controller.addUser(this);
        addUserLog("User signed up.");
        return true;
    }

    public boolean verifyUser(String email) {
        return this.email.equals(email);
    }

    // -------------------------
    // LOGGING SUPPORT
    // -------------------------
    public void addUserLog(String message) {
        ActivityLog log = new ActivityLog(message);
        userLogs.add(log);
    }

    public List<ActivityLog> getUserLogs() {
        return userLogs;
    }

    // -------------------------
    // GETTERS
    // -------------------------
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }

    // Every subclass MUST say their role
    public abstract String getRole();
}
