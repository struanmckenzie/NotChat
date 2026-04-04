import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Comparator;

import javax.swing.border.Border;
import java.util.List;
import java.util.ArrayList;

public class LandingPage {

    private JFrame frame;
    private JLabel title;
    private JPanel chatListPanel;

    private static List<Chat> chats;
    private List<Contact> contacts;

    // Dark theme colors
    private final Color BG = new Color(13, 17, 23);
    private final Color PANEL = new Color(22, 27, 34);
    private final Color BORDER = new Color(48, 54, 61);
    private final Color TEXT = new Color(201, 209, 217);
    private final Color ACCENT = new Color(88, 166, 255);
    private final Color HOVER = new Color(33, 38, 45);
    private final Color EXIT_HOVER = new Color(220, 38, 38);

    public LandingPage(JFrame frame) {
        this.frame = frame;
        launch();
    }

    public void launch() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            UIManager.put("control", BG);
            UIManager.put("info", BG);
            UIManager.put("nimbusBase", BG);
            UIManager.put("nimbusAlertYellow", BG);
            UIManager.put("nimbusDisabledText", TEXT);
            UIManager.put("nimbusFocus", ACCENT);
            UIManager.put("nimbusGreen", BG);
            UIManager.put("nimbusInfoBlue", BG);
            UIManager.put("nimbusLightBackground", BG);
            UIManager.put("nimbusOrange", BG);
            UIManager.put("nimbusRed", EXIT_HOVER);
            UIManager.put("nimbusSelectedText", TEXT);
            UIManager.put("nimbusSelectionBackground", ACCENT);
            UIManager.put("text", TEXT);

        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setVisible(false);
        frame.getContentPane().removeAll();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 800);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG);

        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                loadChats();
                refreshChatList("");
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                loadChats();
                refreshChatList("");
            }
        });

        String username = loadUsername();
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG);
        topBar.setPreferredSize(new Dimension(frame.getWidth(), 60));

        // Load contacts
        contacts = ContactPage.loadContacts();

        // Load chats
        loadChats();

        // Welcome title
        title = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        title.setForeground(TEXT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        topBar.add(title, BorderLayout.CENTER);

        // ===== Window Controls (Minimize, Maximize, Close) =====
        JPanel windowControls = new JPanel();
        windowControls.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        windowControls.setBackground(BG);

        // Maximize button
        JButton maxBtn = new JButton("⬜");
        maxBtn.setForeground(TEXT);
        maxBtn.setBackground(BG);
        maxBtn.setBorder(null);
        maxBtn.setFocusPainted(false);
        maxBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        maxBtn.setPreferredSize(new Dimension(50, 30));

        maxBtn.addActionListener(e -> {
            if ((frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                frame.setExtendedState(JFrame.NORMAL);
                frame.setSize(1280, 800);
                frame.setLocationRelativeTo(null);
            } else {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

        maxBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                maxBtn.setBackground(HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                maxBtn.setBackground(BG);
            }
        });

        // Close button
        JButton closeBtn = new JButton("X");
        closeBtn.setForeground(TEXT);
        closeBtn.setBackground(BG);
        closeBtn.setBorder(null);
        closeBtn.setFocusPainted(false);
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        closeBtn.setPreferredSize(new Dimension(50, 30));

        closeBtn.addActionListener(e -> System.exit(0));
        closeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeBtn.setBackground(EXIT_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeBtn.setBackground(BG);
            }
        });

        // Add both buttons
        windowControls.add(maxBtn);
        windowControls.add(closeBtn);

        // Add to top bar
        topBar.add(windowControls, BorderLayout.EAST);

        // Make window draggable
        final Point dragOffset = new Point();
        topBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dragOffset.x = e.getX();
                dragOffset.y = e.getY();
            }
        });
        topBar.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                frame.setLocation(e.getXOnScreen() - dragOffset.x, e.getYOnScreen() - dragOffset.y);
            }
        });

        frame.add(topBar, BorderLayout.NORTH);

        // ===== Left Panel =====
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(BG);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton edit = createStyledButton("Edit Profile");
        edit.addActionListener(e -> new ProfilePage(this));

        JButton viewCon = createStyledButton("View Contacts");
        viewCon.addActionListener(e -> ContactPage.launch(username, frame));

        JButton create = createStyledButton("Create Chat");

        // Creates a dropdown menu of all the contacts that when clicked will open the
        // existing chat or create a new one
        create.addActionListener(e -> {
            JPopupMenu popup = new JPopupMenu();
            popup.setBackground(PANEL);
            popup.setBorder(BorderFactory.createLineBorder(BORDER));

            for (Contact contact : contacts) {
                JMenuItem item = new JMenuItem(contact.getName());
                item.setBackground(PANEL);
                item.setForeground(TEXT);
                item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                item.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
                item.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent me) {
                        item.setBackground(HOVER);
                    }

                    @Override
                    public void mouseExited(MouseEvent me) {
                        item.setBackground(PANEL);
                    }
                });
                item.addActionListener(ev -> {
                    if (checkIfChatExists(contact)) {
                        for (Chat chat : chats) {
                            if (contact == chat.getContact()) {
                                chat.displayChat();
                            }
                        }
                    } else {
                        Chat newChat = new Chat(contact, contact.getID().toString(), LocalDate.now(),
                                "Unopened", "Unopened");
                        chats.add(newChat);
                        saveChats();
                    }
                    refreshChatList("");
                });
                popup.add(item);
            }

            // show it directly below the create button
            popup.show(create, 0, create.getHeight());
        });

        JButton exit = createStyledButton("Exit");
        addExitHoverEffect(exit);
        exit.addActionListener(e -> System.exit(0));

        leftPanel.add(edit);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(viewCon);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(create);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(exit);

        // Right Panel
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Search Bar
        JPanel searchBar = new JPanel(new BorderLayout());
        searchBar.setBackground(PANEL);

        // The text field for the search bar
        JTextField searchField = new JTextField();
        searchField.setBackground(PANEL);
        searchField.setForeground(TEXT);
        searchField.setBorder(BorderFactory.createLineBorder(BORDER));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        searchField.addActionListener(e -> refreshChatList(searchField.getText()));

        // Add buttons to search and reset the search bar
        JPanel searchButtons = new JPanel(new BorderLayout());
        searchButtons.setBackground(PANEL);

        // Reset button
        JButton resestButton = createStyledButton("Reset");
        resestButton.addActionListener(e -> {
            searchField.setText("");
            refreshChatList("");
        });

        // Search button
        JButton searchButton = createStyledButton("Search");
        searchButton.addActionListener(e -> refreshChatList(searchField.getText()));

        searchButtons.add(resestButton, BorderLayout.WEST);
        searchButtons.add(searchButton, BorderLayout.EAST);

        searchBar.add(searchField, BorderLayout.CENTER);
        searchBar.add(searchButtons, BorderLayout.EAST);

        // ===== Chat List =====
        chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
        chatListPanel.setBackground(BG);
        chatListPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JScrollPane scrollPane = new JScrollPane(chatListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BG);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.getVerticalScrollBar().setBlockIncrement(80);
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            protected void configureScrollBarColors() {
                this.thumbColor = BORDER;
                this.trackColor = BG;
            }

            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        rightPanel.add(searchBar, BorderLayout.NORTH);
        rightPanel.add(scrollPane);

        // ===== Split Pane =====
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(260);
        splitPane.setEnabled(false);
        splitPane.setBorder(null);
        splitPane.setDividerSize(2);
        splitPane.setUI(new javax.swing.plaf.basic.BasicSplitPaneUI() {
            public javax.swing.plaf.basic.BasicSplitPaneDivider createDefaultDivider() {
                return new javax.swing.plaf.basic.BasicSplitPaneDivider(this) {
                    public void setBorder(Border b) {
                    }

                    @Override
                    public void paint(Graphics g) {
                        g.setColor(BORDER);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                    }
                };
            }
        });

        // ===== Creates chats from the list_of_chats file =====

        refreshChatList("");

        frame.add(splitPane, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

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
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(PANEL);
            }
        });
        return button;
    }

    private void addExitHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(EXIT_HOVER);
                button.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(PANEL);
                button.setForeground(TEXT);
            }
        });
    }

    private String loadUsername() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/profile_details"))) {
            return reader.readLine();
        } catch (IOException e) {
            return "Unknown";
        }
    }

    public void loadChats() {
        chats = new ArrayList<>();

        // this is gonna load chats from FILES
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

    public Contact matchContact(String id) {
        for (Contact contact : contacts) {
            if (contact.getID().toString().equals(id)) {
                return contact;
            }
        }
        return null;
    }

    // Saves chats to a file list_of_chats
    public static void saveChats() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("src/resources/list_of_chats"))) {
            for (Chat chat : chats) {
                pw.println(chat.getChatName() + "," +
                        chat.getDate() + "," +
                        chat.getStatusContact() + "," +
                        chat.getStatusUser());
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    public void updateUsername(String newName) {
        title.setText("Welcome, " + newName);
        frame.revalidate();
        frame.repaint();
    }

    private JPanel createChatEntry(String chatName, String contact, String date, String statusContact,
            String statusUser, Chat chat) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel chatTitle = new JLabel(contact);
        chatTitle.setForeground(TEXT);
        chatTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel details = new JLabel("Date: " + chat.getReadableLastMessageTime() + " | Contact: " + statusContact + " | You: " + statusUser);
        details.setForeground(new Color(139, 148, 158));
        details.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(PANEL);
        textPanel.add(chatTitle);
        textPanel.add(details);

        JButton open = createStyledButton("Open");
        open.addActionListener(e -> chat.displayChat());

        JButton delete = createStyledButton("Delete");
        delete.addActionListener(e -> deleteChat(chatName));

        JPanel buttons = new JPanel();
        buttons.setBackground(PANEL);
        buttons.add(open);
        buttons.add(delete);

        panel.add(textPanel, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.EAST);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        return panel;
    }

    // Checks to see if there is already a chat that has been created with the given
    // contact
    private Boolean checkIfChatExists(Contact contact) {
        for (Chat chat : chats) {
            if (chat.getContact() == contact) {
                return true;
            }
        }
        return false;
    }

    // Refreshes the list of chats on the right
    private void refreshChatList(String searchValue) {
        chatListPanel.removeAll();

        // Sort chats by date (newest first)
        chats.sort(Comparator.comparing(Chat::getLastMessageTime).reversed());

        // loadChats();
        for (Chat chat : chats) {
            if (chat.containsSearchValue(searchValue)) {

                chatListPanel.add(createChatEntry(
                        chat.getChatName(),
                        chat.getContact().getName(),
                        chat.getDate().toString(),
                        chat.getStatusContact(),
                        chat.getStatusUser(),
                        chat));
                chatListPanel.add(Box.createVerticalStrut(10));
            }
        }

        chatListPanel.revalidate();
        chatListPanel.repaint();

    }

    // deletes the chat file and delets it from the list_of_chats file
    private void deleteChat(String id) {
        try {
            Path path = Paths.get("src/resources/chats/" + id);
            Files.delete(path); // throws exception if file doesn't exist
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
                if (matched != null) {
                    chats.add(new Chat(matchContact(c[0]), c[0], LocalDate.parse(c[1]), c[2], c[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading chats: " + e.getMessage());
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("src/resources/list_of_chats"))) {
            for (Chat chat : chats) {
                if (chat != null && !chat.getContact().getID().toString().equals(id)) {
                    pw.println(chat.getChatName() + "," +
                            chat.getDate() + "," +
                            chat.getStatusContact() + "," +
                            chat.getStatusUser());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }

        refreshChatList("");

    }
}