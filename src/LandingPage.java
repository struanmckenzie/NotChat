import javax.swing.*;

public class LandingPage {

    public static void launch() {
        JFrame frame = new JFrame();
        JButton button = new JButton("button");

        button.setBounds(150, 200, 220, 50);
        frame.add(button);

        frame.setSize(500, 500);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}