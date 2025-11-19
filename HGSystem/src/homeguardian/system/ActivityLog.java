package homeguardian.system;

public class ActivityLog {
	
	/**
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
	
	private final String message;
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
    }

	
}
