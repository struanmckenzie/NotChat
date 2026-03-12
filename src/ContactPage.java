import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ContactPage {

    private JFrame frame;
    private JPanel contactsPage;
    private List<Contact> contacts;

    public ContactPage(List<Contact> contacts){
        this.contacts = contacts;
    }

    public void drawUI(List<Contact> contacts) {

        frame = new JFrame();
        frame.setSize(1080, 1920);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Contacts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //Temp change the userName to userName variable once I know it 
        JLabel userName = new JLabel("Username", SwingConstants.CENTER); 
        userName.setFont(new Font("Arial", Font.BOLD, 32));

        header.add(userName, BorderLayout.NORTH);

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new GridLayout(0, 1)); 
        sideBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        JButton createNewContact = new JButton("Create new contact");

        JButton sortContacts = new JButton("Sort contacts");

        JButton returnMainMenu = new JButton("Return");

        sideBar.add(createNewContact);
        sideBar.add(sortContacts);
        sideBar.add(returnMainMenu);

        mainContent.add(sideBar, BorderLayout.WEST);
        
        contactsPage = new JPanel();
        contactsPage.setLayout(new BoxLayout(contactsPage, BoxLayout.Y_AXIS));
        contactsPage.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for (Contact contact : contacts ){
            ContactDetails contactBox = new ContactDetails(contact, contacts);
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

    public void reloadContacts() {
        contactsPage.removeAll(); // clear previous contacts

        for (Contact contact : contacts) {
            ContactDetails contactBox = new ContactDetails(contact, contacts);
            JPanel displayableContactBox = contactBox.getDisplayContactDetails();
            contactsPage.add(displayableContactBox);
            contactsPage.add(Box.createVerticalStrut(20));
        }

        contactsPage.revalidate(); // refresh layout
        contactsPage.repaint();    // redraw panel
    }

    public static ContactPage launch(List<Contact> contacts) {
        return new ContactPage(contacts);
    }

}