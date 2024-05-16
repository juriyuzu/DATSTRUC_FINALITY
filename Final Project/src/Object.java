import javax.swing.*;
import java.awt.*;

public class Object {
    Image image;
    int x, y, w, h;
    int priority;

    /**
     * A basic Graphics 2D instance that holds an Image, coordinates, and size.
     * Can be drawn using the public draw function.
     */
    Object(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.w = this.image.getWidth(null);
        this.h = this.image.getHeight(null);
    }

    Object(Image image, int x, int y, int w, int h) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(Graphics2D gg, int camX, int camY) {
        gg.drawImage(image, x + camX, y + camY, w, h, null);
    }

    public int getY() {return y;}

    public int getPriority() {return priority;}
}
