package homeguardian.system;

/**
 * Author: Rawan Genina
 * Student Number: 1196208
 *
 */
public class HomeGuest extends User {

    public HomeGuest(String name, String username, String email, String passwordHash) {
        super(name, username, email, passwordHash);
    }

    @Override
    public String getRole() {
        return "Guest";
    }
}
