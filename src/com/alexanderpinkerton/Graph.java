package com.alexanderpinkerton;

import java.util.*;

/**
 * Created by Ace on 4/15/15.
 */



public class Graph<T> {

    private boolean debug = false;

    private int edgeCount=0;
    private int vertexCount=0;


    private static final int INFINITY = Integer.MAX_VALUE;

    private Map<String,Vertex> vertexList = new HashMap<String,Vertex>( );




    public Vertex addVertex(String name){
        if(!vertexList.containsKey(name)){
            Vertex v = new Vertex(name);
            vertexList.put(name,v);
            vertexCount++;
            return v;
        }
        if(debug){System.out.println("Vertex " + name + " already exists.");}
        return vertexList.get(name);
    }

    public Vertex addVertex(String name, T data){
        if(!vertexList.containsKey(name)){
            Vertex v = new Vertex(name, data);
            vertexList.put(name,v);
            vertexCount++;
            return v;
        }
        if(debug){System.out.println("Vertex " + name + " already exists.");}
        return vertexList.get(name);
    }



    public void addEdge(Vertex v1, Vertex v2){
        v1.outgoingEdges.add(new Edge(v1,v2));
        edgeCount++;
    }

    public void addEdge(Vertex v1, Vertex v2, float weight){
        v1.outgoingEdges.add(new Edge(v1,v2,weight));
        edgeCount++;
    }


    public Vertex getVertex(String name){
        return vertexList.get(name);
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public Map<String, Vertex> getVertexList() {
        return vertexList;
    }

    public void setVertexList(Map<String, Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }


    public void printGraph(){
        Iterator it = vertexList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey());
            it.remove(); // avoids a ConcurrentModificationException
            ((Vertex)pair.getValue()).printEdges();
            System.out.println("-------------------------------------");
        }

    }

    //=======================================================================================================


    private class Vertex<V> {
        private String name;
        private Object data;
        private List<Edge> outgoingEdges;

        private Vertex(String name){
            outgoingEdges = new LinkedList<Edge>();
            this.name = name;
        }

        private Vertex(String name, V data){
            outgoingEdges = new LinkedList<Edge>();
            this.data = data;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public List<Edge> getOutgoingEdges() {
            return outgoingEdges;
        }

        public void setOutgoingEdges(List<Edge> outgoingEdges) {
            this.outgoingEdges = outgoingEdges;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex<?> vertex = (Vertex<?>) o;

            if (name != null ? !name.equals(vertex.name) : vertex.name != null) return false;
            if (data != null ? !data.equals(vertex.data) : vertex.data != null) return false;
            if (outgoingEdges != null ? !outgoingEdges.equals(vertex.outgoingEdges) : vertex.outgoingEdges != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (data != null ? data.hashCode() : 0);
            result = 31 * result + (outgoingEdges != null ? outgoingEdges.hashCode() : 0);
            return result;
        }


        public void printEdges(){
            for(Edge e: outgoingEdges){
                System.out.println("Edge: " + name + " --> " + e.getEnd().getName() + "  Weight: " + e.getWeight());
            }
        }


    }

    //=======================================================================================================


    private class Edge {

        private Vertex start;
        private Vertex end;
        private float weight;

        private Edge(Vertex start, Vertex end){
            this.start = start;
            this.end = end;
        }

        private Edge(Vertex start, Vertex end, float weight){
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        public Vertex getStart() {
            return start;
        }

        public void setStart(Vertex start) {
            this.start = start;
        }

        public Vertex getEnd() {
            return end;
        }

        public void setEnd(Vertex end) {
            this.end = end;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

    }

    //=======================================================================================================



}
