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
