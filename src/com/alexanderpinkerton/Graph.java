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

    private Map<String,Vertex> vertexList = new HashMap<>( );



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

    public void removeEdge(Vertex start, Vertex end){
        //Loop through the linked list via an iterator and remove the matching edge.
        for(Iterator<Edge> it=start.getOutgoingEdges().iterator(); it.hasNext(); ) {
            if(it.next().getEnd().equals(end)) {
                it.remove();
                if(debug){System.out.println("Edge:\t\t" + start.getName() + " ----> " + end.getName() + "   Removed.");}
                break;
            }
        }
    }

    public void removeEdge(String startName, String endName){
        //Loop through the linked list via an iterator and remove the matching edge.
        Vertex start = vertexList.get(startName);
        Vertex end = vertexList.get(endName);

        for(Iterator<Edge> it=start.getOutgoingEdges().iterator(); it.hasNext(); ) {
            if(it.next().getEnd().equals(end)) {
                it.remove();
                if(debug){System.out.println("Edge:\t\t" + start.getName() + " ----> " + end.getName() + "   Removed.");}
                break;
            }
        }
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
            ((Vertex)pair.getValue()).printEdges();
        }

    }

    public void getReachable(String vertex){
        Set<Vertex> reachable = new HashSet<>();
        Queue<Vertex> toVisit = new LinkedList<>();
        Vertex v = getVertex(vertex);

        while(!v.outgoingEdges.isEmpty() && !v.isVisited()){
            for (int i=0;i<v.outgoingEdges.size();i++){
                v.setVisited(true);
                Edge e = (Edge) v.getOutgoingEdges().get(i);
                toVisit.add(e.end);
                reachable.add(e.end);
                v = toVisit.poll();
            }
        }

        Iterator iterator = reachable.iterator();
        while(iterator.hasNext()){
            Vertex vert = (Vertex)iterator.next();
            System.out.println("  " + vert.name);
        }
    }

    public void printReachable(){
        Iterator it = vertexList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getValue().toString());
            getReachable(pair.getValue().toString());
        }
    }

    public void getShortestPath(String startName, String endName){

        Vertex start = getVertex(startName);
        Vertex end = getVertex(endName);

        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        ArrayList<String> path = new ArrayList<>();

        //For Every vertex in the graph, init the distance from source and previous pointers.
        Iterator it = vertexList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Vertex v = (Vertex) pair.getValue();

            if(pair.getValue().equals(start)){
                v.setDistanceFromSource(0);
            }else{
                v.setDistanceFromSource(INFINITY);
            }
            v.setPrev(null);
            queue.add(v);
        }

        while (!queue.isEmpty()){
            //Retrieve the highest priority from the queue.
            Vertex current = queue.poll();

            //Scan all outgoing edges and update their distances and previous.
            for(int i=0;i<current.outgoingEdges.size();i++){

                Edge e = (Edge)current.getOutgoingEdges().get(i);
                Vertex endVertex = e.getEnd();

                /***
                 * As you discovered, a priority queue does not resort all elements whenever an element is added or removed.
                 ***/

                if(current.distanceFromSource + e.getWeight() < endVertex.distanceFromSource){
                    endVertex.setDistanceFromSource(current.distanceFromSource + e.getWeight());
                    endVertex.setPrev(current);
                    if(debug){System.out.println(endVertex.getName() + "'s previous node is " + current.getName());}
                    if(debug){System.out.println(endVertex.getName() + "'s distance is " + endVertex.getDistanceFromSource());}
                }
            }
        }

        /* Output the path in easy to read format */
        Vertex x = end;
        path.add(end.getName());
        while(x.prev!=null){
            path.add(x.getPrev().getName());
            x = x.getPrev();
        }
        Collections.reverse(path);
        for(String s: path){System.out.print(s + " ");}
        System.out.println(end.distanceFromSource);
    }

    //=======================================================================================================
    private class Vertex<V> implements Comparable<Vertex>{
        private float distanceFromSource;
        private boolean visited = false;
        private String name;
        private Vertex prev;
        private Object data;
        private LinkedList<Edge> outgoingEdges;

        private Vertex(String name){
            outgoingEdges = new LinkedList<>();
            this.name = name;
        }

        private Vertex(String name, V data){
            outgoingEdges = new LinkedList<>();
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

        public float getDistanceFromSource() {
            return distanceFromSource;
        }

        public void setDistanceFromSource(float distanceFromSource) {
            this.distanceFromSource = distanceFromSource;
        }

        public Vertex getPrev() {
            return prev;
        }

        public void setPrev(Vertex prev) {
            this.prev = prev;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public void printEdges(){
             Collections.sort(outgoingEdges, new Comparator<Edge>() {
                    @Override
                    public int compare(Edge o1, Edge o2) {
                        return o1.getEnd().getName().compareTo(o2.getEnd().getName());
                    }
                });
            for(Edge e: outgoingEdges){
                System.out.println("  " + e.getEnd().getName() + " " + e.getWeight());
            }
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

        @Override
        public int compareTo(Vertex v) {
            if(distanceFromSource <= v.getDistanceFromSource()){
                return -1;
            }else{
                return 1;
            }
        }

        @Override
        public String toString() {
            return name;
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
