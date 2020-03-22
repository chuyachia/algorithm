package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public class DepthFirstSearch {
    private final List<List<Edge>> graph; // Adjacent list
    private boolean[] visited;

    public DepthFirstSearch(List<List<Edge>> graph) { // graph in adjacent list
        this.graph = graph;
    }


    public void iterativeSearch(int start, Consumer<Integer> func) {
        Stack<Integer> verticesStack = new Stack<>();
        visited = new boolean[graph.size()];
        visited[start] = true;
        verticesStack.push(start);
        while (!verticesStack.empty()) {
            Integer vertex = verticesStack.pop();
            List<Edge> edges = graph.get(vertex);
            visited[vertex] = true;
            for (Edge e : edges) {
                int toVertex = e.getVertex();
                if (!visited[toVertex]) {
                    visited[toVertex] = true;
                    verticesStack.push(toVertex);
                }
            }

            func.accept(vertex);
        }
    }

    public void recursiveSearch(int start, Consumer<Integer> func) {
        recursiveSearch(start, func, new boolean[graph.size()]);
    }

    public void recursiveSearch(int start, Consumer<Integer> func, boolean[] visited) {
        this.visited = visited;
        dfs(start, func);
    }

    public static void main(String[] args) {
        List<List<Edge>> graph = new ArrayList<>();
        graph.add(Arrays.asList(new Edge(1,0), new Edge(9,0)));
        graph.add(Arrays.asList(new Edge(0,0), new Edge(8,0)));
        graph.add(Arrays.asList(new Edge(3,0)));
        graph.add(Arrays.asList(new Edge(2,0), new Edge(4,0), new Edge(5,0), new Edge(7,0)));
        graph.add(Arrays.asList(new Edge(3,0)));
        graph.add(Arrays.asList(new Edge(3,0), new Edge(6,0)));
        graph.add(Arrays.asList(new Edge(5,0), new Edge(7,0)));
        graph.add(Arrays.asList(new Edge(3,0), new Edge(8,0), new Edge(10, 0), new Edge(11, 0), new Edge(6,0)));
        graph.add(Arrays.asList(new Edge(1,0), new Edge(9,0), new Edge(7,0)));
        graph.add(Arrays.asList(new Edge(0,0), new Edge(8,0)));
        graph.add(Arrays.asList(new Edge(7,0), new Edge(11, 0)));
        graph.add(Arrays.asList(new Edge(7,0), new Edge(10, 0)));

        DepthFirstSearch dfs = new DepthFirstSearch(graph);
//        dfs.iterativeSearch(0, integer -> {
//            System.out.println(integer);
//        });

        dfs.recursiveSearch(0, integer -> {System.out.println(integer);});
    }

    private void dfs(int vertex, Consumer<Integer> func) {
        if (!visited[vertex]) {
            visited[vertex] = true;
            List<Edge> edges = graph.get(vertex);
            for (Edge e : edges) {
                int toVertex = e.getVertex();
                dfs(toVertex, func);
            }
            func.accept(vertex);
        }
    }

}
