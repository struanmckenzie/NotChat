import javax.swing.*;
import java.awt.*;

public class ChatPane {

    public static void launch() {

        JFrame frame = new JFrame("CONTACT_NAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Chat panel (scrollable chat list)
        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));

        // TEMPORARY: add 15 fake chats
        for (int i = 1; i <= 15; i++) {
            chatListPanel.add(chatBubble(
                    "Name " + i,
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                            "when an unknown printer took a galley of type and scrambled it to make a type specimen book." + i,
                    "25:60"
            ));
        }

        JScrollPane scrollPane = new JScrollPane(chatListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);


        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private static JPanel chatBubble(String chatName, String content, String time) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.magenta, 1)
        ));

        JLabel title = new JLabel(chatName + ": " + time);
        JLabel body = new JLabel(content);

        panel.add(title);
        panel.add(body);

        return panel;
    }

    public static void main(String[] args) {
        ChatPane.launch();
    }

}
