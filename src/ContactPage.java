import javax.swing.*;
import java.awt.*;

public class ContactPage {

    public void launch(Contact[] contacts) {

        JFrame frame = new JFrame();
        frame.setSize(1080, 1920);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Contacts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        
        //Temp change the userName to userName variable once I know it 
        JLabel userName = new JLabel("Username", SwingConstants.CENTER); 
        userName.setFont(new Font("Arial", Font.BOLD, 32));

        header.add(userName, BorderLayout.NORTH);

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new GridLayout(0, 1)); 
        sideBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton createNewContact = new JButton("Create new contact");

        JButton sortContacts = new JButton("Sort contacts");

        JButton returnMainMenu = new JButton("Return");

        sideBar.add(createNewContact);
        sideBar.add(sortContacts);
        sideBar.add(returnMainMenu);

        mainContent.add(sideBar, BorderLayout.WEST);
        
        JPanel contactsPage = new JPanel();
        contactsPage.setLayout(new BoxLayout(contactsPage, BoxLayout.Y_AXIS));
        contactsPage.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for (Contact contact : contacts ){
            ContactDetails contactBox = new ContactDetails(contact);
            JPanel displayableContactBox = contactBox.getDisplayContactDetails();
            contactsPage.add(displayableContactBox);
            contactsPage.add(Box.createVerticalStrut(20));
        }

        JScrollPane contactsPageScrollable = new JScrollPane(contactsPage);
        contactsPageScrollable.getVerticalScrollBar().setUnitIncrement(20);

        mainContent.add(contactsPageScrollable);

        frame.add(header, BorderLayout.NORTH);
        frame.add(mainContent);
        
        frame.setVisible(true);

    }

}