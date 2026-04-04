import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setUndecorated(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LandingPage.saveChats();
                frame.dispose();
            }
        });

        new LandingPage(frame);
    }
}