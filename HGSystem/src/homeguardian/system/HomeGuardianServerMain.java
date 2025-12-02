package homeguardian.system;

import java.util.List;

/**
 * Author: Rawan Genina
 * Revised by: Nosizo Mabuza
 * Student Number: 1196208
 *
 * Class: HomeGuardianServerMain
 *
 * Description:
 * Main entry point for the Home Guardian system. 
 * Initializes the HGController, registers all smart-home devices 
 * (Light, Lock, Alarm, Camera), creates admin and guest users, 
 * and starts the server to listen for incoming client commands.
 */


public class HomeGuardianServerMain {

    public static void main(String[] args) {

        System.out.println("=== Home Guardian Server Starting ===");

        // ------------------------------
        // 1. Create the controller
        // ------------------------------
        HGController controller = new HGController();

        // ------------------------------
        // 2. Create and register devices
        // ------------------------------
        SmartLight livingLight = new SmartLight("D001", "Living Room Light");
        SmartLock doorLock = new SmartLock("D002", "Front Door Lock");
        Alarm alarm = new Alarm("D003", "Home Alarm");
        SecurityCamera camera = new SecurityCamera("D004", "Door Camera");

        controller.addDevice(livingLight);
        controller.addDevice(doorLock);
        controller.addDevice(alarm);
        controller.addDevice(camera);

        System.out.println("[SETUP] All devices registered.");

        // ------------------------------
        // 3. Create and register users
        // ------------------------------
        HomeAdmin admin = new HomeAdmin(
                "Admin User", "admin1", "admin@example.com", "hash123", true);

        HomeGuest guest = new HomeGuest(
                "Guest User", "guest1", "guest@example.com", "hash456");

        admin.signup(controller);
        guest.signup(controller);

        System.out.println("[SETUP] Users registered.");

        // ------------------------------
        // 4. Start the server (LISTEN)
        // ------------------------------
        int PORT = 12345;
        Server server = new Server(PORT, controller);

        System.out.println("[SERVER] Starting server on port " + PORT + "...");
        server.startServer(); // <---- THIS makes the server listen for clients

        System.out.println("=== Home Guardian Server is now running ===");
    }
}
