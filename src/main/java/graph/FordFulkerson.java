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
    private int[] currentPath;
    private boolean currentPathFound;
    private double currentMinFlow;

    public FordFulkerson(double[][] graph, int source, int sink) {
        this.graph = graph;
        this.source = source;
        this.sink = sink;
        this.n = graph.length;
        this.residualGraph = new EdgeCapacity[n][n];
        this.visited = new boolean[n];
        this.currentPath = new int[n];
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
        do {
            resetCurrentPathState();

            dfs(source);

            if (currentPathFound) {
                maxFlow += currentMinFlow;
                updateFlowInPath();
            }

        } while (currentPathFound);

    }

    private void dfs(int currentNode) {
        visited[currentNode] = true;
        if (currentNode == sink) {
            currentPathFound = true;
            return;
        }

        EdgeCapacity[] edges = residualGraph[currentNode];

        for (int i = 0; i < n; i++) {
            EdgeCapacity edge = edges[i];
            if (!visited[i] && edge != null && edge.remainingCapacity() > 0) {
                dfs(i);
                if (currentPathFound) {
                    currentPath[currentNode] = i;
                    currentMinFlow = Math.min(currentMinFlow, edge.remainingCapacity());
                    return;
                }
            }
        }
    }

    private void resetCurrentPathState() {
        Arrays.fill(currentPath, -1);
        Arrays.fill(visited, false);
        currentPathFound = false;
        currentMinFlow = Double.POSITIVE_INFINITY;
    }

    private void updateFlowInPath() {
        int currentNode = source;
        while (currentNode != sink) {
            int nextNode = currentPath[currentNode];
            if (nextNode == -1) break;
            addFlow(currentNode, nextNode, currentMinFlow);
            addFlow(nextNode, currentNode, -currentMinFlow);
            currentNode = nextNode;
        }
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
