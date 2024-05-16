import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Chaser extends Tile {
    int[][] grid;
    private static final int radius = 10;
    private int px, py, lastPx, lastPy;
    private List<Node> path;

    Chaser(Panel panel, int x, int y) {
        super(panel, x, y, TileType.MOB);
//        this.size = panel.player.size;

        grid = new int[radius * 2][radius * 2];
    }

    public void draw(Graphics2D gg) {
        gg.setColor(new Color(0xFF183B62, true));
        gg.fillRect(x - size/2 - player.getXYCam(true), y - size*3/2 - player.getXYCam(false), size, size*2);
        gg.setColor(new Color(0xC2306EB4, true));
        gg.fillRect(x - size/2 - player.getXYCam(true), y - size/2 - player.getXYCam(false), size, size);

        // grid debug
        {
            for (int i = 0; i < radius * 2; i++) for (int j = 0; j < radius * 2; j++)
                gg.fillRect(x - radius * 50 + 50 * j - size / 4 - player.getXYCam(true), y - radius * 50 + 50 * i - size / 4 - player.getXYCam(false), size / 2, size / 2);
        }

        move();
    }

    private int xVel = 0;
    private int yVel = 0;
    private void move() {
        int speed = 6;

        if ((Math.abs(x) % 50 <= speed && Math.abs(y) % 50 <= speed)) {
//            gridMaker();
            path = AStar.findPath(grid, radius, radius, px, py);

            System.out.println();
            for (int[] g : grid) System.out.println(Arrays.toString(g));
            System.out.println(grid.length + " " + grid[0].length);
            System.out.println(radius + " " + radius + " " + px + " " + py);
            System.out.println(path != null);

            if (path != null && path.size() > 1) {
                for (Node node : path) System.out.println("\t" + node.x + ", " + node.y);
                int xDiff = path.get(1).x - path.get(0).x;
                int yDiff = path.get(1).y - path.get(0).y;
                xVel = xDiff * speed;
                yVel = yDiff * speed;
            } else {
                xVel = 0;
                yVel = 0;
            }
        }
//        else if (path == null && dist(x, y, player.x, player.y) < radius * 50) gotoxy(Math.round((float) x / 50) * 50, Math.round((float) y / 50) * 50);

        boolean xF = true, yF = true;
        gotoxy(x + xVel, y);
//        for (Tile tile : panel.tiles.get("PLAYGROUND")) {
//            if (tile != this && tile.solid && rectRect(tile.x - (float) tile.size/2, tile.y - (float) tile.size/2, tile.size, tile.size, x - (float) player.size/2, y - (float) player.size/2)) {
//                xF = false;
//                break;
//            }
//        }
//        gotoxy(x - xVel, y + yVel);
//        for (Tile tile : panel.tiles.get("PLAYGROUND")) {
//            if (tile != this && tile.solid && rectRect(tile.x - (float) tile.size/2, tile.y - (float) tile.size/2, tile.size, tile.size, x - (float) player.size/2, y - (float) player.size/2)) {
//                yF = false;
//                break;
//            }
//        }
        if (xF) gotoxy(x + xVel, y);
        if (!yF) gotoxy(x, y - yVel);
    }

//    public void gridMaker() {
//        for (int j = 0; j < radius * 2; j++) {
//            for (int i = 0; i < radius * 2; i++) {
//                boolean wallFlag = false;
//                for (Tile tile : panel.tiles.get("PLAYGROUND")) {
//                    if (tile.type == TileType.WALL && rectRect(
//                            tile.x - (float) tile.size/2, tile.y - (float) tile.size/2, tile.size, tile.size,
//                            x - radius * 50 + 50 * j - (float) size / 4,
//                            y - radius * 50 + 50 * i - (float) size / 4)) {
//                        wallFlag = true;
//                        break;
//                    }
//                }
//                grid[j][i] = wallFlag ? 1 : 0;
//
//                if (rectRect(
//                        lastPx - (float) player.size/2, lastPy - (float) player.size/2, player.size, player.size,
//                        x - radius * 50 + 50 * j - (float) size / 4,
//                        y - radius * 50 + 50 * i - (float) size / 4)) {
//                    px = j;
//                    py = i;
//                }
//                if (rectRect(
//                        player.x - (float) player.size/2, player.y - (float) player.size/2, player.size, player.size,
//                        x - radius * 50 + 50 * j - (float) size / 4,
//                        y - radius * 50 + 50 * i - (float) size / 4)) {
//                    px = j;
//                    py = i;
//                    lastPx = player.x;
//                    lastPy = player.y;
////                    grid[i][j] = 2;
//                }
//            }
//        }
//    }

    boolean rectRect(float r1x, float r1y, float r1w, float r1h, float r2x, float r2y) {
        float r2w = (float) size/2, r2h = (float) size/2;
        return r1x + r1w >= r2x &&
                r1x <= r2x + r2w &&
                r1y + r1h >= r2y &&
                r1y <= r2y + r2h;
    }

    public static double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public void gotoxy(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }
}
