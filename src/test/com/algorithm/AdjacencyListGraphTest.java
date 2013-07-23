package com.algorithm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: ryang
 * Date: 2013-07-22
 * Time: 9:49 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(JUnit4.class)
public class AdjacencyListGraphTest {
    @Test
    public void EmptyAdjacencyListGraph() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        Assert.assertEquals(0, graph.numberOfVertices());
    }

    @Test
    public void AddEdge() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdge(1,3);
        graph.addEdge(1,2);
        Assert.assertEquals(1, graph.numberOfVertices());
        Assert.assertEquals(2, graph.numberOfEdges());
    }

    @Test
    public void OutEdges() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdge(1,3);
        graph.addEdge(1,2);

        List<Integer> outEdges = graph.outEdges(1);
        Integer[] actual = outEdges.toArray(new Integer[] {});
        Integer[] expected = {3,2};

        Assert.assertArrayEquals(actual, expected);
    }

    @Test
    public void InEdges() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdge(1,3);
        graph.addEdge(1,2);
        graph.addEdge(2,3);

        List<Integer> outEdges = graph.inEdges(3);
        Integer[] actual = outEdges.toArray(new Integer[] {});
        Integer[] expected = {1,2};

        Assert.assertArrayEquals(actual, expected);
    }

    @Test
    public void fuse() {
        AdjacencyListGraph old = buildGraph1();

        int minCut = old.numberOfCuts();

        for(int i=0; i< 100; i++) {
            AdjacencyListGraph next = old.fuse();
            if(minCut > next.numberOfCuts()) {
                minCut = next.numberOfCuts();
            }
        }

        Assert.assertEquals(minCut, 2);

    }

    @Test
    public void testMinCutIsOne() {
        AdjacencyListGraph old = buildGraphMinCut1();

        int minCut = old.numberOfCuts();

        for(int i=0; i< 100; i++) {
            AdjacencyListGraph next = old.fuse();
            if(minCut > next.numberOfCuts()) {
                minCut = next.numberOfCuts();
            }
        }

        Assert.assertEquals(minCut, 1);

    }

    @Test public void testGetAllEdges() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdge(1, 3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        Edge[] expected = { new Edge(new Vertex(1), new Vertex(3)),
                new Edge(new Vertex(1), new Vertex(2)),
                new Edge(new Vertex(2), new Vertex(3))};
        Edge[] actual = graph.getAllEdges().toArray(new Edge[] {});
        Assert.assertArrayEquals(expected, actual);
    }

    @Test public void testRandomEdge() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdge(1, 3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        List<Edge> edges = graph.getAllEdges();
        int iteration = 100;

        while(edges.size() > 0 && iteration > 0) {
            Edge actual = graph.randomEdge();
            edges.remove(actual);
            iteration--;
        }

        Assert.assertEquals(0, edges.size());

    }

    AdjacencyListGraph buildGraph1() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdges(1, new Integer[]{2, 3, 4});
        graph.addEdges(2, new Integer[]{1, 3, 4, 5});
        graph.addEdges(3, new Integer[] {1, 2, 4});
        graph.addEdges(4, new Integer[] {1,2,3,6});
        graph.addEdges(5, new Integer[] {2,6,7,8});
        graph.addEdges(6, new Integer[] {4,5,7,8});
        graph.addEdges(7, new Integer[]{5, 6, 8});
        graph.addEdges(8, new Integer[]{5, 6, 7});

        return graph;
    }


    AdjacencyListGraph buildGraphMinCut1() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdges(1, new Integer[]{2, 3, 4});
        graph.addEdges(2, new Integer[]{1, 3, 4, 5});
        graph.addEdges(3, new Integer[]{1, 2, 4});
        graph.addEdges(4, new Integer[] {1,2,3});
        graph.addEdges(5, new Integer[] {2,6,7,8});
        graph.addEdges(6, new Integer[] {5,7,8});
        graph.addEdges(7, new Integer[]{5, 6, 8});
        graph.addEdges(8, new Integer[]{5, 6, 7});

        return graph;
    }

    @Test public void testFuseByEdge() {
        AdjacencyListGraph graph = buildGraph1();
        AdjacencyListGraph next = graph.fuseByEdge(new Vertex(1), new Vertex(2));
        Assert.assertEquals(next.numberOfVertices() + 1, graph.numberOfVertices());
        next = next.fuseByEdge(new Vertex(2), new Vertex(3));
        next = next.fuseByEdge(new Vertex(3), new Vertex(4));

        //fuse right side
        next = next.fuseByEdge(new Vertex(7), new Vertex(8));
        next = next.fuseByEdge(new Vertex(8), new Vertex(6));
        next = next.fuseByEdge(new Vertex(6), new Vertex(5));

        Assert.assertEquals(2, next.numberOfVertices());
        Assert.assertEquals(2, next.numberOfCuts());
    }

}
