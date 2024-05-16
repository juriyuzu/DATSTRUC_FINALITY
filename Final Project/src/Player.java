import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Player extends Object {
    Panel panel;
    Game game;
    public double stamina = 1000;
    int size;

    Player(Panel panel, Game game) {
        super(new ImageIcon("").getImage(), 0, 0);

        this.panel = panel;
        this.game = game;
        x = 3 * game.tileSize;
        y = 3 * game.tileSize;
        priority = 1;
        size = (int) (game.tileSize * 0.7);
    }

    public void draw(Graphics2D gg, int camX, int camY) {
        int xOff = game.tileSize/2 - size/2 + camX;
        int yOff = game.tileSize/2 - size/2 + camY;

        gg.setColor(new Color(0xFF7A1313, true));
        gg.fillRect(x + xOff, y + yOff - size, size, size);
        gg.setColor(new Color(0xFF0000));
        gg.fillRect(x + xOff, y + yOff, size, size);

        move(gg);
    }

    public void gotoxy(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    private void move(Graphics2D gg) {
        int speed = 5;

        gotoxy(panel.curX - panel.camX - (double) w/2 - (double) game.tileSize/2, panel.curY - panel.camY - (double) h/2 - (double) game.tileSize/2);
    }

    boolean rectRect(float r1x, float r1y, float r1w, float r1h) {
        float r2x = x - (float) size/2, r2y = y - (float) size/2, r2w = size, r2h = size;
        return r1x + r1w >= r2x &&
                r1x <= r2x + r2w &&
                r1y + r1h >= r2y &&
                r1y <= r2y + r2h;
    }
}
