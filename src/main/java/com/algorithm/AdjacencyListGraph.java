package com.algorithm;

import java.util.*;

public class AdjacencyListGraph {
    private Map<Integer, ArrayList<Integer>> adj;
    private Random generator = new Random();

    public AdjacencyListGraph() {
        adj = new HashMap<Integer, ArrayList<Integer>>();
    }

    public int numberOfVertices() {
        return adj.size();
    }

    public void addEdge(int i, int j) {
        ArrayList<Integer> edges = adj.get(i);
        if(null == edges) {
            edges = new ArrayList<Integer>();
        }
        edges.add(j);
        adj.put(i, edges);
    }

    public void addEdges(Integer i, Integer[] edges) {
        for(int edge : edges) {
            addEdge(i, edge);
        }
    }

    public int numberOfEdges()
    {
        int total = 0;
        Iterator<Integer> it = adj.keySet().iterator();

        while(it.hasNext()) {
            ArrayList<Integer> edges = adj.get(it.next());
            total += edges.size();
        }
        return total;
    }

    public int numberOfCuts()
    {
        return this.numberOfEdges() /  2;
    }

    public List<Integer> outEdges(int i) {
        return adj.get(i);
    }

    public List<Integer> inEdges(int j) {
        List<Integer> edges = new ArrayList<Integer>();
        Iterator<Integer> it = adj.keySet().iterator();
        while(it.hasNext()) {
            Integer key = it.next();
            List<Integer> inEdges = adj.get(key);
            if(inEdges.contains(j)) {
               edges.add(key);
            }
        }
        return edges;
    }

    AdjacencyListGraph fuseByEdge(Vertex v1, Vertex v2) {
        AdjacencyListGraph fusedGraph = new AdjacencyListGraph();

        //copy all the vertices and edges to new fused graph
        Iterator<Integer> it = this.adj.keySet().iterator();
        while (it.hasNext()) {
            Integer key = it.next();
            Iterator<Integer> itEdges = this.adj.get(key).iterator();

            while(itEdges.hasNext()) {
                Integer edge = itEdges.next();

                if(key == v1.getValue()) {
                    if(edge != v2.getValue()) {
                       fusedGraph.addEdge(v2.getValue(), edge);
                    }
                } else if (key == v2.getValue()) {
                    if(edge != v1.getValue()) {
                        fusedGraph.addEdge(key, edge);
                    }
                } else {
                    if(edge == v1.getValue()) {
                        fusedGraph.addEdge(key, v2.getValue());
                    } else {
                        fusedGraph.addEdge(key, edge);
                    }
                }
            }
        }

        return fusedGraph;
    }

    public AdjacencyListGraph fuse() {
        AdjacencyListGraph fused = this;
        while(fused.numberOfVertices() > 2) {
            Edge edge = fused.randomEdge();
            fused = fused.fuseByEdge(edge.getV1(),edge.getV2());
        }
        return fused;
    }

    public List<Edge> getAllEdges() {
        List<Edge> edges = new ArrayList<Edge>();
        Iterator<Integer> it = adj.keySet().iterator();
        while(it.hasNext()) {
            Integer key = it.next();
            List<Integer> inEdges = outEdges(key);
            Iterator<Integer> edgeIt = inEdges.iterator();
            while(edgeIt.hasNext()) {
                edges.add(new Edge(new Vertex(key), new Vertex(edgeIt.next())));
            }
        }
        return edges;
    }

    public Edge randomEdge() {
        Edge[] edges = getAllEdges().toArray( new Edge[] {});
        int index = generator.nextInt(edges.length);
        return edges[index];
    }
}
