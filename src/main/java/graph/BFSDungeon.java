package graph;

import java.util.LinkedList;
import java.util.Queue;

public class BFSDungeon {
    private static final int[] d1Vector = {1, -1, 0, 0};
    private static final int[] d2Vector = {0, 0, 1, -1};

    private final String[][] grid;
    private final String obstable;
    private final int d1Limit;
    private final int d2Limit;
    private final int[] start;
    private final int[] destination;
    private final Queue<int[]> queue;
    private final boolean[][] visited;
    private final int[][][] route;
    private int currentLayerNeighbors = 0;
    private int nextLayerNeighbors = 0;
    private int stepCount = 0;

    public BFSDungeon(String[][] grid, String obstable, int[] start, int[] destination) {
        this.grid = grid;
        this.obstable = obstable;
        this.d1Limit = grid.length;
        this.d2Limit = grid[0].length;
        this.start = start;
        this.destination = destination;
        queue = new LinkedList<>();
        visited = new boolean[grid.length][grid[0].length];
        route = new int[grid.length][grid[0].length][2];
    }

    public void solve() {
        boolean solvable = bfs();
        if (solvable) {
            int[][] shortestPath =  reconstructRoute();
            System.out.println(String.format("Solvable in %s steps",stepCount));
            for (int[] step :shortestPath) {
                System.out.print(step[0]);
                System.out.print(":");
                System.out.print(step[1]);
                System.out.println();
            }
        } else {
            System.out.println("Unsolvable");
        }
    }

    private int[][] reconstructRoute() {
        int count = stepCount;
        int[][] shortestPath = new int[stepCount][2];
        int[] currentCell = destination;
        while (!isSame(currentCell,start) && count > 0) {
            shortestPath[count-1] = currentCell;
            currentCell = route[currentCell[0]][currentCell[1]];
            count--;
        }

        return shortestPath;
    }

    private boolean bfs() {
        queue.offer(start);
        visited[start[0]][start[1]] = true;
        currentLayerNeighbors = 1;
        while (!queue.isEmpty()) {
            int[] currentCell = queue.poll();

            if (isSame(currentCell, destination)) {
                return true;
            }

            enqueueNeighbors(currentCell);

            currentLayerNeighbors --;
            if (currentLayerNeighbors == 0) {
                stepCount ++;
                currentLayerNeighbors = nextLayerNeighbors;
                nextLayerNeighbors = 0;
            }
        }

        return false;
    }

    private void enqueueNeighbors(int[] cell) {
        for (int i = 0;i< 4; i++) {
            int[] neighborCell = new int[] {cell[0] + d1Vector[i], cell[1]+ d2Vector[i]};
            if (isAccessible(neighborCell) && !isVisited(neighborCell)) {
                queue.offer(neighborCell);
                nextLayerNeighbors ++;
                visited[neighborCell[0]][neighborCell[1]] = true;
                route[neighborCell[0]][neighborCell[1]] = cell;
            }
        }
    }

    private boolean isSame(int[] cell1, int[] cell2) {
        return cell1[0] == cell2[0] && cell1[1] ==cell2[1];
    }

    private boolean isAccessible(int[] cell) {
        if (cell[0] < 0 || cell[0] >= d1Limit) return false;
        if (cell[1] < 0 || cell[1] >= d2Limit) return false;
        if (grid[cell[0]][cell[1]].equals(obstable)) return false;

        return true;
    }

    private boolean isVisited(int[] cell) {
        return visited[cell[0]][cell[1]];
    }

    public static void main(String[] args) {
        String[][] dungeon = {
                {"S","","","#","","",""},
                {"","#","","","","#",""},
                {"","#","","","","",""},
                {"","","#","#","","",""},
                {"#","","#","E","","#",""}};

        BFSDungeon BFSDungeon = new BFSDungeon(dungeon, "#", new int[]{0,0}, new int[]{4,3});
        BFSDungeon.solve();
    }
}
