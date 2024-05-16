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

        for (HashMap.Entry<String, Object> entry : objects.entrySet()) entry.getValue().draw(gg, panel.camX, panel.camY);

        pressFun(gg);
        if (click) clickFun();
    }

    private void pressFun(Graphics2D gg) {
        // pressFun detects presses in the screen
        
        gg.setColor(new Color(0x000000));
        gg.drawString(String.valueOf(press), 100, 100);
        if (press && panel.hovering(objects.get("START BUTTON"), panel.curX, panel.curY) && panel.hovering(objects.get("START BUTTON"), pressX, pressY)) {
            // this runs only if the initially pressed location is hovering the start button
            // and if the cursor is hovering the start button
            
            Object o = objects.get("START BUTTON");

            o.image = imageStock.get("2");
        } else {
            // reset pressX and pressY so the code above will not run when the cursor is dragged out the button and back again
            pressX = -1;
            pressY = -1;

            Object o = objects.get("START BUTTON");

            o.image = imageStock.get("1");
        }
    }
    
    private void clickFun() {
        // clickFun runs when the screen is clicked
        
        System.out.print("screen clicked");
        if (panel.hovering(objects.get("START BUTTON"), panel.curX, panel.curY)) {
            System.out.print("butt clicked");

            // turn off the mainMenu
            visible = false;
            // turn on the game
            panel.game.start();
        }
        System.out.println();
        click = false;
    }
}
