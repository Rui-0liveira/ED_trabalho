package ed_trabalho;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import ClassImplementation.Network;

public class Maps{
    private Network<Locations> network;

    public Maps(){
        this.network = new Network<Locations>();
    }

    public void addLocal(Locations local){
        network.addVertex(local);
        System.out.println("Vertex added: " + local.getIndex());
    }

    public void removeLocal(Locations local){
        network.removeVertex(local);
        System.out.println("Vertex removed: " + local.getIndex());
    }

    public void addFlag(int index, Flag flag){
        network.getVertex(index).setFlag(flag);
        network.getVertex(index).setHasFlag(false);
        System.out.println("Flag added to vertex: " + index);
    }

    public void removeFlag(int index){
        network.getVertex(index).setFlag(null);
        network.getVertex(index).setHasFlag(false);
        System.out.println("Flag removed from vertex: " + index);
    }

    public Locations[] getLocations(){
        Locations[] locations = new Locations[network.size()];
        for(int i = 0; i < network.size(); i++){
            locations[i] = network.getVertex(i);
        }
        return locations;
    }
    public Locations getLocation(int index){
        return network.getVertex(index);
    }


    public void importMap(String file) {
        try (FileReader fileReader = new FileReader(file)) {
            JSONTokener tokener = new JSONTokener(fileReader);
            JSONObject json = new JSONObject(tokener);

            // Lê nós do JSON
            JSONArray nodesArray = json.getJSONArray("Locations");
            for (int i = 0; i < nodesArray.length(); i++) {
                JSONObject nodeJson = nodesArray.getJSONObject(i);
                int nodeId = nodeJson.getInt("id");
                // Crie seu objeto Locations e adicione ao grafo
                Locations location = new Locations(nodeId); // Substitua isso com sua lógica real
                this.network.addVertex(location);
            }

            // Lê arestas do JSON
            JSONArray edgesArray = json.getJSONArray("edges");
            for (int i = 0; i < edgesArray.length(); i++) {
                JSONObject edgeJson = edgesArray.getJSONObject(i);
                int source = edgeJson.getInt("source");
                int target = edgeJson.getInt("target");
                double weight = edgeJson.getDouble("weight");
                // Adicione a aresta ao grafo
                this.network.addEdge(source, target, weight);
            }

            System.out.println("Mapa importado com sucesso de " + file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void exportMap() {
        JSONObject json = new JSONObject();

        // Adiciona nós ao JSON
        JSONArray nodesArray = new JSONArray();
        for (Locations location : getLocations()) {
            JSONObject nodeJson = new JSONObject();
            nodeJson.put("id", location.getIndex());
            nodesArray.put(nodeJson);
        }
        json.put("Locations", nodesArray);

        // Adiciona arestas ao JSON (matriz adjacente)
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

        // Escreve o JSON no arquivo
        try (FileWriter file = new FileWriter("map.json")) {
            file.write(json.toString());
            System.out.println("Mapa exportado com sucesso para map.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Getters
    public Network<Locations> getNetwork(){
        return network;
    }
}
