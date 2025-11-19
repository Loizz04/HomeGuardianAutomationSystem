package homeguardian.system;

public class Alarm extends Device{
	
	// -------------------------
    // ATTRIBUTES
    // -------------------------
    private boolean isArmed;        // TRUE when alarm is ready to trigger
    private boolean isEnabled;      // TRUE when alarm system is active
    private boolean connectedTMS;   // TRUE if linked to a motion sensor

    private String linkedCameraId;  // Optional – triggers camera recording when alarm activates

    // -------------------------
    // CONSTRUCTOR
    // -------------------------
    public Alarm(String deviceID, String deviceName) {
        super(deviceID, deviceName);
        this.isArmed = false;
        this.isEnabled = true;  // Enabled by default
        this.connectedTMS = false;
        this.linkedCameraId = null;

        addLog("Alarm created. Status: Enabled & Disarmed.");
    }

    // -------------------------
    // CORE METHODS
    // -------------------------

    /** Enables the alarm system */
    public boolean enableAlarm() {
        if (!isEnabled) {
            isEnabled = true;
            notifyEvents("Alarm enabled.");
        }
        isArmed = true;
        addLog("Alarm armed.");
        return true;
    }

    /** Disarms the alarm but keeps system active */
    public boolean disableAlarm() {
        isArmed = false;
        notifyEvents("Alarm disarmed.");
        addLog("Alarm system still enabled, but disarmed.");
        return true;
    }

    /** Completely turns off alarm system */
    public void powerOffAlarm() {
        isEnabled = false;
        isArmed = false;
        notifyEvents("Alarm system completely disabled.");
        addLog("Alarm disabled and powered off.");
    }

    /** Links Motion Sensor to alarm */
    public void enableMS() {
        connectedTMS = true;
        notifyEvents("Motion sensor successfully linked to Alarm.");
        addLog("TMS connection enabled.");
    }

    /** Unlinks Motion Sensor from alarm */
    public void disableMS() {
        connectedTMS = false;
        notifyEvents("Motion sensor unlinked from Alarm.");
        addLog("TMS connection disabled.");
    }

    // -------------------------
    // CAMERA TRIGGER
    // -------------------------

    /** Connects alarm to a camera for triggered recording */
    public void linkCamera(String cameraId) {
        this.linkedCameraId = cameraId;
        addLog("Linked to camera ID: " + cameraId);
        notifyEvents("Alarm now linked to camera: " + cameraId);
    }

    /** Triggers camera to begin recording */
    public void triggerCameraRecording() {
        if (linkedCameraId == null) {
            notifyEvents("No camera linked. Cannot record.");
            return;
        }
        String message = "Alarm triggered. Starting recording on camera ID: " + linkedCameraId;
        addLog(message);
        notifyEvents(message);
        // Later: integrate with ServerController.controlDevice(linkedCameraId, "START_RECORDING");
    }

    // -------------------------
    // EMERGENCY SERVICES
    // -------------------------

    /** Notifies emergency services of a triggered intrusion */
    public void contactEmergencyServices() {
        String message = "[ALERT] Forced entry detected by Alarm: " + getDeviceName() +
                         " (ID: " + getDeviceID() + "). Contacting emergency services...";
        addLog(message);
        notifyEvents(message);
        // Later: ServerController.notifyEmergencyServices(message);
    }

    // -------------------------
    // COMMAND HANDLING
    // -------------------------
    @Override
    public boolean handleCommand(String command) {
        switch (command.toUpperCase()) {
            case "ARM":
                return enableAlarm();
            case "DISARM":
                return disableAlarm();
            case "POWER_OFF":
                powerOffAlarm();
                return true;
            case "ENABLE_MS":
                enableMS();
                return true;
            case "DISABLE_MS":
                disableMS();
                return true;
            case "TRIGGER_CAM":
                triggerCameraRecording();
                return true;
            case "EMERGENCY":
                contactEmergencyServices();
                return true;
            default:
                return super.handleCommand(command);
        }
    }

    // -------------------------
    // GETTERS
    // -------------------------
    public boolean isArmed() { return isArmed; }
    public boolean isEnabled() { return isEnabled; }
    public boolean isMotionSensorLinked() { return connectedTMS; }
    public String getLinkedCameraId() { return linkedCameraId; }
}

