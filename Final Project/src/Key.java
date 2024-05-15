import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;

public class Key {
    HashMap<String, Boolean> key;

    Key(Main main) {
        String[] keys = {
                "W",
                "S",
                "A",
                "D",
                "SHIFT",
        };

        key = new HashMap<>();
        for (String s : keys) key.put(s, false);

        main.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> key.put("W", true);
                    case KeyEvent.VK_S -> key.put("S", true);
                    case KeyEvent.VK_A -> key.put("A", true);
                    case KeyEvent.VK_D -> key.put("D", true);
                    case KeyEvent.VK_SHIFT -> key.put("SHIFT", true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> key.put("W", false);
                    case KeyEvent.VK_S -> key.put("S", false);
                    case KeyEvent.VK_A -> key.put("A", false);
                    case KeyEvent.VK_D -> key.put("D", false);
                    case KeyEvent.VK_SHIFT -> key.put("SHIFT", false);
                }
            }
        });
    }
}
