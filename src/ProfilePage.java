import javax.swing.*;
import java.awt.*;


public class ProfilePage extends JFrame {
    private String name;
    private String phone;


    public ProfilePage() {
        setSize(1080, 1920);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setTitle("Profile Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        // menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem landingPage = new JMenuItem("Landing Page");
        menu.add(landingPage);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        JTextArea textArea = new JTextArea("Profile Page");
        textArea.setEditable(false);
        add(textArea, BorderLayout.CENTER);

        // info box panels
        JPanel namePanel = new JPanel();
        JPanel phonePanel = new JPanel();

        JTextArea nameField = new JTextArea(name);
        namePanel.add(nameField);

        JTextArea phoneField = new JTextArea(phone);
        phonePanel.add(phoneField);


        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                namePanel,
                phonePanel
        );

        add(splitPane, BorderLayout.CENTER);

        // save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            System.out.println("Save button pressed");
            name = nameField.getText();
            phone = phoneField.getText();
            // save to storage for name and telephone number
        });
        add(saveButton);

        // landing page action
        //landingPage.addActionListener(e -> LandingPage.launch());
    }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProfilePage::new);
    }

}