package ListsDavid;


import Interfaces.NetworkADT;

/**
 * 
 * @author David Francisco (8210088)
 * @author Guilherme Silva (8210190)
 */
public class Network<T> extends ArrayGraph<T> implements NetworkADT<T>{
    private ArrayList<ArrayList<Double>> weights;

    public Network() {
        super();
        weights = new ArrayList<ArrayList<Double>>();
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        if(getVertices().contains(vertex1) && getVertices().contains(vertex2)) {
            getEdges().get(indexOf(vertex1)).add(vertex2);
            getEdges().get(indexOf(vertex2)).add(vertex1);
            weights.get(indexOf(vertex1)).add(weight);
            weights.get(indexOf(vertex2)).add(weight);
        }
        System.out.println("Fodeu");

    }

    @Override
    public void removeVertex(T vertex) {
        if(getVertices().contains(vertex)) {
            int index = indexOf(vertex);
            getVertices().remove(index);
            getEdges().remove(index);
            weights.remove(index);
            for(int i = 0; i < getEdges().size(); i++) {
                for(int j = 0; j < getEdges().get(i).size(); j++) {
                    if(getEdges().get(i).get(j).equals(vertex)) {
                        getEdges().get(i).remove(j);
                        weights.get(i).remove(j);
                    }
                }
            }
        }
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        if(getVertices().contains(vertex1) && getVertices().contains(vertex2)) {
            int index1 = indexOf(vertex1);
            int index2 = indexOf(vertex2);
            for(int i = 0; i < getEdges().get(index1).size(); i++) {
                if(getEdges().get(index1).get(i).equals(vertex2)) {
                    getEdges().get(index1).remove(i);
                    weights.get(index1).remove(i);
                }
            }

            for(int i = 0; i < getEdges().get(index2).size(); i++) {
                if(getEdges().get(index2).get(i).equals(vertex1)) {
                    getEdges().get(index2).remove(i);
                    weights.get(index2).remove(i);
                }
            }
        }
    }

    @Override
    public double shortestPathWeight(T startVertex, T targetVertex) {
        int startIndex = indexOf(startVertex);
        int targetIndex = indexOf(targetVertex);
        if (startIndex == -1 || targetIndex == -1) {
            throw new IllegalArgumentException("Both vertices must be in the graph");
        }
        ArrayList<Integer> path = shortestPath(startIndex, targetIndex);
        double result = 0;
        for(int i = 0; i < path.size(); i++) {
            if(weights.get(path.get(i)).get(path.get(i+1)) != null)
            	result += weights.get(path.get(i)).get(path.get(i+1));
        }
        return result;
    }

    //shortest path between two vertices considering weights
    public ArrayList<Integer> shortestPath(int startIndex, int targetIndex) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        ArrayList<Integer> visited = new ArrayList<Integer>();
        ArrayList<Integer> path = new ArrayList<Integer>();
        ArrayList<Double> distance = new ArrayList<Double>();
        for(int i = 0; i < getVertices().size(); i++) {
            distance.add(Double.MAX_VALUE);
        }
        distance.set(startIndex, 0.0);
        path.add(startIndex);
        while(!path.isEmpty()) {
            int current = path.remove(0);
            visited.add(current);
            for(int i = 0; i < getEdges().get(current).size(); i++) {
                int neighbor = indexOf(getEdges().get(current).get(i));
                if(!visited.contains(neighbor)) {
                    if(distance.get(neighbor) > distance.get(current) + weights.get(current).get(i)) {
                        distance.set(neighbor, distance.get(current) + weights.get(current).get(i));
                        path.add(neighbor);
                    }
                }
            }
        }
        int current = targetIndex;
        while(current != startIndex) {
            result.add(current);
            double min = Double.MAX_VALUE;
            int minIndex = -1;
            for(int i = 0; i < getEdges().get(current).size(); i++) {
                int neighbor = indexOf(getEdges().get(current).get(i));
                if(distance.get(neighbor) < min) {
                    min = distance.get(neighbor);
                    minIndex = neighbor;
                }
            }
            current = minIndex;
        }
        result.add(startIndex);
        result = reverseOrder(result);
        return result;
    }

    public ArrayList<Integer> reverseOrder(ArrayList<Integer> list) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int i = list.size() - 1; i >= 0; i--) {
            result.add(list.get(i));
        }
        return result;
    }

    public boolean hasEdge(T vertex1, T vertex2) {
        if(getVertices().contains(vertex1) && getVertices().contains(vertex2)) {
            int index1 = indexOf(vertex1);
            for(int i = 0; i < getEdges().get(index1).size(); i++) {
                if(getEdges().get(index1).get(i).equals(vertex2)) {
                    return true;
                }
            }
        }
        return false;
    }

    //returns a list with the vertices that are connected to the given vertex
    public ArrayList<T> getNeighbors(T vertex) {
        ArrayList<T> result = new ArrayList<T>();
        if(getVertices().contains(vertex)) {
            int index = indexOf(vertex);
            for(int i = 0; i < getEdges().get(index).size(); i++) {
                result.add(getEdges().get(index).get(i));
            }
        }
        return result;
    }

    public T getVertex(int index) {
        return getVertices().get(index);
    }

    public ArrayList<ArrayList<Double>> getWeights() {
        return weights;
    }

    @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Network: {\n");

    for (int i = 0; i < getVertices().size(); i++) {
        T vertex = getVertex(i);
        sb.append("  ").append(vertex).append(": ");

        ArrayList<T> neighbors = getNeighbors(vertex);
        for (int j = 0; j < neighbors.size(); j++) {
            T neighbor = neighbors.get(j);
            double weight = weights.get(i).get(j);

            sb.append(neighbor).append("(").append(weight).append(") ");
        }
        sb.append("\n");
    }

    sb.append("}");
    return sb.toString();
}
}
