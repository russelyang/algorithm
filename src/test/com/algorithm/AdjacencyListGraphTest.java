package com.algorithm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: ryang
 * Date: 2013-07-22
 * Time: 9:49 PM
 */

@RunWith(JUnit4.class)
public class AdjacencyListGraphTest {
    @Test
    public void testEmptyAdjacencyListGraph() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        Assert.assertEquals(0, graph.numberOfVertices());
    }

    @Test
    public void testAddEdge() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdge(1,3);
        graph.addEdge(1,2);
        Assert.assertEquals(3, graph.numberOfVertices());
        Assert.assertEquals(2, graph.numberOfEdges());
    }

    @Test
    public void testAddEdges() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdges(1, new Integer[] {2, 3});
        Assert.assertEquals(3, graph.numberOfVertices());
        Assert.assertEquals(2, graph.numberOfEdges());
    }


    @Test
    public void testNumberOfCut1()
    {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdge(1, 2);
        graph.addEdge(2, 1);
        Assert.assertEquals(1, graph.numberOfCuts());
    }

    @Test
    public void testNumberOfCut2()
    {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdges(1, new Integer[] { 2, 2});
        graph.addEdge(2, 1);
        Assert.assertEquals(2, graph.numberOfCuts());
    }

    @Test
    public void testNumberOfCut3()
    {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdges(1, new Integer[] { 2, 2});
        graph.addEdges(2, new Integer[] {1, 1});
        Assert.assertEquals(2, graph.numberOfCuts());
    }

    /*

    3--4-----5--6
    |\/|     |\/|
    |/\|     |/\|
    2--1-----7--8

   */
    AdjacencyListGraph buildGraph1() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdges(1, new Integer[]{2, 3, 4, 7});
        graph.addEdges(2, new Integer[]{1, 3, 4});
        graph.addEdges(3, new Integer[] {1, 2, 4});
        graph.addEdges(4, new Integer[] {1, 2, 3 ,5});
        graph.addEdges(5, new Integer[] {4,6,7,8});
        graph.addEdges(6, new Integer[] {5,7,8});
        graph.addEdges(7, new Integer[]{1, 5, 6, 8});
        graph.addEdges(8, new Integer[]{5, 6, 7});

        return graph;
    }

    @Test public void testFuseByEdge() {
        AdjacencyListGraph graph = buildGraph1();
        AdjacencyListGraph next = graph.contract(new Vertex(1), new Vertex(2));
        Assert.assertEquals(next.numberOfVertices() + 1, graph.numberOfVertices());
        next = next.contract(new Vertex(2), new Vertex(3));
        next = next.contract(new Vertex(3), new Vertex(4));

        //fuse right side
        next = next.contract(new Vertex(7), new Vertex(8));
        next = next.contract(new Vertex(8), new Vertex(6));
        next = next.contract(new Vertex(6), new Vertex(5));

        Assert.assertEquals(2, next.numberOfVertices());
        Assert.assertEquals(2, next.numberOfCuts());
    }


    @Test
    public void testMinCutSample1() {
        String input = "0 1 2 3 4 5 6 7 8 9\n" +
        "1 2 3 4 5 6 7 8 9\n" +
        "2 3 4 5 6 7 8 9\n" +
        "3 4 5 6 7 8 9\n" +
        "4 5 6 7 8 9\n" +
        "5 6 7 8 9\n" +
        "6 7 8 9\n" +
        "7 8 9\n" +
        "8 9\n" +
        "9 10\n" +
        "10 11 12 13 14 15 16 17 18 19\n" +
        "11 12 13 14 15 16 17 18 19\n" +
        "12 13 14 15 16 17 18 19\n" +
        "13 14 15 16 17 18 19\n" +
        "14 15 16 17 18 19\n" +
        "15 16 17 18 19\n" +
        "16 17 18 19\n" +
        "17 18 19\n" +
        "18 19\n";

        AdjacencyListGraph g = new AdjacencyListGraph();

        String[] splits = input.split("\n");
        for(String line : splits) {
            String[] graph = line.split(" ");
            Integer vertex = Integer.parseInt(graph[0]);
            List<Integer> edges = new ArrayList<Integer>();
            for( int i=1; i<graph.length; i++) {
                edges.add(Integer.parseInt(graph[i]));
            }
            g.addEdges(vertex, edges.toArray(new Integer[] {}));
        }

        int minCut = Integer.MAX_VALUE;

        for(int i=0; i< 500; i++) {
            AdjacencyListGraph next = g.fuse();
            if(minCut > next.numberOfCuts()  && next.numberOfCuts() != -1 ) {
                minCut = next.numberOfCuts();
            }
        }

        Assert.assertEquals(1, minCut);
    }

}
