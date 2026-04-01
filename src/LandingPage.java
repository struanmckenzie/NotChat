import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class LandingPage {

    private JFrame frame;
    private JLabel title;

    // Dark theme colors (GitHub Dark Tritanopia inspired)
    private final Color BG = new Color(13, 17, 23);
    private final Color PANEL = new Color(22, 27, 34);
    private final Color BORDER = new Color(48, 54, 61);
    private final Color TEXT = new Color(201, 209, 217);
    private final Color ACCENT = new Color(88, 166, 255);
    private final Color HOVER = new Color(33, 38, 45);
    private final Color EXIT_HOVER = new Color(220, 38, 38);

    public LandingPage() {
        launch();
    }

    public void launch() {
        frame = new JFrame("Landing Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(BG);

        String username = loadUsername();

        title = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 34));
        title.setForeground(TEXT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(title, BorderLayout.NORTH);

        // LEFT PANEL
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(BG);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton edit = createStyledButton("Edit Profile");
        edit.addActionListener(e -> new ProfilePage(this));

        JButton viewCon = createStyledButton("View Contacts");
        viewCon.addActionListener(e -> ContactPage.launch(username));

        JButton create = createStyledButton("Create Chat");

        JButton exit = createStyledButton("Exit");
        addExitHoverEffect(exit);
        exit.addActionListener(e -> System.exit(0));

        leftPanel.add(edit);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(viewCon);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(create);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(exit);

        // CHAT LIST
        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
        chatListPanel.setBackground(BG);

        JScrollPane scrollPane = new JScrollPane(chatListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BG);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, scrollPane);
        splitPane.setDividerLocation(260);
        splitPane.setEnabled(false);
        splitPane.setBorder(null);

        for (int i = 1; i <= 15; i++) {
            chatListPanel.add(createChatEntry(
                    "Chat " + i,
                    "Contact " + i,
                    "2026-03-11",
                    (i % 2 == 0) ? "Delivered" : "Read",
                    (i % 3 == 0) ? "Unread" : "Read"
            ));
            chatListPanel.add(Box.createVerticalStrut(10));
        }

        frame.add(splitPane);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setForeground(TEXT);
        button.setBackground(PANEL);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(PANEL);
            }
        });

        return button;
    }

    private void addExitHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(EXIT_HOVER);
                button.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(PANEL);
                button.setForeground(TEXT);
            }
        });
    }

    private String loadUsername() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/profile_details"))) {
            return reader.readLine();
        } catch (IOException e) {
            return "Unknown";
        }
    }

    private JPanel createChatEntry(String chatName, String contact, String date, String statusContact, String statusUser) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel title = new JLabel(chatName + " — " + contact);
        title.setForeground(TEXT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel details = new JLabel("Date: " + date + " | Contact: " + statusContact + " | You: " + statusUser);
        details.setForeground(new Color(139, 148, 158));
        details.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(PANEL);
        textPanel.add(title);
        textPanel.add(details);

        JButton open = createStyledButton("Open");

        panel.add(textPanel, BorderLayout.CENTER);
        panel.add(open, BorderLayout.EAST);

        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        return panel;
    }
}
