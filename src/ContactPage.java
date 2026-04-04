import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

public class ContactPage {

    // Dark theme colors (matching LandingPage)
    private final Color BG = new Color(13, 17, 23);
    private final Color PANEL = new Color(22, 27, 34);
    private final Color BORDER = new Color(48, 54, 61);
    private final Color TEXT = new Color(201, 209, 217);
    private final Color HOVER = new Color(33, 38, 45);
    private final Color ACCENT = new Color(88, 166, 255);

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
        frame.getContentPane().setBackground(BG);

        frame.setSize(1280, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Contacts");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel userName = new JLabel(USERNAME, SwingConstants.CENTER);
        userName.setFont(new Font("Segoe UI", Font.BOLD, 32));
        userName.setForeground(TEXT);
        header.add(userName, BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(BG);

        // Sidebar
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(BG);
        sideBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        JButton createNewContact = createStyledButton("Create new contact");
        createNewContact.addActionListener(e -> addContactPage());

        JButton sortContacts = createStyledButton("Sort contacts");
        sortContacts.addActionListener(e -> sortContacts());

        JButton returnMainMenu = createStyledButton("Return");
        returnMainMenu.addActionListener(e -> new LandingPage(frame));

        sideBar.add(createNewContact);
        sideBar.add(Box.createVerticalStrut(20));
        sideBar.add(sortContacts);
        sideBar.add(Box.createVerticalStrut(20));
        sideBar.add(returnMainMenu);

        mainContent.add(sideBar, BorderLayout.WEST);

        // Contacts panel
        contactsPage = new JPanel();
        contactsPage.setLayout(new BoxLayout(contactsPage, BoxLayout.Y_AXIS));
        contactsPage.setBackground(BG);
        contactsPage.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        reloadContacts();

        JScrollPane contactsPageScrollable = new JScrollPane(contactsPage);
        contactsPageScrollable.getVerticalScrollBar().setUnitIncrement(20);
        contactsPageScrollable.setBorder(null);
        contactsPageScrollable.getViewport().setBackground(BG);

        mainContent.add(contactsPageScrollable, BorderLayout.CENTER);

        frame.add(header, BorderLayout.NORTH);
        frame.add(mainContent, BorderLayout.CENTER);

        sortContacts(); // initial sort

        frame.revalidate();
        frame.repaint();
    }

    public void reloadContacts() {
        contactsPage.removeAll();
        loadChats();

        JLabel sortTitle = new JLabel(sorted);
        sortTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        sortTitle.setForeground(TEXT);
        sortTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contactsPage.add(sortTitle);
        contactsPage.add(Box.createVerticalStrut(20));

        for (Contact contact : contacts) {
            ContactDetails contactBox = new ContactDetails(contact, contacts, contactPage);
            JPanel displayableContactBox = contactBox.getDisplayContactDetails();
            contactsPage.add(displayableContactBox);
            contactsPage.add(Box.createVerticalStrut(20));
        }

        contactsPage.revalidate();
        contactsPage.repaint();
    }

    public void addContactPage() {
        contactsPage.removeAll();

        JPanel addNewContactPage = new JPanel(new GridBagLayout());
        addNewContactPage.setBackground(BG);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 0, 10, 0);

        JLabel title = new JLabel("Add new contact");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(TEXT);

        // Name field
        JPanel nameBox = new JPanel(new BorderLayout());
        nameBox.setBackground(BG);
        JLabel name = new JLabel("Name         ");
        name.setFont(new Font("Segoe UI", Font.BOLD, 32));
        name.setForeground(TEXT);

        InputBox nameField = new InputBox();
        nameField.setBackground(PANEL);
        nameField.setPreferredSize(new Dimension(760, 80));
        JTextField nameText = new JTextField();
        nameText.setPreferredSize(new Dimension(720, 70));
        nameText.setOpaque(false);
        nameText.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        nameText.setForeground(TEXT);
        nameText.setCaretColor(TEXT);
        nameText.setBorder(BorderFactory.createEmptyBorder());
        nameField.add(nameText, BorderLayout.CENTER);

        nameBox.add(name, BorderLayout.WEST);
        nameBox.add(nameField, BorderLayout.EAST);

        // Phone field
        JPanel phoneBox = new JPanel(new BorderLayout());
        phoneBox.setBackground(BG);
        JLabel phone = new JLabel("Phone Number ");
        phone.setFont(new Font("Segoe UI", Font.BOLD, 32));
        phone.setForeground(TEXT);

        InputBox phoneNumberField = new InputBox();
        phoneNumberField.setBackground(PANEL);
        phoneNumberField.setPreferredSize(new Dimension(760, 80));
        JTextField phoneNumberText = new JTextField();
        phoneNumberText.setPreferredSize(new Dimension(720, 70));
        phoneNumberText.setOpaque(false);
        phoneNumberText.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        phoneNumberText.setForeground(TEXT);
        phoneNumberText.setCaretColor(TEXT);
        phoneNumberText.setBorder(BorderFactory.createEmptyBorder());
        phoneNumberField.add(phoneNumberText, BorderLayout.CENTER);

        phoneBox.add(phone, BorderLayout.WEST);
        phoneBox.add(phoneNumberField, BorderLayout.EAST);

        JButton submitButton = createStyledButton("Add Contact");
        submitButton.addActionListener(e -> addContact(nameText.getText(), phoneNumberText.getText()));

        JButton returnButton = createStyledButton("Return to contacts");
        returnButton.addActionListener(e -> reloadContacts());

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
        contactsPage.revalidate();
        contactsPage.repaint();
    }

