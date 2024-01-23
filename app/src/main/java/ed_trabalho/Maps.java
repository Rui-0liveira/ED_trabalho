package ed_trabalho;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import ClassImplementation.Network;

/**
 * A classe Maps representa um mapa do jogo.
 * Cada mapa tem uma rede de localizações e uma matriz de adjacência.
 */
public class Maps {
    private Network<Locations> network;

    /**
     * Construtor que cria um novo mapa.
     */
    public Maps() {
        this.network = new Network<Locations>();
    }

    /**
     * Adiciona um local ao mapa.
     *
     * @param local O local a ser adicionado.
     */
    public void addLocal(Locations local) {
        network.addVertex(local);
        System.out.println("Vertex added: " + local.getIndex());
    }

    /**
     * Remove um local do mapa.
     *
     * @param local O local a ser removido.
     */
    public void removeLocal(Locations local) {
        network.removeVertex(local);
        System.out.println("Vertex removed: " + local.getIndex());
    }

    /**
     * Adiciona uma bandeira a um local específico do mapa.
     *
     * @param index O índice do local.
     * @param flag  A bandeira a ser adicionada.
     */
    public void addFlag(int index, Flag flag) {
        network.getVertex(index).setFlag(flag);
        network.getVertex(index).setHasFlag(true);
        System.out.println("Flag added to vertex: " + index);
    }

    /**
     * Remove a bandeira de um local específico do mapa.
     *
     * @param index O índice do local.
     */
    public void removeFlag(int index) {
        network.getVertex(index).setFlag(null);
        network.getVertex(index).setHasFlag(false);
        System.out.println("Flag removed from vertex: " + index);
    }

    /**
     * Retorna todos os locais do mapa.
     *
     * @return Um array contendo todos os locais do mapa.
     */
    public Locations[] getLocations() {
        Locations[] locations = new Locations[network.size()];
        for (int i = 0; i < network.size(); i++) {
            locations[i] = network.getVertex(i);
        }
        return locations;
    }

    /**
     * Retorna um local específico do mapa com base no índice.
     *
     * @param index O índice do local.
     * @return O local correspondente ao índice.
     */
    public Locations getLocation(int index) {
        return network.getVertex(index);
    }

    /**
     * Importa um mapa a partir de um arquivo JSON.
     * O arquivo JSON deve conter um array de vértices e um array de arestas.
     * Cada vértice deve ter um índice e cada aresta deve ter um vértice de origem, um vértice de destino e um peso.
     *
     * @param file O caminho do arquivo JSON.
     * @throws IOException Se ocorrer um erro ao ler o arquivo.
     */
    public void importMap(String file) {
        try (FileReader fileReader = new FileReader(file)) {
            JSONTokener tokener = new JSONTokener(fileReader);
            JSONObject json = new JSONObject(tokener);

            JSONArray nodesArray = json.getJSONArray("Locations");
            for (int i = 0; i < nodesArray.length(); i++) {
                JSONObject nodeJson = nodesArray.getJSONObject(i);
                int nodeId = nodeJson.getInt("id");
                Locations location = new Locations(nodeId); 
                this.network.addVertex(location);
            }

            JSONArray edgesArray = json.getJSONArray("edges");
            for (int i = 0; i < edgesArray.length(); i++) {
                JSONObject edgeJson = edgesArray.getJSONObject(i);
                int source = edgeJson.getInt("source");
                int target = edgeJson.getInt("target");
                double weight = edgeJson.getDouble("weight");
                this.network.addEdge(source, target, weight);
            }

            System.out.println("Mapa importado com sucesso de " + file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exporta o mapa para um arquivo JSON.
     * O arquivo JSON contém um array de vértices e um array de arestas.
     * Cada vértice tem um índice e um array de bots.
     * Cada aresta tem um vértice de origem, um vértice de destino e um peso.
     */
    public void exportMap() {
        JSONObject json = new JSONObject();

        JSONArray nodesArray = new JSONArray();
        for (Locations location : getLocations()) {
            JSONObject nodeJson = new JSONObject();
            nodeJson.put("id", location.getIndex());
            nodesArray.put(nodeJson);
        }
        json.put("Locations", nodesArray);

        JSONArray edgesArray = new JSONArray();
        double[][] adjacencyMatrix = this.network.getAdjMatrix();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] > 0 && adjacencyMatrix[i][j] < 16) {
                    JSONObject edgeJson = new JSONObject();
                    edgeJson.put("source", i);
                    edgeJson.put("target", j);
                    edgeJson.put("weight", adjacencyMatrix[i][j]);
                    edgesArray.put(edgeJson);
                }
            }
        }
        json.put("edges", edgesArray);

        try (FileWriter file = new FileWriter("map.json")) {
            file.write(json.toString());
            System.out.println("Mapa exportado com sucesso para map.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna a rede de localizações deste mapa.
     *
     * @return A rede de localizações deste mapa.
     */
    public Network<Locations> getNetwork() {
        return network;
    }
}
