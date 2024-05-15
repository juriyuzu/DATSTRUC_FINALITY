import javax.swing.*;
import java.awt.*;

public class Object {
    Image image;
    int x, y, w, h;

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

    public void draw(Graphics2D gg) {
        gg.drawImage(image, x, y, w, h, null);
    }
}
