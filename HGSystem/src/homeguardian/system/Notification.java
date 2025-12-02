package homeguardian.system;

/**
 * Name: Nosizo Mabuza
 * Date Implemented: Nov 1th, 2025
 * Revised by: Rawan Genina December 1st, 2025
 * Date Tested: Nov 17th, 2025
 *
 * Description: Represents a system notification or alert intended for a specific user
 * or a general event. It manages details like the recipient, message, timestamp,
 * and tracks the user's notification preferences (enabled/disabled) and contact information.
 *
 * --ATTRIBUTES
 * notificationID: String   | A unique identifier for the notification.
 * userID: String           | The ID of the user the notification is targeted for.
 * timeStamp: LocalDateTime | The time the notification was created.
 * isEnabled: boolean       | Indicates whether notifications are currently enabled for this user.
 * userEmail: String        | The user's email address for sending alerts.
 * recipient: User          | The User object (can be null for system/global alerts).
 * message: String          | The content of the notification.
 *
 * --METHODS
 * Notification(recipient: User, message: String, isEnabled: boolean, userEmail: String) | Constructor.
 * Notification(recipient: User, message: String)                                       | Convenience constructor.
 * isEnabled(): boolean          | Checks if notifications are enabled for the recipient.
 * sendAlert(): boolean          | Simulates sending an alert (e.g., to email/app).
 * getEmail(): String            | Returns the user's email address.
 * setEmail(email: String): void | Sets the user's email address.
 * getRecipient(): User          | Getter for the recipient User object.
 * getMessage(): String          | Getter for the notification message.
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Notification {

    // New Required Attributes
    private final String notificationID;
    private final String userID;
    private final LocalDateTime timeStamp;
    private boolean isEnabled; // Tracks user preference for this type of notification/user
    private String userEmail;

    // Existing Attributes
    private final User recipient; // can be null for emergency/global
    private final String message;

    /**
     * Full constructor for a new Notification.
     *
     * @param recipient The target User object (can be null).
     * @param message   The content of the notification.
     * @param isEnabled Initial notification status for the user.
     * @param userEmail The user's primary contact email.
     */
    public Notification(User recipient, String message, boolean isEnabled, String userEmail) {
        this.recipient = recipient;
        this.message = message;

        // Initialization of New Attributes
        this.notificationID = UUID.randomUUID().toString();
        this.timeStamp = LocalDateTime.now();
        this.isEnabled = isEnabled;
        this.userEmail = userEmail;
        this.userID = (recipient != null) ? recipient.getUsername() : "SYSTEM";
    }

    /**
     * Convenience constructor used by HGController: defaults to enabled, with a placeholder email.
     *
     * @param recipient The target User object (can be null).
     * @param message   The content of the notification.
     */
    public Notification(User recipient, String message) {
        this(recipient, message, true, "unknown@example.com");
    }

    // METHODS

    /**
     * Returns the current enabled status for this notification type/user.
     *
     * @return true if notifications are enabled, false otherwise.
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Simulates sending the alert.
     * In a real system, this would trigger an email, push notification, or SMS.
     *
     * @return true if the alert was conceptually sent, false if disabled.
     */
    public boolean sendAlert() {
        if (!isEnabled) {
            System.out.println("[" + getTimestampFormatted() + "] ALERT DISABLED for User " + userID + ": " + message);
            return false;
        }

        String recipientInfo = (recipient != null) ? recipient.getName() : "System/Global";System.out.println("--- ALERT SENT ---");
        System.out.println("ID: " + notificationID);
        System.out.println("To: " + recipientInfo + " | Email: " + userEmail);
        System.out.println("Time: " + getTimestampFormatted());
        System.out.println("Message: " + message);
        System.out.println("------------------");
        return true;
    }

    /**
     * Retrieves the user's current email address.
     *
     * @return the email string.
     */
    public String getEmail() {
        return userEmail;
    }

    /**
     * Sets a new email address for the user.
     *
     * @param email The new email address.
     */
    public void setEmail(String email) {
        if (email != null && !email.trim().isEmpty()) {
            this.userEmail = email;
            System.out.println("Notification email updated to: " + email);
        }
    }

    // GETTERS FOR NEW ATTRIBUTES

    public String getNotificationID() {
        return notificationID;
    }

    public String getUserID() {
        return userID;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getTimestampFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timeStamp.format(formatter);
    }

    // GETTERS

    public User getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
}