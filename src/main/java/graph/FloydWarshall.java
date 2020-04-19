package graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FloydWarshall {
    private final double[][] graph;
    private final double[][] solution;
    private final Integer[][] nextInPath;
    private boolean solved = false;

    public FloydWarshall(double[][] graph) {
        this.graph = graph;
        this.solution = new double[graph.length][graph.length];
        this.nextInPath = new Integer[graph.length][graph.length];
        this.setUp();
    }


    public double[][] allPairsDistance() {
        fw();
        detectNegativeCycle();
        solved = true;

        return solution;
    }

    public List<Integer> shortestPath(int i, int j) {
        if (!solved) {
            allPairsDistance();
        }

        List<Integer> shortest = new LinkedList<>();
        // Unreachable
        if (nextInPath[i][j] == null) {
            return shortest;
        }

        Integer currentNode = i;
        shortest.add(currentNode);
        while (!currentNode.equals(j)) {
            currentNode = nextInPath[currentNode][j];
            // Falls into negative cycle
            if (currentNode == -1) {
                return Arrays.asList(-1);
            }
            shortest.add(currentNode);
        }

        return shortest;
    }


    private void setUp() {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                double e = graph[i][j];
                solution[i][j] = e;
                if (e < Double.POSITIVE_INFINITY) {
                    nextInPath[i][j] = j;
                }
            }
        }
    }

    private void fw() {
        for (int k = 0; k < solution.length; k++) {
            for (int i = 0; i < solution.length; i++) {
                for (int j = 0; j < solution.length; j++) {
                    if (solution[i][j] > solution[i][k] + solution[k][j]) {
                        solution[i][j] = solution[i][k] + solution[k][j];
                        nextInPath[i][j] = nextInPath[i][k];
                    }
                }
            }
        }
    }

    private void detectNegativeCycle() {
        for (int k = 0; k < solution.length; k++) {
            for (int i = 0; i < solution.length; i++) {
                for (int j = 0; j < solution.length; j++) {
                    if (solution[i][j] > solution[i][k] + solution[k][j]) {
                        solution[i][j] = Double.NEGATIVE_INFINITY;
                        nextInPath[i][j] = -1;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // Graph without negative cycle
        double[][] normalGraph = Util.emptyAdjacencyMatrix(4);
        normalGraph[0][2] = -2;
        normalGraph[1][0] = 4;
        normalGraph[1][2] = 3;
        normalGraph[2][3] = 2;
        normalGraph[3][1] = -1;

        FloydWarshall floydWarshall = new FloydWarshall(normalGraph);

        List<Integer> sp = floydWarshall.shortestPath(1, 2);
        for (Integer i : sp) {
            System.out.println(i);
        }

        // Graph with negative cycle
        double[][] negativeCycleGraph = Util.emptyAdjacencyMatrix(7);
        negativeCycleGraph[0][1] = 1;
        negativeCycleGraph[0][2] = 1;
        negativeCycleGraph[1][3] = 4;
        negativeCycleGraph[2][1] = 1;
        negativeCycleGraph[3][2] = -6;
        negativeCycleGraph[3][4] = 1;
        negativeCycleGraph[3][5] = 1;
        negativeCycleGraph[4][5] = 1;
        negativeCycleGraph[4][6] = 3;
        negativeCycleGraph[5][6] = 1;

        FloydWarshall floydWarshall1 = new FloydWarshall(negativeCycleGraph);

        // No shortest path because of negative loop
        List<Integer> sp1 = floydWarshall1.shortestPath(2, 6);
        for (Integer i : sp1) {
            System.out.println(i);
        }


    }
}
