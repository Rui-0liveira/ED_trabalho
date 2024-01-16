package ClassImplementation;
/**
 * @author 8210311 Daniela Moreira
 * @author 8210367 Orlando Pires
 */
import Interfaces.NetworkADT;

import java.util.Iterator;

public class Network<T> extends Graph<T> implements NetworkADT<T> {
    private double[][] adjMatrix;

    public double[][] getAdjMatrix(){
        return this.adjMatrix;
    }

    public Network() {
        numVertices = 0;
        this.adjMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }


    public String toString() {
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
                if (adjMatrix[i][j] < Double.POSITIVE_INFINITY) {
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

        result += "\n\nWeights of Edges";
        result += "\n----------------\n";
        result += "index\tweight\n\n";

        for (int i = 0; i < numVertices; i++) {
            for (int j = numVertices - 1; j >=0; j--) {
                if (adjMatrix[i][j] < Double.POSITIVE_INFINITY) {
                    result += i + " to " + j + "\t";
                    result += adjMatrix[i][j] + "\n";
                }
            }
        }
        result += "\n";
        return result;
    }

    public void addEdge(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
            adjMatrix[index2][index1] = weight;
            System.out.println("Edge added between " + index1 + " and " + index2 + " with weight " + weight);
        }else{
            System.out.println("Edge not added");
        }
    }

