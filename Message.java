import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single message by storing the sender, content, and timestamp.
 */
public class Message {
    private String sender;
    private String content;
    private LocalDateTime timestamp;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Creates a message with the current time as its timestamp.
     *
     * @param sender  The name of the person sending the message.
     * @param content The text of the message.
     */
    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Creates a message with an explicit timestamp.
     * This can be used when loading messages from a file.
     *
     * @param sender    The name of the person sending the message.
     * @param content   The text of the message.
     * @param timestamp The time the message was sent.
     */
    public Message(String sender, String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Saves this message as a single line in our data file.
     * The three fields (sender, content, timestamp) are separated by a | 
     *
     * Example output:  Alice|Hello there!|2026-03-12 10:05:00
     */
    public String toCSVRow() {
        return sender + "|" + content + "|" + timestamp.format(FORMATTER);
    }

    /**
     * Reads a saved line back from the data file and turns it into a Message.
     *
     * @param savedLine A line from the data file, e.g. Alice|Hello there!|2026-03-12 10:05:00
     * @return The reconstructed Message.
     */
    public static Message fromCSVRow(String savedLine) {
        // Split on the | character, limit to 3 parts so a | inside a message won't break things
        String[] parts = savedLine.split("\\|", 3);
        String messageSender    = parts[0];
        String messageContent   = parts[1];
        LocalDateTime messageTime = LocalDateTime.parse(parts[2], FORMATTER);
        return new Message(messageSender, messageContent, messageTime);
    }

    @Override
    public String toString() {
        return "[" + timestamp.format(FORMATTER) + "] " + sender + ": " + content;
    }
}
