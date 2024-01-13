package ed_trabalho;

import ClassImplementation.LinkedList;

public class Player {
    private String name;
    private int numBots;
    private LinkedList<Bot> bots;
    private Flag flag;

    //Constructors
    public Player(String name, Flag flag){
        this.name = name;
        this.numBots = 0;
        this.bots = new LinkedList<Bot>();
        this.flag = flag;
    }

    //Metodo para adicionar um bot ao jogador
    public void addBot(Bot bot){
        this.bots.add(bot);
        this.numBots++;
    }

    //Metodo para remover um bot do jogador
    public void removeBot(Bot bot){
        this.bots.remove(bot);
        this.numBots--;
    }

    //Getters and Setters
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getNumBots(){
        return numBots;
    }
    public void setNumBots(int numBots){
        this.numBots = numBots;
    }
    public LinkedList<Bot> getBots(){
        return bots;
    }
    public void setBots(LinkedList<Bot> bots){
        this.bots = bots;
    }
    public Flag getFlag(){
        return flag;
    }
    public void setFlag(Flag flag){
        this.flag = flag;
    }
}
