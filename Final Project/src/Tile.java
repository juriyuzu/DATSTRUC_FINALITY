import java.awt.*;

public class Tile {
    Panel panel;
    int x, y, size;
    Image image;
    Player player;
    boolean solid;

    Tile(Panel panel, int x, int y, boolean solid) {
        this.panel = panel;
        player = panel.player;
        this.x = x;
        this.y = y;
        size = panel.tileSize;
        this.solid = solid;
    }

    public void draw(Graphics2D gg) {
        gg.setColor(new Color(0xFF122A09, true));
        gg.fillRect(x - size/2 - player.getXYCam(true), y - size*3/2 - player.getXYCam(false), size, size*2);
        gg.setColor(new Color(0xC2275E12, true));
        gg.fillRect(x - size/2 - player.getXYCam(true), y - size/2 - player.getXYCam(false), size, size);
    }

    public int getY() {return y;}
}
