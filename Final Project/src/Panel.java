import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Panel extends JPanel implements Runnable {
    Thread thread;
    int tileSize;
    int width, height;
    Key key;
    Player player;
    int fpsCounter, fps;
    HashMap<String, Tile> tiles;

    Panel(Main main, int width, int height) {
        this.width = width;
        this.height = height;
        setBounds(0, 0, width, height);

        thread = new Thread(this);
        thread.start();

        tileSize = 50;
        key = new Key(main);
        player = new Player(this);

        fpsCounter = 0;
        Timer timer = new Timer(1000, e -> {
            fps = fpsCounter;
            fpsCounter = 0;
        });
        timer.start();

        tiles = new HashMap<>();
        tiles.put("test", new Tile(this, 50, 50));
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

        for (HashMap.Entry<String, Tile> entry : tiles.entrySet()) entry.getValue().draw(gg);

        player.draw(gg);

        gg.setFont(new Font("Consolas", Font.BOLD, 15));
        gg.setColor(new Color(0x000000));
        gg.drawString(width + " " + height + " " + fps + " " + fpsCounter, width/2, height/2);

        gg.dispose();
    }

    public int tilePos(int n) {
        return n * tileSize;
    }
}
