package homeguardian.system;

import java.util.List;

/**
 * Author: Nosizo Mabuza
 * Revised by: Rawan Genina
 * Student Number (Reviser): 1196208
 * 
 * Class: SmartLightTestDriver
 * 
 * Description:
 * This class is a test driver for SmartLight device integration in the Home Guardian system.
 * It demonstrates registering a SmartLight with HGController, sending ON/OFF commands,
 * and reviewing activity logs generated during testing.
 * 
 * Methods:
 * - main(String[] args): Initializes controller and device, performs test commands,
 *   prints results, and displays activity logs.
 * 
 * Attributes:
 * - lightID: ID of the SmartLight device.
 * - lightName: Name of the SmartLight device.
 */

/**
 * Author: Rawan Genina
 * Student Number: 1196208
 * 
 * changed a bit to this file 
 */
public class SmartLightTestDriver {

    public static void main(String[] args) {
        System.out.println("--- Home Guardian SmartLight Integration Test ---");
        
        // 1. Initialize the Central Controller
        HGController controller = new HGController();
        String lightID = "SL-KITCHEN";
        String lightName = "Kitchen Ceiling Light";
        
        // 2. Create the Device
        SmartLight kitchenLight = new SmartLight(lightID, lightName);
        
        // 3. Register the Device with the Controller
        controller.addDevice(kitchenLight);
        System.out.println("\n[SETUP] " + lightName + " registered with HGController.");
        
        // --- Test 1: Turn the Light ON ---
        System.out.println("\n-------------------------------------------------");
        System.out.println("TEST 1: Sending command 'ON' to " + lightID);
        System.out.println("-------------------------------------------------");
        
        boolean successOn = controller.controlDevice(lightID, "ON");
        
        System.out.println("\n Control Command Success: " + successOn);
        if (successOn && kitchenLight.isEnabled() && kitchenLight.connectionStatus()) {
            System.out.println("Light is ON and system connection is active.");
        } else {
            System.out.println("Light state or connection status is incorrect.");
        }
        
        // --- Test 2: Turn the Light OFF ---
        System.out.println("\n-------------------------------------------------");
        System.out.println("TEST 2: Sending command 'OFF' to " + lightID);
        System.out.println("-------------------------------------------------");
        
        boolean successOff = controller.controlDevice(lightID, "OFF");
        
        System.out.println("\n[RESULT] Control Command Success: " + successOff);
        if (successOff && !kitchenLight.isEnabled() && !kitchenLight.connectionStatus()) {
            System.out.println("Light is OFF and system connection is closed.");
        } else {
            System.out.println("Light state or connection status is incorrect.");
        }
        
        // --- Review Logs ---
        System.out.println("\n=================================================");
        System.out.println("HGController Activity Log Summary");
        System.out.println("=================================================");
        
        List<ActivityLog> logs = controller.getAllLogs();
        for (ActivityLog log : logs) {
            System.out.println("[Time: " + log.getTimestamp() + "] " + log.getMessage());
        }
        
        System.out.println("Test Complete");
    }
}
