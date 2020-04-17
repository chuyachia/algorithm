package graph;

import java.util.Arrays;

public class FordFulkerson {
    private static class EdgeCapacity {
        private final double capacity;
        private double flow;

        public EdgeCapacity(double capacity, double flow) {
            this.capacity = capacity;
            this.flow = flow;
        }

        public double remainingCapacity() {
            return capacity - flow;
        }

        public void addFlow(double flow) {
            this.flow += flow;
        }
    }

    private final double[][] graph;
    private final int n;
    private final int source;
    private final int sink;
    private EdgeCapacity[][] residualGraph;
    private double maxFlow;
    private boolean[] visited;

    public FordFulkerson(double[][] graph, int source, int sink) {
        this.graph = graph;
        this.source = source;
        this.sink = sink;
        this.n = graph.length;
        this.residualGraph = new EdgeCapacity[n][n];
        this.visited = new boolean[n];
    }

    public static void main(String[] args) {
        double[][] graph = Util.emptyGraph(11);
        graph[0][1] = 7;
        graph[0][2] = 2;
        graph[0][3] = 1;
        graph[1][4] = 2;
        graph[1][5] = 4;
        graph[2][5] = 5;
        graph[3][4] = 4;
        graph[3][8] = 8;
        graph[4][7] = 7;
        graph[4][8] = 1;
        graph[5][7] = 3;
        graph[5][9] = 3;
        graph[5][6] = 8;
        graph[6][9] = 3;
        graph[7][10] = 1;
        graph[8][10] = 3;
        graph[9][10] = 4;

        FordFulkerson fordFulkerson = new FordFulkerson(graph, 0, 10);
        double maxFlow = fordFulkerson.computeMaxFlow();
        System.out.println(String.format("Max flow : %1$,.0f", maxFlow));
    }

    public double computeMaxFlow() {
        setupResidualGraph();
        searchPath();

        return maxFlow;
    }

    private void searchPath() {
        for (double flow = dfs(source, Double.POSITIVE_INFINITY); flow > 0; flow = dfs(source, Double.POSITIVE_INFINITY)){
            Arrays.fill(visited, false);
            maxFlow += flow;
        }
    }

    private double dfs(int currentNode, double inFlow) {
        visited[currentNode] = true;
        // Sink reached
        if (currentNode == sink) {
            return inFlow;
        }

        EdgeCapacity[] edges = residualGraph[currentNode];

        for (int i = 0; i < n; i++) {
            EdgeCapacity edge = edges[i];
            if (!visited[i] && edge != null && edge.remainingCapacity() > 0) {
                double outFlow = Math.min(inFlow, edge.remainingCapacity());

                double nextOutFlow = dfs(i, outFlow);

                if (nextOutFlow > 0) {
                    addFlow(currentNode, i, nextOutFlow);
                    addFlow(i, currentNode, -nextOutFlow);
                    return nextOutFlow;
                }
            }
        }

        // No available out edge
        return 0;
    }

    private void addFlow(int from, int to, double flow) {
        EdgeCapacity edgeCapacity = residualGraph[from][to];
        edgeCapacity.addFlow(flow);
    }

    private void setupResidualGraph() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (graph[i][j] < Double.POSITIVE_INFINITY) {
                    residualGraph[i][j] = new EdgeCapacity(graph[i][j], 0);
                    residualGraph[j][i] = new EdgeCapacity(0, 0);
                }
            }
        }
    }
}
