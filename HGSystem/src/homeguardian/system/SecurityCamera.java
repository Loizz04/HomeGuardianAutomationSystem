package homeguardian.system;

public class SecurityCamera extends Device{
	
	// -------------------------
    // ATTRIBUTES
    // -------------------------
    private boolean isRecording;
    private boolean isEnabled;
    private boolean connectedTMS;    // motion sensor linked?
    private int zoomLevel;           // 1–10 recommended

    // -------------------------
    // CONSTRUCTOR
    // -------------------------
    public SecurityCamera(String deviceID, String deviceName) {
        super(deviceID, deviceName);
        this.isRecording = false;
        this.isEnabled = true;
        this.connectedTMS = false;
        this.zoomLevel = 1;  // default zoom

        addLog("SecurityCamera created. Ready for manual control.");
    }

    // -------------------------
    // CAMERA FUNCTIONS
    // -------------------------

    /** Enables the camera system */
    public void enableCamera() {
        isEnabled = true;
        notifyEvents("Security camera enabled.");
        addLog("Camera turned ON.");
    }

    /** Disables the camera system */
    public void disableCamera() {
        isEnabled = false;
        isRecording = false;
        notifyEvents("Security camera disabled.");
        addLog("Camera turned OFF.");
    }

    /** Views footage (placeholder function for future GUI integration) */
    public void viewFootage() {
        notifyEvents("Viewing footage from camera: " + getDeviceName());
        addLog("Footage accessed.");
    }

    /** Manually starts recording */
    public boolean recordStream() {
        if (!isEnabled) {
            notifyEvents("Camera disabled. Cannot record.");
            return false;
        }
        isRecording = true;
        notifyEvents("Camera recording started.");
        addLog("Manual recording active.");
        return true;
    }

    /** Stops recording **manually** */
    public void stopRecording() {
        isRecording = false;
        notifyEvents("Camera recording stopped.");
        addLog("Manual recording stopped.");
    }

    // -------------------------
    // MOTION SENSOR SUPPORT
    // -------------------------

    /** Enables motion-based recording (links to TMS) */
    public void enableMotionRecording() {
        connectedTMS = true;
        notifyEvents("Motion-triggered recording enabled.");
        addLog("Connected to TMS for automatic recording.");
    }

    /** Disables motion-based recording */
    public void disableMotionRecording() {
        connectedTMS = false;
        notifyEvents("Motion-triggered recording disabled.");
        addLog("Disconnected from TMS.");
    }

    // -------------------------
    // ZOOM CONTROL
    // -------------------------

    /** Adjusts zoom level (1-10 recommended) */
    public void zoom(int level) {
        if (level < 1 || level > 10) {
            notifyEvents("Invalid zoom level. Must be between 1–10.");
            return;
        }
        zoomLevel = level;
        addLog("Zoom level set to: " + level);
        notifyEvents("Zoom adjusted to level: " + level);
    }

    // -------------------------
    // EMERGENCY HANDLING
    // -------------------------
    public void contactEmergencyServices() {
        String message = "[ALERT] Suspicious activity detected at camera: " 
                + getDeviceName() + " (ID: " + getDeviceID() + "). Contacting emergency services...";
        addLog(message);
        notifyEvents(message);
        // Later: integrate with ServerController.notifyEmergencyServices(message);
    }

    // -------------------------
    // COMMAND HANDLING (Optional)
    // -------------------------
    @Override
    public boolean handleCommand(String command) {
        switch (command.toUpperCase()) {
            case "ENABLE":
                enableCamera();
                return true;
            case "DISABLE":
                disableCamera();
                return true;
            case "RECORD":
                return recordStream();
            case "STOP":
                stopRecording();
                return true;
            case "ENABLE_MOTION":
                enableMotionRecording();
                return true;
            case "DISABLE_MOTION":
                disableMotionRecording();
                return true;
            default:
                return super.handleCommand(command);
        }
    }

    // -------------------------
    // GETTERS
    // -------------------------
    public boolean isRecording() { return isRecording; }
    public boolean isEnabled() { return isEnabled; }
    public boolean isMotionSensorLinked() { return connectedTMS; }
    public int getZoomLevel() { return zoomLevel; }
}

