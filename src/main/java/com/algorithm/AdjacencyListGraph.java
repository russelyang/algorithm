package com.algorithm;

import java.util.*;

public class AdjacencyListGraph {
    private List<Edge> _edges;
    private Map<Vertex, List<Edge>> _vetices;
    private Random generator = new Random();

    public AdjacencyListGraph() {
        _edges = new ArrayList<Edge>();
        _vetices = new HashMap<Vertex, List<Edge>>();
    }

    public int numberOfVertices() {
        return _vetices.size();
    }

    private void addVertex(Vertex v) {
        if(!_vetices.containsKey(v)) {
            _vetices.put(v, new ArrayList<Edge>());
        }
    }

    public void addEdge(int i, int j) {
        Vertex tail = new Vertex(i);
        Vertex head = new Vertex(j);

        addEdge(tail,head);
    }

    public void addEdge(Edge e) {
        addEdge(e.getV1(), e.getV2());
    }

    public void addEdge(Vertex tail, Vertex head) {
        addVertex(tail);
        addVertex(head);

        Edge e = new Edge(tail, head);
        _vetices.get(tail).add(e);
        _edges.add(e);
    }

    public void addEdges(Integer i, Integer[] heads) {
        for(int head : heads) {
            addEdge(i, head);
        }
    }

    public int numberOfEdges()
    {
        return _edges.size();
    }

    /*
    the edges when vertices equeals 2.
     */
    public int numberOfCuts()
    {
        Iterator<Vertex> itVertices = _vetices.keySet().iterator();

        int first = _vetices.get(itVertices.next()).size();
        int second = _vetices.get(itVertices.next()).size();

        return first > second ? first : second;
    }

    AdjacencyListGraph contract(Vertex v1, Vertex v2) {
        AdjacencyListGraph fusedGraph = new AdjacencyListGraph();

        for(Edge e : _edges) {
            if(e.getV1().equals(v1)) {
               if(!e.getV2().equals(v2)) {
                fusedGraph.addEdge(v2, e.getV2());
               }
            } else {
                if(e.getV1().equals(v2)) {
                    if(!e.getV2().equals(v1)) {
                        fusedGraph.addEdge(v2, e.getV2());
                    }
                } else {
                    if(e.getV2().equals(v1)) {
                        fusedGraph.addEdge(e.getV1().getValue(), v2.getValue());
                    } else {
                        fusedGraph.addEdge(e);
                    }
                }
            }
        }

        //copy over the vertices


        return fusedGraph;
    }

    public AdjacencyListGraph fuse() {
        AdjacencyListGraph fused = this;
        while(fused.numberOfVertices() > 2) {
            Edge edge = fused.randomEdge();
            fused = fused.contract(edge.getV1(), edge.getV2());
        }
        return fused;
    }

    public Edge randomEdge() {
        int index = generator.nextInt(_edges.size());
        return _edges.get(index);
    }
}
