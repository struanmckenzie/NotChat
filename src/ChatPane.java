import javax.swing.*;
import java.awt.*;
import java.time.Instant;
//
public class ChatPane {

    private JFrame frame;
    private JPanel chat;
    private JTextField messageField;
    private JButton sendButton;
    private final Queue messages = new Queue();
    private String chatName = "TEMP_NAME";

    public ChatPane() {
        initializeUI();
    }

    public void launch() {
        frame.setVisible(true);
    }

    private void initializeUI() {

        frame = new JFrame(chatName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLayout(new BorderLayout());

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenuItem landingPage = new JMenuItem("Landing Page");
        menuBar.add(landingPage);
        landingPage.addActionListener(e -> {
            messages.saveToCSV(chatName);
            LandingPage.launch();
        } );

        frame.setJMenuBar(menuBar);

        // Chat panel (scrollable chat list)
        chat = new JPanel();
        chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(chat);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);

        frame.add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("Send");

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.SOUTH);

        // Send Button Action
        sendButton.addActionListener(e -> sendMessage());

        // Enter Key Action
        messageField.addActionListener(e -> sendMessage());
    }

    private void sendMessage() {
        String text = messageField.getText().trim();
        if (text.isEmpty()) return;

        // save to message queue
        Message msg = new Message("You", text);
        messages.push(msg);

        chat.add(createChatBubble("TEMP_NAME", text, Instant.now()));

        chat.revalidate();
        chat.repaint();

        // Clear message box
        messageField.setText("");
    }

    private JPanel createChatBubble(String chatName, String content, Instant time) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.magenta, 1)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel(chatName + ": " + time);
        JTextArea body = new JTextArea(content);

        body.setLineWrap(true);
        body.setWrapStyleWord(true);
        body.setEditable(false);
        body.setOpaque(false);

        panel.add(title);
        panel.add(body);

        return panel;
    }

    public static void main(String[] args) {
        new ChatPane().launch();
    }
}
