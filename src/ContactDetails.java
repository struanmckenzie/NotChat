import javax.swing.*;
import java.awt.*;
import java.util.List;

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
        ContactPage.contactPage.reloadContacts();
    }

    public void editContact() {
        ContactPage.contactPage.reloadContacts();
        contactPage.editContactPage(contact);
    }

    public void viewContact(){
        contactPage.viewContactPage(contact);
    }

}
