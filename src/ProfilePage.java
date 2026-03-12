import javax.swing.*;
import java.awt.*;


public class ProfilePage extends JFrame {
    private String name = "TEMP_NAME";
    private String phone = "TEMP_NUMBER";


    public ProfilePage() {
        setSize(1080, 1920);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setTitle("Profile Page");
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JLabel title = new JLabel(name, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        add(title, BorderLayout.NORTH);

        // menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem landingPage = new JMenuItem("Landing Page");
        menu.add(landingPage);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        JPanel profilePanel = new JPanel();
        JTextField nameField = new JTextField(name);
        JTextField phoneField = new JTextField(phone);
        add(nameField);
        add(phoneField);

        // landing page action
        //landingPage.addActionListener(e -> LandingPage.launch());



    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProfilePage::new);
    }

}