    public void editContactPage(Contact contact) {
        contactsPage.removeAll();

        JPanel editContactPage = new JPanel(new GridBagLayout());
        editContactPage.setBackground(BG);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 0, 10, 0);

        JLabel title = new JLabel("Edit contact");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(TEXT);

        // Name field
        JPanel nameBox = new JPanel(new BorderLayout());
        nameBox.setBackground(BG);
        JLabel name = new JLabel("Name         ");
        name.setFont(new Font("Segoe UI", Font.BOLD, 32));
        name.setForeground(TEXT);

        InputBox nameField = new InputBox();
        nameField.setBackground(PANEL);
        nameField.setPreferredSize(new Dimension(760, 80));
        JTextField nameText = new JTextField(contact.getName());
        nameText.setPreferredSize(new Dimension(720, 70));
        nameText.setOpaque(false);
        nameText.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        nameText.setForeground(TEXT);
        nameText.setCaretColor(TEXT);
        nameText.setBorder(BorderFactory.createEmptyBorder());
        nameField.add(nameText, BorderLayout.CENTER);

        nameBox.add(name, BorderLayout.WEST);
        nameBox.add(nameField, BorderLayout.EAST);

        // Phone field
        JPanel phoneBox = new JPanel(new BorderLayout());
        phoneBox.setBackground(BG);
        JLabel phone = new JLabel("Phone Number ");
        phone.setFont(new Font("Segoe UI", Font.BOLD, 32));
        phone.setForeground(TEXT);

        InputBox phoneNumberField = new InputBox();
        phoneNumberField.setBackground(PANEL);
        phoneNumberField.setPreferredSize(new Dimension(760, 80));
        JTextField phoneNumberText = new JTextField(contact.getPhoneNumber());
        phoneNumberText.setPreferredSize(new Dimension(720, 70));
        phoneNumberText.setOpaque(false);
        phoneNumberText.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        phoneNumberText.setForeground(TEXT);
        phoneNumberText.setCaretColor(TEXT);
        phoneNumberText.setBorder(BorderFactory.createEmptyBorder());
        phoneNumberField.add(phoneNumberText, BorderLayout.CENTER);

        phoneBox.add(phone, BorderLayout.WEST);
        phoneBox.add(phoneNumberField, BorderLayout.EAST);

        JButton submitButton = createStyledButton("Edit contact");
        submitButton
                .addActionListener(e -> editContact(contact.getID(), nameText.getText(), phoneNumberText.getText()));

