import java.awt.*;
import java.util.HashMap;

public class Player extends Tile {
    private final Key key;
    private int camX, camY;

    Player(Panel panel) {
        super(panel, 0, 0, true);

        this.panel = panel;
        key = panel.key;
        camX = -panel.width/2;
        camY = -panel.height/2;
        x = 50;
        y = 50;
        size = (int) (panel.tileSize * 0.7);
    }

    public void draw(Graphics2D gg) {
        gg.setColor(new Color(0xAD0000));
        gg.fillRect(x - size/2 - camX, y - size*3/2 - camY, size, size*2);
        gg.setColor(new Color(0xFF0000));
        gg.fillRect(x - size/2 - camX, y - size/2 - camY, size, size);

        move();
        gotoxyCam(x - (double) panel.width /2, y - (double) panel.height /2);
    }

    public void gotoxy(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    public void gotoxyCam(double x, double y) {
        this.camX = (int) x;
        this.camY = (int) y;
    }

    public int getXYCam(boolean getX) {
        return getX ? camX : camY;
    }

    private void move() {
        float speed = 5;
        int xVel = 0;
        int yVel = 0;

        if (key.key.get("W")) yVel -= (int) speed;
        if (key.key.get("S")) yVel += (int) speed;
        if (key.key.get("A")) xVel -= (int) speed;
        if (key.key.get("D")) xVel += (int) speed;
        if ((key.key.get("W") || key.key.get("S")) && (key.key.get("A") || key.key.get("D"))) {
            double length = Math.sqrt(xVel * xVel + yVel * yVel);
            if (length != 0) {
                xVel = (int) ((xVel / length) * speed);
                yVel = (int) ((yVel / length) * speed);
            }
        }

        boolean xF = true, yF = true;
        gotoxy(x + xVel, y);
        for (Tile tile : panel.tiles) {
            if (tile.solid && rectRect(tile.x - (float) tile.size/2, tile.y - (float) tile.size/2, tile.size, tile.size)) {
                xF = false;
                break;
            }
        }
        gotoxy(x - xVel, y + yVel);
        for (Tile tile : panel.tiles) {
            if (tile.solid && rectRect(tile.x - (float) tile.size/2, tile.y - (float) tile.size/2, tile.size, tile.size)) {
                yF = false;
                break;
            }
        }
        if (xF) gotoxy(x + xVel, y);
        if (!yF) gotoxy(x, y - yVel);
    }

    boolean rectRect(float r1x, float r1y, float r1w, float r1h) {
        float r2x = x - (float) size/2, r2y = y - (float) size/2, r2w = size, r2h = size;
        return r1x + r1w >= r2x &&
                r1x <= r2x + r2w &&
                r1y + r1h >= r2y &&
                r1y <= r2y + r2h;
    }
}
