import javax.swing.*;
import java.awt.*;
import java.time.Instant;

public class ChatPane {

    public static void launch() {

        JFrame frame = new JFrame("CONTACT_NAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenuItem landingPage = new JMenuItem("Landing Page");
        menuBar.add(landingPage);
        frame.setJMenuBar(menuBar);

        //landingPage.addActionListener(e -> LandingPage.launch());

        // Chat panel (scrollable chat list)
        JPanel chat = new JPanel();
        chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField messageField = new JTextField();
        JButton sendButton = new JButton("Send");

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);


        // Send Button Action
        sendButton.addActionListener(e -> {
            chat.add(chatBubble("TEMP_NAME", messageField.getText().trim(), Instant.now()));

            // refresh when sent
            chat.revalidate();
            chat.repaint();

            // make new field blank, ready for new message
            messageField.setText("");
        });


        // Enter Key Action
        messageField.addActionListener(e -> {
            chat.add(chatBubble("TEMP_NAME", messageField.getText().trim(), Instant.now()));


            // refresh when sent
            chat.revalidate();
            chat.repaint();

            // make new field blank, ready for new message
            messageField.setText("");
        });

        JScrollPane scrollPane = new JScrollPane(chat);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);


        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private static JPanel chatBubble(String chatName, String content, Instant time) {
        // configure how the chat bubble looks
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.magenta, 1)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // configure how chat content is displayed
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
        ChatPane.launch();
    }

}
