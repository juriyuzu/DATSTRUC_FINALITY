import java.util.*;

public class AStar {
    static int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    static int heuristic(int x, int y, int targetX, int targetY) {
        return Math.abs(x - targetX) + Math.abs(y - targetY);
    }

    static List<Node> findPath(int[][] grid, int startX, int startY, int targetX, int targetY) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(a -> a.g + a.h));
        Set<Node> closedSet = new HashSet<>();
        Node[][] nodes = new Node[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                nodes[i][j] = new Node(i, j);
            }
        }

        Node startNode = nodes[startX][startY];
        startNode.g = 0;
        startNode.h = heuristic(startX, startY, targetX, targetY);
        openSet.offer(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.x == targetX && current.y == targetY) {
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            closedSet.add(current);


            Random rnd = new Random();
            for (int i = directions.length - 1; i > 0; i--) {
                int index = rnd.nextInt(i + 1);
                int[] temp = directions[index];
                directions[index] = directions[i];
                directions[i] = temp;
            }
            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                if (newX < 0 || newX >= grid.length || newY < 0 || newY >= grid[0].length || grid[newX][newY] == 1) {
                    continue;
                }

                Node neighbor = nodes[newX][newY];

                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeG = current.g + 1;

                if (tentativeG < neighbor.g || !openSet.contains(neighbor)) {
                    neighbor.parent = current;
                    neighbor.g = tentativeG;
                    neighbor.h = heuristic(newX, newY, targetX, targetY);

                    if (!openSet.contains(neighbor)) {
                        openSet.offer(neighbor);
                    }
                }
            }
        }

        return null;
    }
    static List<Node> findPath(int size, LinkedList<Tile> map, Object location, LinkedList<Integer> mapSizes) {
        int w = mapSizes.getFirst();
        int h = mapSizes.getLast();
        int[][] grid = new int[h][w];
        int lx = -1, ly = -1, dx = -1, dy = -1;
        LinkedList<Object> exits = new LinkedList<>();
        for (Tile tile : map) if (tile.type == TileType.EXIT) exits.add(tile);

        List<Node> minPath = null;
        for (Object destination : exits) {
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                    boolean wallFlag = false;
                    for (Tile tile : map) {
                        if (tile.solid && rectRect(
                                tile.x - (float) tile.size / 2, tile.y - (float) tile.size / 2, tile.size, tile.size,
                                size * j - (float) location.w / 4,
                                size * i - (float) location.h / 4,
                                location.w, location.h)) {
                            wallFlag = true;
                            break;
                        }
                    }
                    grid[j][i] = wallFlag ? 1 : 0;

                    if (rectRect(
                            destination.x, destination.y, (float) destination.w/2, (float) destination.h/2,
                            size * j, size * i, size, size)) {
                        dx = j;
                        dy = i;
                    }

                    if (rectRect(
                            location.x + (float) size/2 - (float) location.w/2, location.y + (float) size/2 - (float) location.h/2, location.w, location.h,
                            size * j, size * i, size, size)) {
                        lx = j;
                        ly = i;
                    }
                }
            }
            System.out.println(lx + " " + ly + " " + dx + " " + dy);
            if (lx == -1 || ly == -1 || dx == -1 || dy == -1) return null;
            List<Node> path = findPath(grid, lx, ly, dx, dy);
            if (minPath == null) minPath = path;
            else if (path != null && minPath.size() > path.size()) minPath = path;
        }

        for (int i = 0; i < grid.length; i++) {
            for (int[] j : grid) System.out.print(j[i] + ", ");
            System.out.println();
        }
        if (minPath != null) for (Node node : minPath) System.out.println(node.x + " " + node.y);

        return minPath;
    }

    static boolean rectRect(float r1x, float r1y, float r1w, float r1h, float r2x, float r2y, float r2w, float r2h) {
        return r1x + r1w >= r2x &&
                r1x <= r2x + r2w &&
                r1y + r1h >= r2y &&
                r1y <= r2y + r2h;
    }

    public static void main(String[] args) {
        int[][] grid = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        int startX = 10;
        int startY = 10;
        int targetX = 6;
        int targetY = 9;

        List<Node> path = findPath(grid, startX, startY, targetX, targetY);

        if (path != null) {
            System.out.println("Path found: " + path.size());
            for (Node node : path) {
                System.out.println("(" + node.x + ", " + node.y + ")");
            }
        } else {
            System.out.println("No path found.");
        }
    }
}
