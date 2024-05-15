import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Panel extends JPanel implements Runnable {
    Thread thread;
    int tileSize;
    int width, height;
    Key key;
    Save save;
    int fpsCounter, fps;
    HashMap<String, LinkedList<Tile>> tiles;
    LinkedList<Tile> layer;
    Random random;
    Player player;
    MainMenu mainMenu;
    int curX, curY;

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

        fpsCounter = 0;
        Timer timer = new Timer(1000, e -> {
            fps = fpsCounter;
            fpsCounter = 0;
        });
        timer.start();

        mainMenu = new MainMenu(this);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                curX = e.getX();
                curY = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                curX = e.getX();
                curY = e.getY();
            }
        });
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

        // backdrop
        gg.setColor(new Color(0xA1A1A1));
        gg.fillRect(0, 0, width, height);

        mainMenu.draw(gg);

        // debug
        {
            gg.setFont(new Font("Consolas", Font.BOLD, 15));
            gg.setColor(new Color(0x000000));
            gg.drawString(width + " " + height + " " + fps + " " + fpsCounter, width / 2, height / 2);
            gg.drawString(curX + " " + curY, width / 2, height / 2 + 15);
            gg.drawString(key.key.get("W") + " " + key.key.get("S"), width / 2, height / 2 + 30);
        }

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
