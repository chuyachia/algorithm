package graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FordFulkerson {
    private static class EdgeCapacity {
        private final int to;
        private final int capacity;
        private int flow;
        private EdgeCapacity residual;

        public EdgeCapacity(int to, int capacity, int flow) {
            this.to = to;
            this.capacity = capacity;
            this.flow = flow;
        }

        public int remainingCapacity() {
            return capacity - flow;
        }

        public void addFlow(int flow) {
            this.flow += flow;
        }

        public void setResidual(EdgeCapacity edgeCapacity) {
            this.residual = edgeCapacity;
        }
    }

    private final List<Edge>[] graph;
    private final int n;
    private final int source;
    private final int sink;
    private final List<EdgeCapacity>[] residualGraph;
    private int maxFlow;
    private boolean[] visited;

    public FordFulkerson(List<Edge>[] graph, int source, int sink) {
        this.graph = graph;
        this.source = source;
        this.sink = sink;
        this.n = graph.length;
        this.residualGraph = new LinkedList[graph.length];
        this.visited = new boolean[n];
    }

    public static void main(String[] args) {
        List<Edge>[] graph = new List[11];
        graph[0] = Arrays.asList(new Edge(1, 7), new Edge(2, 2), new Edge(3, 1));
        graph[1] = Arrays.asList(new Edge(4, 2), new Edge(5, 4));
        graph[2] = Arrays.asList(new Edge(5, 5));
        graph[3] = Arrays.asList(new Edge(4, 4), new Edge(8, 8));
        graph[4] = Arrays.asList(new Edge(7, 7), new Edge(8, 1));
        graph[5] = Arrays.asList(new Edge(7, 3), new Edge(6, 8), new Edge(9, 3));
        graph[6] = Arrays.asList(new Edge(9, 3));
        graph[7] = Arrays.asList(new Edge(10, 1));
        graph[8] = Arrays.asList(new Edge(10, 3));
        graph[9] = Arrays.asList(new Edge(10, 4));

        FordFulkerson fordFulkerson = new FordFulkerson(graph, 0, 10);
        int maxFlow = fordFulkerson.computeMaxFlow();
        System.out.println(String.format("Max flow : %d", maxFlow));
    }

    public int computeMaxFlow() {
        setupResidualGraph();
        searchPath();

        return maxFlow;
    }

    private void searchPath() {
        for (int flow = dfs(source, Integer.MAX_VALUE); flow > 0; flow = dfs(source, Integer.MAX_VALUE)) {
            Arrays.fill(visited, false);
            maxFlow += flow;
        }
    }

    private int dfs(int currentNode, int inFlow) {
        visited[currentNode] = true;
        // Sink reached
        if (currentNode == sink) {
            return inFlow;
        }

        List<EdgeCapacity> edges = residualGraph[currentNode];

        for (EdgeCapacity edge : edges) {
            int nextNode = edge.to;
            if (!visited[nextNode] && edge != null && edge.remainingCapacity() > 0) {
                int outFlow = Math.min(inFlow, edge.remainingCapacity());
                int nextOutFlow = dfs(nextNode, outFlow);

                if (nextOutFlow > 0) {
                    pushFlow(edge, nextOutFlow);
                    return nextOutFlow;
                }
            }
        }

        // No available out edge
        return 0;
    }

    private void pushFlow(EdgeCapacity edge, int flow) {
        edge.addFlow(flow);
        edge.residual.addFlow(-flow);
    }

    private void setupResidualGraph() {
        for (int i = 0; i < graph.length; i++) {
            List<Edge> inputGraphEdges = graph[i];

            if (inputGraphEdges == null) continue;

            for (Edge edge : inputGraphEdges) {
                EdgeCapacity newEdge = new EdgeCapacity(edge.getVertex(), edge.getWeight(), 0);
                EdgeCapacity newResidualEdge = new EdgeCapacity( i, 0, 0);
                newEdge.setResidual(newResidualEdge);
                newResidualEdge.setResidual(newEdge);

                if (residualGraph[i] == null) {
                    residualGraph[i] = new LinkedList<>();
                }
                residualGraph[i].add(newEdge);

                if (residualGraph[edge.getVertex()] == null) {
                    residualGraph[edge.getVertex()] = new LinkedList<>();
                }
                residualGraph[edge.getVertex()].add(newResidualEdge);
            }
        }
    }
}
