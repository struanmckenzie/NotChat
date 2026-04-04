import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.nio.file.*;
import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class ContactDetails {

    Contact contact;
    List<Contact> allContacts;
    ContactPage contactPage;

    public ContactDetails(Contact contact, List<Contact> contacts, ContactPage contactPage) {
        this.contact = contact;
        this.allContacts = contacts;
        this.contactPage = contactPage;
    }

    public JPanel getDisplayContactDetails() {
        JPanel messageBox = new JPanel();
        messageBox.setLayout(new BorderLayout());
        messageBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        messageBox.setBackground(Color.GRAY);
        messageBox.setPreferredSize(new Dimension(1500, 100));
        messageBox.setMaximumSize(messageBox.getPreferredSize());

        JLabel userName = new JLabel(contact.getName());
        userName.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel phoneNumber = new JLabel(contact.getPhoneNumber());
        phoneNumber.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel userDetails = new JPanel();
        userDetails.setLayout(new BorderLayout());
        userDetails.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        userDetails.setBackground(Color.LIGHT_GRAY);

        userDetails.add(userName, BorderLayout.NORTH);
        userDetails.add(phoneNumber, BorderLayout.SOUTH);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 0));

        JButton viewButton = new JButton("View details");
        viewButton.addActionListener(e -> viewContact());

        JButton editButton = new JButton("Edit details");
        editButton.addActionListener(e -> editContact());

        JButton deleteButton = new JButton("Delete contact");
        deleteButton.addActionListener(e -> deleteContact());

        buttons.add(viewButton);
        buttons.add(editButton);
        buttons.add(deleteButton);

        messageBox.add(userDetails);
        messageBox.add(buttons, BorderLayout.EAST);

        return messageBox;
    }

    public void deleteContact() {
        allContacts.remove(contact);
        try {
            Path path = Paths.get("src/resources/chats/" + contact.getID().toString());
            Files.delete(path); // throws exception if file doesn't exist
            System.out.println("File deleted successfully");
        } catch (Exception e) {
            System.out.println("Error deleting file: " + e.getMessage());
        }

        List<Chat> chats = new ArrayList<>();

        // this is gonna load chats from FILES
        try (BufferedReader br = new BufferedReader(new FileReader("src/resources/list_of_chats"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // read line and split into parts
                String[] c = line.split(",");

                // add to arraylist
                Contact matched = matchContact(c[0]);
                if (matched != null){
                    chats.add(new Chat(matchContact(c[0]), c[0], LocalDate.parse(c[1]), c[2], c[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading chats: " + e.getMessage());
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("src/resources/list_of_chats"))) {
            for (Chat chat : chats) {
                if (chat != null && !chat.getContact().getID().equals(contact.getID())){
                    System.out.println("Working");
                    pw.println(chat.getChatName() + "," +
                            chat.getDate() + "," +
                            chat.getStatusContact() + "," +
                            chat.getStatusUser());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }

        ContactPage.saveContacts();
        ContactPage.contactPage.reloadContacts();
    }


    public Contact matchContact(String id) {
        for (Contact contact : allContacts) {
            if (contact.getID().toString().equals(id)) {
                return contact;
            }
        }
        return null;
    }

    public void editContact() {
        ContactPage.contactPage.reloadContacts();
        contactPage.editContactPage(contact);
    }

    public void viewContact() {
        contactPage.viewContactPage(contact);
    }

}
