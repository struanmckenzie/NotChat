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

        JButton rand, add, search, print, exit;
        rand = new JButton("Edit Profile");
        rand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 20; i++) {
                    list.add((int) (1 + Math.random() * 100));
                }
            }
        });

        add = new JButton("View Contacts");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input;
                try {
                    input = Integer.parseInt(JOptionPane.showInputDialog("Give me a number"));
                    list.add(input);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "An Error Occured!");
                }
            }
        });

        search = new JButton("Create a new chat");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input;
                try {
                    input = Integer.parseInt(JOptionPane.showInputDialog("Give me a number"));
                    if (list.contains(input)) {
                        JOptionPane.showMessageDialog(null, input + " was found!");
                    } else {
                        JOptionPane.showMessageDialog(null, input + " wasn't found!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "An Error Occured!");
                }
            }
        });

        print = new JButton("View chats");
        search.addActionListener(new ActionListener() {
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

        panel.add(rand);
        panel.add(add);
        panel.add(search);
        panel.add(print);
        panel.add(exit);
        panel.setBounds(400, 100, 300, 400);
        frame.add(panel);
        frame.setVisible(true);
    }
}