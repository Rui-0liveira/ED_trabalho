package ListsDavid;


import java.util.Iterator;

import Exceptions.EmptyCollectionException;
import Exceptions.NoSuchElementException;
import Interfaces.GraphADT;

/**
 * 
 * @author David Francisco (8210088)
 * @author Guilherme Silva (8210190)
 */
public class ArrayGraph<T> implements GraphADT<T>{

    private ArrayList<T> vertices;
    private ArrayList<ArrayList<T>> edges;

    public ArrayGraph() {
        vertices = new ArrayList<T>();
        edges = new ArrayList<ArrayList<T>>();
    }

    @Override
    public void addVertex(T vertex) {
        if(!vertices.contains(vertex)) {
            vertices.add(vertex);
            edges.add(new ArrayList<T>());}
    }

    @Override
    public void removeVertex(T vertex) {
        if(vertices.contains(vertex)) {
            int index = indexOf(vertex);
            vertices.remove(index);
            edges.remove(index);
            for(int i = 0; i < edges.size(); i++) {
                edges.get(i).remove(vertex);
            }
    }
    }

    @Override
    public void addEdge(T vertex1, T vertex2) {
        if(vertices.contains(vertex1) && vertices.contains(vertex2)) {
            edges.get(indexOf(vertex1)).add(vertex2);
            edges.get(indexOf(vertex2)).add(vertex1);
        }
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        if(vertices.contains(vertex1) && vertices.contains(vertex2)) {
            edges.get(indexOf(vertex1)).remove(vertex2);
            edges.get(indexOf(vertex2)).remove(vertex1);
        }

    }

    @Override
    public Iterator<T> iteratorBFS(T startVertex) {

        int startIndex = indexOf(startVertex);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Vertex not in graph");
        }
        return new BFSIterator(startIndex);
    }

    @Override
    public Iterator<T> iteratorDFS(T startVertex) {
        int startIndex = indexOf(startVertex);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Vertex not in graph");
        }
        return new DFSIterator(startIndex);
    }

    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        int startIndex = indexOf(startVertex);
        int targetIndex = indexOf(targetVertex);
        if (startIndex == -1 || targetIndex == -1) {
            throw new IllegalArgumentException("Both vertices must be in the graph");
        }
        ArrayList<Integer> path = shortestPath(startIndex, targetIndex);
        return new PathIterator(path);
    }

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    @Override
    public boolean isConnected() {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        boolean result = true;
        int index = 0;
        while(index < vertices.size() && result) {
            if(edges.get(index).isEmpty()) {
                result = false;
            } else {
                index++;
            }
        }
        return result;
    }

    @Override
    public int size() {
        return vertices.size();
    }

    public int indexOf(T vertex) {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        boolean found = false;
        int index = 0;
        while(index < vertices.size() && !found) {
            if(vertex.equals(vertices.get(index))) {
                found = true;
            } else {
                index++;
            }
        }
        if(!found) {
            throw new EmptyCollectionException("Linked List");
        }
        return index;

    }

    private class BFSIterator implements Iterator<T> {

        private Queue<Integer> queue;
        private boolean[] visited;

        public BFSIterator(int start) {
            queue = new Queue<>();
            visited = new boolean[vertices.size()];
            visited[start] = true;
            queue.enqueue(start);
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            if(!hasNext()) {
                //throw new NoSuchElementException();
            }
            int index = queue.dequeue();
            for(int i = 0; i < edges.get(index).size(); i++) {
                int next = indexOf(edges.get(index).get(i));
                if(!visited[next]) {
                    visited[next] = true;
                    queue.enqueue(next);
                }
            }
            return vertices.get(index);
        }
    }

    private class DFSIterator implements Iterator<T> {

        private Stack<Integer> stack;
        private boolean[] visited;

        public DFSIterator(int start) {
            stack = new Stack<>();
            visited = new boolean[vertices.size()];
            visited[start] = true;
            stack.push(start);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if(!hasNext()) {
                //throw new NoSuchElementException();
            }
            int index = stack.pop();
            for(int i = 0; i < edges.get(index).size(); i++) {
                int next = indexOf(edges.get(index).get(i));
                if(!visited[next]) {
                    visited[next] = true;
                    stack.push(next);
                }
            }
            return vertices.get(index);
        }
    }

    private class PathIterator implements Iterator<T> {

        private ArrayList<Integer> path;
        private int index;

        public PathIterator(ArrayList<Integer> path) {
            this.path = path;
            index = path.size() - 1;
        }

        @Override
        public boolean hasNext() {
            return index >= 0;
        }

        @Override
        public T next() {
            if(!hasNext()) {
                //throw new NoSuchElementException();
            }
            return (T) vertices.get(path.get(index--));
        }
    }

    private ArrayList<Integer> shortestPath(int start, int target){
        ArrayList<Integer> path = new ArrayList<>();
        Queue<Integer> queue = new Queue<>();
        boolean[] visited = new boolean[vertices.size()];
        int[] previous = new int[vertices.size()];
        visited[start] = true;
        queue.enqueue(start);
        while(!queue.isEmpty()) {
            int index = queue.dequeue();
            for(int i = 0; i < edges.get(index).size(); i++) {
                int next = indexOf(edges.get(index).get(i));
                if(!visited[next]) {
                    visited[next] = true;
                    previous[next] = index;
                    queue.enqueue(next);
                }
            }
        }
        int current = target;
        while(current != start) {
            path.add(current);
            current = previous[current];
        }
        path.add(start);
        return path;
    }

    public ArrayList<T> getVertices() {
        return vertices;
    }

    public ArrayList<ArrayList<T>> getEdges() {
        return edges;
    }

}
