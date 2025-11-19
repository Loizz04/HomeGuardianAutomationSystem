package homeguardian.system;


/**
 * Impliment the following : 
 * ATTRIBUTES 
 * -notificationID: String 
 * -userID: String 
 * -timeStamp: Date
 * isEnabled: Indicates whether notifications are currently enabled for the user.
 * 
 * METHODS
 * isEnabled()
 * sendAlert()
 * getEmail()
 * setEmail()
 **/


public class Notification {
	 private final User recipient; // can be null for emergency/global
	    private final String message;

	    public Notification(User recipient, String message) {
	        this.recipient = recipient;
	        this.message = message;
	    }

	    public User getRecipient() {
	        return recipient;
	    }

	    public String getMessage() {
	        return message;
	    }	

}
