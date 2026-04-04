import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatMessage {

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
        JPanel messageBox = new JPanel();
        messageBox.setLayout(new BorderLayout());
        messageBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        messageBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if (SwingUtilities.isLeftMouseButton(e)){
                    new ChatPane(chatName);
                }
            }
        });

        JLabel userName = new JLabel(user + ": " + time);
        userName.setFont(new Font("Arial", Font.PLAIN, 24));

        JTextArea messageText = new JTextArea(message);
        messageText.setEditable(false);
        messageText.setWrapStyleWord(true);
        messageText.setLineWrap(true);

        messageText.setFont(new Font("Arial", Font.PLAIN, 22));

        messageText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if (SwingUtilities.isLeftMouseButton(e)){
                    new ChatPane(chatName);
                }
            }
        });

        messageBox.setBackground(Color.GRAY);
        messageText.setBackground(Color.GRAY);

        messageBox.add(userName, BorderLayout.NORTH);
        messageBox.add(messageText);



        return messageBox;
    }

}