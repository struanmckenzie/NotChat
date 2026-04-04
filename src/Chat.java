import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Chat {

    Contact contact;
    String chatName;
    LocalDate date;
    String statusContact;
    String statusUser;
    Queue messages = new Queue();

    public Chat(Contact contact, String chatName, LocalDate date, String statusContact, String statusUser) {
        this.contact = contact;
        this.chatName = chatName;
        this.date = date;
        this.statusContact = statusContact;
        this.statusUser = statusUser;
        messages.loadFromCSV("src/resources/chats/" + chatName);
    }

    public void displayChat() {
        statusUser = "Read";
        LandingPage.saveChats();
        new ChatPane(chatName, this);
    }

    // contact
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    // chatName
    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    // date
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // statusContact
    public String getStatusContact() {
        return statusContact;
    }

    public void setStatusContact(String statusContact) {
        this.statusContact = statusContact;
    }

    // statusUser
    public String getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }

    // messages
    public Queue getMessages() {
        return messages;
    }

    public Message[] getLast3Messages() {
        return messages.getLastThree();
    }

    // Returns true if the search value is within any of the chat messages
    public boolean containsSearchValue(String searchValue) {
        if (messages.containsSeachValue(searchValue)) {
            return true;
        } else {
            return false;
        }
    }

    public LocalDateTime getLastMessageTime() {
        if (messages.isEmpty()) {
            return date.atStartOfDay();
        }
        return messages.getBottom().getData().getTimestamp();
    }

    public String getReadableLastMessageTime(){
        LocalDateTime lastMessageTime =  getLastMessageTime();
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return lastMessageTime.format(FORMATTER);
    }

}