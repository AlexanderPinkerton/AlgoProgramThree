package com.alexanderpinkerton;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Graph graph = new Graph();

        try(BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            for(String line; (line = br.readLine()) != null; ) {
                // process the line.
                String[] lineContents = line.split(" ");

                //TODO Graph.getVertex

                graph.addVertex(lineContents[0]);
                graph.addVertex(lineContents[1]);

                graph.addEdge(
                        graph.getVertex(lineContents[0]),
                        graph.getVertex(lineContents[1]),
                        Float.parseFloat(lineContents[2]));



            }
        }catch (IOException e){

        }


        Iterator it = graph.getVertexList().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }



    }
}
