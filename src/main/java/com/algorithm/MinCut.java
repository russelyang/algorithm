package com.algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryang
 * Date: 2013-07-23
 * Time: 6:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class MinCut {
    public static void main(String[] args) throws IOException{
        if(args.length < 1) {
            System.out.println("please provide graph file");
            System.exit(-1);
        }

        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        AdjacencyListGraph graph = new AdjacencyListGraph();

        try {
            String line = reader.readLine();
            while (null != line) {
                String[] splits = line.split("\t");
                Integer vertex = Integer.parseInt(splits[0]);
                List<Integer> edges = new ArrayList<Integer>();
                for(int i=1; i< splits.length; i++) {
                    edges.add(Integer.parseInt(splits[i]));
                }
                graph.addEdges(vertex, edges.toArray(new Integer[]{}));
                line = reader.readLine();
            }
        }

        finally {
            reader.close();
        }

        int minCut = graph.numberOfCuts();
        for(int i=0; i< 500; i++) {
            AdjacencyListGraph next = graph.fuse();
            if(minCut > next.numberOfCuts()) {
                minCut = next.numberOfCuts();
            }
        }

        System.out.println("Min cut is " + minCut + ".");

    }
}
