package homeguardian.system;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class MotionSensor extends Device{

	
	// -------------------------
    // ATTRIBUTES
    // -------------------------
    private boolean isEnabled;
    private final List<String> linkedAlarms;
    private final List<String> linkedLights;
    private final List<String> linkedCameras;
    private LocalDateTime lastDetection;

    // Extra helpful attribute for adjustSensitivity(level:int)
    private int sensitivityLevel;   // 0–10 recommended

    // -------------------------
    // CONSTRUCTOR
    // -------------------------
    public MotionSensor(String deviceId, String deviceName) {
        super(deviceId, deviceName);
        this.isEnabled = false;
        this.linkedAlarms = new ArrayList<>();
        this.linkedLights = new ArrayList<>();
        this.linkedCameras = new ArrayList<>();
        this.lastDetection = null;
        this.sensitivityLevel = 5; // default sensitivity

        addLog("MotionSensor created with default sensitivity: " + sensitivityLevel);
    }

    // -------------------------
    // CORE METHODS
    // -------------------------

    public boolean enableSensor() {
        if (!isEnabled) {
            isEnabled = true;
            notifyEvents("Motion sensor enabled.");
            addLog("Sensor enabled.");
        }
        return isEnabled;
    }

    public boolean disableSensor() {
        if (isEnabled) {
            isEnabled = false;
            notifyEvents("Motion sensor disabled.");
            addLog("Sensor disabled.");
        }
        return !isEnabled;
    }

    public void adjustSensitivity(int level) {
        if (level < 0 || level > 10) {
            notifyEvents("Invalid sensitivity level. Must be between 0 and 10.");
            return;
        }
        this.sensitivityLevel = level;
        addLog("Sensitivity adjusted to: " + level);
    }

    public boolean linkToAlarm(String alarmId) {
        if (alarmId == null || alarmId.isBlank()) {
            return false;
        }
        if (!linkedAlarms.contains(alarmId)) {
            linkedAlarms.add(alarmId);
            addLog("Linked to Alarm ID: " + alarmId);
            notifyEvents("Motion sensor linked to alarm " + alarmId);
        }
        return true;
    }

    public boolean linkToLight(String lightId) {
        if (lightId == null || lightId.isBlank()) {
            return false;
        }
        if (!linkedLights.contains(lightId)) {
            linkedLights.add(lightId);
            addLog("Linked to SmartLight ID: " + lightId);
            notifyEvents("Motion sensor linked to light " + lightId);
        }
        return true;
    }

    public boolean linkToCamera(String cameraId) {
        if (cameraId == null || cameraId.isBlank()) {
            return false;
        }
        if (!linkedCameras.contains(cameraId)) {
            linkedCameras.add(cameraId);
            addLog("Linked to SecurityCamera ID: " + cameraId);
            notifyEvents("Motion sensor linked to camera " + cameraId);
        }
        return true;
    }

    // -------------------------
    // DETECTION LOGIC (HELPER)
    // -------------------------

    /**
     * Call this when motion is actually detected.
     * Updates lastDetection and logs the event.
     */
    public void registerDetection() {
        if (!isEnabled) {
            addLog("Motion detected but sensor is disabled.");
            return;
        }

        lastDetection = LocalDateTime.now();
        String timestamp = getLastDetectionFormatted();
        notifyEvents("Motion detected at " + timestamp);
        addLog("Motion detected at " + timestamp);

        // Later: ServerController can use linkedAlarms / linkedLights / linkedCameras
        // to trigger responses (alarm, lights, recording, etc.).
    }

    // -------------------------
    // GETTERS
    // -------------------------

    public boolean isEnabled() {
        return isEnabled;
    }

    public int getSensitivityLevel() {
        return sensitivityLevel;
    }

    public List<String> getLinkedAlarms() {
        return linkedAlarms;
    }

    public List<String> getLinkedLights() {
        return linkedLights;
    }

    public List<String> getLinkedCameras() {
        return linkedCameras;
    }

    public LocalDateTime getLastDetection() {
        return lastDetection;
    }

    public String getLastDetectionFormatted() {
        if (lastDetection == null) {
            return "No detections yet.";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return lastDetection.format(formatter);
    }
	
}
