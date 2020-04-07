package graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DFSFindBridge {
    private final List<List<Integer>> graph;
    private final boolean[] visited;
    private final List<Bridge> bridges;
    // Lowest reachable node id
    private final int[] lowLink;

    private static class Bridge {
        private final int from;
        private final int to;

        public Bridge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return String.format("Bridge between node %d and node %d", from, to);
        }
    }

    public DFSFindBridge(List<List<Integer>> graph) {
        this.graph = graph;
        this.visited = new boolean[graph.size()];
        this.bridges = new LinkedList<>();
        this.lowLink = new int[graph.size()];
    }

    public List<Bridge> findBridge() {
        dfs(0, -1);

        return bridges;
    }

    private void dfs(int currentNode, int parentNode) {
        if (!visited[currentNode]) {
            visited[currentNode] = true;
            lowLink[currentNode] = currentNode;
            List<Integer> linkedNodes = graph.get(currentNode);
            for (Integer nextNode : linkedNodes) {
                if (nextNode == parentNode) continue;
                dfs(nextNode, currentNode);
                maybeUpdateLowLink(currentNode, nextNode);
                maybeAddToBridges(currentNode, nextNode);
            }
        }
    }

    private void maybeUpdateLowLink(int nodeFrom, int nodeTo) {
        lowLink[nodeFrom] = Math.min(lowLink[nodeFrom], lowLink[nodeTo]);
    }

    private void maybeAddToBridges(int nodeFrom, int nodeTo) {
        if (nodeFrom < lowLink[nodeTo]) {
            bridges.add(new Bridge(nodeFrom, nodeTo));
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> graph = new LinkedList<>();
        graph.add(Arrays.asList(1, 2));
        graph.add(Arrays.asList(0, 2));
        graph.add(Arrays.asList(0, 1, 3, 5));
        graph.add(Arrays.asList(2, 4));
        graph.add(Arrays.asList(3));
        graph.add(Arrays.asList(2, 6, 8));
        graph.add(Arrays.asList(5, 7));
        graph.add(Arrays.asList(6, 8));
        graph.add(Arrays.asList(5, 7));

        DFSFindBridge dfsFindBridge = new DFSFindBridge(graph);
        List<Bridge> bridges = dfsFindBridge.findBridge();
        for (Bridge bridge : bridges) {
            System.out.println(bridge);
        }
    }
}
