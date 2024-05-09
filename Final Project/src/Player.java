import java.awt.*;

public class Player {
    Panel panel;
    private final Key key;
    private int camX, camY, x, y, size;

    Player(Panel panel) {
        this.panel = panel;
        key = panel.key;
        camX = -panel.width/2;
        camY = -panel.height/2;
        x = 0;
        y = 0;
        size = panel.tileSize;
    }

    public void draw(Graphics2D gg) {
        gg.setColor(new Color(0xFF0000));
        gg.fillRect(x - size/2 - camX, y - size/2 - camY, size, size);

        move();
    }

    public void gotoxy(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
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

        gotoxy(x + xVel, y + yVel);
    }
}
