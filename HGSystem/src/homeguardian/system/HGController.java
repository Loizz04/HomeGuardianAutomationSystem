package homeguardian.system;
// Implimented by Nosizo Mabuza 
// Date : Nov 13th, 2025
// Tested : Nov 17th, 2025

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;



public class HGController {

	private final List<ActivityLog> activityLogs;
	private final List<Notification> notifications;
	private final List<Device> deviceList; 
	private final List<User> userList;
	
	/**
	 ADDED FOR TEST DRIVER TESTING 
	 */
	// Device registration section
	// Added so that the test driver can register devices 
	public void addDevice(Device device) {
	    if (device != null && !deviceList.contains(device)) {
	        deviceList.add(device);
	        // Assuming Device has public getDeviceID() and getDeviceName()
	        logActivity("New device added: " + device.getDeviceName() + " (" + device.getDeviceID() + ")");
	    }
	}
	/**
	 ADDED FOR TEST DRIVER TESTING 
	 */
	
	// create a new list of the activity logs, notifications, device list, user list 
	
	public HGController() {
		
		
		this.activityLogs = new ArrayList<>();
		this.notifications = new ArrayList<>();
		this.deviceList = new ArrayList<>();
		this.userList = new ArrayList<>();
		
	}
	
   
	// DEVICE CONTROL SECTION
	/** Device Control ( utilise the devices ID using a command string 
	- ex. command : "ON", "OFF", "LOCK", "UNLOCK"
	- Hope is the device ID + the command will cause the opperation to take place
	
	**/
	
	public boolean controlDevice(String deviceID, String command) {
		
	/**
	 a method named deviceOpt is created of type <Device>
	 call method findDeviceByID and pass the deviceID to return a type of device 
	 method returns a device of type <Device> 
	**/
		Optional<Device> deviceOpt = findDeviceByID( deviceID);
		
		if (deviceOpt.isEmpty()) {
			
			logActivity("Device with ID" + deviceID + "can't be found.");
			return false;
		}
		
		Device device = deviceOpt.get(); 
		boolean success = device.handleCommand(command); 
		
		String statusMessage = success 
				 ? "Command '" + command + "' executed on device " + deviceID
			     : "Failed to execute command '" + command + "' on device " + deviceID;
		
		logActivity(statusMessage);
		return success;
		
		
	}
	
	/** the method searches through the deviceList to find the first Device whose ID matched the 
	 deviceID passed through the method, it then returns the result in an OptionalDevice
	 **/
	private Optional<Device> findDeviceByID(String deviceID) {
		return deviceList.stream()
				.filter(d -> d.getDeviceID().equals(deviceID))
                .findFirst();
	}
	
	
	// USER MANAGEMENT SECTION
	
	public void addUser( User user) {
		
		if (user != null && !userList.contains(user)) {
			
			userList.add(user); 
		}
	}
	
	public boolean removeUser( User user) {
		if (user == null) return false;
		return userList.remove(user);
		
	}
	
	// Return an unmodifiable view of all the users to prevent the external modification of user list
	public List<User> getAllUsers() {
		
		return Collections.unmodifiableList(userList);
	}
	
	
	// ACTIVITY LOGGING SECTION
	
	public void logActivity(String message) {
		
		ActivityLog log = new ActivityLog(message);
		activityLogs.add(log);
	    
		// Optional : print to console for debugging include if wanted later 
		/**
		 * System.out.println ("[LOG]" + message); 
		 **/
		
	}
	
	// return a list of the unmodifiable logs so that users are unable to modify the list when depicted
	public List<ActivityLog> getAllLogs() {
		return Collections.unmodifiableList(activityLogs);
		
	}
	
	
	// NOTIFICATION SECTION 
	
	public void notifyUser(User user, String message) {
		
		if (user == null || message == null || message.isBlank()) return; 
		
		Notification notification = new Notification(user, message); 
		notifications.add(notification);
		
		// Optional: also send through to the user 
		/**
		 * System.out.println("Notification sent to" + user.getName() + ":" + message);
		 **/
		 	
	}
	
	// can be expanded to integrate an API or gateway to contact emergency authoriites 
	public void notifyEmergencyServices(String message) {
		
		Notification emergencyNotification = new Notification(null, "[EMERGENCY]" + message);
		
		notifications.add(emergencyNotification);
		
		logActivity("Emergeny services notified: " + message); 
		System.out.println("Emergency services notified: " + message);
		
	}
	
	
	public List<Notification> getAllNotifications() {
		
		return Collections.unmodifiableList(notifications);
		
	}
	
	
}
