package ed_trabalho;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import ClassImplementation.LinkedList;

import java.util.InputMismatchException;

public class Game {
    private Maps map;
    private LinkedList<Player> players;

    public Game(){
        this.map = new Maps();
        this.players = new LinkedList<Player>();
    }

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


    public int lerInt() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            String temp = br.readLine();
            if(Integer.parseInt(temp) >= 0){
                return Integer.parseInt(temp);
            }
            else{
                System.out.println("Value must be positive!");
                return lerInt();
            }
        }catch(NumberFormatException e){
            System.out.println("Valor invalido! Insira novamente: ");
            return lerInt();
        }
    }
    
    
    public void initiatePlayer(String name1, String name2){
        //cria os dois jogadores
        Flag flag1 = new Flag();
        flag1.setColour("BLUE");
        Flag flag2 = new Flag();
        flag2.setColour("RED");
        Player player1 = new Player(name1, flag1);
        Player player2 = new Player(name2, flag2);
        players.add(player1);
        players.add(player2);
        System.out.println(name1 + " " + name2);
        
    }

    //da para inserir so enters (nao pode)
    public String ler() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        return br.readLine();
    } 
    

    public int randomDistance() {
        Random random = new Random();
        int randomNumber = random.nextInt(15) + 1;
        return randomNumber;
    }

    public Maps getMap() {
        return map;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }
    

    public Player getPlayerByName(String name){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getName().equals(name)){
                return players.get(i);
            }
        }
        throw new InputMismatchException("Player not found!");
    }



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


    public void addBots() throws IOException{
        System.out.println("Insira o numero de bots: ");
        int numBots = lerInt();
        int numVertices = map.getNetwork().size();
        if(numBots > 1 + (numVertices/10)){
            System.out.println("Numero de bots invalido!");
            addBots();
        }
        else{
            for(int i = 0; i < players.size(); i++){
                for(int j = 0; j < numBots; j++){
                    Bot bot = new Bot(players.get(i).getFlag().getIndex(),j+1);
                    map.getLocation(players.get(i).getFlag().getIndex()).addBot(bot);
                    this.players.get(i).addBot(bot);
                }
            }
        }
    }
    //funçao que devolve um player á sorte dos dois existentes
    public Player randomPlayer(){
        Random random = new Random();
        int randomNumber = random.nextInt(2);
        
        return players.get(randomNumber);
    }
    
    public int play(Player player) throws IOException{

        int mov = movBot(player.getBotTurn(), player);
        if(mov == -1){
            System.out.println("Algoritmo invalido");
        }
        if(mov != player.getBotTurn().getIndex()){
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
        else{
            System.out.println(player.getBotTurn().getMovEnum());
            System.out.println("mesmo sitio");
        }
        return mov;
    }

    //funçao movBot que recebe bot e devolve o movimento que vai fazer consuante o algoritmo que ele escolheu
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
    

    //funçao que verifica se tem um bot na localizaçao da bandeira do jogador
    public boolean Win(Bot bot, Player player){
        if(player.getFlag().getIndex() == bot.getLocation()){
            return true;
        }
        return false;
    }

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
            System.out.println("Invalid index!");
        }
    }

    //funçao que recebe um algoritmo e ve se esse ja esta em algum bot do jogador
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
