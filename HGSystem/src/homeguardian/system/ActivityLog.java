package homeguardian.system;

	
	/**
	 * just a push test 
	 * Impliment the following into the class 
	 * ATTRIBUTES 
	 * deviceName: String
	 * ( Not included into the class diagram but included in description ) logID: unique identifier for the log entry
	 * ( Not included into the class diagram but included in description )user: identifies the used who performed the action
	 * timeStamp: Date
	 * actionType: String 
	 * deviceID : String
	 * 
	 * METHODS
	 * +logActivity()
	 * +getAllLogs()
	 **/
	
	/*private final String message;
    private final long timestamp;

    public ActivityLog(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }*/
	
	

	import java.util.ArrayList;
	import java.util.Date;
	import java.util.List;

	/**
	 * Represents a single activity entry within the Home Guardian system.
	 * Attributes: logID, user, actionType, deviceName, deviceID, message, timestamp.
	 */
	public class ActivityLog {

	    // -------------------------
	    // STATIC STORAGE
	    // -------------------------
	    private static int nextLogId = 1;              // Auto-incremented ID
	    private static final List<ActivityLog> logStore = new ArrayList<>(); // Stores all logs

	    // -------------------------
	    // ATTRIBUTES
	    // -------------------------
	    private final String logID;
	    private final String user;
	    private final String actionType;
	    private final String deviceName;
	    private final String deviceID;
	    private final String message;
	    private final Date timeStamp;     // Human-readable
	    private final long timestamp;     // Milliseconds

	    // -------------------------
	    // CONSTRUCTORS
	    // -------------------------
	    public ActivityLog(String user, String actionType, String deviceName, String deviceID, String message) {
	        this.logID = "LOG: " + nextLogId++;
	        this.user = user;
	        this.actionType = actionType;
	        this.deviceName = deviceName;
	        this.deviceID = deviceID;
	        this.message = message;
	        this.timeStamp = new Date();
	        this.timestamp = this.timeStamp.getTime();

	        logActivity(this); // Automatically store the log
	    }

	    public ActivityLog(String message) {
	        this("SYSTEM/UNKNOWN", "Basic Log", "N/A", "N/A", message);
	    }

	    // -------------------------
	    // GETTERS
	    // -------------------------
	    public String getLogID() { return logID; }
	    public String getUser() { return user; }
	    public String getActionType() { return actionType; }
	    public String getDeviceName() { return deviceName; }
	    public String getDeviceID() { return deviceID; }
	    public String getMessage() { return message; }
	    public Date getTimeStamp() { return timeStamp; }
	    public long getTimestamp() { return timestamp; }

	    // -------------------------
	    // STATIC OPERATIONS
	    // -------------------------

	    /** Store a log entry and print to console */
	    public static void logActivity(ActivityLog logEntry) {
	        logStore.add(logEntry);
	        System.out.println("LOGGING: [" + logEntry.getTimeStamp() + "] " + logEntry.getLogID() +
	                " | User: " + logEntry.getUser() +
	                " | Action: " + logEntry.getActionType() +
	                " | Device: " + logEntry.getDeviceName() +
	                " | Message: " + logEntry.getMessage());
	    }

	    /** Return all logs as an unmodifiable list */
	    public static List<ActivityLog> getAllLogs() {
	        return List.copyOf(logStore);
	    }
	


	
}
