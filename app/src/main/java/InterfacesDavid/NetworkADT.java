package InterfacesDavid;
public interface NetworkADT<T> extends GraphADT<T> {
	public void addEdge(T vertex1, T vertex2, double weight);
	public double shortestPathWeight(T startVertex, T targetVertex);
}
