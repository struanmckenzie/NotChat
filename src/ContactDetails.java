import javax.swing.*;
import java.awt.*;

public class ContactDetails {

    Contact contact;

    public ContactDetails(Contact contact) {
        this.contact = contact;
    }
   
    public JPanel getDisplayContactDetails() {
        JPanel messageBox = new JPanel();
        messageBox.setLayout(new BorderLayout());
        messageBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        messageBox.setBackground(Color.GRAY);

        JLabel userName = new JLabel(contact.getName());
        userName.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel phoneNumber = new JLabel(contact.getPhoneNumber());
        phoneNumber.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 0));

        JButton viewButton = new JButton("View details");

        JButton editButton = new JButton("Edit details");

        JButton deleteButton = new JButton("Delete contact");

        buttons.add(viewButton);
        buttons.add(editButton);
        buttons.add(deleteButton);

        messageBox.add(userName, BorderLayout.NORTH);
        messageBox.add(phoneNumber);
        messageBox.add(buttons, BorderLayout.EAST);

        return messageBox;
    }

}
        
