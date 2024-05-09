import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Main extends JFrame {
    Panel panel;

    Main() {
        setResizable(false);
        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel = new Panel(getWidth(), getHeight());
                add(panel);
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Hello Nigga.");
        new Main();
    }
}
