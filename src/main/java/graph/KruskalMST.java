package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class KruskalMST {
    private final List<List<Edge>> graph;
    private final List<Edge> mst;
    private Integer mstCost;
    private boolean solved;
    private int[] group;
    private int[] groupSize;

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

        KruskalMST mst = new KruskalMST(graph);
        List<Edge> tree = mst.getTree();
        for (Edge e : tree) {
            System.out.println(e);
        }
        System.out.println(String.format("Total cost %d", mst.getCost()));

    }

    public KruskalMST(List<List<Edge>> graph) {
        this.graph = graph;
        this.mst = new ArrayList<>();
        this.group = IntStream.range(0, graph.size()).toArray();
        this.groupSize  = new int[graph.size()];
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
        PriorityQueue<Edge> edgesQueue = new PriorityQueue();
        setup(edgesQueue);
        int[] group = IntStream.range(0, graph.size()).toArray();
        int[] groupSize = new int[graph.size()];
        Arrays.fill(groupSize, 1);

        while (edgesQueue.size() > 0 && mst.size() < graph.size() - 1) {
            Edge currentEdge = edgesQueue.poll();
            if (!isConnected(currentEdge.from, currentEdge.to)) {
                union(currentEdge.from, currentEdge.to);
                mst.add(currentEdge);
            }
        }
    }

    private boolean isConnected(int nodeA, int nodeB) {
        return findGroup(nodeA) == findGroup(nodeB) ;
    }

    private void union(int nodeA, int nodeB) {
        int groupA = findGroup(nodeA);
        int groupB = findGroup(nodeB);

        if (groupSize[groupA] < groupSize[groupB]) {
            group[groupA] = groupB;
            groupSize[groupB] += groupSize[groupA];
        } else {
            group[groupB] = groupA;
            groupSize[groupA] += groupSize[groupB];
        }
    }

    private int findGroup(int node) {
        int currentGroup = node;
        while(currentGroup != group[currentGroup]) {
            currentGroup = group[currentGroup];
        }

        while (group[node] != currentGroup) {
            int next = group[node];
            group[node] = currentGroup;
            node = next;
        }

        group[node] = currentGroup;

        return currentGroup;
    }


    private void setup(PriorityQueue<Edge> queue) {
        for (int i = 0 ; i < graph.size(); i++) {
            for (Edge e : graph.get(i)) {
                if (e.to >= i) {
                    queue.add(e);
                }
            }
        }
    }
}
