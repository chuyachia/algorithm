package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public class DepthFirstSearch {
    private final List<List<Integer>> graph; // Adjacent list
    private boolean[] visited;

    public DepthFirstSearch(List<List<Integer>> graph) { // graph in adjacent list
        this.graph = graph;
    }


    public void iterativeSearch(Consumer<Integer> func) {
        Stack<Integer> verticesStack = new Stack<>();
        visited = new boolean[graph.size()];
        verticesStack.push(0);
        visited[0] = true;
        while (!verticesStack.empty()) {
            Integer vertex = verticesStack.pop();
            List<Integer> edges = graph.get(vertex);
            visited[vertex] = true;
            for (Integer e : edges) {
                if (!visited[e]) {
                    visited[e] = true;
                    verticesStack.push(e);
                }
            }
            // Do something with current vertex
            func.accept(vertex);
        }
    }

    public void recursiveSearch(Consumer<Integer> func) {
        visited = new boolean[graph.size()];
        dfs(0, func);
    }

    public static void main(String[] args) {
        List<List<Integer>> graph = new ArrayList<>();
        graph.add(Arrays.asList(1, 9));
        graph.add(Arrays.asList(0, 8));
        graph.add(Arrays.asList(3));
        graph.add(Arrays.asList(2, 4, 5, 7));
        graph.add(Arrays.asList(3));
        graph.add(Arrays.asList(3, 6));
        graph.add(Arrays.asList(5, 7));
        graph.add(Arrays.asList(3, 8, 10, 11, 6));
        graph.add(Arrays.asList(1, 9, 7));
        graph.add(Arrays.asList(0, 8));
        graph.add(Arrays.asList(7, 11));
        graph.add(Arrays.asList(7, 10));

        DepthFirstSearch dfs = new DepthFirstSearch(graph);
        dfs.iterativeSearch(integer -> {
            System.out.println(integer);
        });

//        dfs.recursiveSearch(integer -> {System.out.println(integer);});
    }

    private void dfs(Integer vertex, Consumer<Integer> func) {
        if (!visited[vertex]) {
            visited[vertex] = true;
            func.accept(vertex);
            List<Integer> edges = graph.get(vertex);
            for (Integer e : edges) {
                dfs(e, func);
            }
        }
    }

}
