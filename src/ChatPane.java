import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;

/**
 * open chat window, load chats from selected chat
 * 
 * @author struan
 */
public class ChatPane extends JFrame {

    private JPanel chat;
    private JTextField messageField;
    private final String chatName;
    private final String CHAT_LOCATION;
    private final Queue messages = new Queue();
    private Chat chatDetails;

    // theme
    private final Color BG = new Color(13, 17, 23);
    private final Color BORDER = new Color(48, 54, 61);
    private final Color TEXT = new Color(201, 209, 217);

    /**
     * overloaded constructor
     * 
     * @param chatName name oof contact
     */
    public ChatPane(String chatName, Chat chatDetails) {
        this.chatName = chatName;
        this.CHAT_LOCATION = "src/resources/chats/" + chatName;
        this.chatDetails = chatDetails;
        initializeUI();
    }

    /**
     * create and deploy gui
     */
    private void initializeUI() {
        // get screen size to set size of chat page accordingly
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 3;
        int height = screenSize.height * 5 / 6;

        // frame
        JFrame frame = new JFrame(chatName);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setBackground(BG);
        frame.setResizable(false);

        // listens for when close button is pressed and calls
        // message method to save current conversation to a csv
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                messages.saveToCSV(CHAT_LOCATION);
                frame.dispose();
            }
        });

        // loads previous chats into messages queue when window opened
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                messages.loadFromCSV(CHAT_LOCATION);
                Node current = messages.getTop();
                while (current != null) {
                    Message m = current.getData();
                    chat.add(createChatBubble(m.getSender(), m.getContent(), m.getTimestamp()));
                    current = current.getNext();
                }
                chat.revalidate();
                chat.repaint();
            }
        });

        // search bar
        JPanel searchBar = new JPanel(new BorderLayout());
        JTextField searchBox = new JTextField();
        JButton search = new JButton("Search");

        // style button
        ProfilePage.aesthetic(search);

        searchBar.add(searchBox, BorderLayout.CENTER);
        searchBar.add(search, BorderLayout.EAST);
        frame.add(searchBar, BorderLayout.NORTH);

        // search for text from the search box in messages
        search.addActionListener(e -> {
            String query = searchBox.getText();
            if (query != null && !query.trim().isEmpty()) {
                // Create a new window for search results
                JFrame searchResultsFrame = new JFrame("Search Results: \"" + query + "\"");
                searchResultsFrame.setSize(600, 400);
                searchResultsFrame.setLocationRelativeTo(frame);

                JPanel resultsPanel = new JPanel();
                resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

                JScrollPane scrollPane = new JScrollPane(resultsPanel);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.getVerticalScrollBar().setUnitIncrement(30);

                searchResultsFrame.add(scrollPane);

                // Search through messages
                int resultCount = 0;
                for (int i = 0; i < messages.getLength(); i++) {
                    Message m = messages.peek(i);
                    if (m != null && m.getContent().toLowerCase().contains(query.toLowerCase())) {
                        JPanel highlightedBubble = createChatBubble(m.getSender(), m.getContent(), m.getTimestamp());
                        highlightedBubble.setBackground(BORDER);
                        resultsPanel.add(highlightedBubble);
                        resultCount++;
                    }
                }

                if (resultCount == 0) {
                    JLabel noResults = new JLabel("No messages found matching \"" + query + "\"");
                    noResults.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    resultsPanel.add(noResults);
                }

                searchResultsFrame.setVisible(true);
            }
        });

        // chat page
        chat = new JPanel();
        chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(chat);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);

        frame.add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        messageField = new JTextField();
        JButton sendButton = new JButton("Send");

        ProfilePage.aesthetic(sendButton);

        // add buttons to button panel
        buttonPanel.add(sendButton);

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.SOUTH);

        // Send Button Action
        sendButton.addActionListener(e -> sendMessage("You"));

        // Enter Key Action
        messageField.addActionListener(e -> sendMessage("You"));

        frame.setVisible(true);
    }

    /**
     * formats a message with name, time, and text
     * this is for chats sent by the user
     */
    private void sendMessage(String sender) {
        chatDetails.setStatusContact("Unopened");
        LandingPage.saveChats();

        String text = messageField.getText().trim();
        if (text.isEmpty())
            return;

        // save to message queue
        Message msg = new Message(sender, text);
        messages.push(msg);
        messages.saveToCSV(CHAT_LOCATION);;

        chat.add(createChatBubble(sender, text, LocalDateTime.now()));

        // refresh page in order for the message to show up
        chat.revalidate();
        chat.repaint();

        // Clear message box
        messageField.setText("");
    }

    /**
     * creates a chat bubble
     * 
     * @param sender  person who sent the message
     * @param content what is in the message
     * @param time    time
     * @return a chat bubble panel
     */
    private JPanel createChatBubble(String sender, String content, LocalDateTime time) {
        // Main panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BORDER);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createCompoundBorder(BorderFactory.createSoftBevelBorder(0),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5))));

        // Title
        JLabel title = new JLabel(sender + ": " + time);
        title.setForeground(TEXT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Message body
        JTextArea body = new JTextArea(content);
        body.setForeground(TEXT);
        body.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        body.setLineWrap(true);
        body.setWrapStyleWord(true);
        body.setEditable(false);
        body.setOpaque(false);

        // Panel to hold title and body
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(BORDER);
        textPanel.add(title);
        textPanel.add(body);

        // Delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        deleteButton.setPreferredSize(new Dimension(60, 24));
        deleteButton.setMaximumSize(new Dimension(60, 24));
        deleteButton.setFocusPainted(false);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(Color.GRAY);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteButton.setBackground(new Color(180, 30, 30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deleteButton.setBackground(Color.GRAY);
            }
        });

        // Button action: delete from queue and file, then remove bubble from UI
        deleteButton.addActionListener(e -> {
            messages.deleteMessageByTimestamp(time);
            messages.saveToCSV(CHAT_LOCATION);
            Container parent = panel.getParent();
            if (parent != null) {
                parent.remove(panel);
                parent.revalidate();
                parent.repaint();
            }
        });

        // Panel to hold button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BORDER);
        buttonPanel.add(deleteButton);

        // Assemble the bubble
        panel.add(textPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}