package ClassImplementation;

import Interfaces.GraphADT;

import java.util.Iterator;

/**
 * @author 8210311 Daniela Moreira
 * @author 8210367 Orlando Pires
 */
public class Graph<T> implements GraphADT<T> {
    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices; // number of vertices in the graph
    protected boolean[][] adjMatrix; // adjacency matrix
    protected T[] vertices; // values of vertices
    /**
     * Creates an empty graph.
     */
    public Graph() {
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[])(new Object[DEFAULT_CAPACITY]);
    }
/**
     * Creates a graph with the specified capacity.
     *
     * @param size the number of vertices that the graph can hold
     */
    public Graph(int size) {
        numVertices = 0;
        this.adjMatrix = new boolean[size][size];
        this.vertices = (T[])(new Object[size]);
    }

    public void addEdge (int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }
    public void removeEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = false;
            adjMatrix[index2][index1] = false;
        }
    }
    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    public void addEdge (T vertex1, T vertex2) {
        addEdge (getIndex(vertex1), getIndex(vertex2));
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        removeEdge(getIndex(vertex1), getIndex(vertex2));
    }
    private Iterator<T> iteratorBFS(int startIndex) {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(Integer.valueOf(startIndex));
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x.intValue()]);

            //Find all vertices adjacent to x that have not been visited and
            //queue them up
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalQueue.enqueue(Integer.valueOf(i));
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }
    @Override
    public Iterator<T> iteratorBFS(T startVertex) {
        return iteratorBFS(getIndex(startVertex));
    }
    private Iterator<T> iteratorDFS(int startIndex) {
        Integer x;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(Integer.valueOf(startIndex));
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.pop();
            resultList.addToRear(vertices[x.intValue()]);

            //Find all vertices adjacent to x that have not been visited and
            //push them onto the stack
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalStack.push(Integer.valueOf(i));
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    @Override
    public Iterator<T> iteratorDFS(T startVertex) {
        return iteratorDFS(getIndex(startVertex));
    }
    protected Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) {
        int index = startIndex;
        int[] pathLength = new int[numVertices];
        int[] predecessor = new int[numVertices];
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<Integer> resultList
                = new ArrayUnorderedList<Integer>();

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)
                || (startIndex == targetIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(Integer.valueOf(startIndex));
        visited[startIndex] = true;
        pathLength[startIndex] = 0;
        predecessor[startIndex] = -1;

        while (!traversalQueue.isEmpty() && (index != targetIndex)) {
            index = (traversalQueue.dequeue()).intValue();

            //Update the pathLength for each unvisited vertex adjacent to the
            //vertex at the current index
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[index][i] && !visited[i]) {
                    pathLength[i] = pathLength[index] + 1;
                    predecessor[i] = index;
                    traversalQueue.enqueue(Integer.valueOf(i));
                    visited[i] = true;
                }
            }
        }
        if (index != targetIndex) // no path must have been found
        {
            return resultList.iterator();
        }

        LinkedStack<Integer> stack = new LinkedStack<Integer>();
        index = targetIndex;
        stack.push(Integer.valueOf(index));
        do {
            index = predecessor[index];
            stack.push(Integer.valueOf(index));
        } while (index != startIndex);

        while (!stack.isEmpty()) {
            resultList.addToRear(stack.pop());
        }

        return resultList.iterator();
    }
    public Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return resultList.iterator();
        }

        Iterator<Integer> it = iteratorShortestPathIndices(startIndex,
                targetIndex);
        while (it.hasNext()) {
            resultList.addToRear(vertices[it.next().intValue()]);
        }
        return resultList.iterator();
    }
    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        return iteratorShortestPath(getIndex(startVertex), getIndex(targetVertex));
    }

    @Override
    public boolean isEmpty() {
        return (numVertices == 0);
    }

    @Override
    public boolean isConnected() {
        if (isEmpty()) {
            return false;
        }

        Iterator<T> it = iteratorBFS(0);
        int count = 0;

        while (it.hasNext()) {
            it.next();
            count++;
        }
        return (count == numVertices);
    }

    @Override
    public int size() {
        return numVertices;
    }

    public void addVertex (T vertex) {
        if (numVertices == vertices.length)
            expandCapacity();
        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        numVertices++;
    }

    public void removeVertex(int index) {
        if (indexIsValid(index)) {
            numVertices--;

            for (int i = index; i < numVertices; i++) {
                vertices[i] = vertices[i + 1];
            }

            for (int i = index; i < numVertices; i++) {
                if (numVertices + 1 >= 0) System.arraycopy(adjMatrix[i + 1], 0, adjMatrix[i], 0, numVertices + 1);
            }

            for (int i = index; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    adjMatrix[j][i] = adjMatrix[j][i + 1];
                }
            }
        }
    }
    @Override
    public void removeVertex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertex.equals(vertices[i])) {
                removeVertex(i);
                return;
            }
        }
    }
    private void expandCapacity() {
        T[] larger = (T[])(new Object[vertices.length*2]);
        System.arraycopy(vertices, 0, larger, 0, vertices.length);
        vertices = larger;
        boolean[][] largerMatrix = new boolean[vertices.length][vertices.length];
        for (int i = 0; i < numVertices; i++)
            System.arraycopy(adjMatrix[i], 0, largerMatrix[i], 0, numVertices);
        adjMatrix = largerMatrix;
    }

    public int getIndex(T vertex) {
        for(int i = 0; i < numVertices; i++){
            if(vertices[i].equals(vertex)){
                return i;
            }
        }
        return -1;
    }

    public boolean indexIsValid(int index) {
        return (index >= 0) && (index < numVertices);
    }

    public String toString(){
        if (numVertices == 0) {
            return "Graph is empty";
        }
        String result = "";
        result += "Adjacency Matrix\n";
        result += "----------------\n";
        result += "index\t";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i;
            if (i < 10) {
                result += " ";
            }
        }
        result += "\n\n";
        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j]) {
                    result += "1 ";
                } else {
                    result += "0 ";
                }
            }
            result += "\n";
        }
        result += "\n\nVertex Values";
        result += "\n-------------\n";
        result += "index\tvalue\n\n";
        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";
            result += vertices[i].toString() + "\n";
        }
        result += "\n";
        return result;
    }
    public Graph getMST() {
        int x, y;
        int[] edge = new int[2];
        LinkedStack<int[]> vertexStack = new LinkedStack<int[]>();
        Graph<T> resultGraph = new Graph<T>();

        if (isEmpty() || !isConnected()) {
            return resultGraph;
        }

        resultGraph.adjMatrix = new boolean[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                resultGraph.adjMatrix[i][j] = false;
            }
        }

        resultGraph.vertices = (T[]) (new Object[numVertices]);
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        edge[0] = 0;
        resultGraph.vertices[0] = this.vertices[0];
        resultGraph.numVertices++;
        visited[0] = true;

        //Add all edges that are adjacent to vertex 0 to the stack
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && this.adjMatrix[0][i]) {
                edge[1] = i;
                vertexStack.push(edge.clone());
                visited[i] = true;
            }
        }

        while ((resultGraph.size() < this.size()) && !vertexStack.isEmpty()) {

            //Pop an edge off the stack and add it to the resultGraph
            edge = vertexStack.pop();
            x = edge[0];
            y = edge[1];
            resultGraph.vertices[y] = this.vertices[y];
            resultGraph.numVertices++;
            resultGraph.adjMatrix[x][y] = true;
            resultGraph.adjMatrix[y][x] = true;
            visited[y] = true;

            //Add all unvisited edges that are adjacent to vertex y to the stack
            for (int i = 0; i < numVertices; i++) {
                if (!visited[i] && this.adjMatrix[i][y]) {
                    edge[0] = y;
                    edge[1] = i;
                    vertexStack.push(edge.clone());
                    visited[i] = true;
                }
            }
        }
        return resultGraph;
    }

    public T getVertex(int index){
        return vertices[index];
    }


}
