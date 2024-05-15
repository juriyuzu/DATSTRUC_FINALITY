import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class MainMenu {
    boolean visible = true;
    Panel panel;
    HashMap<String, Object> objects;
    HashMap<String, Image> imageStock;
    boolean click, press;
    int pressX, pressY;

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
                pressX = e.getX();
                pressY = e.getY();
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
        if (!visible) return;

        for (HashMap.Entry<String, Object> entry : objects.entrySet()) entry.getValue().draw(gg);

        pressFun(gg);
        if (click) clickFun();
    }

    private void pressFun(Graphics2D gg) {
        gg.setColor(new Color(0x000000));
        gg.drawString(String.valueOf(press), 100, 100);
        if (hovering(objects.get("START BUTTON"), panel.curX, panel.curY) && press && hovering(objects.get("START BUTTON"), pressX, pressY)) {
            Object o = objects.get("START BUTTON");

            o.image = imageStock.get("2");
        } else {
            Object o = objects.get("START BUTTON");

            o.image = imageStock.get("1");
        }
    }
    private void clickFun() {
        System.out.print("screen clicked");
        if (hovering(objects.get("START BUTTON"), panel.curX, panel.curY)) {
            System.out.print("butt clicked");
            visible = false;
        }
        System.out.println();
        click = false;
    }

    private boolean hovering(Object o, int x, int y) {
        return x < o.x + o.w &&
                x > o.x &&
                y < o.y + o.h &&
                y > o.y;
    }
}
