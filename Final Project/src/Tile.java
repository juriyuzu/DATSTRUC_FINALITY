import java.awt.*;

public class Tile {
    Panel panel;
    int x, y, size;
    Image image;
    Player player;

    Tile(Panel panel, int x, int y) {
        this.panel = panel;
        player = panel.player;
        this.x = x;
        this.y = y;
        size = panel.tileSize;
    }

    public void draw(Graphics2D gg) {
        gg.setColor(new Color(0xC2275E12, true));
        gg.fillRect(panel.tilePos(x) - size/2 - player.getXYCam(true), panel.tilePos(y) - size/2 - player.getXYCam(false), size, size);
    }
}
