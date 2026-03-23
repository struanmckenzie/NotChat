import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ContactPage {

    private JFrame frame;
    private JPanel contactsPage;
    private List<Contact> contacts;
    private final String USERNAME;

    public ContactPage(List<Contact> contacts, String username){
        this.contacts = contacts;
        this.USERNAME = username;
        drawUI(contacts);
    }

    public void drawUI(List<Contact> contacts) {

        frame = new JFrame();
        frame.setSize(1080, 1920);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Contacts");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ive made this dispose so that you can close the page without closing landingpage as well
        frame.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //Temp change the userName to userName variable once I know it
        // DW lewis i got u ;-)
        JLabel userName = new JLabel(USERNAME, SwingConstants.CENTER);
        userName.setFont(new Font("Arial", Font.BOLD, 32));

        header.add(userName, BorderLayout.NORTH);

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new GridBagLayout()); 
        //sideBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        JButton createNewContact = new JButton("Create new contact");
        createNewContact.addActionListener(e -> addContactPage());

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

    public void addContactPage() {
        contactsPage.removeAll();

        JPanel addNewContactPage = new JPanel();
        addNewContactPage.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 0, 10, 0);

        InputBox nameField = new InputBox();
        nameField.setPreferredSize(new Dimension(660, 80));

        InputBox phoneNumberField = new InputBox();
        phoneNumberField.setPreferredSize(new Dimension(660, 80));

        JButton submitButton = new JButton("Add Contact"); 

        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneNumberField.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        constraints.gridy = 0;
        addNewContactPage.add(nameField, constraints);

        constraints.gridy = 1;
        addNewContactPage.add(phoneNumberField, constraints);

        constraints.gridy = 2;
        addNewContactPage.add(submitButton, constraints);

        contactsPage.add(addNewContactPage);

        contactsPage.revalidate(); // refresh layout
        contactsPage.repaint();    // redraw panel
    }
}