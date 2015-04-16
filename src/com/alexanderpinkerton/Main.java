package com.alexanderpinkerton;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph();
        //graph.setDebug(true);

        try(BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            for(String line; (line = br.readLine()) != null; ) {
                // process the line.
                String[] lineContents = line.split(" ");

                graph.addVertex(lineContents[0]);
                graph.addVertex(lineContents[1]);

                graph.addEdge(
                        graph.getVertex(lineContents[0]),
                        graph.getVertex(lineContents[1]),
                        Float.parseFloat(lineContents[2]));



            }
        }catch (IOException e){

        }


        graph.printGraph();



    }
}
