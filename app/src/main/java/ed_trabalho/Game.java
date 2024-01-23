package ed_trabalho;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

import java.io.IOException;
import java.util.Random;
import ClassImplementation.LinkedList;
import java.util.InputMismatchException;

/**
 * A classe Game representa o jogo
 * Cada jogo tem um mapa e 2 jogadores
 * 
 */
public class Game {
    private Maps map;
    private LinkedList<Player> players;

    /**
     * Construtor da classe Game
     */
    public Game(){
        this.map = new Maps();
        this.players = new LinkedList<Player>();
    }

    /**
     * Função que cria o mapa apartir do numero de vertices e a densidade
     * @param numVert numero de vertices do mapa
     * @param densidade densidade do mapa
     * @throws NumberFormatException 
     * @throws IOException
     */
    public void createMap(int numVert, float densidade) throws NumberFormatException, IOException{
        for (int i = 0; i < numVert; i++) {
            Locations newLocation = new Locations();
            this.map.addLocal(newLocation);
        }
        float numArestas = (numVert * (numVert - 1)) * (densidade / 100);

        int count = 0;
        while (count < numArestas) {
            Random random = new Random();
            int randomNumber1 = random.nextInt(numVert);
            int randomNumber2 = random.nextInt(numVert);
            if (randomNumber1 != randomNumber2) {
                if (!this.map.getNetwork().hasEdge(randomNumber1, randomNumber2)) {
                    int distance = randomDistance();
                    this.map.getNetwork().addEdge(randomNumber1, randomNumber2, distance);
                    count++;
                }
            }
        }
    }

    /**
     * Função que cria o mapa bidirecional apartir do numero de vertices e a densidade
     * @param numVert numero de vertices do mapa
     * @param densidade densidade do mapa
     * @throws NumberFormatException 
     * @throws IOException
     */
    public void createBiMap(int numVert, float densidade) throws NumberFormatException, IOException{
        for (int i = 0; i < numVert; i++) {
            Locations newLocation = new Locations();
            this.map.addLocal(newLocation);
        }
        float numArestas = (numVert * (numVert - 1)) * (densidade / 50);
    
        int count = 0;
        while (count < numArestas/2) {
            Random random = new Random();
            int randomNumber1 = random.nextInt(numVert);
            int randomNumber2 = random.nextInt(numVert);
            if (randomNumber1 != randomNumber2) {
                if (!this.map.getNetwork().hasEdge(randomNumber1, randomNumber2)) {
                    int distance = randomDistance();
                    this.map.getNetwork().addEdge(randomNumber1, randomNumber2, distance);
                    this.map.getNetwork().addEdge(randomNumber2, randomNumber1, distance); 
                    count++;
                }
            }
        }
    }
    
    /**
     * Inicia os jogadores
     * @param name1 nome do jogador 1
     * @param name2 nome do jogador 2
     */
    public void initiatePlayer(String name1, String name2){
        Flag flag1 = new Flag();
        flag1.setColour(FlagColour.BLUE);
        Flag flag2 = new Flag();
        flag2.setColour(FlagColour.RED);
        Player player1 = new Player(name1, flag1);
        Player player2 = new Player(name2, flag2);
        players.add(player1);
        players.add(player2);
    }

    
    /**
     * Função que gera um distancia aleatoria
     * @return distancia aleatoria entre 1 e 15
     */
    public int randomDistance() {
        Random random = new Random();
        int randomNumber = random.nextInt(15) + 1;
        return randomNumber;
    }


    /**
     * Função que gera um jogador aleatorio entre os 2
     * @return jogador aleatorio
     */
    public Player randomPlayer(){
        Random random = new Random();
        int randomNumber = random.nextInt(2);
        return players.get(randomNumber);
    }


    /**
     * Função get do mapa
     * @return mapa
     */
    public Maps getMap() {
        return map;
    }


    /**
     * Função get dos jogadores
     * @return jogadores
     */
    public LinkedList<Player> getPlayers() {
        return players;
    }
    

