package graph;

import math.Combination;

import java.util.Arrays;
import java.util.List;

public class TravellingSalesman {
    private int n;
    private final double[][] graph;
    private final double[][] memo;
    private int minDistanceLastIndex;
    private TSSolution solution;

    public TravellingSalesman(double[][] graph) {
        this.graph = graph;
        this.n = graph.length;
        this.memo = new double[n][(int) Math.pow(2, n)];
    }

    public static double[][] emptyGraph(int n) {
        double[][] graph = new double[n][n];
        for (int i = 0; i < graph.length; i++) {
            Arrays.fill(graph[i], Double.POSITIVE_INFINITY);
            graph[i][i] = 0;
        }

        return graph;
    }

    public static void main(String[] args) {
        double[][] graph = emptyGraph(6);
        graph[5][0] = 10;
        graph[1][5] = 12;
        graph[4][1] = 2;
        graph[2][4] = 4;
        graph[3][2] = 6;
        graph[0][3] = 8;

        TravellingSalesman ts = new TravellingSalesman(graph);
        System.out.println(String.format("Total distance : %1$,.0f",ts.getTotalDistance(0)));
        int[] route = ts.getOptimalPath(0);
        System.out.println("Optimal path :");
        for (int i : route) {
            System.out.print(i);
            System.out.print(" ");
        }
    }

    private int[] getOptimalPath(int start) {
        if (solution == null || solution.start != start) {
            solve(start);
            double cost = totalDistance(start);
            int[] route = optimalRoute(start);
            solution = new TSSolution(start, cost, route);
        }

        return solution.route;
    }

    private double getTotalDistance(int start) {
        if (solution == null || solution.start != start) {
            solve(start);
            double cost = totalDistance(start);
            int[] route = optimalRoute(start);
            solution = new TSSolution(start, cost, route);
        }

        return solution.cost;
    }

    private static class TSSolution {
        private int start;
        private final double cost;
        private final int[] route;

        public TSSolution(int start, double cost, int[] route) {
            this.start = start;
            this.cost = cost;
            this.route = route;
        }
    }

    private double totalDistance(int start) {
        int finalState = (1 << n) - 1;
        double minDistance = Double.POSITIVE_INFINITY;
        int[] route = new int[n+1];
        route[0] = start;
        for (int i = 0; i < n; i++) {
            if (i == start) continue;
            double distance = memo[i][finalState] + graph[i][start];
            if (distance < minDistance) {
                minDistance = distance;
                minDistanceLastIndex = i;
                route[i+1] = i;
            }
        }

        return minDistance;
    }

    private int[] optimalRoute(int start) {
        int[] route = new int[n + 1];
        int currentMinIndex = minDistanceLastIndex;
        route[n] =  route[0] = start;
        route[n - 1] = currentMinIndex;
        int finalState = ((1 << n) - 1) ^ (1 << currentMinIndex);

        for (int current = n - 2; current >= 1; current--) {

            double minDistance = Double.POSITIVE_INFINITY;
            int prevMinIndex = -1;
            for (int prev = 0; prev < n; prev++) {
                if (prev == start || stateNotContains(finalState, prev)) continue;
                double distance = memo[prev][finalState] + graph[prev][currentMinIndex];

                if (distance < minDistance) {
                    minDistance = distance;
                    prevMinIndex = prev;
                }

            }

            currentMinIndex = prevMinIndex;
            finalState = finalState ^ (1 << currentMinIndex);
            route[current] = currentMinIndex;
        }

        return route;
    }

    private void solve(int start) {
        this.setup(start);
        // Build up min distance picking 3 to n nodes
        for (int pick = 3; pick <= n; pick++) {
            List<String> possibleStates = Combination.pick(n, pick);
            for (String state : possibleStates) {
                int stateBinary = Integer.parseInt(state, 2);
                if (stateNotContains(stateBinary, start)) continue;

                for (int next = 0; next < n; next++) {
                    if (stateNotContains(stateBinary, next) || next == start) continue;
                    int stateExcludeNext = stateBinary ^ (1 << next);

                    double minDist = Double.POSITIVE_INFINITY;
                    for (int end = 0; end < n; end++) {
                        if (stateNotContains(stateBinary, end) || end == start || end == next) continue;
                        double newDist = memo[end][stateExcludeNext] + graph[end][next];
                        if (newDist < minDist) {
                            minDist = newDist;
                        }
                    }
                    memo[next][stateBinary] = minDist;
                }
            }
        }
    }

    private void setup(int start) {
        // Setup min distance picking 2 nodes
        for (int end = 0; end < n; end++) {
            if (end == start) continue;
            int endState = (1 << end) | (1 << start);
            memo[end][endState] = graph[start][end];
        }
    }

    private boolean stateNotContains(int state, int bit) {
        return (state & (1 << bit)) == 0;
    }
}
