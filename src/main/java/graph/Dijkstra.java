package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

// TODO use indexed priority queue to efficiently update edge weight in queue instead of inserting new each time
public class Dijkstra {
    private final List<List<Edge>> graph;
    private final int[] distance;
    private final int[] path;
    private final boolean[] visited;

    public Dijkstra(List<List<Edge>> graph) {
        this.graph = graph;
        this.distance = new int[graph.size()];
        Arrays.fill(distance, Integer.MAX_VALUE);
        this.path = new int[graph.size()];
        Arrays.fill(this.path, -1);
        this.visited = new boolean[graph.size()];
    }

    public List<Integer> shortestPath(int start, int end) {
        if (start < 0 || end >= graph.size() || end < start) {
            throw new IllegalArgumentException("Invalid start end vertices");
        }

        computeDistance(start, end);
        List<Integer> shortestPath = new ArrayList<>();
        int currentVertex = end;
        while (currentVertex > -1) {
            if (currentVertex == start) {
                return shortestPath;
            }
            shortestPath.add(0, currentVertex);
            currentVertex = path[currentVertex];
        }

        return Arrays.asList(-1);
    }

    public int[] getDistance() {
        return this.distance;
    }

    public static void main(String[] args) {
        List<List<Edge>> graph = new ArrayList<>();

        graph.add(Arrays.asList(new Edge(1,1), new Edge(2,1)));
        graph.add(Arrays.asList(new Edge(3,1)));
        graph.add(Arrays.asList(new Edge(4,1)));
        graph.add(Arrays.asList(new Edge(4,1)));
        graph.add(Arrays.asList());

        Dijkstra dijkstra = new Dijkstra(graph);
        List<Integer> path  = dijkstra.shortestPath(0,4);
        for (Integer d : path) {
            System.out.println(d);
        }
    }

    private void computeDistance(int start, int end) {
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        Edge startEdge = new Edge(start, 0);
        distance[start] = 0;
        queue.offer(startEdge);

        while (!queue.isEmpty()) {
            Edge current = queue.poll();
            int currentVertex = current.getVertex();
            visited[currentVertex] = true;

            for (Edge e : graph.get(currentVertex)) {
                int linkedVertex = e.getVertex();
                int newDistance = distance[currentVertex] + e.getWeight();

                if (visited[linkedVertex]) continue;

                if (distance[linkedVertex] > newDistance) {
                    distance[linkedVertex] = newDistance;
                    path[linkedVertex] = currentVertex;
                    Edge newEdge = new Edge(linkedVertex, newDistance);
                    queue.offer(newEdge);
                }
            }
        }
    }
}
