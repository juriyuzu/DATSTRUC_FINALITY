import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class MainMenu {
    Panel panel;
    HashMap<String, Object> objects;
    HashMap<String, Image> imageStock;
    boolean click, press;

    MainMenu(Panel panel) {
        this.panel = panel;

        imageStock = new HashMap<>();
        imageStock.put("1", new ImageIcon("Final Project/assets/mainMenu/my beloved.png").getImage());
        imageStock.put("2", new ImageIcon("Final Project/assets/mainMenu/my beloved2.png").getImage());

        objects = new HashMap<>();
        objects.put("START BUTTON", new Object(imageStock.get("1"), panel.width/2, panel.height/2 + 100, 100, 100));

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click = true;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                press = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                press = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void draw(Graphics2D gg) {
        for (HashMap.Entry<String, Object> entry : objects.entrySet()) entry.getValue().draw(gg);

        pressFun(gg);
        if (click) clickFun();
    }

    private void pressFun(Graphics2D gg) {
        gg.setColor(new Color(0x000000));
        gg.drawString(String.valueOf(press), 100, 100);
        if (cursorHovering(objects.get("START BUTTON")) && press) {
            Object o = objects.get("START BUTTON");

            o.image = imageStock.get("2");
        } else {
            Object o = objects.get("START BUTTON");

            o.image = imageStock.get("1");
        }
    }
    private void clickFun() {
        System.out.print("screen clicked");
        if (cursorHovering(objects.get("START BUTTON"))) {
            System.out.print("butt clicked");
        }
        System.out.println();
        click = false;
    }

    private boolean cursorHovering(Object o) {
        return panel.curX < o.x + o.w &&
                panel.curX > o.x &&
                panel.curY < o.y + o.h &&
                panel.curY > o.y;
    }
}
