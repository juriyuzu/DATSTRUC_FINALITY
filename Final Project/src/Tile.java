import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class Tile extends Object {
    Panel panel;
    Game game;
    TileType type;
    boolean solid = true;
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
        solid = type == TileType.WALL || type == TileType.BLOCK ;
        priority = 1;
        blockOn = type == TileType.BLOCK;
        if (type == TileType.FLOOR) priority = 0;

        images = new HashMap<>();
        images.put("WALL", new ImageIcon("Final Project/assets/game/tiles/wall.png").getImage());
        images.put("FLOOR", new ImageIcon("Final Project/assets/game/tiles/floor.png").getImage());
        images.put("BLOCK FLOOR", new ImageIcon("Final Project/assets/game/tiles/blockFloor.png").getImage());
        images.put("BLOCK WALL", new ImageIcon("Final Project/assets/game/tiles/blockWall.png").getImage());
        images.put("EXIT", new ImageIcon("Final Project/assets/game/tiles/exit.png").getImage());
    }

    public void draw(Graphics2D gg, int camX, int camY) {
        switch (type) {
            case WALL -> gg.drawImage(images.get("WALL"), x + camX, y+ camY, size, size, null);
            case FLOOR -> gg.drawImage(images.get("FLOOR"), x + camX, y + camY, size, size, null);
            case BLOCK -> drawBlock(gg, camX, camY);
            case EXIT -> gg.drawImage(images.get("EXIT"), x + camX, y + camY, size, size, null);
    }}

    public void clickFun(Player player) {
        if (type == TileType.BLOCK && !player.rectRect(x - (float) w/2, y - (float) h/2, size, size)) {
            blockOn = !blockOn;
            solid = blockOn;
        }
    }

    private void drawBlock(Graphics2D gg, int camX, int camY) {
        if (blockOn) gg.drawImage(images.get("BLOCK WALL"), x + camX, y + camY, size, size, null);
        else gg.drawImage(images.get("BLOCK FLOOR"), x + camX, y + camY, size, size, null);
    }
}
