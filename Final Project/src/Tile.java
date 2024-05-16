import java.awt.*;

public class Tile {
    Panel panel;
    int x, y, size;
    Image image;
    Player player;
    TileType type;
    boolean solid;

    Tile(Panel panel, int x, int y, TileType type) {
        this.panel = panel;
//        player = panel.player;
        this.x = x;
        this.y = y;
//        size = panel.tileSize;
        this.type = type;
        solid = type != TileType.FLOOR;
    }

    public void draw(Graphics2D gg) {
        switch (type) {
            case WALL -> {
                gg.setColor(new Color(0xFF122A09, true));
                gg.fillRect(x - size / 2 - player.getXYCam(true), y - size * 3 / 2 - player.getXYCam(false), size, size * 2);
                gg.setColor(new Color(0xC2275E12, true));
                gg.fillRect(x - size / 2 - player.getXYCam(true), y - size / 2 - player.getXYCam(false), size, size);
            }
            case FLOOR -> {
                gg.setColor(new Color(0xFF767E75, true));
                gg.fillRect(x - size / 2 - player.getXYCam(true), y - size / 2 - player.getXYCam(false), size, size);
            }
        }
    }

    public int getY() {return y;}
}
