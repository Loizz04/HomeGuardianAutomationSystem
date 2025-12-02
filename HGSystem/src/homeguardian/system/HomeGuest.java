package homeguardian.system;

/**
 * Author: Rawan Genina
 * Student Number: 1196208
 * 
 * Class: HomeGuest
 * 
 * Description:
 * The HomeGuest class represents a guest user within the HomeGuardian smart
 * home system. Unlike HomeAdmin, a guest has limited permissions and may only
 * interact with devices that have been explicitly assigned to them by an admin.
 * 
 * This class extends the User base class and adds functionality specific to
 * guest accounts, including:
 * 
 *  - Storing a list of accessible devices
 *  - Allowing login attempts through the inherited authentication system
 *  - Maintaining an optional guest-specific lock passcode for SmartLock devices
 *  - Supporting logs of guest actions through the base User activity log
 * 
 * A guest cannot manage other users or modify system-level configurations. 
 * Their capabilities are restricted to using assigned devices and interacting
 * with the smart home environment within the permissions granted by admins.
 */

import java.util.ArrayList;
import java.util.List;

public class HomeGuest extends User {

    // -------------------------
    // ATTRIBUTES
    // -------------------------
    private final List<Device> accessibleDevices; // Devices this guest can use
    private String guestLockPasscode;            // Optional passcode for SmartLocks

    // -------------------------
    // CONSTRUCTOR
    // -------------------------
    public HomeGuest(String name, String username, String email, String passwordHash) {
        super(name, username, email, passwordHash);
        this.accessibleDevices = new ArrayList<>();
    }

    // -------------------------
    // OPERATIONS
    // -------------------------
    public boolean loginGuest(String username, String passwordHash) {
        if (login(username, passwordHash)) {
            addUserLog("Guest logged in.");
            return true;
        } else {
            addUserLog("Guest login failed.");
            return false;
        }
    }

    public void addAccessibleDevice(Device device) {
        if (!accessibleDevices.contains(device)) {
            accessibleDevices.add(device);
        }
    }

    public void removeAccessibleDevice(Device device) {
        accessibleDevices.remove(device);
    }

    public void setGuestLockPasscode(String passcode) {
        this.guestLockPasscode = passcode;
    }

    // -------------------------
    // GETTERS
    // -------------------------
    public List<Device> getAccessibleDevices() {
        return accessibleDevices;
    }

    public String getGuestLockPasscode() {
        return guestLockPasscode;
    }

    @Override
    public String getRole() {
        return "Guest";
    }
}

