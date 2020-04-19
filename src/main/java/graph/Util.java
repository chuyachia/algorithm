package graph;

import java.util.Arrays;

public class Util {
    public static double[][] emptyAdjacencyMatrix(int n) {
        double[][] graph = new double[n][n];
        for (int i = 0; i < graph.length; i++) {
            Arrays.fill(graph[i], Double.POSITIVE_INFINITY);
            graph[i][i] = 0;
        }

        return graph;
    }
}
