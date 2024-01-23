package ed_trabalho;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

import ClassImplementation.LinkedList;

/**
 * A classe Player representa um jogador no jogo.
 * Cada jogador tem um ID, um nome, um número de bots, uma lista de bots e uma bandeira.
 */
public class Player {
    private static int id = 0;
    private String name;
    private int numBots;
    private LinkedList<Bot> bots;
    private Flag flag;
    
    /**
     * Construtor para a classe Player.
     * Inicializa o jogador com um nome e uma bandeira específicos, sem bots.
     *
     * @param name O nome do jogador.
     * @param flag A bandeira do jogador.
     */
    public Player(String name, Flag flag){
        this.name = name;
        this.numBots = 0;
        this.bots = new LinkedList<Bot>();
        this.flag = flag;
        id++;
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

    /**
     * Retorna o nome deste jogador.
     *
     * @return O nome deste jogador.
     */
    public String getName(){
        return name;
    }

    /**
     * Define o nome deste jogador.
     *
     * @param name O nome deste jogador.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Retorna o número de bots que este jogador tem.
     *
     * @return O número de bots que este jogador tem.
     */
    public int getNumBots(){
        return numBots;
    }

    /**
     * Define o número de bots que este jogador tem.
     *
     * @param numBots O número de bots que este jogador tem.
     */
    public void setNumBots(int numBots){
        this.numBots = numBots;
    }

    /**
     * Retorna a lista de bots que este jogador tem.
     *
     * @return A lista de bots que este jogador tem.
     */
    public LinkedList<Bot> getBots(){
        return bots;
    }

    /**
     * Define a lista de bots que este jogador tem.
     *
     * @param bots A lista de bots que este jogador tem.
     */
    public void setBots(LinkedList<Bot> bots){
        this.bots = bots;
    }

    /**
     * Retorna a bandeira que este jogador tem.
     *
     * @return A bandeira que este jogador tem.
     */
    public Flag getFlag(){
        return flag;
    }

    /**
     * Define a bandeira que este jogador tem.
     *
     * @param flag A bandeira que este jogador tem.
     */
    public void setFlag(Flag flag){
        this.flag = flag;
    }

    /**
     * Retorna o ID deste jogador.
     *
     * @return O ID deste jogador.
     */
    public int getId(){
        return id;
    }

    /**
     * Retorna o bot que tem o turno a true.
     *
     * @return O bot que tem o turno a true, ou null se nenhum bot tem o turno a true.
     */
    public Bot getBotTurn(){
        for(int i = 0; i < this.bots.size(); i++){
            if(this.bots.get(i).getTurn()){
                return this.bots.get(i);
            }
        }
        return null;
    }

    /**
     * Define o turno de todos os bots para true.
     */
    public void setTurnTrue(){
        for(int i = 0; i < this.bots.size(); i++){
            this.bots.get(i).setTurn(true);
        }
    }
}
