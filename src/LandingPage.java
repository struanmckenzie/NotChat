import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LandingPage {

    public static void launch() {
        JFrame frame = new JFrame();
        frame.setSize(1080, 1920);

        frame.setTitle("Landing Page");
        frame.setLocationByPlatform(true);

        Label l1 = new Label("UserName");
        l1.setBounds(100, 50, 120, 80);
        frame.add (l1);

        JButton button = new JButton("button");
        button.setBounds(150, 200, 220, 50);
        frame.add(button);

        frame.setLayout(null);
        frame.setVisible(true);
    }
}