        JButton returnButton = createStyledButton("Return to contacts");
        returnButton.addActionListener(e -> reloadContacts());

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
        contactsPage.revalidate();
        contactsPage.repaint();
    }

    public void viewContactPage(Contact contact) {
        contactsPage.removeAll();

        JPanel viewContactPage = new JPanel(new GridBagLayout());
        viewContactPage.setBackground(BG);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 500, 10, 500);

        JLabel nameLabel = new JLabel("Name: " + contact.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        nameLabel.setForeground(TEXT);

        JLabel phoneNumber = new JLabel("Phone Number: " + contact.getPhoneNumber());
        phoneNumber.setFont(new Font("Segoe UI", Font.PLAIN, 26));
        phoneNumber.setForeground(TEXT);

        JLabel date = new JLabel("Contact created: " + contact.getReadableTime());
        date.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        date.setForeground(TEXT);

        JLabel mostRecentMessages = new JLabel("Most recent messages");
        mostRecentMessages.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        mostRecentMessages.setForeground(TEXT);

        constraints.gridy = 0;
        viewContactPage.add(nameLabel, constraints);
        constraints.gridy = 1;
        viewContactPage.add(phoneNumber, constraints);
        constraints.gridy = 2;
        viewContactPage.add(date, constraints);

        Chat chat = matchContactToChat(contact);
        if (chat == null) {
            JLabel noMessagesText = new JLabel("There are no currently messages between you and this contact");
            noMessagesText.setFont(new Font("Segoe UI", Font.PLAIN, 24));
            noMessagesText.setForeground(TEXT);
            constraints.gridy = 3;
            viewContactPage.add(noMessagesText, constraints);
        } else {
            Message[] last3Messages = chat.getLast3Messages();
            if (last3Messages.length == 0) {
                JLabel noMessagesText = new JLabel("There are currently no messages between you and this contact");
                noMessagesText.setFont(new Font("Segoe UI", Font.PLAIN, 24));
                noMessagesText.setForeground(TEXT);
                constraints.gridy = 3;
                viewContactPage.add(noMessagesText, constraints);
            } else {
                constraints.gridy = 3;
                viewContactPage.add(mostRecentMessages, constraints);
                for (Message message : last3Messages) {
                    ChatMessage chatMessage = new ChatMessage(message.getContent(), message.getSender(),
                            message.getReadableTime(), contact.getID().toString(), chat);
                    JPanel chatMessageBox = chatMessage.getDisplayMessage();

                    constraints.gridy++;
                    constraints.fill = GridBagConstraints.HORIZONTAL;
                    constraints.weightx = 0.8;
                    viewContactPage.add(chatMessageBox, constraints);
                }
            }
        }

        JButton returnButton = createStyledButton("Return to contacts");
        returnButton.addActionListener(e -> reloadContacts());

        constraints.gridy++;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.NONE;
        viewContactPage.add(returnButton, constraints);

        contactsPage.add(viewContactPage);
        contactsPage.revalidate();
        contactsPage.repaint();
    }

    // Makes buttons that have a consistent custom style
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setForeground(TEXT);
        button.setBackground(PANEL);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(HOVER);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(PANEL);
            }
        });
        return button;
    }

    private void addContact(String contactName, String phoneNumber) {
        contacts.add(new Contact(contactName, phoneNumber));
        saveContacts();
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

    // Load the contatcs from the contacts file
    public static List<Contact> loadContacts() {
        List<Contact> contacts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/resources/contacts"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] c = line.split(",");
                contacts.add(new Contact(c[0], c[1], LocalDateTime.parse(c[2]), UUID.fromString(c[3])));
            }
        } catch (IOException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
        return contacts;
    }

    static void launch(String username, JFrame frame) {
        List<Contact> contacts = loadContacts();
        new ContactPage(contacts, username, frame);
    }

    public static void saveContacts() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("src/resources/contacts"))) {
            for (Contact contact : contacts) {
                pw.println(contact.getName() + "," + contact.getPhoneNumber() + "," +
                        contact.getTime() + "," + contact.getID());
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    private void loadChats() {
        chats = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/resources/list_of_chats"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] c = line.split(",");
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