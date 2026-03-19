import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class ProfilePage extends JFrame {
    // fields
    private String name;
    private String number;
    private String pfp;
    private final String saveLocation = "profile_details";

    /**
     * loads name and number into variables from file
     */
    public ProfilePage() {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveLocation))) {
            this.name = reader.readLine();
            this.number = reader.readLine();
            this.pfp = reader.readLine();

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
        JPanel detailsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        detailsPanel.setBackground(Color.LIGHT_GRAY);

        JTextArea nameField = new JTextArea(name);
        JTextArea numberField = new JTextArea(number);
        nameField.setFont(new Font(null, Font.PLAIN, 20));
        numberField.setFont(new Font(null, Font.PLAIN, 20));

        // get profile picture
        // the AMOUNT of research i had to do, all the stackoverflow
        // and wikihows and everything JUST FOR THIS
        // worth it ;-D
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource(getPfp())));
        Image scaledImg = image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon profileP = new ImageIcon(scaledImg);
        JLabel pic = new JLabel(profileP);
        pic.setPreferredSize(new Dimension(100, 100));

        detailsPanel.add(pic);
        detailsPanel.add(nameField);
        detailsPanel.add(numberField);

        add(detailsPanel, BorderLayout.CENTER);

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
            JOptionPane.showMessageDialog(null, "Name "+getName()+" saved");
        });

        saveNumber.addActionListener(e -> {
            setNumber(numberField.getText());
            saveDetails();
            JOptionPane.showMessageDialog(null, "Number "+getNumber()+" saved");
        });

        /**
         * this action listener opens a graphical file explorer fr the user
         * so they can shoose a new pfp to use
         * then copies the pfp to the img directory for the program to use
         */
        selectPhoto.addActionListener(e -> {
            // open file explorer
            JFileChooser f = new JFileChooser();
            f.showSaveDialog(null);

            // copy selected image to the img directory
            try {
                Files.copy(f.getSelectedFile().toPath(), Paths.get("./src/img"), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        setVisible(true);
    }

    private void aesthetic(JButton b) {
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
    public void setPfp(String pfp) { this.pfp = pfp; }
    public String getName() { return name; }
    public String getNumber() { return number; }
    public String getPfp() { return pfp; }

    /**
     * saves details to a file
     * existing file contents are overwritten
     */
    public void saveDetails() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(saveLocation))) {
            writer.println(getName());
            writer.println(getNumber());
            writer.println(getPfp());
        } catch (IOException e) {
            System.out.println("Error saving profile details: " + e.getMessage());
        }
    }

    // starts profile page for testing
    static void main(){
        new ProfilePage();
    }

}
