import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import javax.swing.border.Border;

public class LandingPage {

    private JFrame frame;
    private JLabel title;

    // Dark theme colors
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
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            UIManager.put("control", BG);
            UIManager.put("info", BG);
            UIManager.put("nimbusBase", BG);
            UIManager.put("nimbusAlertYellow", BG);
            UIManager.put("nimbusDisabledText", TEXT);
            UIManager.put("nimbusFocus", ACCENT);
            UIManager.put("nimbusGreen", BG);
            UIManager.put("nimbusInfoBlue", BG);
            UIManager.put("nimbusLightBackground", BG);
            UIManager.put("nimbusOrange", BG);
            UIManager.put("nimbusRed", EXIT_HOVER);
            UIManager.put("nimbusSelectedText", TEXT);
            UIManager.put("nimbusSelectionBackground", ACCENT);
            UIManager.put("text", TEXT);

        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(BG);

        String username = loadUsername();
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG);
        topBar.setPreferredSize(new Dimension(frame.getWidth(), 60));

        // Welcome title
        title = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        title.setForeground(TEXT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        topBar.add(title, BorderLayout.CENTER);


        // ===== Window Controls (Minimize, Maximize, Close) =====
        JPanel windowControls = new JPanel();
        windowControls.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        windowControls.setBackground(BG);

        // Maximize button
        JButton maxBtn = new JButton("⬜");
        maxBtn.setForeground(TEXT);
        maxBtn.setBackground(BG);
        maxBtn.setBorder(null);
        maxBtn.setFocusPainted(false);
        maxBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        maxBtn.setPreferredSize(new Dimension(50, 30));

        maxBtn.addActionListener(e -> {
            if ((frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                frame.setExtendedState(JFrame.NORMAL);
            } else {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

        maxBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { maxBtn.setBackground(HOVER); }
            @Override
            public void mouseExited(MouseEvent e) { maxBtn.setBackground(BG); }
        });

        // Close button
        JButton closeBtn = new JButton("X");
        closeBtn.setForeground(TEXT);
        closeBtn.setBackground(BG);
        closeBtn.setBorder(null);
        closeBtn.setFocusPainted(false);
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        closeBtn.setPreferredSize(new Dimension(50, 30));

        closeBtn.addActionListener(e -> System.exit(0));
        closeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { closeBtn.setBackground(EXIT_HOVER); }
            @Override
            public void mouseExited(MouseEvent e) { closeBtn.setBackground(BG); }
        });

        // Add both buttons
        windowControls.add(maxBtn);
        windowControls.add(closeBtn);

        // Add to top bar
        topBar.add(windowControls, BorderLayout.EAST);

        // Make window draggable
        final Point dragOffset = new Point();
        topBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dragOffset.x = e.getX();
                dragOffset.y = e.getY();
            }
        });
        topBar.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                frame.setLocation(e.getXOnScreen() - dragOffset.x, e.getYOnScreen() - dragOffset.y);
            }
        });

        frame.add(topBar, BorderLayout.NORTH);

        // ===== Left Panel =====
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

        // ===== Chat List =====
        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
        chatListPanel.setBackground(BG);

        JScrollPane scrollPane = new JScrollPane(chatListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BG);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.getVerticalScrollBar().setBlockIncrement(80);
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            protected void configureScrollBarColors() {
                this.thumbColor = BORDER;
                this.trackColor = BG;
            }
            protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
            protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        // ===== Split Pane =====
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, scrollPane);
        splitPane.setDividerLocation(260);
        splitPane.setEnabled(false);
        splitPane.setBorder(null);
        splitPane.setDividerSize(2);
        splitPane.setUI(new javax.swing.plaf.basic.BasicSplitPaneUI() {
            public javax.swing.plaf.basic.BasicSplitPaneDivider createDefaultDivider() {
                return new javax.swing.plaf.basic.BasicSplitPaneDivider(this) {
                    public void setBorder(Border b) {}
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(BORDER);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                    }
                };
            }
        });

        // ===== Dummy Chat Entries =====
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

        frame.add(splitPane, BorderLayout.CENTER);
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
            public void mouseEntered(MouseEvent e) { button.setBackground(HOVER); }
            public void mouseExited(MouseEvent e) { button.setBackground(PANEL); }
        });
        return button;
    }

    private void addExitHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { button.setBackground(EXIT_HOVER); button.setForeground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { button.setBackground(PANEL); button.setForeground(TEXT); }
        });
    }

    private String loadUsername() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/profile_details"))) {
            return reader.readLine();
        } catch (IOException e) {
            return "Unknown";
        }
    }

    public void updateUsername(String newName) {
        title.setText("Welcome, " + newName);
        frame.revalidate();
        frame.repaint();
    }

    private JPanel createChatEntry(String chatName, String contact, String date, String statusContact, String statusUser) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel chatTitle = new JLabel(chatName + " — " + contact);
        chatTitle.setForeground(TEXT);
        chatTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel details = new JLabel("Date: " + date + " | Contact: " + statusContact + " | You: " + statusUser);
        details.setForeground(new Color(139, 148, 158));
        details.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(PANEL);
        textPanel.add(chatTitle);
        textPanel.add(details);

        JButton open = createStyledButton("Open");

        open.addActionListener(e -> new ChatPane(chatName));

        panel.add(textPanel, BorderLayout.CENTER);
        panel.add(open, BorderLayout.EAST);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        return panel;
    }
}