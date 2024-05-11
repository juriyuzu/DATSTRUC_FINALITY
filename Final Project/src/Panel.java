import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class Panel extends JPanel implements Runnable {
    Thread thread;
    int tileSize;
    int width, height;
    Key key;
    Player player;
    Save save;
    int fpsCounter, fps;
    LinkedList<Tile> tiles;
    LinkedList<Tile> layer;

    Panel(Main main, int width, int height) {
        this.width = width;
        this.height = height;
        setBounds(0, 0, width, height);

        thread = new Thread(this);
        thread.start();

        tileSize = 50;
        key = new Key(main);
        player = new Player(this);
        save = new Save();

        fpsCounter = 0;
        Timer timer = new Timer(1000, e -> {
            fps = fpsCounter;
            fpsCounter = 0;
        });
        timer.start();

        layer = new LinkedList<>();

        tiles = new LinkedList<>();
        // sample map
        {
            LinkedList<String> sampleMap = new LinkedList<>(save.read("Final Project/save/sampleMap.txt"));
            for (int j = 0; j < sampleMap.size(); j++)
                for (int i = 0; i < sampleMap.get(j).length(); i++)
                    if (sampleMap.get(j).charAt(i) == '0') tiles.add(new Tile(this, i * 50, j * 50, true));
        }
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

        layerFun();
        for (Tile tile : layer) tile.draw(gg);

        gg.setFont(new Font("Consolas", Font.BOLD, 15));
        gg.setColor(new Color(0x000000));
        gg.drawString(width + " " + height + " " + fps + " " + fpsCounter, width/2, height/2);

        gg.dispose();
    }

    private void layerFun() {
        layer.clear();

        layer.add(player);
        layer.addAll(tiles);

        layer.sort(Comparator.comparingInt(Tile::getY));
    }
}
