import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Runnable {
    Thread thread;
    int width, height;

    Panel(int width, int height) {
        this.width = width;
        this.height = height;
        setBounds(0, 0, width, height);

        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        double drawInterval = (double) 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (thread != null) {
            repaint();

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

        gg.setFont(new Font("Consolas", Font.BOLD, 15));
        gg.setColor(new Color(0x000000));
        gg.drawString(width + " " + height, width/2, height/2);

        gg.dispose();
    }
}
