import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game {
    Panel panel;
    Save save;
    Random random;
    LinkedList<Object> layer;
    LinkedList<LinkedList<Tile>> maps;
    Player player;
    int tileSize;
    boolean visible;
    int currentMap;
    boolean click, press;
    int pressX, pressY;
    List<Node> path;
    Image pathImage;

    Game(Panel panel) {
        this.panel = panel;

        tileSize = 70;
        save = new Save();
        random = new Random();
        visible = false;
        layer = new LinkedList<>();
        player = new Player(panel, this);
        pathImage = new ImageIcon("Final Project/assets/game/tiles/path.png").getImage();

        int mapsAmount = 2;
        maps = new LinkedList<>();
        currentMap = 1;
        for (int i = 0; i < mapsAmount; i++) {
            char[][] map = save.read("Final Project/save/floors/floor " + i + ".txt");
            maps.add(new LinkedList<>());

            for (int j = 0; j < map.length; j++) for (int k = 0; k < map[j].length; k++)
                switch (map[j][k]) {
                    case '.' -> maps.getLast().add(new Tile(panel, this, k * tileSize, j * tileSize, TileType.FLOOR));
                    case '0' -> maps.getLast().add(new Tile(panel, this, k * tileSize, j * tileSize, TileType.WALL));
                    case '1' -> maps.getLast().add(new Tile(panel, this, k * tileSize, j * tileSize, TileType.EXIT));
                    case '2' -> maps.getLast().add(new Tile(panel, this, k * tileSize, j * tileSize, TileType.BLOCK));
        }}

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click = true;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                press = true;
                pressX = e.getX();
                pressY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                press = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void draw(Graphics2D gg) {
        if (!visible) return;

        layerFun(gg);

        if (player.x % tileSize <= 5 && player.y % tileSize <= 5) path = AStar.findPath(tileSize, maps.get(currentMap), player);
        if (path != null) for (Node node : path)
            gg.drawImage(pathImage,
                    (player.x + tileSize/2) / tileSize * tileSize + (node.x - 10) * tileSize + panel.camX,
                    (player.y + tileSize/2) / tileSize * tileSize + (node.y - 10) * tileSize + panel.camY,
                    tileSize, tileSize, null);

        if (click) clickFun();
    }

    private void clickFun() {
        Tile tile = getTileHovering();
        if (tile != null) tile.clickFun();

        click = false;
    }

    private void layerFun(Graphics2D gg) {
        layer.clear();
        layer.addAll(maps.get(currentMap));
        layer.add(player);
        layer.sort(Comparator.comparingInt(Object::getPriority).thenComparing(Object::getY));
        for (Object o : layer) o.draw(gg, panel.camX, panel.camY);
    }

    public void start() {
        panel.game.visible = true;
        panel.camX = panel.width/2 - tileSize/2 - 13 / 2 * tileSize;
        panel.camY = panel.height/2 - tileSize/2 - 13 / 2 * tileSize;
    }

    private Tile getTileHovering() {
        for (Tile tile : maps.get(currentMap))
            if (panel.hovering(tile, panel.curX - panel.camX, panel.curY - panel.camY))
                return tile;
        return null;
    }

//    public Tile getTileAt(int x, int y) {
//        for (Tile tile : tiles.get("BACKGROUND")) {
//            if (tile.x == x && tile.y == y) {
//                return tile;
//            }
//        }
//        return null; // Return null if no tile is found at the specified coordinates
//    }
}
