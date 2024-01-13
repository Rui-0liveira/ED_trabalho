package ed_trabalho;

import ClassImplementation.Network;

public class Map extends Network<String> {
    private Network<Locations> network;

    public Map(){
        this.network = new Network<Locations>();
    }

    public void addLocal(Locations local){
        network.addVertex(local);
    }

    //Getters
    public Network<Locations> getNetwork(){
        return network;
    }
}
