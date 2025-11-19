package homeguardian.system;

import java.util.ArrayList;
import java.util.List;

public class SmartLock extends Device {
	
	// -------------------------
    // ATTRIBUTES
    // -------------------------
    private boolean isLocked;
    private boolean isEnabled;
    private int lockDuration;      // how long (sec) the lock stays locked (optional use)
    private int unlockDuration;    // how long (sec) the door stays unlocked (optional use)

    // Extra helpful attributes
    private String ownerPasscode;
    private final List<String> guestPasscodes;
    private String linkedAlarmId;  // alarm this lock is tied to

    // -------------------------
    // CONSTRUCTOR
    // -------------------------
    public SmartLock(String deviceId, String deviceName) {
        super(deviceId, deviceName);
        this.isLocked = true;
        this.isEnabled = true;
        this.lockDuration = 0;
        this.unlockDuration = 0;
        this.ownerPasscode = "0000"; // default, can be changed with managePasscode()
        this.guestPasscodes = new ArrayList<>();
        this.linkedAlarmId = null;

        addLog("SmartLock created. Initial state: LOCKED & ENABLED.");
    }

    // -------------------------
    // CORE METHODS (requested)
    // -------------------------

    /**
     * Arms the lock (enables it and locks the door).
     */
    public boolean armLock() {
        if (!isEnabled) {
            isEnabled = true;
        }
        isLocked = true;
        addLog("SmartLock armed and locked.");
        notifyEvents("SmartLock armed.");
        return true;
    }

    /**
     * Disarms the lock (disables security functions, optional unlock).
     */
    public boolean disarmLock() {
        isEnabled = false;
        addLog("SmartLock disarmed.");
        notifyEvents("SmartLock disarmed.");
        return true;
    }

    /**
     * Placeholder for passcode management logic.
     * In a real UI, this would open a settings screen.
     */
    public void managePasscode() {
        addLog("Passcode management accessed.");
        // This could later call methods like setOwnerPasscode(...) or manage guest codes
    }

    /**
     * Unlocks the door with a guest passcode.
     */
    public boolean unlockWithGuestPasscode(String guestPasscode) {
        if (!isEnabled) {
            notifyEvents("Attempt to use guest passcode while lock is disabled.");
            return false;
        }

        if (guestPasscodes.contains(guestPasscode)) {
            isLocked = false;
            addLog("Door unlocked with guest passcode.");
            notifyEvents("Door unlocked using guest access.");
            return true;
        } else {
            addLog("Invalid guest passcode attempt.");
            notifyEvents("Invalid guest passcode used.");
            return false;
        }
    }

    /**
     * Sets how long the lock should remain locked (optional behaviour).
     */
    public void setLockDuration(int duration) {
        if (duration < 0) {
            notifyEvents("Lock duration cannot be negative.");
            return;
        }
        this.lockDuration = duration;
        addLog("Lock duration set to " + duration + " seconds.");
    }

    /**
     * Sets how long the lock should remain unlocked (optional behaviour).
     */
    public void setUnlockDuration(int duration) {
        if (duration < 0) {
            notifyEvents("Unlock duration cannot be negative.");
            return;
        }
        this.unlockDuration = duration;
        addLog("Unlock duration set to " + duration + " seconds.");
    }

    /**
     * Links this lock to an alarm (by alarm ID) for intrusion response.
     */
    public void linkToAlarm(String alarmId) {
        this.linkedAlarmId = alarmId;
        addLog("SmartLock linked to Alarm with ID: " + alarmId);
        notifyEvents("Lock linked to Alarm: " + alarmId);
    }

    /**
     * Notifies emergency services when forced entry is detected.
     * (For now this just logs; ServerController can listen or wrap this later.)
     */
    public void contactEmergencyServices() {
        String message = "Forced entry detected at lock " + getDeviceName()
                + " (ID: " + getDeviceID() + "). Contacting emergency services.";
        addLog(message);
        notifyEvents(message);
        // Later: call ServerController.notifyEmergencyServices(message);
    }

    // -------------------------
    // EXTRA HELPER METHODS
    // -------------------------

    public void lock() {
        isLocked = true;
        addLog("Door locked.");
        notifyEvents("Door locked.");
    }

    public void unlock() {
        isLocked = false;
        addLog("Door unlocked.");
        notifyEvents("Door unlocked.");
    }

    public void setOwnerPasscode(String newPasscode) {
        this.ownerPasscode = newPasscode;
        addLog("Owner passcode updated.");
    }

    public void addGuestPasscode(String guestCode) {
        guestPasscodes.add(guestCode);
        addLog("Guest passcode added.");
    }

    public void removeGuestPasscode(String guestCode) {
        guestPasscodes.remove(guestCode);
        addLog("Guest passcode removed.");
    }

    // -------------------------
    // GETTERS
    // -------------------------
    public boolean isLocked() { return isLocked; }
    public boolean isEnabled() { return isEnabled; }
    public int getLockDuration() { return lockDuration; }
    public int getUnlockDuration() { return unlockDuration; }
    public String getLinkedAlarmId() { return linkedAlarmId; }

    // -------------------------
    // COMMAND HANDLING (optional)
    // -------------------------
    @Override
    public boolean handleCommand(String command) {
        switch (command.toUpperCase()) {
            case "LOCK":
                lock();
                return true;
            case "UNLOCK":
                unlock();
                return true;
            case "ARM":
                return armLock();
            case "DISARM":
                return disarmLock();
            default:
                return super.handleCommand(command);
        }
    }
	

}
