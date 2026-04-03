import java.time.LocalDate;

public class Chat {
    
    Contact contact;
    String chatName;
    LocalDate date;
    String statusContact;
    String statusUser;

    public Chat(Contact contact){
        this.contact = contact;
        this.chatName = this.contact.getID().toString();
        date = LocalDate.now();
        statusContact = "Delivered";
        statusUser = "Unread";
    }

    public void displayChat(){
        new ChatPane(chatName);
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
}