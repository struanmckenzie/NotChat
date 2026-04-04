import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

public class ContactPage {

    private JFrame frame;
    private JPanel contactsPage;
    private static List<Contact> contacts;
    private List<Chat> chats;
    private final String USERNAME;
    public static ContactPage contactPage;
    private Boolean alphabeltical = false;
    private String sorted = "Alphabetical";

    public ContactPage(List<Contact> contacts, String username, JFrame frame) {
        this.contacts = contacts;
        this.USERNAME = username;
        this.contactPage = this;
        this.frame = frame;
        drawUI(contacts);
    }

    public void drawUI(List<Contact> contacts) {
        frame.getContentPane().removeAll();

        frame.setSize(1280, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Contacts");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ive made this dispose so that you can close the page
                                                                 // without closing landingpage as well
        frame.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel userName = new JLabel(USERNAME, SwingConstants.CENTER);
        userName.setFont(new Font("Arial", Font.BOLD, 32));

        header.add(userName, BorderLayout.NORTH);

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());

        // Sidebar
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new GridLayout(5, 1, 10, 10));
        sideBar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton createNewContact = new JButton("Create new contact");
        createNewContact.addActionListener(e -> addContactPage());

        JButton sortContacts = new JButton("Sort contacts");
        sortContacts.addActionListener(e -> sortContacts());

        JButton returnMainMenu = new JButton("Return");
        returnMainMenu.addActionListener(e -> new LandingPage(frame));

        sideBar.add(createNewContact);
        sideBar.add(sortContacts);
        sideBar.add(returnMainMenu);

        mainContent.add(sideBar, BorderLayout.WEST);

        // Create the contacts
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

