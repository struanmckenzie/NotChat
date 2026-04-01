import java.time.Instant;
import java.util.UUID;

public class Contact {

    //Contact details depending on time could implement profile pictures
    private String name;
    private String phoneNumber;
    private Instant creationTime;
    private UUID id;

    public Contact(String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.creationTime = Instant.now();
        this.id = UUID.randomUUID();
    }

    //Getters and setters 
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }    

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public Instant getTime(){
        return creationTime;
    }

    public UUID getID(){
        return id;
    }
}