import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ChatPane extends JFrame {

    private JPanel chat;
    private JTextField messageField;
    private final String chatName;
    private final Queue messages = new Queue();

    /**
     * overloaded constructor
     * @param chatName name oof contact
     */
    public ChatPane(String chatName) {
        this.chatName = chatName;
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
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // listens for when close button is pressed and calls
        // message method to save current conversation to a csv
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                messages.saveToCSV(chatName);
                frame.dispose();
            }
        });

        // loads previous chats into messages queue when window opened
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                // load messages into q
                messages.loadFromCSV(chatName);
                int len = messages.getLength();

                // send messages on chat
                for (int i = 0; i<len; i++) {
                    Message m = messages.pop();
                    sendMessage(m.getSender(), m.getContent(), m.getTimestamp());
                }
            }
        });

        // menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu m = new JMenu("File");
        JMenuItem sendContactMessage = new JMenuItem("Receive from " + chatName);
        m.add(sendContactMessage);
        menuBar.add(m);

        // send other action - probably just for testing
        sendContactMessage.addActionListener(e -> sendMessage(chatName));

        frame.setJMenuBar(menuBar);

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

        frame.setVisible(true);
    }

    /**
     * formats a message with name, time, and text
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
        // create the panel itself
        JPanel panel = new JPanel(new GridLayout(2, 2));

        // format appearance
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.magenta, 1)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // add title and content
        JLabel title = new JLabel(sender + ": " + time);
        JTextArea body = new JTextArea(content);

        // set appearance of body text
        body.setLineWrap(true);
        body.setWrapStyleWord(true);
        body.setEditable(false);
        body.setOpaque(false);

        panel.add(title);
        panel.add(body);

        return panel;
    }

    static void main() {
        new ChatPane("John");
    }
}