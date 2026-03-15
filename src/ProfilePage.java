import javax.swing.*;
import java.awt.*;
import java.io.*;


public class ProfilePage extends JFrame {
    private String name;
    private String number;
    private final String saveLocation = "src/profile_details";

    /**
     * loads name and number into variables from file
     */
    public ProfilePage() {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveLocation))) {
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
        setSize(500, 300);

        setTitle(getName()+"'s Profile");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // show details
        JPanel detailsPanel = new JPanel();

        JTextArea nameField = new JTextArea(name);
        JTextArea numberField = new JTextArea(number);
        nameField.setFont(new Font("default", Font.PLAIN, 20));
        numberField.setFont(new Font("default", Font.PLAIN, 20));
        detailsPanel.add(nameField, BorderLayout.WEST);
        detailsPanel.add(numberField, BorderLayout.EAST);

        add(detailsPanel, BorderLayout.CENTER);

        // save buttons
        JPanel savePanel = new JPanel();

        JButton saveName = new JButton("Save name");
        JButton saveNumber = new JButton("Save number");
        saveName.setPreferredSize(new Dimension(200, 50));
        saveNumber.setPreferredSize(new Dimension(200, 50));

        savePanel.add(saveName, BorderLayout.WEST);
        savePanel.add(saveNumber, BorderLayout.EAST);
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

        setVisible(true);
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
        try (PrintWriter writer = new PrintWriter(new FileWriter(saveLocation))) {
            writer.println(getName());
            writer.println(getNumber());
        } catch (IOException e) {
            System.out.println("Error saving profile details: " + e.getMessage());
        }
    }

    // starts profile page for testing
    static void main(){
        new ProfilePage();
    }

}
