package homeguardian.system;


/**
 * Author: Rawan Genina
 * Student Number: 1196208
 * 
 */

import java.util.List;

public class HomeAdmin extends User {

    // -------------------------
    // ATTRIBUTES
    // -------------------------
    private boolean isPrimaryAdmin;

    // -------------------------
    // CONSTRUCTOR
    // -------------------------
    public HomeAdmin(String name, String username, String email, String passwordHash, boolean isPrimaryAdmin) {
        super(name, username, email, passwordHash);
        this.isPrimaryAdmin = isPrimaryAdmin;
    }

    // -------------------------
    // OPERATIONS
    // -------------------------

    public HomeGuest createGuest(String name, String username, String email, String passwordHash) {
        return new HomeGuest(name, username, email, passwordHash);
    }

    public void assignDevice(HGController controller, HomeGuest guest, Device device) {
        guest.addAccessibleDevice(device);
        addUserLog("Assigned device " + device.getDeviceName() + " to guest " + guest.getUsername());
    }

    public void revokeAccess(HGController controller, HomeGuest guest, Device device) {
        guest.removeAccessibleDevice(device);
        addUserLog("Revoked access to device " + device.getDeviceName() + " from guest " + guest.getUsername());
    }

    public void createGuestLockPasscode(HomeGuest guest, String passcode) {
        guest.setGuestLockPasscode(passcode);
        addUserLog("Set guest lock passcode for " + guest.getUsername());
    }

    // -------------------------
    // GETTERS / SETTERS
    // -------------------------
    public boolean isPrimaryAdmin() {
        return isPrimaryAdmin;
    }

    public void setPrimaryAdmin(boolean primaryAdmin) {
        isPrimaryAdmin = primaryAdmin;
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}

