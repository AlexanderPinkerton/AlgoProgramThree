package com.alexanderpinkerton;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Alexander Pinkerton on 4/15/15.
 */

public class Main {

    public static void main(String[] args) {

        String input = "";
        Graph graph = new Graph();
        //graph.setDebug(true);

        //This will read in all of the lines from the input file and build the graph.
        try(BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            for(String line; (line = br.readLine()) != null; ) {
                // process the line.
                String[] lineContents = line.split(" ");

                graph.addVertex(lineContents[0]);
                graph.addVertex(lineContents[1]);

                graph.addEdge(
                        lineContents[0],
                        lineContents[1],
                        Float.parseFloat(lineContents[2]));

                graph.addEdge(
                        lineContents[1],
                        lineContents[0],
                        Float.parseFloat(lineContents[2]));
            }
        }catch (IOException e){
            e.printStackTrace();
        }



        //This loop is the controller for the console operations. It will delegate to the graph class.
        while(!input.equals("quit")){
            System.out.println("Awaiting Query: ");
            try{
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                input = bufferRead.readLine();
                String[] commandArgs = input.split(" ");
                switch (commandArgs[0]){
                    case "addedge":
                        graph.addEdge(commandArgs[1],commandArgs[2]);
                        break;
                    case "deleteedge":
                        if(graph.getVertex(commandArgs[2]) != null && graph.getVertex(commandArgs[1]) != null){
                            graph.removeEdge(commandArgs[2],commandArgs[1]);
                        }else{
                            System.out.println("Vertex not found");
                        }
                        break;
                    case "path":
                        graph.getShortestPath(commandArgs[1],commandArgs[2]);
                        break;
                    case "reachable":
                        graph.printReachable();
                        break;
                    case "print":
                        graph.printGraph();
                        break;
                    default:
                        if(!input.equals("quit")){
                            System.out.println("Incorrect Command. Wrong arguments?");
                        }
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            System.out.println();
        }
    }
}
