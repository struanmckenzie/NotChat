import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LandingPage {

    public static void launch() {

        JFrame frame = new JFrame("Landing Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setIconImage(
                new ImageIcon(LandingPage.class.getResource("/resources/images/alaba.png")).getImage()
        );

        // gets name of user from the profile details file
        String username = "Unknown user";
        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/profile_details"))) {
            username = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error getting name " + e.getMessage());
        }


        JLabel title = new JLabel(username, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        frame.add(title, BorderLayout.NORTH);

        // LEFT PANEL (buttons)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(5, 1, 10, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton edit = new JButton("Edit Profile");
        edit.addActionListener(e ->
                new ProfilePage()
        );

        JButton viewCon = new JButton("View Contacts");
        viewCon.addActionListener(e ->
                JOptionPane.showMessageDialog(null, "temporary message, View contacts function")
        );

        JButton create = new JButton("Create New Chat");
        create.addActionListener(e ->
                JOptionPane.showMessageDialog(null, "temporary message, create chat function")
        );

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Bye!");
            System.exit(0);
        });

        leftPanel.add(edit);
        leftPanel.add(viewCon);
        leftPanel.add(create);
        leftPanel.add(exit);

        // RIGHT PANEL (scrollable chat list)
        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));



        JScrollPane scrollPane = new JScrollPane(chatListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);

        // SPLIT PANE (left menu + right chat list)
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                leftPanel,
                scrollPane
        );

        splitPane.setDividerLocation(300);
        splitPane.setEnabled(false);

        // TEMPORARY: add 15 fake chats
        for (int i = 1; i <= 15; i++) {
            chatListPanel.add(createChatEntry(
                    splitPane,
                    "Chat " + i,
                    "Contact " + i,
                    "2026-03-11",
                    (i % 2 == 0) ? "Delivered" : "Read",
                    (i % 3 == 0) ? "Unread" : "Read"
            ));
        }

        frame.add(splitPane);
        frame.setVisible(true);

        // BUTTON ACTIONS
        exit.addActionListener(e -> System.exit(0));
    }

    private static JPanel createChatEntry(
            JSplitPane splitPane,
            String chatName,
            String contact,
            String date,
            String statusContact,
            String statusUser
    ) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.GRAY, 1)
        ));
        panel.setPreferredSize(new Dimension(0, 80));

        JLabel title = new JLabel(chatName + " — " + contact);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel details = new JLabel("Date: " + date +
                " | Contact: " + statusContact +
                " | You: " + statusUser);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.add(title);
        textPanel.add(details);

        JButton openButton = new JButton("Open Chat");
        openButton.addActionListener(e -> {
            new ChatPane(chatName);
        });

        panel.add(textPanel, BorderLayout.CENTER);
        panel.add(openButton, BorderLayout.EAST);

        return panel;
    }
}