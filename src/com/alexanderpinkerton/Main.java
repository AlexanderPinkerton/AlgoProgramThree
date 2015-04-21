package com.alexanderpinkerton;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        String input = "";
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

                graph.addEdge(
                        graph.getVertex(lineContents[1]),
                        graph.getVertex(lineContents[0]),
                        Float.parseFloat(lineContents[2]));



            }
        }catch (IOException e){
            e.printStackTrace();
        }




        while(!input.equals("exit")){
            System.out.println("Awaiting Query: ");
            try{
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                input = bufferRead.readLine();
                String[] commandArgs = input.split(" ");
                switch (commandArgs[0]){
                    case "addedge":
                        graph.addEdge(graph.getVertex(commandArgs[1]),graph.getVertex(commandArgs[2]));
                        break;
                    case "deleteedge":
                        graph.removeEdge(commandArgs[2],commandArgs[1]);
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
                        System.out.println("Incorrect Command. Wrong arguments?");
                }

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            System.out.println();
        }




        //graph.removeEdge("Duke","Belk");
        //graph.printGraph();
        //graph.getShortestPath("Belk","Education");
        //graph.printReachable();

    }
}
