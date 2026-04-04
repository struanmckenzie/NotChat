import javax.swing.*;
import java.awt.*;
import java.nio.file.*;
import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class ContactDetails {

    private final Color BG = new Color(13, 17, 23);
    private final Color PANEL = new Color(22, 27, 34);
    private final Color BORDER = new Color(48, 54, 61);
    private final Color TEXT = new Color(201, 209, 217);
    private final Color HOVER = new Color(33, 38, 45);

    Contact contact;
    List<Contact> allContacts;
    ContactPage contactPage;

    public ContactDetails(Contact contact, List<Contact> contacts, ContactPage contactPage) {
        this.contact = contact;
        this.allContacts = contacts;
        this.contactPage = contactPage;
    }

    public JPanel getDisplayContactDetails() {
        JPanel messageBox = new JPanel(new BorderLayout());
        messageBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        messageBox.setBackground(PANEL);
        messageBox.setPreferredSize(new Dimension(1500, 100));
        messageBox.setMaximumSize(messageBox.getPreferredSize());

        JLabel userName = new JLabel(contact.getName());
        userName.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        userName.setForeground(TEXT);

        JLabel phoneNumber = new JLabel(contact.getPhoneNumber());
        phoneNumber.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        phoneNumber.setForeground(TEXT);

        JPanel userDetails = new JPanel(new BorderLayout());
        userDetails.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        userDetails.setBackground(PANEL);
        userDetails.add(userName, BorderLayout.NORTH);
        userDetails.add(phoneNumber, BorderLayout.SOUTH);

        JPanel buttons = new JPanel(new GridLayout(1, 0));
        buttons.setBackground(PANEL);

        JButton viewButton = createStyledButton("View details");
        JButton editButton = createStyledButton("Edit details");
        JButton deleteButton = createStyledButton("Delete contact");

        viewButton.addActionListener(e -> viewContact());
        editButton.addActionListener(e -> editContact());
        deleteButton.addActionListener(e -> deleteContact());

        buttons.add(viewButton);
        buttons.add(editButton);
        buttons.add(deleteButton);

        messageBox.add(userDetails, BorderLayout.CENTER);
        messageBox.add(buttons, BorderLayout.EAST);

        return messageBox;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(TEXT);
        button.setBackground(BG);
        button.setBorder(BorderFactory.createLineBorder(BORDER));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(BG);
            }
        });
        return button;
    }

    //Deletes the contact from the contact file while also deleting the contacts chat file and log in the list_of_chats file
    public void deleteContact() {
        allContacts.remove(contact);
        try {
            Path path = Paths.get("src/resources/chats/" + contact.getID().toString());
            Files.delete(path);
        } catch (Exception e) {
            System.out.println("Error deleting file: " + e.getMessage());
        }

        List<Chat> chats = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/resources/list_of_chats"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] c = line.split(",");
                Contact matched = matchContact(c[0]);
                if (matched != null) {
                    chats.add(new Chat(matchContact(c[0]), c[0], LocalDate.parse(c[1]), c[2], c[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading chats: " + e.getMessage());
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("src/resources/list_of_chats"))) {
            for (Chat chat : chats) {
                if (chat != null && !chat.getContact().getID().equals(contact.getID())) {
                    pw.println(chat.getChatName() + "," + chat.getDate() + "," +
                            chat.getStatusContact() + "," + chat.getStatusUser());
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