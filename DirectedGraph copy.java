package edu.sdsu.cs.datastructures;

import java.io.IOException;
import java.util.*;
import java.util.LinkedList;
import java.util.List;

/*
    Program 3
    Neel Kumar and Alyssa Gonzales

 */


public class DirectedGraph<V> implements IGraph<V>{

    private int currSize = 0;
    private Map<V, LinkedList<V>> graph =  new HashMap<>();
    Map<V,V> connection = new HashMap();

    DirectedGraph(){

    }
    public void add(V vertexName) {
            if (graph.containsKey(vertexName)) {
                throw new NoSuchElementException();
            }
            graph.put(vertexName, new LinkedList<V>());
            currSize++;
    }

    @Override
    public void connect(V start, V destination) {
        if (!contains(start) || !contains(destination))
            throw new NoSuchElementException();
        graph.get(start).add(destination);
    }

    @Override
    public void clear() {
        graph.clear();
        currSize = 0;
    }

    @Override
    public boolean contains(V label){
        if(graph.containsKey(label))
            return true;
        return false;
    }

    @Override
    public void disconnect(V start, V destination) {
        if(!contains(start) || !contains(destination))
            throw new NoSuchElementException();
        graph.get(start).remove(destination);
    }

    @Override
    public boolean isConnected(V start, V destination) {
        return shortestPath(start, destination).size() > 0;
    }

    @Override
    public Iterable<V> neighbors(V vertexName){
        if(!contains(vertexName)){
            throw new NoSuchElementException();
        }
        Iterable<V> friends = graph.get(vertexName);
        return friends;
    }

    @Override
    public void remove(V vertexName){
        if (!contains(vertexName)) {
            throw new NoSuchElementException();
        }

        for(V v : graph.keySet()){
            if(graph.get(v).contains(vertexName)){
                graph.get(v).remove(vertexName);
            }
        }
        graph.remove(vertexName);
        currSize--;
    }

    @Override
    public List<V> shortestPath(V start, V destination) {
        LinkedList<V> finalPath = new LinkedList<>();
        if(!contains(start) || !contains(destination))
            throw new NoSuchElementException();

        Set<V> unvisited = new HashSet<>(graph.keySet());
        Map<V,Integer> map = new Hashtable();
        PriorityQueue<V> Q = new PriorityQueue<>();
        Q.add(start);

        for(V key: graph.keySet()){
            map.put(key, -1);
        }
        map.replace(start,-1,0);
        while (!Q.isEmpty()){
            V current = Q.poll();
            if (!unvisited.contains(destination)){
                Q.remove();
            }
            for(V neighbor : neighbors(current)){
                    if(map.get(neighbor) < map.get(current)){
                        map.replace(neighbor, map.get(neighbor)+1);
                        connection.put(neighbor,current);
                    }
                    if(neighbor.equals(destination)){
                        finalPath = findPath(destination);
                        break;
                    }
                    Q.add(neighbor);
                }
                unvisited.remove(current);
         }
        return finalPath;
    }

    private LinkedList<V> findPath(V parent){
        LinkedList<V> list = new LinkedList<>();
        V next = parent;
        if(connection.get(parent) == null){
            return null;
        }
        list.add(next);
        while(connection.get(next) != null){
            next = connection.get(next);
            list.add(next);
        }
        Collections.reverse(list);
        return list;
    }

    @Override
    public int size() {
        return currSize;
    }

    @Override
    public Iterable<V> vertices() {
        return graph.keySet();
    }

    @Override
    public IGraph<V> connectedGraph(V origin) {
        if (!contains(origin))
            throw new NoSuchElementException();
        IGraph<V> g = new DirectedGraph<>();

        LinkedList<V> queue = new LinkedList<>();
        LinkedList<V> visited = new LinkedList<>();
        visited.add(origin);
        g.add(origin);
        queue.addAll(graph.keySet());
        while(!queue.isEmpty()){
            V edge = queue.poll();
            for(V v: neighbors(edge)){
                if(!visited.contains(v) && isConnected(origin,v)){
                    g.add(v);
                    try{
                        g.connect(origin,v);
                    }catch (IOException e){

                    }
                }
            }
            queue.remove(edge);
        }

        return g;
    }

    @Override
    public String toString(){
        String line = "";
        for(V v : graph.keySet()){
            line = line + ("Vertex: " + v +" | Edges: ");
            for(V edge: graph.get(v)){
                line = line + (edge + "  ");
            }
            line = line + "\n";
        }
        return line;
    }

}


