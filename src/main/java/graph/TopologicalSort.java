package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TopologicalSort {
    private final List<List<Edge>> graph;
    private final List<Integer> ordering;
    private final boolean[] visited;
    private final Set<Integer> unvisted;

    public TopologicalSort(List<List<Edge>> graph) {
        this.graph = graph;
        this.ordering = new ArrayList<>();
        this.visited = new boolean[graph.size()];
        List<Integer>  vertices = IntStream.range(0, graph.size()).boxed().collect(Collectors.toList());
        this.unvisted = new HashSet<>(vertices);
    }

    public List<Integer> sort() {
        DepthFirstSearch depthFirstSearch = new DepthFirstSearch(graph);
        while (unvisted.size() > 0) {
            Integer start = unvisted.stream().findFirst().orElseThrow(RuntimeException::new);
            depthFirstSearch.recursiveSearch(start, this::addToOrdering,visited);
        }

        return this.ordering;
    }

    private void addToOrdering(Integer vertex) {
        ordering.add(0, vertex);
        visited[vertex] = true;
        unvisted.remove(vertex);
    }

    public static void main(String[] args) {
        List<List<Edge>> graph = new ArrayList<>();
        graph.add(Arrays.asList(new Edge(3,0)));
        graph.add(Arrays.asList(new Edge(3,0)));
        graph.add(Arrays.asList(new Edge(0,0), new Edge(1,0)));
        graph.add(Arrays.asList(new Edge(6,0),new Edge(7,0)));
        graph.add(Arrays.asList(new Edge(0,0),new Edge(3,0),new Edge(5,0)));
        graph.add(Arrays.asList(new Edge(9,0),new Edge(10,0)));
        graph.add(Arrays.asList(new Edge(8,0)));
        graph.add(Arrays.asList(new Edge(8,0),new Edge(9,0)));
        graph.add(Arrays.asList(new Edge(11,0)));
        graph.add(Arrays.asList(new Edge(11,0), new Edge(12,0)));
        graph.add(Arrays.asList(new Edge(9,0)));
        graph.add(Arrays.asList());
        graph.add(Arrays.asList());

        TopologicalSort topologicalSort = new TopologicalSort(graph);
        List<Integer> ordering = topologicalSort.sort();
        for (int order : ordering) {
            System.out.println(order);
        }
    }
}
