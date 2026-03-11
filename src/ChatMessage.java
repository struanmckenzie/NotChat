import java.time.Instant;

/**
 * Chat objects
 * @author Struan McK
 */
public class ChatMessage {
    // fields
    private String text;
    private String contact;
    private Instant time;
    private boolean seen;

    /**
     * overloaded constructor
     */
    public ChatMessage(String text, String contact, Instant time, boolean seen) {
        this.text = text;
        this.contact = contact;
        this.time = time;
        this.seen = seen;
    }

    /**
     * get / set methods
     */
    public void setText(String text) { this.text = text; }
    public void setContact(String contact) { this.contact = contact; }
    public void setTime(Instant time) { this.time = time; }
    public void setSeen(boolean seen) { this.seen = seen; }

    public String getText() { return this.text; }
    public String getContact() { return this.contact; }
    public Instant getTime() { return this.time; }
    public boolean getSeen() { return this.seen; }

}
