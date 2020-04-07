package graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Tarjan {
    // Find strongly connected components
    private final List<List<Integer>> graph;
    private final boolean[] visited;
    private final Stack<Integer> currentComponentNodes;
    private final int[] lowLink;

    public Tarjan(List<List<Integer>> graph) {
        this.graph = graph;
        this.visited = new boolean[graph.size()];
        this.currentComponentNodes = new Stack<>();
        this.lowLink = new int[graph.size()];
    }

    public int[] stronglyConnectedComponents() {
        for (int i = 0; i<graph.size(); i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }

        return lowLink;
    }

    public static void main(String[] args) {
        List<List<Integer>> graph = new LinkedList<>();
        graph.add(Arrays.asList(1));
        graph.add(Arrays.asList(2));
        graph.add(Arrays.asList(0));
        graph.add(Arrays.asList(7,4));
        graph.add(Arrays.asList(5));
        graph.add(Arrays.asList(6,0));
        graph.add(Arrays.asList(4,0,2));
        graph.add(Arrays.asList(3,5));

        Tarjan tarjan = new Tarjan(graph);
        int[] scc = tarjan.stronglyConnectedComponents();
        for (int i : scc) {
            System.out.println(i);
        }
    }

    private void dfs(int currentNode) {
        if (!visited[currentNode]) {
            lowLink[currentNode] = currentNode;
            visited[currentNode] = true;
            currentComponentNodes.add(currentNode);
            List<Integer> connected = graph.get(currentNode);
            for (Integer nextNode : connected) {
                dfs(nextNode);
                if (currentComponentNodes.contains(nextNode)) {
                    maybeUpdateLowLink(currentNode, nextNode);
                }
                if (currentNode == lowLink[nextNode]) {
                    clearCurrentComponentNodesStack(currentNode);
                }
            }
        }
    }

    private void maybeUpdateLowLink(int fromNode, int toNode) {
        lowLink[fromNode] = Math.min(lowLink[fromNode], lowLink[toNode]);
    }

    private void clearCurrentComponentNodesStack(Integer rootNode) {
        Integer currentNode = currentComponentNodes.pop();
        while (!currentNode.equals(rootNode) && currentComponentNodes.size() > 0) {
            currentNode = currentComponentNodes.pop();
        }
    }
}
