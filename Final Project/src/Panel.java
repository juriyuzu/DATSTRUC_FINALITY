import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Panel extends JPanel implements Runnable {
    Thread thread;
    int tileSize;
    int width, height;
    Key key;
    Player player;
    Save save;
    int fpsCounter, fps;
    HashMap<String, LinkedList<Tile>> tiles;
    LinkedList<Tile> layer;
    Chaser chaser;
    Random random;

    Panel(Main main, int width, int height) {
        this.width = width;
        this.height = height;
        setBounds(0, 0, width, height);

        thread = new Thread(this);
        thread.start();

        tileSize = 50;
        key = new Key(main);
        save = new Save();
        random = new Random();

        tiles = new HashMap<>();
        tiles.put("BACKGROUND", new LinkedList<>());
        tiles.put("PLAYGROUND", new LinkedList<>());

        player = new Player(this);
        tiles.get("PLAYGROUND").add(player);

        fpsCounter = 0;
        Timer timer = new Timer(1000, e -> {
            fps = fpsCounter;
            fpsCounter = 0;
        });
        timer.start();

        layer = new LinkedList<>();

        // sample map
        {
            LinkedList<String> sampleMap = new LinkedList<>(save.read("Final Project/save/sampleMap.txt"));
            for (int j = 0; j < sampleMap.size(); j++)
                for (int i = 0; i < sampleMap.get(j).length(); i++) {
                    switch (sampleMap.get(j).charAt(i)) {
                        case '0' -> tiles.get("PLAYGROUND").add(new Tile(this, i * 50, j * 50, TileType.WALL));
                        case '.' -> tiles.get("BACKGROUND").add(new Tile(this, i * 50, j * 50, TileType.FLOOR));
            }}
        }

        chaser = new Chaser(this, 18 * 50, 20 * 50);
        tiles.get("PLAYGROUND").add(chaser);
    }

    public void run() {
        double drawInterval = (double) 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (thread != null) {
            repaint();
            fpsCounter++;

            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;

        gg.setColor(new Color(0xA1A1A1));
        gg.fillRect(0, 0, width, height);

        layerFun(gg);

        gg.setFont(new Font("Consolas", Font.BOLD, 15));
        gg.setColor(new Color(0x000000));
        gg.drawString(width + " " + height + " " + fps + " " + fpsCounter + " " + player.x + " " + player.y + " Stamina: " + (int) player.stamina, width/2, height/2);

        gg.dispose();
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
