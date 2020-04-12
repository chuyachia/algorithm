package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class PrimMST {
    private final List<List<Edge>> graph;
    private final List<Edge> mst;
    private Integer mstCost;
    private boolean solved;

    private static class Edge implements Comparable<Edge> {
        private final Integer from;
        private final Integer to;
        private final int weight;

        public Edge(Integer from, Integer to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge edge) {
            return Integer.compare(weight, edge.weight);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }

    public static void main(String[] args) {
        List<List<Edge>> graph = new LinkedList<>();
        graph.add(Arrays.asList(new Edge(0, 1, 10), new Edge(0, 2, 1), new Edge(0, 3, 4)));
        graph.add(Arrays.asList(new Edge(1, 0, 10), new Edge(1, 2, 3), new Edge(1, 4, 0)));
        graph.add(Arrays.asList(new Edge(2, 0, 1), new Edge(2, 1, 3), new Edge(2, 5, 8), new Edge(2, 3, 2)));
        graph.add(Arrays.asList(new Edge(3, 0, 4), new Edge(3, 2, 2), new Edge(3, 5, 2), new Edge(3, 6, 7)));
        graph.add(Arrays.asList(new Edge(4, 1, 0), new Edge(4, 5, 1), new Edge(4, 7, 8)));
        graph.add(Arrays.asList(new Edge(5, 2, 8), new Edge(5, 4, 1), new Edge(5, 7, 9), new Edge(5, 6, 6)));
        graph.add(Arrays.asList(new Edge(6, 3, 7), new Edge(6, 5, 6), new Edge(6, 7, 12)));
        graph.add(Arrays.asList(new Edge(7, 4, 8), new Edge(7, 5, 9), new Edge(7, 6, 12)));

        PrimMST mst = new PrimMST(graph);
        List<Edge> tree = mst.getTree();
        for (Edge e : tree) {
            System.out.println(e);
        }
        System.out.println(String.format("Total cost %d", mst.getCost()));
    }

    public PrimMST(List<List<Edge>> graph) {
        this.graph = graph;
        this.mst = new ArrayList<>();
    }

    public List<Edge> getTree() {
        if (!solved) {
            findMST();
            solved = true;
        }

        return mst;
    }

    public int getCost() {
        if (!solved) {
            findMST();
            solved = true;
        }

        if (mstCost == null) {
            mstCost = mst.stream().mapToInt(edge -> edge.weight).sum();
        }

        return mstCost;
    }

    private void findMST() {
        PriorityQueue<Edge> unVisitedEdges = new PriorityQueue<>();
        boolean[] visited = new boolean[graph.size()];
        Integer currentNode = 0;
        unVisitedEdges.addAll(graph.get(0));
        visited[currentNode] = true;
        while (unVisitedEdges.size() > 0 && mst.size() < graph.size() - 1) {
            Edge currentEdge = unVisitedEdges.poll();
            currentNode = currentEdge.to;
            if (visited[currentNode]) continue;

            mst.add(currentEdge);
            visited[currentEdge.to] = true;

            for (Edge e : graph.get(currentNode)) {
                if (!visited[e.to]) {
                    unVisitedEdges.add(e);
                }
            }
        }
    }

}
