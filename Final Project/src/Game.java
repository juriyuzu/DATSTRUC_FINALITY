import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Game {
    Panel panel;
    HashMap<String, LinkedList<Tile>> tiles;
    LinkedList<Tile> layer;
    Random random;
    Player player;
    Save save;
    int tileSize;

    Game(Panel panel) {
        this.panel = panel;

        tileSize = 50;
        save = new Save();
        random = new Random();
    }

    public void draw(Graphics2D gg) {

    }

    private void layerFun(Graphics2D gg) {
        layer.clear();
        layer.addAll(tiles.get("BACKGROUND"));
        layer.sort(Comparator.comparingInt(Tile::getY));
        for (Tile tile : layer) tile.draw(gg);

        layer.clear();
        layer.addAll(tiles.get("PLAYGROUND"));
        layer.sort(Comparator.comparingInt(Tile::getY));
        for (Tile tile : layer) tile.draw(gg);
    }

    public Tile getTileAt(int x, int y) {
        for (Tile tile : tiles.get("BACKGROUND")) {
            if (tile.x == x && tile.y == y) {
                return tile;
            }
        }
        return null; // Return null if no tile is found at the specified coordinates
    }
}
