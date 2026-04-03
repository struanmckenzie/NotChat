import java.time.LocalDateTime;
import java.util.UUID;
import java.time.format.DateTimeFormatter;

public class Contact {

    // Contact details depending on time could implement profile pictures
    private String name;
    private String phoneNumber;
    private LocalDateTime creationTime;
    private UUID id;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.creationTime = LocalDateTime.now();
        this.id = UUID.randomUUID();
    }

    // this is for loading already created contacts
    public Contact(String name, String phoneNumber, LocalDateTime cTime, UUID id) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.creationTime = cTime;
        this.id = id;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getTime() {
        return creationTime;
    }

    public String getReadableTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return creationTime.format(formatter);

    }

    public UUID getID() {
        return id;
    }
}