    /**
     * Função de pesquisar um jogador pelo nome
     * @param name nome do jogador
     * @return jogador
     */
    public Player getPlayerByName(String name){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getName().equals(name)){
                return players.get(i);
            }
        }
        throw new InputMismatchException("Player not found!");
    }


    /**
     * Função que adiciona as bandeiras dos jogadores no mapa
     * @param flag1 index da bandeira do jogador 1
     * @param flag2 index da bandeira do jogador 2
     * @return 1 se as bandeiras forem diferentes, 0 se forem iguais e -1 se forem invalidas
     * @throws IOException
     */
    public int chooseFlags(int flag1, int flag2) throws IOException{
        if (flag1<0 || flag2<0) {
            return -1;
        }
        if(flag1 >= map.getNetwork().size() || flag2 >= map.getNetwork().size()){
            return -1;
        }
        else if(flag1 == flag2){
            return 0;
        } 
        else{
            map.getLocation(flag1).setFlag(players.get(0).getFlag());
            map.getLocation(flag1).setHasFlag(true);
            players.get(0).setFlag(map.getLocation(flag1).getFlag());
            players.get(0).getFlag().setIndex(flag1);
            map.getLocation(flag2).setFlag(players.get(1).getFlag());
            map.getLocation(flag2).setHasFlag(true);
            players.get(1).setFlag(map.getLocation(flag2).getFlag());
            players.get(1).getFlag().setIndex(flag2);
            return 1;
        }
        
    }
  
  
    /**
     * Função que trata do movimento de um bot
     * @param player jogador que vai fazer o movimento
     * @return posição para onde o bot se moveu
     * @throws IOException
     */
    public int play(Player player) throws IOException{

        int mov = movBot(player.getBotTurn(), player);
        if(mov == -1){
            System.out.println("Algoritmo invalido");
        }
        if(mov != player.getBotTurn().getLocation()){
            Bot bot = player.getBotTurn();
            getMap().getLocation(player.getBotTurn().getLocation()).removeBot(bot);
            getMap().getLocation(player.getBotTurn().getLocation()).setHasBot(false);
            getMap().getLocation(mov).addBot(bot);
            getMap().getLocation(mov).setHasBot(true);
            player.getBotTurn().setLocation(mov);
            player.getBotTurn().setTurn(false);
            if(player.getBotTurn() == null){
                player.setTurnTrue();
            }
        }
        return mov;
    }

    
    /**
     * Função que devolve o movimento de um bot apartir do algoritmo que este tem
     * @param bot bot que vai fazer o movimento
     * @param player jogador que vai fazer o movimento
     * @return posição para onde o bot se deve mover
     */
    public int movBot(Bot bot, Player player){
        if(bot.getMovEnum().equals(MovEnum.SHORTESTPATH)){
            Locations location;
            if(player.getId() == 1){
                location = this.map.getLocation(players.getRear().getElement().getFlag().getIndex());
            }
            else{
                location = this.map.getLocation(players.getFront().getElement().getFlag().getIndex());
            }
            return MovementAlgoritms.moveShortestPath(bot, map, location);
        }
        else if(bot.getMovEnum().equals(MovEnum.RANDOMPATH)){
            return MovementAlgoritms.moveRandomly(bot, getMap());
        }
        else if(bot.getMovEnum().equals(MovEnum.GREEDYPATH)){
            return MovementAlgoritms.getGreedyMove(bot, getMap());
        }
        else if(bot.getMovEnum().equals(MovEnum.DUMBPATH)){
            return MovementAlgoritms.getDumbMove(bot, getMap());
        }
        else{
            return -1;
        }
    }
    

    /**
     * Função que verifica se um jogador ganhou
     * @param bot bot que deseja verificar se ganhou
     * @param player jogador inimigo do bot
     * @return true se o bot ganhou, false se não
     */
    public boolean Win(Bot bot, Player player){
        if(player.getFlag().getIndex() == bot.getLocation()){
            return true;
        }
        return false;
    }


    /**
     * Função de escolher um algoritmo para um bot
     * @param player jogador que vai escolher o algoritmo
     * @param index index do bot que vai receber o algoritmo
     * @param op opção do algoritmo
     * @throws IOException
     */
    public void chooseAlgoritms(Player player, int index, int op) throws IOException{
        if(op == 1){
            player.getBots().get(index).setMov(MovEnum.SHORTESTPATH);
        }
        else if(op == 2){
            player.getBots().get(index).setMov(MovEnum.RANDOMPATH);
        }
        else if(op == 3){
            player.getBots().get(index).setMov(MovEnum.GREEDYPATH);
        }
        else if(op == 4){
            player.getBots().get(index).setMov(MovEnum.DUMBPATH);
        }
        else{
            throw new InputMismatchException("Invalid option!");
        }
    }

    
    /**
     * Função que verifica se um jogador tem todos os bots com algoritmos diferentes
     * @param player jogador que vai ser verificado
     * @param op opção do algoritmo
     * @return true se tiver todos os bots com algoritmos diferentes, false se não ou se for null
     */
    public boolean checkAlgoritms(Player player, int op){
        for(int i = 0; i < player.getBots().size(); i++){
            if(player.getBots().get(i).getMovEnum() == null){
                return false;
            }
            if(player.getBots().get(i).getMovEnum().equals(MovEnum.SHORTESTPATH) && op == 1){
                return true;
            }
            else if(player.getBots().get(i).getMovEnum().equals(MovEnum.RANDOMPATH) && op == 2){
                return true;
            }
            else if(player.getBots().get(i).getMovEnum().equals(MovEnum.GREEDYPATH) && op == 3){
                return true;
            }
            else if(player.getBots().get(i).getMovEnum().equals(MovEnum.DUMBPATH) && op == 4){
                return true;
            }
        }
        return false;
    }


    /**
     * Função toString da classe Game
     */
    public String toString(){
        String str = "";
        for(int i = 0; i < map.getNetwork().size(); i++){
            str += "Vertex " + i + ": ";
            if(map.getLocations()[i].getHasFlag()){
                str += "Flag: " + map.getLocations()[i].getFlag().getColour() + " ";
            }
            if(map.getLocations()[i].getHasBot()){
                for(int j = 0; j < map.getLocations()[i].getBots().size(); j++){
                    str += "Bot "+ map.getLocations()[i].getBots().get(j).getIndex();
                }
            }
            str += "\n";
        }
        return str;
    }
}
