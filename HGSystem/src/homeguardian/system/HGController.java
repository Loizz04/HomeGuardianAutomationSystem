package homeguardian.system;

// Author: Nosizo Mabuza
// Revised by: Rawan Genina (added getAllNotifications() and getAllDevices() methods)
// Date Created: Nov 1, 2025
// Date Revised: Nov 28, 2025
// Description: Controller class for managing devices, users, activity logs, and notifications
// in the Home Guardian system.

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HGController {

    private final List<ActivityLog> activityLogs;
    private final List<Notification> notifications;
    private final List<Device> deviceList; 
    private final List<User> userList;

    // -------------------- CONSTRUCTOR --------------------
    public HGController() {
        this.activityLogs = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.deviceList = new ArrayList<>();
        this.userList = new ArrayList<>();
    }

    // -------------------- DEVICE REGISTRATION --------------------
    /**
     * Adds a new device if not already present.
     * Added for test driver testing.
     */
    public void addDevice(Device device) {
        if (device != null && !deviceList.contains(device)) {
            deviceList.add(device);
            logActivity("New device added: " + device.getDeviceName() + " (" + device.getDeviceID() + ")");
        }
    }

    // -------------------- DEVICE CONTROL --------------------
    /**
     * Control a device using its ID and a command string (ON, OFF, LOCK, UNLOCK)
     */
    public boolean controlDevice(String deviceID, String command) {
        Optional<Device> deviceOpt = findDeviceByID(deviceID);
        if (deviceOpt.isEmpty()) {
            logActivity("Device with ID " + deviceID + " can't be found.");
            return false;
        }

        Device device = deviceOpt.get();
        boolean success = device.handleCommand(command);
        String statusMessage = success 
            ? "Command '" + command + "' executed on device " + deviceID
            : "Failed to execute command '" + command + "' on device " + deviceID;
        logActivity(statusMessage);
        return success;
    }

    private Optional<Device> findDeviceByID(String deviceID) {
        return deviceList.stream()
                .filter(d -> d.getDeviceID().equals(deviceID))
                .findFirst();
    }

    // -------------------- USER MANAGEMENT --------------------
    public void addUser(User user) {
        if (user != null && !userList.contains(user)) {
            userList.add(user);
        }
    }

    public boolean removeUser(User user) {
        if (user == null) return false;
        return userList.remove(user);
    }

    public List<User> getAllUsers() {
        return Collections.unmodifiableList(userList);
    }

    // -------------------- ACTIVITY LOGGING --------------------
    public void logActivity(String message) {
        ActivityLog log = new ActivityLog(message);
        activityLogs.add(log);
    }

    public List<ActivityLog> getAllLogs() {
        return Collections.unmodifiableList(activityLogs);
    }

    // -------------------- NOTIFICATIONS --------------------
    public void notifyUser(User user, String message) {
        if (user == null || message == null || message.isBlank()) return;
        Notification notification = new Notification(user, message);
        notifications.add(notification);
    }

    public void notifyEmergencyServices(String message) {
        Notification emergencyNotification = new Notification(null, "[EMERGENCY]" + message);
        notifications.add(emergencyNotification);
        logActivity("Emergency services notified: " + message);
        System.out.println("Emergency services notified: " + message);
    }

    // -------------------- NEW METHODS ADDED BY RAWAN --------------------
    // Returns an unmodifiable list of all notifications
    public List<Notification> getAllNotifications() {
        return Collections.unmodifiableList(notifications);
    }

    // Returns an unmodifiable list of all devices
    public List<Device> getAllDevices() {
        return Collections.unmodifiableList(deviceList);
    }
}