    public void addEdgeDiretional(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
            System.out.println("Edge added between " + index1 + " and " + index2 + " with weight " + weight);
        }else{
            System.out.println("Edge not added");
        }
    }

    public void removeEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = Double.POSITIVE_INFINITY;
            adjMatrix[index2][index1] = Double.POSITIVE_INFINITY;
        }
    }

    public void addEdge(T vertex1, T vertex2, double weight) {
        addEdge(getIndex(vertex1), getIndex(vertex2), weight);
    }

    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2), 0);
    }

    public void removeEdge(T vertex1, T vertex2) {
        removeEdge(getIndex(vertex1), getIndex(vertex2));
    }

    public void addVertex() {
        if (numVertices == vertices.length) {
            expandCapacity();
        }
        vertices[numVertices] = null;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = Double.POSITIVE_INFINITY;
            adjMatrix[i][numVertices] = Double.POSITIVE_INFINITY;
        }
        numVertices++;
    }

    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }
        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = Double.POSITIVE_INFINITY;
            adjMatrix[i][numVertices] = Double.POSITIVE_INFINITY;
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

    public void removeVertex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertex.equals(vertices[i])) {
                removeVertex(i);
                return;
            }
        }
    }

    public Iterator<T> iteratorDFS(int startIndex) {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        boolean[] visited = new boolean[numVertices];

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(Integer.valueOf(startIndex));
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;

            //Find a vertex adjacent to x that has not been visited and push it
            //on the stack
            for (int i = 0; (i < numVertices) && !found; i++) {
                if ((adjMatrix[x.intValue()][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                    traversalStack.push(Integer.valueOf(i));
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }
        return resultList.iterator();
    }

    public Iterator<T> iteratorDFS(T startVertex) {
        return iteratorDFS(getIndex(startVertex));
    }

    public Iterator<T> iteratorBFS(int startIndex) {
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
                if ((adjMatrix[x.intValue()][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                    traversalQueue.enqueue(Integer.valueOf(i));
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    public Iterator<T> iteratorBFS(T startVertex) {
        return iteratorBFS(getIndex(startVertex));
    }

    protected Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) {
        int index;
        double weight;
        int[] predecessor = new int[numVertices];
        LinkedHeap<Double> traversalMinHeap = new LinkedHeap<Double>();
        ArrayUnorderedList<Integer> resultList = new ArrayUnorderedList<Integer>();
        LinkedStack<Integer> stack = new LinkedStack<Integer>();

        int[] pathIndex = new int[numVertices];
        double[] pathWeight = new double[numVertices];
        for (int i = 0; i < numVertices; i++) {
            pathWeight[i] = Double.POSITIVE_INFINITY;
        }
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex) || (startIndex == targetIndex) || isEmpty()) {
            return resultList.iterator();
        }

        pathWeight[startIndex] = 0;
        predecessor[startIndex] = -1;
        visited[startIndex] = true;
        weight = 0;
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                pathWeight[i] = pathWeight[startIndex]
                        + adjMatrix[startIndex][i];
                predecessor[i] = startIndex;
                traversalMinHeap.addElement(new Double(pathWeight[i]));
            }
        }
        do {
            weight = (traversalMinHeap.removeMin()).doubleValue();
            traversalMinHeap.removeAllElements();
            if (weight == Double.POSITIVE_INFINITY) {
                return resultList.iterator();
            } else {
                index = getIndexOfAdjVertexWithWeightOf(visited, pathWeight, weight);
                visited[index] = true;
            }
            for (int i = 0; i < numVertices; i++) {
                if (!visited[i]) {
                    if ((adjMatrix[index][i] < Double.POSITIVE_INFINITY)
                            && (pathWeight[index] + adjMatrix[index][i]) < pathWeight[i]) {
                        pathWeight[i] = pathWeight[index] + adjMatrix[index][i];
                        predecessor[i] = index;
                    }
                    traversalMinHeap.addElement(new Double(pathWeight[i]));
                }
            }
        } while (!traversalMinHeap.isEmpty() && !visited[targetIndex]);

        index = targetIndex;
        stack.push(Integer.valueOf(index));
        do {
            index = predecessor[index];
            stack.push(Integer.valueOf(index));
        } while (index != startIndex);

        while (!stack.isEmpty()) {
            resultList.addToRear((stack.pop()));
        }
        return resultList.iterator();
    }

    protected int getIndexOfAdjVertexWithWeightOf(boolean[] visited, double[] pathWeight, double weight) {
        for (int i = 0; i < numVertices; i++) {
            if ((pathWeight[i] == weight) && !visited[i]) {
                for (int j = 0; j < numVertices; j++) {
                    if ((adjMatrix[i][j] < Double.POSITIVE_INFINITY) && visited[j]) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) {
        ArrayUnorderedList templist = new ArrayUnorderedList();
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return templist.iterator();
        }

        Iterator<Integer> it = iteratorShortestPathIndices(startIndex,
                targetIndex);
        while (it.hasNext()) {
            templist.addToRear(vertices[(it.next()).intValue()]);
        }
        return templist.iterator();
    }

    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        return iteratorShortestPath(getIndex(startVertex), getIndex(targetVertex));
    }
    public double shortestPathWeight(int startIndex, int targetIndex) {
        double result = 0;
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return Double.POSITIVE_INFINITY;
        }

        int index1, index2;
        Iterator<Integer> it = iteratorShortestPathIndices(startIndex, targetIndex);

        if (it.hasNext()) {
            index1 = it.next().intValue();
        } else {
            return Double.POSITIVE_INFINITY;
        }
        while (it.hasNext()) {
            index2 = (it.next()).intValue();
            result += adjMatrix[index1][index2];
            index1 = index2;
        }
        return result;
    }

    public double shortestPathWeight(T startVertex, T targetVertex) {
        return shortestPathWeight(getIndex(startVertex), getIndex(targetVertex));
    }

    public Network mstNetwork() {
        int x, y;
        int index;
        double weight;
        int[] edge = new int[2];
        LinkedHeap<Double> minHeap = new LinkedHeap<Double>();
        Network<T> resultGraph = new Network<T>();

        if (isEmpty() || !isConnected()) {
            return resultGraph;
        }
        resultGraph.adjMatrix = new double[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; i++) {
                resultGraph.adjMatrix[i][j] = Double.POSITIVE_INFINITY;
            }
            resultGraph.vertices = (T[]) (new Object[numVertices]);
        }
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }
        edge[0] = 0;
        resultGraph.vertices[0] = this.vertices[0];
        resultGraph.numVertices++;
        visited[0] = true;

        /**
         * Add all edges, which are adjacent to the starting vertex, to the heap
         */
        for (int i = 0; i < numVertices; i++) {
            minHeap.addElement(new Double(adjMatrix[0][i]));
        }

        while ((resultGraph.size() < this.size()) && !minHeap.isEmpty()) {
            /**
             * Get the edge with the smallest weight that has exactly one vertex
             * already in the resultGraph
             */
            do {
                weight = (minHeap.removeMin()).doubleValue();
                edge = getEdgeWithWeightOf(weight, visited);
            } while (!indexIsValid(edge[0]) || !indexIsValid(edge[1]));

            x = edge[0];
            y = edge[1];
            if (!visited[x]) {
                index = x;
            } else {
                index = y;
            }

            /**
             * Add the new edge and vertex to the resultGraph
             */
            resultGraph.vertices[index] = this.vertices[index];
            visited[index] = true;
            resultGraph.numVertices++;

            resultGraph.adjMatrix[x][y] = this.adjMatrix[x][y];
            resultGraph.adjMatrix[y][x] = this.adjMatrix[y][x];

            /**
             * Add all edges, that are adjacent to the newly added vertex, to
             * the heap
             */
            for (int i = 0; i < numVertices; i++) {
                if (!visited[i] && (this.adjMatrix[i][index]
                        < Double.POSITIVE_INFINITY)) {
                    edge[0] = index;
                    edge[1] = i;
                    minHeap.addElement(new Double(adjMatrix[index][i]));
                }
            }
        }
        return resultGraph;
    }

    protected int[] getEdgeWithWeightOf(double weight, boolean[] visited) {
        int[] edge = new int[2];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if ((adjMatrix[i][j] == weight) && (visited[i] ^ visited[j])) {
                    edge[0] = i;
                    edge[1] = j;
                    return edge;
                }
            }
        }
        edge[0] = -1;
        edge[1] = -1;
        return edge;
    }
    
    protected void expandCapacity() {
        T[] largerVertices = (T[]) (new Object[vertices.length * 2]);
        double[][] largerAdjMatrix
                = new double[vertices.length * 2][vertices.length * 2];

        for (int i = 0; i < numVertices; i++) {
            System.arraycopy(adjMatrix[i], 0, largerAdjMatrix[i], 0, numVertices);
            largerVertices[i] = vertices[i];
        }

        vertices = largerVertices;
        adjMatrix = largerAdjMatrix;
    }

    public boolean hasEdge(int startIndex, int targetIndex){
        return adjMatrix[startIndex][targetIndex] < Double.POSITIVE_INFINITY;
    }
}