        frame.revalidate();
        frame.repaint();
    }

    public void reloadContacts() {
        contactsPage.removeAll(); // clear previous contacts
        loadChats();

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

        //Create panel for the name input field
        JPanel nameBox = new JPanel(new BorderLayout());

        JLabel name = new JLabel("Name         ");
        name.setFont(new Font ("Arial", Font.BOLD, 32));

        InputBox nameField = new InputBox();
        nameField.setPreferredSize(new Dimension(760, 80));
        JTextField nameText = new JTextField();
        nameText.setPreferredSize(new Dimension(720, 70));
        nameText.setOpaque(false);
        nameText.setFont(new Font("Arial", Font.PLAIN, 28));
        nameText.setBorder(BorderFactory.createEmptyBorder());
        nameField.add(nameText, BorderLayout.CENTER);

        nameBox.add(name, BorderLayout.WEST);
        nameBox.add(nameField, BorderLayout.EAST);

        //Create panel for the phone number input field
        JPanel phoneBox = new JPanel(new BorderLayout());

        JLabel phone = new JLabel("Phone Number ");
        phone.setFont(new Font("Arial", Font.BOLD, 32));

        InputBox phoneNumberField = new InputBox();
        phoneNumberField.setPreferredSize(new Dimension(760, 80));
        JTextField phoneNumberText = new JTextField();
        phoneNumberText.setPreferredSize(new Dimension(720, 70));
        phoneNumberText.setOpaque(false);
        phoneNumberText.setFont(new Font("Arial", Font.PLAIN, 28));
        phoneNumberText.setBorder(BorderFactory.createEmptyBorder());
        phoneNumberField.add(phoneNumberText, BorderLayout.CENTER);

        phoneBox.add(phone, BorderLayout.WEST);
        phoneBox.add(phoneNumberField, BorderLayout.EAST);

        JButton submitButton = new JButton("Add Contact");
        submitButton.addActionListener(e -> addContact(nameText.getText(), phoneNumberText.getText()));

        JButton returnButton = new JButton("Return to contacts");
        returnButton.addActionListener(e -> reloadContacts());

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneNumberField.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        constraints.gridy = 0;
        addNewContactPage.add(title, constraints);

        constraints.gridy = 1;
        addNewContactPage.add(nameBox, constraints);

        constraints.gridy = 2;
        addNewContactPage.add(phoneBox, constraints);

        constraints.gridy = 3;
        addNewContactPage.add(submitButton, constraints);

        constraints.gridy = 4;
        addNewContactPage.add(returnButton, constraints);

        contactsPage.add(addNewContactPage);

        contactsPage.revalidate(); // refresh layout
        contactsPage.repaint(); // redraw panel
    }

    public void editContactPage(Contact contact) {
        contactsPage.removeAll();

        JPanel editContactPage = new JPanel();
        editContactPage.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 0, 10, 0);

        JLabel title = new JLabel("Edit contact");
        title.setFont(new Font("Arial", Font.BOLD, 28));

        //Create panel for the name input field
        JPanel nameBox = new JPanel(new BorderLayout());

        JLabel name = new JLabel("Name         ");
        name.setFont(new Font ("Arial", Font.BOLD, 32));

        InputBox nameField = new InputBox();
        nameField.setPreferredSize(new Dimension(760, 80));
        JTextField nameText = new JTextField(contact.getName());
        nameText.setPreferredSize(new Dimension(720, 70));
        nameText.setOpaque(false);
        nameText.setFont(new Font("Arial", Font.PLAIN, 28));
        nameText.setBorder(BorderFactory.createEmptyBorder());
        nameField.add(nameText, BorderLayout.CENTER);

        nameBox.add(name, BorderLayout.WEST);
        nameBox.add(nameField, BorderLayout.EAST);

        //Create panel for the phone number input field
        JPanel phoneBox = new JPanel(new BorderLayout());

        JLabel phone = new JLabel("Phone Number ");
        phone.setFont(new Font("Arial", Font.BOLD, 32));

        InputBox phoneNumberField = new InputBox();
        phoneNumberField.setPreferredSize(new Dimension(760, 80));
        JTextField phoneNumberText = new JTextField(contact.getPhoneNumber());
        phoneNumberText.setPreferredSize(new Dimension(720, 70));
        phoneNumberText.setOpaque(false);
        phoneNumberText.setFont(new Font("Arial", Font.PLAIN, 28));
        phoneNumberText.setBorder(BorderFactory.createEmptyBorder());
        phoneNumberField.add(phoneNumberText, BorderLayout.CENTER);

        phoneBox.add(phone, BorderLayout.WEST);
        phoneBox.add(phoneNumberField, BorderLayout.EAST);

        JButton submitButton = new JButton("Edit contact");
        submitButton
                .addActionListener(e -> editContact(contact.getID(), nameText.getText(), phoneNumberText.getText()));

        JButton returnButton = new JButton("Return to contacts");
        returnButton.addActionListener(e -> reloadContacts());

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneNumberField.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        constraints.gridy = 0;
        editContactPage.add(title, constraints);

        constraints.gridy = 1;
        editContactPage.add(nameBox, constraints);

        constraints.gridy = 2;
        editContactPage.add(phoneBox, constraints);

        constraints.gridy = 3;
        editContactPage.add(submitButton, constraints);

        constraints.gridy = 4;
        editContactPage.add(returnButton, constraints);

        contactsPage.add(editContactPage);

        contactsPage.revalidate(); // refresh layout
        contactsPage.repaint(); // redraw panel
    }

    public void viewContactPage(Contact contact) {
        contactsPage.removeAll();

        JPanel viewContactPage = new JPanel();
        viewContactPage.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 500, 10, 500);

        JLabel name = new JLabel("Name: " + contact.getName());
        name.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel phoneNumber = new JLabel("Phone Number: " + contact.getPhoneNumber());
        phoneNumber.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel date = new JLabel("Contact created: " + contact.getReadableTime());
        date.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel mostRecentMessages = new JLabel("Most recent messages");
        mostRecentMessages.setFont(new Font("Arial", Font.BOLD, 22));

        constraints.gridy = 0;
        viewContactPage.add(name, constraints);

        constraints.gridy = 1;
        viewContactPage.add(phoneNumber, constraints);

        constraints.gridy = 2;
        viewContactPage.add(date, constraints);

        Chat chat = matchContactToChat(contact);
        if (chat == null) {
            JLabel noMessagesText = new JLabel("There are no currently messages between you and this contact");
            noMessagesText.setFont(new Font("Arial", Font.BOLD, 24));
            constraints.gridy = 3;
            viewContactPage.add(noMessagesText, constraints);
        } else {
            Message[] last3Messages = chat.getLast3Messages();
            if (last3Messages.length == 0) {
                JLabel noMessagesText = new JLabel("There are currently no messages between you and this contact");
                noMessagesText.setFont(new Font("Arial", Font.BOLD, 24));
                constraints.gridy = 3;
                viewContactPage.add(noMessagesText, constraints);
            } else {
                constraints.gridy = 3;
                viewContactPage.add(mostRecentMessages, constraints);
                for (Message message : last3Messages) {
                    ChatMessage chatMessage = new ChatMessage(message.getContent(), message.getSender(),
                            message.getReadableTime(), contact.getID().toString());
                    JPanel chatMessageBox = chatMessage.getDisplayMessage();

                    constraints.gridy++;
                    constraints.fill = GridBagConstraints.HORIZONTAL;
                    constraints.weightx = 0.8;
                    viewContactPage.add(chatMessageBox, constraints);
                }
            }
        }

        JButton returnButton = new JButton("Return to contacts");
        returnButton.addActionListener(e -> reloadContacts());

        constraints.gridy++;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.NONE;
        viewContactPage.add(returnButton, constraints);

        contactsPage.add(viewContactPage);

        contactsPage.revalidate(); // refresh layout
        contactsPage.repaint(); // redraw panel
    }

    private void addContact(String contactName, String phoneNumber) {
        contacts.add(new Contact(contactName, phoneNumber));
        saveContacts(); // update contacts file
        reloadContacts();
    }

    private void editContact(UUID id, String contactName, String phoneNumber) {
        for (Contact c : contacts) {
            if (c.getID().equals(id)) {
                c.setName(contactName);
                c.setPhoneNumber(phoneNumber);
            }
        }
        saveContacts();
        reloadContacts();
    }

    private void sortContacts() {
        if (alphabeltical) {
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

    public static List<Contact> loadContacts() {
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
     * 
     * @param username name of current user
     */
    static void launch(String username, JFrame frame) {

        List<Contact> contacts = loadContacts();

        new ContactPage(contacts, username, frame);
    }

    /**
     * save list of contacts to ONE FILE
     * each line stores 1 contacts details
     */
    public static void saveContacts() {
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

    private void loadChats() {
        chats = new ArrayList<>();

        // this is gonna load contacts from FILES
        try (BufferedReader br = new BufferedReader(new FileReader("src/resources/list_of_chats"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // read line and split into parts
                String[] c = line.split(",");

                // add to arraylist
                chats.add(new Chat(matchContact(c[0]), c[0], LocalDate.parse(c[1]), c[2], c[3]));
            }
        } catch (IOException e) {
            System.out.println("Error loading chats: " + e.getMessage());
        }
    }

    private Contact matchContact(String id) {
        for (Contact contact : contacts) {
            if (contact.getID().toString().equals(id)) {
                return contact;
            }
        }
        return null;
    }

    private Chat matchContactToChat(Contact contact) {
        for (Chat chat : chats) {
            if (chat.getContact() == contact) {
                return chat;
            }
        }
        return null;
    }
}