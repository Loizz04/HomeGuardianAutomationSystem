package homeguardian.system;

import java.util.List;

/**
 * HomeGuardianServerTestDriver
 *
 * Integration-style test driver for the Home Guardian server-side classes.
 *
 * It:
 *  - Creates a HGController and Server
 *  - Creates test Users and Devices
 *  - Issues commands via HGController (simulating client actions)
 *  - Creates Notifications
 *  - Uses ActivityLog
 *
 * Wherever the CLIENT GUI is expected to use a getter, it is marked with:
 *    // CLIENT: uses ...
 *
 * You can share this file with the front-end dev so they know exactly
 * which methods to call.
 */
public class HomeGuardianServerTestDriver {

    public static void main(String[] args) {
        System.out.println("=== Home Guardian Server Test Driver ===");

        // --------------------------------------------------------------------
        // 1. SETUP CONTROLLER + SERVER
        // --------------------------------------------------------------------
        HGController controller = new HGController();

        // NOTE: Adjust port if needed
        Server server = new Server(12345, controller);

        // (Optional) you can actually start the server if you want:
        // server.startServer();
        System.out.println("\n[STEP 1] Controller and Server created.\n");

        // --------------------------------------------------------------------
        // 2. CREATE TEST USERS
        // --------------------------------------------------------------------
        System.out.println("[STEP 2] Creating and registering users...");

        // User is abstract, so we make simple anonymous subclasses for testing.
        User owner = new User("Owner Name", "ownerUser", "owner@example.com", "passHash123") {
            @Override
            public String getRole() { return "HOME_ADMIN"; }
        };

        User guest = new User("Guest Name", "guestUser", "guest@example.com", "passHash456") {
            @Override
            public String getRole() { return "HOME_GUEST"; }
        };

        // Use the signup() method which internally calls controller.addUser(this)
        owner.signup(controller);
        guest.signup(controller);

        // CLIENT: for user info display use:
        //   - user.getUserName()  (friendly display name)
        //   - user.getEmail()
        //   - user.getUsername()  (for IDs / login)
        System.out.println("  Registered users via HGController:");
        List<User> users = controller.getAllUsers();
        for (User u : users) {
            System.out.println("    - " + u.getUserName()
                    + " | username=" + u.getUsername()
                    + " | email=" + u.getEmail());
        }

        // --------------------------------------------------------------------
        // 3. CREATE TEST DEVICES
        // --------------------------------------------------------------------
        System.out.println("\n[STEP 3] Creating and registering devices...");

        // Using base Device class; subclasses like SmartLight, Alarm, etc.
        // can be used the same way as long as they extend Device.
        Device livingRoomLight = new Device("D001", "Living Room Light");
        Device bedroomLight    = new Device("D002", "Bedroom Light");
        Device frontDoorCam    = new Device("D003", "Front Door Camera");
        Device mainAlarm       = new Device("D004", "Main Alarm");

        controller.addDevice(livingRoomLight);
        controller.addDevice(bedroomLight);
        controller.addDevice(frontDoorCam);
        controller.addDevice(mainAlarm);

        // CLIENT: to display devices, use:
        //   - device.getDeviceID()
        //   - device.getDeviceName()
        //   - device.connectionStatus()   (true = ON/connected, false = OFF)
        System.out.println("  Registered devices via HGController:");
        List<Device> devices = controller.getAllDevices();
        for (Device d : devices) {
            System.out.println("    - " + d.getDeviceName()
                    + " | ID=" + d.getDeviceID()
                    + " | connected=" + d.connectionStatus());
        }

        // --------------------------------------------------------------------
        // 4. SIMULATE CLIENT ACTIONS ON DEVICES (via HGController.controlDevice)
        // --------------------------------------------------------------------
        System.out.println("\n[STEP 4] Simulating client actions on devices...");

        // In your Server.handleMessageFromClient, you use:
        //   controller.controlDevice(deviceId, "ON"/"OFF"/"LOCK"/"UNLOCK")
        //
        // So here we exercise the same path directly.

        // Example 1: Client turns ON the living room light
        controller.controlDevice(livingRoomLight.getDeviceID(), "ON");

        // Example 2: Client turns OFF the bedroom light
        controller.controlDevice(bedroomLight.getDeviceID(), "OFF");

        // Example 3: Client "LOCKS" the main alarm (interpret as armed)
        controller.controlDevice(mainAlarm.getDeviceID(), "LOCK");

        // Example 4: Client "UNLOCKS" (disarms) the main alarm
        controller.controlDevice(mainAlarm.getDeviceID(), "UNLOCK");

        System.out.println("\n  Device states after commands (as seen by server):");
        devices = controller.getAllDevices();
        for (Device d : devices) {
            // CLIENT: after a SYNC or refresh, it should again use:
            //   - getDeviceName()
            //   - getDeviceID()
            //   - connectionStatus()
            System.out.println("    - " + d.getDeviceName()
                    + " | ID=" + d.getDeviceID()
                    + " | connected=" + d.connectionStatus());
        }

        // --------------------------------------------------------------------
        // 5. SIMULATE NOTIFICATIONS (via Notification + HGController)
        // --------------------------------------------------------------------
        System.out.println("\n[STEP 5] Simulating notifications...");

        // Controller-based notifications (what server would typically use)
        controller.notifyUser(owner, "Motion detected at the Front Door Camera.");
        controller.notifyUser(guest, "Your Bedroom Light was turned OFF.");
        controller.notifyEmergencyServices("Main Alarm has been triggered!");

        // CLIENT: when listing notifications, use:
        //   - notif.getNotificationID()
        //   - notif.getRecipient()  (may be null for SYSTEM/emergency)
        //   - notif.getMessage()
        //   - notif.getEmail()      (for contact info)
        //   - notif.getTimestampFormatted()
        List<Notification> notifications = controller.getAllNotifications();
        System.out.println("  Notifications created via HGController:");
        for (Notification n : notifications) {
            String recipientName =
                    (n.getRecipient() != null) ? n.getRecipient().getUserName() : "SYSTEM / GLOBAL";

            System.out.println("    - ID=" + n.getNotificationID()
                    + " | To=" + recipientName
                    + " | Email=" + n.getEmail()
                    + " | Time=" + n.getTimestampFormatted()
                    + " | Msg=\"" + n.getMessage() + "\"");
        }

        // Also directly test Notification.sendAlert()
        System.out.println("\n  Testing sendAlert() for first notification (if exists):");
        if (!notifications.isEmpty()) {
            Notification first = notifications.get(0);
            first.sendAlert();
        }

        // --------------------------------------------------------------------
        // 6. ACTIVITY LOGS (GLOBAL, PER-USER, PER-DEVICE)
        // --------------------------------------------------------------------
        System.out.println("\n[STEP 6] Activity logs overview...");

        // HGController also logs via logActivity(String)
        controller.logActivity("Manual controller log from TestDriver.");

        // CLIENT (for an Activity Log table) should typically show:
        //   - log.getDeviceName()        (if you logged that)
        //   - log.getActionType()        (depends on your design)
        //   - log.getFormattedDateTime() (from ActivityLog class)
        //
        // But since ActivityLog is quite flexible, you can decide the exact fields.

        // A) Logs maintained inside HGController
        System.out.println("  HGController-managed logs:");
        for (ActivityLog log : controller.getAllLogs()) {
            System.out.println("    - [ControllerLog] msg=" + log.getMessage()
                    + " | datetime=" + log.getFormattedDateTime());
        }

        // B) Global static logs from ActivityLog itself
        System.out.println("\n  Global ActivityLog entries (static store):");
        List<ActivityLog> allLogs = ActivityLog.getAllLogs();
        for (ActivityLog log : allLogs) {
            System.out.println("    - LOGID=" + log.getLogID()
                    + " | user=" + log.getUser()
                    + " | deviceName=" + log.getDeviceName()
                    + " | actionType=" + log.getActionType()
                    + " | deviceID=" + log.getDeviceID()
                    + " | msg=" + log.getMessage()
                    + " | datetime=" + log.getFormattedDateTime());
        }

        // C) Per-user logs
        System.out.println("\n  Per-user logs (from User.getUserLogs()):");
        for (User u : users) {
            System.out.println("    User: " + u.getUserName());
            for (ActivityLog log : u.getUserLogs()) {
                System.out.println("      - " + log.getMessage()
                        + " @ " + log.getFormattedDateTime());
            }
        }

        // D) Per-device logs
        System.out.println("\n  Per-device logs (from Device.getDeviceLogs()):");
        for (Device d : devices) {
            System.out.println("    Device: " + d.getDeviceName());
            for (ActivityLog log : d.getDeviceLogs()) {
                System.out.println("      - " + log.getMessage()
                        + " @ " + log.getFormattedDateTime());
            }
        }

        // --------------------------------------------------------------------
        // 7. OPTIONAL: BASIC SERVER FUNCTION TESTS
        // --------------------------------------------------------------------
        System.out.println("\n[STEP 7] Testing basic Server lifecycle (start/stop)...");

        // This just calls serverStarted/serverStopped hooks and listen()/close().
        // If you don't want networking, you can comment these out.
        server.startServer();
        server.stopServer();

        System.out.println("\n[TEST DRIVER COMPLETE] See comments for client-side getter usage.\n");
    }
}