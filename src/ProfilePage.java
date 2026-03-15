import javax.swing.*;
import java.awt.*;


public class ProfilePage extends JFrame {
    private String name;
    private String phone;

    public ProfilePage(String name, String phone) {
        this.name = name;
        this.phone = phone;

        initialiseUI();
    }

    public void initialiseUI() {
        setSize(800, 600);

        setTitle("Profile");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem landingPage = new JMenuItem("Return to landing page");
        JMenuItem saveDetails = new JMenuItem("Save details");
        menu.add(landingPage);
        menu.add(saveDetails);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // landing page action
        landingPage.addActionListener(e -> LandingPage.launch());

        JTextArea textArea = new JTextArea("Profile");
        textArea.setEditable(false);
        add(textArea, BorderLayout.CENTER);

        // show details
        JPanel detailsPanel = new JPanel();

        JTextArea nameField = new JTextArea(name);
        JTextArea phoneField = new JTextArea(phone);
        detailsPanel.add(nameField);
        detailsPanel.add(phoneField);

        add(detailsPanel);

        // save menu item action
        saveDetails.addActionListener(e -> {
            // save to storage for name and telephone number
            name = nameField.getText();
            phone = phoneField.getText();
            JOptionPane.showMessageDialog(null, "Details saved!");
        });

        setVisible(true);
    }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getName() { return name; }
    public String getNumber() { return phone; }


    public static void main(String[] args) {
        new ProfilePage("YourName", "07589 365934");
    }

}
