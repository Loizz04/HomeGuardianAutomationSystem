package homeguardian.system;

import java.util.List;
import java.util.ArrayList;

public class Device {
	
	/**
	 * Name: Nosizo Mabuza
	 * Date Implimented : Nov 1th, 2025
	 * Revised by: Rawan Genina Decmber 2025
	 * Date Tested: Nov 17th, 2025
	 */
	
	/** (need to add a brief description of the file attributes and methods 
	 * ATTRIBUTES 
	 * deviceName: string 
	 * 
	 * METHODS
	 * connecttionStatus()
	 * addLog()
	 * notifyEvents()
	 **/ 
	
	private final String deviceID; 
	private final String deviceName;
	private boolean connected; 
	private final List<ActivityLog> deviceLogs; 
	
	public Device(String deviceID, String deviceName) {
		
		this.deviceID = deviceID;
		this.deviceName = deviceName; 
		// False connection is the default state as all devices are initially disconnected
		this.connected = false;
		this.deviceLogs = new ArrayList<>(); //-> Resulted qin an error because the ArrayList<> isn't reccognised 
	}                                        // Had to add import java.util.ArrayList; so that the class can be used with the appropriet imports
	
	
	// GETTERS
	
	// Spelling mistake in the getters getDeviceID and getDeviceName resulted in the child classes such as smartlight having error results when being called
	
	public String getDeviceID() { 
		return deviceID;
	}
	
	
	public String getDeviceName() {
		return deviceName;
	}
	
   // Returns the current connection status of the device 
	
	public boolean connectionStatus() {
		return connected; 
	}
	
	// Adds a new log entry to the devices internal log history 
	
	public void addLog(String message) {
		ActivityLog log = new ActivityLog("[Device: " + deviceName + "] " + message);
		/** line 59 resulted in an error because the ActivityLog class is not yet recognized. 
		    This causes the code to try and create a new object of the class ActivityLog but 
		    since it hasn't been defined in the class the compiler fails to find the definition for ActivityLog
		
		  - To fix this : import the definition for activity log from the activity log class 
		**/
		deviceLogs.add(log);
		System.out.println("[LOG][DEVICE]" + message);
		
	}
	
	
	// sends notifications for important device events 
	// this can be forwarded to the servers controller or user in future instances 
	// currently only prints to console - later integrate to the ServerController
	public void notifyEvents(String eventMessage) {
		System.out.println("[DEVICE EVENT][" + deviceName + "] " + eventMessage);
		
		// Log the event internally
        addLog("Event: " + eventMessage);
	}
	
	// Device's command handler 
	public boolean handleCommand(String command) {
		//System.out.println("Handling command '" + command + "' for device " + name);
		
		switch (command.toUpperCase()) {

        case "ON":
            connected = true;
            notifyEvents("Device turned ON");
            return true;

        case "OFF":
            connected = false;
            notifyEvents("Device turned OFF");
            return true;

        case "LOCK":
            notifyEvents("Lock activated");
            return true;

        case "UNLOCK":
            notifyEvents("Lock released");
            return true;

        default:
            notifyEvents("Unknown command: " + command);
            return false;
        }
	}
	
	public List<ActivityLog> getDeviceLogs() {
		return deviceLogs;
	}
	
}
