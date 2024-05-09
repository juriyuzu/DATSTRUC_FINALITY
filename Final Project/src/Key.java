import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class Key {
    HashMap<String, Boolean> key;

    Key(JFrame frame) {
        String[] keys = {
                "UP",
                "DOWN",
                "LEFT",
                "RIGHT"
        };

        key = new HashMap<>();
        for (String s : keys) key.put(s, false);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> System.out.println("UP");
                    case KeyEvent.VK_DOWN -> System.out.println("DOWN");
                    case KeyEvent.VK_LEFT -> System.out.println("LEFT");
                    case KeyEvent.VK_RIGHT -> System.out.println("RIGHT");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
