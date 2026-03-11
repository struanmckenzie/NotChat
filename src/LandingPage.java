import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LandingPage {

    public static void launch() {
        ArrayList<Integer> list = new ArrayList<>();
        JFrame frame = new JFrame();
        frame.setSize(1080, 1920);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setTitle("Landing Page");
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JLabel title = new JLabel("Username", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        frame.add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton edit, viewCon, create, viewCha, exit;
        edit = new JButton("Edit Profile");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "temporary message, edit profile function");
            }
        });

        viewCon = new JButton("View Contacts");
        viewCon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "temporary message, View contacts function");
            }
        });

        create = new JButton("Create a new chat");
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "temporary message, create chat function");
            }
        });

        viewCha = new JButton("View chats");
        viewCha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();              // close LandingPage window
                //ChatPage.launch();            // opens ChatPage window
            }
        });


        exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Bye!");
                System.exit(0);
            }
        });

        panel.add(edit);
        panel.add(viewCon);
        panel.add(create);
        panel.add(viewCha);
        panel.add(exit);
        panel.setBounds(400, 100, 300, 400);
        frame.add(panel);
        frame.setVisible(true);
    }
}