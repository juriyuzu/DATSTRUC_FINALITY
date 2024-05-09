import javax.swing.*;

public class Main extends JFrame {
    Panel panel;

    Main() {
        setUndecorated(true);
        System.out.println("tite");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        panel = new Panel();
        add(panel);
    }

    public static void main(String[] args) {
        System.out.println("Hello Nigga.");
        new Main();
    }
}
