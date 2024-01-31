package Interfaces;

/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

public interface NetworkADT<T> extends GraphADT<T> {
    void addEdge(T vertex1, T vertex2, double weight);
    double shortestPathWeight(T startVertex, T targetVertex);
}

