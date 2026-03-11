import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ContactPage {

    public void launch(Contact[] contacts) {

        JFrame frame = new JFrame();
        frame.setSize(1080, 1920);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Contacts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(0, 1));

        for (Contact contact : contacts) {
            JButton button = new JButton(contact.getName());
            //Update action listener to display contact details with options to edit and delete contact
            button.addActionListener(e ->
                JOptionPane.showMessageDialog(null, "Open contact details for " + contact.getName())
            );
            sidebar.add(button);
        }

        JButton button = new JButton("Add new contact");
        sidebar.add(button);

        JScrollPane sidebarScrollBar = new JScrollPane(sidebar);
        sidebarScrollBar.setPreferredSize(new Dimension(200, 0));
        sidebarScrollBar.getVerticalScrollBar().setUnitIncrement(20);

        frame.add(sidebarScrollBar, BorderLayout.WEST);

        frame.setVisible(true);

    }

}