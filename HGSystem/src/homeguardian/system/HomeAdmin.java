package homeguardian.system;


/**
 * Author: Rawan Genina
 * Student Number: 1196208
 * 
 */

public class HomeAdmin extends User {

    public HomeAdmin(String name, String username, String email, String passwordHash) {
        super(name, username, email, passwordHash);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    public void adminAddDevice(HGController controller, Device device) {
        controller.addDevice(device);
        addUserLog("Added device: " + device.getDeviceName());
    }

    public void adminRemoveDevice(HGController controller, String deviceId) {
        controller.removeDevice(deviceId);
        addUserLog("Removed device with ID: " + deviceId);
    }
}
