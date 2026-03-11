import javax.swing.*;
import java.awt.*;
import java.time.Instant;

/**
 * Class to view chat history
 * @author Struan McK
 */
public class ChatPage extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    public ChatPage() {
        setTitle("Simple Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem menuItem = new JMenuItem("Landing Page");
        menu.add(menuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // landing page action
        menuItem.addActionListener(e -> LandingPage.launch());


        // Chat Area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("Send");
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);


        // Send Button Action
        sendButton.addActionListener(e -> sendMessage());

        // Enter Key Action
        messageField.addActionListener(e -> sendMessage());

        setVisible(true);
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            chatArea.append("You: " + message + "\n"); // Add message to chat area
            messageField.setText("");  // Clear input field
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatPage::new);
    }
}
