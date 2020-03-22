package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleSourceShortestPath {
    private final List<List<Edge>> graph;
    private final int[] distance;
    private final boolean[] visited;

    public SingleSourceShortestPath(List<List<Edge>> graph) {
        this.graph = graph;
        this.distance = new int[graph.size()];
        this.visited = new boolean[graph.size()];
    }

    public int[] solve(int start) {
        TopologicalSort topologicalSort = new TopologicalSort(graph);
        List<Integer> ordering = topologicalSort.sort();
        distance[start] = 0;
        int startIndex = ordering.indexOf(start);
        for (int i = startIndex; i < ordering.size(); i++) {
            Integer currentVertex = ordering.get(i);
            List<Edge> edges = graph.get(currentVertex);
            for (Edge e : edges) {
                int connectedVertex = e.getVertex();
                int newDistance = distance[currentVertex] + e.getWeight();
                if (!visited[connectedVertex] || newDistance < distance[connectedVertex]) {
                    visited[connectedVertex] = true;
                    distance[connectedVertex] = newDistance;
                }
            }
        }

        return distance;
    }

    public static void main(String[] args) {
        List<List<Edge>> graph = new ArrayList<>();
        graph.add(Arrays.asList(new Edge(1,3), new Edge(2,6)));
        graph.add(Arrays.asList(new Edge(2,4), new Edge(3,4), new Edge(4,11)));
        graph.add(Arrays.asList(new Edge(3,8), new Edge(6, 11)));
        graph.add(Arrays.asList(new Edge(4,-4), new Edge(5, 5), new Edge(6, 2)));
        graph.add(Arrays.asList(new Edge(7,9)));
        graph.add(Arrays.asList(new Edge(7,1)));
        graph.add(Arrays.asList(new Edge(7,2)));
        graph.add(Arrays.asList());


        SingleSourceShortestPath sssp = new SingleSourceShortestPath(graph);
        int[] path = sssp.solve(0);
        for (int w : path) {
            System.out.println(w);
        }
    }
}
