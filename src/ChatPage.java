import javax.swing.*;

/**
 * Class to view chat history
 * @author Struan McK
 */
public class ChatPage {
    public static void launch() {
        JFrame frame = new JFrame();
        frame.setSize(1080, 1920);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setTitle("Chat");
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
