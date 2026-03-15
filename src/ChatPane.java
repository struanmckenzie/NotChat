import javax.swing.*;
import java.awt.*;
import java.time.Instant;

public class ChatPane extends JPanel {

    private JPanel chat;
    private JTextField messageField;
    private final String chatName;
    private final Queue messages = new Queue();

    public ChatPane(String chatName) {
        this.chatName = chatName;

        initializeUI();
    }

    private void initializeUI() {
        // frame
        JFrame frame = new JFrame(chatName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLayout(new BorderLayout());

        // menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem landingPage = new JMenuItem("Return to landing page");
        JMenuItem sendContactMessage = new JMenuItem("Receive from " + chatName);
        menu.add(landingPage);
        menu.add(sendContactMessage);
        menuBar.add(menu);

        // send other action - probably just for testing
        sendContactMessage.addActionListener(e -> sendMessage(chatName));

        // save chats to messages q when leaving
        landingPage.addActionListener(e -> {
            messages.saveToCSV(chatName);
            LandingPage.launch();
        } );

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
        messageField = new JTextField();
        JButton sendButton = new JButton("Send");

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

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

        chat.add(createChatBubble(sender, text, Instant.now()));

        // refresh page in order for the message to show up
        chat.revalidate();
        chat.repaint();

        // Clear message box
        messageField.setText("");
    }

    private JPanel createChatBubble(String sender, String content, Instant time) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.magenta, 1)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel(sender + ": " + time);
        JTextArea body = new JTextArea(content);

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