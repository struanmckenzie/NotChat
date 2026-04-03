import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ContactPage {

    private JFrame frame;
    private JPanel contactsPage;
    private List<Contact> contacts;
    private final String USERNAME;
    public static ContactPage contactPage;
    private Boolean alphabeltical = false;
    private String sorted = "Alphabetical";

    public ContactPage(List<Contact> contacts, String username) {
        this.contacts = contacts;
        this.USERNAME = username;
        this.contactPage = this;
        drawUI(contacts);
    }

    public void drawUI(List<Contact> contacts) {

        frame = new JFrame();
        frame.setSize(1080, 1920);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Contacts");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ive made this dispose so that you can close the page
                                                                 // without closing landingpage as well
        frame.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Temp change the userName to userName variable once I know it
        // DW lewis i got u ;-)
        JLabel userName = new JLabel(USERNAME, SwingConstants.CENTER);
        userName.setFont(new Font("Arial", Font.BOLD, 32));

        header.add(userName, BorderLayout.NORTH);

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new GridLayout(5, 1, 10, 10));
        sideBar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton createNewContact = new JButton("Create new contact");
        createNewContact.addActionListener(e -> addContactPage());

        JButton sortContacts = new JButton("Sort contacts");
        sortContacts.addActionListener(e -> sortContacts());

        JButton returnMainMenu = new JButton("Return");

        sideBar.add(createNewContact);
        sideBar.add(sortContacts);
        sideBar.add(returnMainMenu);

        mainContent.add(sideBar, BorderLayout.WEST);

        contactsPage = new JPanel();
        contactsPage.setLayout(new BoxLayout(contactsPage, BoxLayout.Y_AXIS));
        contactsPage.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        reloadContacts();


        JScrollPane contactsPageScrollable = new JScrollPane(contactsPage);
        contactsPageScrollable.getVerticalScrollBar().setUnitIncrement(20);

        mainContent.add(contactsPageScrollable);

        frame.add(header, BorderLayout.NORTH);
        frame.add(mainContent);

        sortContacts();

        frame.setVisible(true);

    }

    public void reloadContacts() {
        contactsPage.removeAll(); // clear previous contacts

        JLabel sortTitle = new JLabel(sorted);
        sortTitle.setFont(new Font("Arial", Font.BOLD, 26));
        contactsPage.add(sortTitle);

        for (Contact contact : contacts) {
            ContactDetails contactBox = new ContactDetails(contact, contacts, contactPage);
            JPanel displayableContactBox = contactBox.getDisplayContactDetails();
            contactsPage.add(displayableContactBox);
            contactsPage.add(Box.createVerticalStrut(20));
        }

        contactsPage.revalidate(); // refresh layout
        contactsPage.repaint(); // redraw panel
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

        JLabel title = new JLabel("Add new contact");
        title.setFont(new Font("Arial", Font.BOLD, 28));

        InputBox nameField = new InputBox();
        nameField.setPreferredSize(new Dimension(760, 80));
        JTextArea nameText = new JTextArea();
        nameText.setPreferredSize(new Dimension(720, 70));
        nameText.setWrapStyleWord(true);
        nameText.setLineWrap(true);
        nameText.setOpaque(false);
        nameText.setFont(new Font("Arial", Font.PLAIN, 28));
        nameField.add(nameText, BorderLayout.CENTER);

        InputBox phoneNumberField = new InputBox();
        phoneNumberField.setPreferredSize(new Dimension(760, 80));
        JTextArea phoneNumberText = new JTextArea();
        phoneNumberText.setPreferredSize(new Dimension(720, 70));
        phoneNumberText.setWrapStyleWord(true);
        phoneNumberText.setLineWrap(true);
        phoneNumberText.setOpaque(false);
        phoneNumberText.setFont(new Font("Arial", Font.PLAIN, 28));
        phoneNumberField.add(phoneNumberText, BorderLayout.CENTER);

        JButton submitButton = new JButton("Add Contact");
        submitButton.addActionListener(e -> addContact(nameText.getText(), phoneNumberText.getText()));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneNumberField.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        constraints.gridy = 0;
        addNewContactPage.add(title, constraints);

        constraints.gridy = 1;
        addNewContactPage.add(nameField, constraints);

        constraints.gridy = 2;
        addNewContactPage.add(phoneNumberField, constraints);

        constraints.gridy = 3;
        addNewContactPage.add(submitButton, constraints);

        contactsPage.add(addNewContactPage);

        contactsPage.revalidate(); // refresh layout
        contactsPage.repaint(); // redraw panel
    }

    public void editContactPage(Contact contact) {
        contactsPage.removeAll();

        JPanel addNewContactPage = new JPanel();
        addNewContactPage.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 0, 10, 0);

        JLabel title = new JLabel("Edit contact");
        title.setFont(new Font("Arial", Font.BOLD, 28));

        InputBox nameField = new InputBox();
        nameField.setPreferredSize(new Dimension(760, 80));
        JTextArea nameText = new JTextArea();
        nameText.setPreferredSize(new Dimension(720, 70));
        nameText.setWrapStyleWord(true);
        nameText.setLineWrap(true);
        nameText.setOpaque(false);
        nameText.setFont(new Font("Arial", Font.PLAIN, 28));
        nameField.add(nameText, BorderLayout.CENTER);

        InputBox phoneNumberField = new InputBox();
        phoneNumberField.setPreferredSize(new Dimension(760, 80));
        JTextArea phoneNumberText = new JTextArea();
        phoneNumberText.setPreferredSize(new Dimension(720, 70));
        phoneNumberText.setWrapStyleWord(true);
        phoneNumberText.setLineWrap(true);
        phoneNumberText.setOpaque(false);
        phoneNumberText.setFont(new Font("Arial", Font.PLAIN, 28));
        phoneNumberField.add(phoneNumberText, BorderLayout.CENTER);

        JButton submitButton = new JButton("Edit contact");
        submitButton.addActionListener(e -> editContact(contact.getID() ,nameText.getText(), phoneNumberText.getText()));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneNumberField.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        constraints.gridy = 0;
        addNewContactPage.add(title, constraints);

        constraints.gridy = 1;
        addNewContactPage.add(nameField, constraints);

        constraints.gridy = 2;
        addNewContactPage.add(phoneNumberField, constraints);

        constraints.gridy = 3;
        addNewContactPage.add(submitButton, constraints);

        contactsPage.add(addNewContactPage);

        contactsPage.revalidate(); // refresh layout
        contactsPage.repaint(); // redraw panel
    }

    private void addContact(String contactName, String phoneNumber) {
        contacts.add(new Contact(contactName, phoneNumber));
        saveContacts(); // update contacts file
        reloadContacts();
    }

    private void editContact(UUID id, String contactName, String phoneNumber){
        for (Contact c : contacts){
            if (c.getID().equals(id)){
                c.setName(contactName);
                c.setPhoneNumber(phoneNumber);
            }
        }
        reloadContacts();
    }

    private void sortContacts(){
        if (alphabeltical){
            contacts.sort(Comparator.comparing(Contact::getTime).reversed());
            sorted = "Recently added";
            alphabeltical = false;
        } else {
            contacts.sort(Comparator.comparing(Contact::getName));
            sorted = "Alphabetical";
            alphabeltical = true;
        }
        reloadContacts();
    }

    public static List<Contact> loadContacts(){
        List<Contact> contacts = new ArrayList<>();

        // this is gonna load contacts from FILES
        try (BufferedReader br = new BufferedReader(new FileReader("src/resources/contacts"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // read line and split into parts
                String[] c = line.split(",");

                // add to arraylist
                contacts.add(new Contact(c[0], c[1], LocalDateTime.parse(c[2]), UUID.fromString(c[3])));
            }
        } catch (IOException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }

        return contacts;
    }

    /**
     * loads list of contacts into a list
     * @param username name of current user
     */
    static void launch(String username) {

        List<Contact> contacts = loadContacts();

        new ContactPage(contacts, username);
    }

    /**
     * save list of contacts to ONE FILE
     * each line stores 1 contacts details
     */
    private void saveContacts() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("src/resources/contacts"))) {
            for (Contact contact : contacts) {
                pw.println(contact.getName() + "," +
                        contact.getPhoneNumber() + "," +
                        contact.getTime() + "," +
                        contact.getID());
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }
}