import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatMessage {

    private final Color PANEL = new Color(22, 27, 34);
    private final Color TEXT = new Color(201, 209, 217);

    String message;
    String user;
    String time;
    String chatName;

    public ChatMessage(String message, String user, String time, String chatName) {
        this.message = message;
        this.user = user;
        this.time = time;
        this.chatName = chatName;
    }

    public JPanel getDisplayMessage() {
        JPanel messageBox = new JPanel(new BorderLayout());
        messageBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        messageBox.setBackground(PANEL);

        messageBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    new ChatPane(chatName);
                }
            }
        });

        JLabel userName = new JLabel(user + ": " + time);
        userName.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        userName.setForeground(TEXT);

        JTextArea messageText = new JTextArea(message);
        messageText.setEditable(false);
        messageText.setWrapStyleWord(true);
        messageText.setLineWrap(true);
        messageText.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        messageText.setForeground(TEXT);
        messageText.setBackground(PANEL);

        messageText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    new ChatPane(chatName);
                }
            }
        });

        messageBox.add(userName, BorderLayout.NORTH);
        messageBox.add(messageText, BorderLayout.CENTER);

        return messageBox;
    }
}