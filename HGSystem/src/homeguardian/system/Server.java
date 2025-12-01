package homeguardian.system;

/**
 * Author: Rawan Genina
 * Student Number: 1196208
 * 
 * Class: Server
 * 
 * Description:
 * This class extends AbstractServer and implements a concrete server
 * for handling smart home device commands. It interacts with HGController
 * to control devices and manage client connections.
 */

public class Server extends AbstractServer {

    private HGController controller; // Reference to the controller managing devices

    /**
     * Constructor initializes the server with a port and controller
     * @param port Port number for server to listen on
     * @param controller Reference to HGController for device management
     */
    public Server(int port, HGController controller) {
        super(port);          // Call parent constructor
        this.controller = controller;
    }

    /**
     * Start the server and begin listening for client connections
     */
    public void startServer() {
        try {
            listen(); // AbstractServer method that starts the listener thread
        } catch (Exception e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }

    /**
     * Stop the server and close all client connections
     */
    public void stopServer() {
        close(); // AbstractServer method to stop and clean up clients
    }

    /**
     * Handle messages received from clients
     * @param msg Message from the client
     * @param client ConnectionToClient object representing the sender
     */
    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        if (!(msg instanceof String)) {
            client.sendToClient("ERROR: Unsupported message type");
            return;
        }

        String message = (String) msg;

        // ---- TURN ON SmartLight ----
        if (message.startsWith("TURN_ON:")) {
            String deviceId = message.substring(8);
            boolean success = controller.controlDevice(deviceId, "ON"); // call controller
            client.sendToClient("TURN_ON:" + deviceId + ":" + success); // response to client
            sendToAllClients("SYNC:" + deviceId); // update all clients
            return;
        }

        // ---- TURN OFF SmartLight ----
        if (message.startsWith("TURN_OFF:")) {
            String deviceId = message.substring(9);
            boolean success = controller.controlDevice(deviceId, "OFF");
            client.sendToClient("TURN_OFF:" + deviceId + ":" + success);
            sendToAllClients("SYNC:" + deviceId);
            return;
        }

        // ---- SYNC all devices ----
        if (message.equals("SYNC")) {
            client.sendToClient(controller.getAllDevices()); // send current device states
            return;
        }

        // Unknown command
        client.sendToClient("ERROR: Unknown command");
    }

    /**
     * Called when server has successfully started
     */
    @Override
    protected void serverStarted() {
        System.out.println("Server started on port " + getPort());
    }

    /**
     * Called when server has stopped
     */
    @Override
    protected void serverStopped() {
        System.out.println("Server stopped.");
    }
}
