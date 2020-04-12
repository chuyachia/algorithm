package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EularianPath {
    private final List<List<Integer>> graph;
    private final int[] outDegree;
    private final int[] inDegree;
    private final List<Integer> path;
    private int pathStart;
    private int pathEnd;

    public EularianPath(List<List<Integer>> graph) {
        this.graph = graph;
        this.outDegree = new int[graph.size()];
        this.inDegree = new int[graph.size()];
        this.path = new LinkedList<>();
    }

    public static void main(String[] args) {
        List<List<Integer>> graph = new ArrayList<>();
        graph.add(Arrays.asList());
        graph.add(Arrays.asList(2,3));
        graph.add(Arrays.asList(2,4,4));
        graph.add(Arrays.asList(1,2,5));
        graph.add(Arrays.asList(3,6));
        graph.add(Arrays.asList(6));
        graph.add(Arrays.asList(3));

        EularianPath ep = new EularianPath(graph);
        List<Integer> path = ep.findPath();
        for (Integer i : path) {
            System.out.print(i);
        }
    }

    public List<Integer> findPath() {
        setup();
        boolean hasPath = hasEularianPath();
        if (!hasPath) {
            return Arrays.asList(-1);
        }

        int start = pathStart == -1 ? 0 : pathStart;
        dfs(start);

        return path;
    }

    private void dfs(int node) {
        List<Integer> outEdges = graph.get(node);

        while (outDegree[node] > 0) {
            outDegree[node] -= 1;
            Integer nextNode = outEdges.get(outDegree[node]);
            dfs(nextNode);
        }
        path.add(0, node);
    }

    private void setup() {
        for (int i = 0; i < graph.size(); i++) {
            List<Integer> outEdges = graph.get(i);
            outDegree[i] = outEdges.size();
            for (Integer to : outEdges) {
                inDegree[to] += 1;
            }
        }
    }

    private boolean hasEularianPath() {
        int extraOutDegreeNode = -1;
        int extraInDegreeNode = -1;
        for (int i = 0; i < graph.size(); i++) {
            int degreeDiff = outDegree[i] - inDegree[i];
            if (degreeDiff > 1 || degreeDiff < -1) {
                return false;
            }
            if (degreeDiff == 1) {
                if (extraOutDegreeNode == -1) {
                    extraOutDegreeNode = i;
                } else {
                    return false;
                }
            }
            if (degreeDiff == -1) {
                if (extraInDegreeNode == -1) {
                    extraInDegreeNode = i;
                } else {
                    return false;
                }
            }
        }

        setPathStartEnd(extraOutDegreeNode, extraInDegreeNode);

        return true;
    }

    private void setPathStartEnd(int start, int end) {
        pathStart = start;
        pathEnd = end;
    }
}
