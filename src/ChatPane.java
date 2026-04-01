import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;

/**
 * open chat window, load chats from selected chat
 * @author struan
 */
public class ChatPane extends JFrame {

    private JPanel chat;
    private JTextField messageField;
    private final String chatName;
    private final String CHAT_LOCATION;
    private final Queue messages = new Queue();

    /**
     * overloaded constructor
     * @param chatName name oof contact
     */
    public ChatPane(String chatName) {
        this.chatName = chatName;
        this.CHAT_LOCATION = "src/resources/chats/" + chatName;
        initializeUI();
    }

    /**
     * create and deploy gui
     */
    private void initializeUI() {
        // frame
        JFrame frame = new JFrame(chatName);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLocationRelativeTo(null);

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
                // load messages into q
                messages.loadFromCSV(CHAT_LOCATION);
                int len = messages.getLength();

                // send messages on chat
                for (int i = 0; i<len; i++) {
                    Message m = messages.pop();
                    sendMessage(m.getSender(), m.getContent(), m.getTimestamp());
                }
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem search =  new JMenuItem("Search");
        file.add(search);
        menuBar.add(file);
        frame.setJMenuBar(menuBar);

        search.addActionListener(e -> {
            String query = JOptionPane.showInputDialog(frame, "Enter search:");
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
                        highlightedBubble.setBackground(Color.ORANGE);
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
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));

        messageField = new JTextField();
        JButton sendButton = new JButton("Send");
        JButton deleteButton = new JButton("Delete");

        // add buttons to button panel
        buttonPanel.add(sendButton);
        buttonPanel.add(deleteButton);

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.SOUTH);

        // Send Button Action
        sendButton.addActionListener(e -> sendMessage("You"));

        // Enter Key Action
        messageField.addActionListener(e -> sendMessage("You"));

        deleteButton.addActionListener(e -> {
            // DELETE MESSAGE WHEN PRESSED!!!!!!!!!!!!!!
        });

        frame.setVisible(true);
    }

    /**
     * formats a message with name, time, and text
     * this is for chats sent by the user
     */
    private void sendMessage(String sender) {
        String text = messageField.getText().trim();
        if (text.isEmpty()) return;

        // save to message queue
        Message msg = new Message(sender, text);
        messages.push(msg);

        chat.add(createChatBubble(sender, text, LocalDateTime.now()));

        // refresh page in order for the message to show up
        chat.revalidate();
        chat.repaint();

        // Clear message box
        messageField.setText("");
    }

    /**
     * send message from queue
     * @param sender;
     * @param content;
     * @param t time of messaghe sent
     */
    private void sendMessage(String sender, String content, LocalDateTime t) {
        chat.add(createChatBubble(sender, content, t));

        // save to message queue
        Message msg = new Message(sender, content, t);
        messages.push(msg);

        // refresh page in order for the message to show up
        chat.revalidate();
        chat.repaint();
    }

    /**
     * creates a chat bubble
     * @param sender person who sent the message
     * @param content what is in the message
     * @param time time
     * @return a chat bubble panel
     */
    private JPanel createChatBubble(String sender, String content, LocalDateTime time) {
        // create the chat itself
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(Color.ORANGE);

        // format border
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5,5,5,5),
                BorderFactory.createCompoundBorder(BorderFactory.createSoftBevelBorder(0),
                        BorderFactory.createEmptyBorder(5,5,5,5)
                )));

        // add title and content
        JLabel title = new JLabel(sender + ": " + time);
        JTextArea body = new JTextArea(content);

        body.setFont(new Font(null, Font.PLAIN, 15));

        // set appearance of body text
        body.setLineWrap(true);
        body.setWrapStyleWord(true);
        body.setEditable(false);
        body.setOpaque(false);

        /* set side chat is on
        if (sender.equals("You"))
            panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        else
            panel.setAlignmentX(Component.RIGHT_ALIGNMENT);

         */

        panel.add(title);
        panel.add(body);

        return panel;
    }
}