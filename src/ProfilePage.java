import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class ProfilePage extends JFrame {
    // fields
    private LandingPage landingPage;
    private String name;
    private String number;
    private JTextArea nameField;
    private JTextArea numberField;
    private final String PFP_LOCATION = "/resources/images/profile_picture.png";
    private final String DETAILS_LOCATION = "src/resources/profile_details";

    /**
     * loads name and number into variables from file
     */
    public ProfilePage(LandingPage landingPage) {
        this.landingPage = landingPage;
        try (BufferedReader reader = new BufferedReader(new FileReader(DETAILS_LOCATION))) {
            this.name = reader.readLine();
            this.number = reader.readLine();

        } catch (IOException e) {
            System.out.println("Error loading profile details " + e.getMessage());
        }
        initialiseUI();
    }

    /**
     * formats and starts profile page
     */
    public void initialiseUI() {
        setSize(500, 170);
        setLocationRelativeTo(null);

        setTitle(getName()+"'s Profile");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // show details
        JPanel details = new JPanel(new FlowLayout(FlowLayout.CENTER));
        details.setBackground(Color.LIGHT_GRAY);

        nameField = new JTextArea(name);
        numberField = new JTextArea(number);
        nameField.setFont(new Font(null, Font.PLAIN, 20));
        numberField.setFont(new Font(null, Font.PLAIN, 20));

        updateProfile(details);


        add(details, BorderLayout.CENTER);

        // save buttons
        JPanel savePanel = new JPanel(new GridLayout(1,3));

        JButton saveName = new JButton("Save name");
        JButton saveNumber = new JButton("Save number");
        JButton selectPhoto = new JButton("Select image");
        aesthetic(saveName);
        aesthetic(saveNumber);
        aesthetic(selectPhoto);

        savePanel.add(selectPhoto);
        savePanel.add(saveName);
        savePanel.add(saveNumber);
        add(savePanel, BorderLayout.AFTER_LAST_LINE);

        // save buttons actions
        saveName.addActionListener(e -> {
            setName(nameField.getText());
            saveDetails();
            JOptionPane.showMessageDialog(null, "Name " + getName() + " saved");

            landingPage.updateUsername(getName());
        });

        saveNumber.addActionListener(e -> {
            setNumber(numberField.getText());
            saveDetails();
            JOptionPane.showMessageDialog(null, "Number "+getNumber()+" saved");
        });


        // this action listener opens a graphical file explorer fr the user
        // so they can shoose a new pfp to use
        // then copies the pfp to the img directory for the program to use
        selectPhoto.addActionListener(e -> {
            // open file explorer
            JFileChooser f = new JFileChooser();
            f.showSaveDialog(null);

            // if user selects a file set it as the pfp
            if (f.getSelectedFile() != null) {
                // copy selected image to the img directory
                try {
                    Files.copy(f.getSelectedFile().toPath(), Paths.get(getClass().getResource(PFP_LOCATION).toURI()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException | URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }

                /*
                // this is probably an awful way to do this but idk how else
                // close window and open a new one with updated image
                dispose();
                new ProfilePage(landingPage); */

                updateProfile(details);
            }
        });

        setVisible(true);
    }

    /**
     * get profile picture
     * set profile picture
     * @param details panel to add pic to
     */
    private void updateProfile(JPanel details) {
        details.removeAll();
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource(PFP_LOCATION)));
        Image scaledImg = image.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon profileP = new ImageIcon(scaledImg);
        JLabel pic = new JLabel(profileP);
        pic.setPreferredSize(new Dimension(100, 100));

        details.add(pic);
        details.add(nameField);
        details.add(numberField);
        details.updateUI();
    }

    /**
     * sets the desired appearance of buttons on the page
     * @param b button to configure
     */
    public static void aesthetic(JButton b) {
        b.setPreferredSize(new Dimension(150, 30));
        b.setBackground(Color.gray);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(),
                BorderFactory.createBevelBorder(0)
        ));
    }

    /**
     * get/set methods
     */
    public void setName(String name) { this.name = name; }
    public void setNumber(String phone) { this.number = phone; }
    public String getName() { return name; }
    public String getNumber() { return number; }

    /**
     * saves details to a file
     * existing file contents are overwritten
     */
    public void saveDetails() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DETAILS_LOCATION))) {
            writer.println(getName());
            writer.println(getNumber());
            writer.println(PFP_LOCATION);
        } catch (IOException e) {
            System.out.println("Error saving profile details: " + e.getMessage());
        }
    }
}
