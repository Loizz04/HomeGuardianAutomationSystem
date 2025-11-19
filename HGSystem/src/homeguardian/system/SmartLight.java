package homeguardian.system;

public class SmartLight extends Device {


    // ATTRIBUTES
   
    private int brightness;               // 0–100
    private String colour;                // e.g., "warm white", "blue", "#FF0000"
    private boolean isEnabled;           
    private int motionSensitivity;        // 1–10 recommended
    private int timeoutDuration;          // seconds before auto-off
    private boolean connectedTMS;         // TRUE = light connected to motion sensor

    // -------------------------
    // CONSTRUCTOR
    // -------------------------
    public SmartLight(String deviceID, String deviceName) {
        super(deviceID, deviceName);
        this.brightness = 50;
        this.colour = "White";
        this.isEnabled = false;
        this.motionSensitivity = 5;
        this.timeoutDuration = 30;
        this.connectedTMS = false;

        addLog("SmartLight created with default settings.");
    }

    
    // METHODS
       
    public void enableLight() {
        this.isEnabled = true;
        notifyEvents("SmartLight enabled.");
    }

    public void disableLight() {
        this.isEnabled = false;
        notifyEvents("SmartLight disabled.");
    }

    public void adjustBrightness(int level) {
        if (level < 0 || level > 100) {
            notifyEvents("Brightness value must be between 0 and 100.");
            return;
        }
        this.brightness = level;
        addLog("Brightness changed to: " + level);
    }

    public void changeColour(String newColour) {
        this.colour = newColour;
        addLog("Colour changed to: " + newColour);
    }

    public void setTimeoutDuration(int seconds) {
        if (seconds < 5) {
            notifyEvents("Timeout cannot be less than 5 seconds.");
            return;
        }
        this.timeoutDuration = seconds;
        addLog("Timeout duration set to: " + seconds + " seconds");
    }

    public void adjustMotionSensitivity(int level) {
        if (level < 0 || level > 10) {
            notifyEvents("Motion sensitivity must be between 0 and 10.");
            return;
        }
        this.motionSensitivity = level;
        addLog("Motion sensitivity adjusted to: " + level);
    }

    public void enableMotionSensor() {
        this.connectedTMS = true;
        notifyEvents("Motion sensor linked to SmartLight.");
    }

    public void disableMotionSensor() {
        this.connectedTMS = false;
        notifyEvents("Motion sensor disconnected.");
    }

    
    // OPTIONAL GETTERS
    
    public boolean isEnabled() { return isEnabled; }
    public int getBrightness() { return brightness; }
    public String getColour() { return colour; }
    public int getTimeoutDuration() { return timeoutDuration; }
    public int getMotionSensitivity() { return motionSensitivity; }
    public boolean isMotionSensorConnected() { return connectedTMS; }

    
    // COMMAND HANDLING
    
    @Override
    public boolean handleCommand(String command) {
        switch (command.toUpperCase()) {
            case "ON":
            	// Call the parent's ON handler to update the base 'connected' status
                super.handleCommand("ON"); 
                enableLight();
                return true;

            case "OFF":
            	// Call the parent's OFF handler to update the base 'connected' status
                super.handleCommand("OFF");
                disableLight();
                return true;

            case "ENABLE_SENSOR":
                enableMotionSensor();
                return true;

            case "DISABLE_SENSOR":
                disableMotionSensor();
                return true;

            default:
                return super.handleCommand(command);
        }
    }
}
