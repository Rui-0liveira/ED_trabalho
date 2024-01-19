package ed_trabalho;

import java.util.List;
import java.util.Random;

import ClassImplementation.ArrayList;
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

    public void importMap(){

    }

    public void exportMap(){

    }

    public boolean isVertexEmpty(Locations local){
        if(local.getHasBot() == true){
            return true;
        }else{
            return false;
        }
    }

    //Getters
    public Network<Locations> getNetwork(){
        return network;
    }
}
