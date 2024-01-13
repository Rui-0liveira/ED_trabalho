package ed_trabalho;

import ClassImplementation.LinkedList;

public class Round {
    private LinkedList<Player> players;
    private int numBots;
    private Map map;
    private LinkedList<Bot> bots;

    //Constructors
    public Round(LinkedList<Player> players, int numBots, Map map){
        this.players = players;
        this.numBots = numBots;
        this.map = map;
        bots = new LinkedList<>();
    }

    public void addBot(Bot bot){
        bots.add(bot);
    }
    public void removeBot(Bot bot){
        bots.remove(bot);
    }

    //Getters and Setters
    public LinkedList<Bot> getBots(){
        return bots;
    }
    public LinkedList<Player> getPlayers(){
        return players;
    }
    public int getNumBots(){
        return numBots;
    }
    public Map getMap(){
        return map;
    }
    public void setPlayers(LinkedList<Player> players){
        this.players = players;
    }
    public void setNumBots(int numBots){
        this.numBots = numBots;
    }
    public void setMap(Map map){
        this.map = map;
    }
    public void setBots(LinkedList<Bot> bots){
        this.bots = bots;
    }

    /*
    funçao para uma ronda ronda:
    -definir o mapa
    -onde ficam as bandeiras
    -numero de bots
    -algoritmo dos bots
    -start game
     */
    public void start() {
        //
    }
    public void criarMapa(String ficheiro){
    //funçao que cria um mapa apartir de um ficheiro
    }
    public void criarMapa(){
    //funçao que cria um mapa
    }
}
