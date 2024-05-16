import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class Tile extends Object {
    Panel panel;
    Game game;
    TileType type;
    boolean solid;
    int size;
    HashMap<String, Image> images;
    boolean blockOn;

    /**
    * A Tile is an Object that does not move.
    * It is used in making maps.
     */
    Tile(Panel panel, Game game, int x, int y, TileType type) {
        super(new ImageIcon("").getImage(), x, y, game.tileSize, game.tileSize);
        this.panel = panel;
        this.game = game;
        this.x = x;
        this.y = y;
        size = game.tileSize;
        this.type = type;
        solid = type == TileType.WALL;
        priority = 1;
        if (type == TileType.FLOOR) priority = 0;

        images = new HashMap<>();
        images.put("WALL TOP", new ImageIcon("Final Project/assets/game/tiles/wallTop.png").getImage());
        images.put("WALL FRONT", new ImageIcon("Final Project/assets/game/tiles/wallFront.png").getImage());
        images.put("FLOOR", new ImageIcon("Final Project/assets/game/tiles/floor.png").getImage());
        images.put("BLOCK TOP", new ImageIcon("Final Project/assets/game/tiles/blockTop.png").getImage());
        images.put("BLOCK FRONT", new ImageIcon("Final Project/assets/game/tiles/blockFront.png").getImage());
        images.put("EXIT", new ImageIcon("Final Project/assets/game/tiles/exit.png").getImage());
    }

    public void draw(Graphics2D gg, int camX, int camY) {
        switch (type) {
            case WALL -> {
                gg.drawImage(images.get("WALL TOP"), x + camX, y - size + camY, size, size, null);
                gg.drawImage(images.get("WALL FRONT"), x + camX, y+ camY, size, size, null);
            }
            case FLOOR -> gg.drawImage(images.get("FLOOR"), x + camX, y + camY, size, size, null);
            case BLOCK -> drawBlock(gg, camX, camY);
            case EXIT -> gg.drawImage(images.get("EXIT"), x + camX, y + camY, size, size, null);
        }
    }

    public void clickFun() {
        if (type == TileType.BLOCK) {
            blockOn = !blockOn;
            solid = blockOn;
        }
    }

    private void drawBlock(Graphics2D gg, int camX, int camY) {
        if (blockOn) {
            gg.drawImage(images.get("BLOCK TOP"), x + camX, y - size + camY, size, size, null);
            gg.drawImage(images.get("BLOCK FRONT"), x + camX, y + camY, size, size, null);
        } else gg.drawImage(images.get("BLOCK TOP"), x + camX, y + camY, size, size, null);
    }
}
