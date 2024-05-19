import javax.swing.*;
import java.awt.*;

public class Player extends Object {
    Panel panel;
    Game game;
    int size;
    int pathIndex = 0;
    int lastX, lastY;
    int exitX = -1, exitY = -1;

    Player(Panel panel, Game game) {
        super(new ImageIcon("").getImage(), 0, 0);

        this.panel = panel;
        this.game = game;
        x = 10 * game.tileSize;
        y = 10 * game.tileSize;
        priority = 1;
        size = (int) (game.tileSize * 0.7);
    }

    public void draw(Graphics2D gg, int camX, int camY) {
        int xOff = game.tileSize/2 - size/2 + camX;
        int yOff = game.tileSize/2 - size/2 + camY;

        gg.setColor(new Color(0xFF0000));
        gg.fillRect(x + xOff, y + yOff, size, size);

        move(gg);
    }

    public void gotoxy(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    boolean toNearestTile = false, fixed = true;
    int speed = 2;
    int xVel = 0;
    int yVel = 0;
    private void move(Graphics2D gg) {
        {
            gg.setColor(new Color(0x000000));
            gg.setFont(new Font("Consolas", Font.BOLD, 15));
            gg.drawString(String.valueOf(toNearestTile), panel.curX + 10, panel.curY + 30);
        }
        xVel = 0;
        yVel = 0;

        if (!(exitX != -1 && exitY != -1 && Math.abs(exitX - x) < speed && Math.abs(exitY - y) < speed)) {
            toNearestTile(gg);
            toDestination();
        }

        gotoxy(x + xVel, y + yVel);

//        gotoxy(panel.curX - panel.camX - (double) w/2 - (double) game.tileSize/2, panel.curY - panel.camY - (double) h/2 - (double) game.tileSize/2);
    }

    private void toDestination() {
        if (toNearestTile) return;

        int xPath = Math.round((float) x / game.tileSize) * game.tileSize;
        int yPath = Math.round((float) y / game.tileSize) * game.tileSize;
        int xPathLast = Math.round((float) lastX / game.tileSize) * game.tileSize;
        int yPathLast = Math.round((float) lastY / game.tileSize) * game.tileSize;

        if (game.path != null && game.path.size() > pathIndex && pathIndex > 0) {
            exitX = game.path.getLast().x;
            exitY = game.path.getLast().y;
            xVel = (game.path.get(pathIndex).x - game.path.get(pathIndex-1).x) * speed;
            yVel = (game.path.get(pathIndex).y - game.path.get(pathIndex-1).y) * speed;
        } else {
            if (game.path == null && !fixed) toNearestTile = true;
            else {
                xVel = 0;
                yVel = 0;
            }
        }

        if ((Math.abs(x - xPath) < speed && Math.abs(y - yPath) < speed) && (xPath != xPathLast || yPath != yPathLast)) {
            pathIndex++;
            lastX = x;
            lastY = y;
        }
    }

    private void toNearestTile(Graphics2D gg) {
        if (!toNearestTile) return;

        int xPath = Math.round((float) x / game.tileSize) * game.tileSize;
        int yPath = Math.round((float) y / game.tileSize) * game.tileSize;

        if ((Math.abs(x - xPath) < speed && Math.abs(y - yPath) < speed)) {
            toNearestTile = false;
            gotoxy(xPath, yPath);
            if (!fixed) {
                game.updatePath();
                pathIndex = 0;
            }
            fixed = true;
        } else {
            fixed = false;
            xVel = (xPath - x) == 0 ? 0 : (xPath - x) / Math.abs(xPath - x);
            yVel = (yPath - y) == 0 ? 0 : (yPath - y) / Math.abs(yPath - y);
        }
    }

    boolean rectRect(float r1x, float r1y, float r1w, float r1h) {
        float r2x = x - (float) size/2, r2y = y - (float) size/2, r2w = size, r2h = size;
        return r1x + r1w >= r2x &&
                r1x <= r2x + r2w &&
                r1y + r1h >= r2y &&
                r1y <= r2y + r2h;
    }
}
