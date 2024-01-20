package ed_trabalho;

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

    public void createMap() throws NumberFormatException, IOException{
        
        int numVert = lerInt();

        for(int i = 0; i < numVert; i++){
            Locations newLocation = new Locations();
            map.addLocal(newLocation);
        }
        
        float numArestas;
        System.out.println("Insira a densidade de arestas: ");
        float densidade = lerInt();
        float x = densidade/100;
        numArestas = (numVert * (numVert - 1)) * (x);
        
        
        int count = 0;
        while(count < numArestas){
            Random random = new Random();
            int randomNumber1 = random.nextInt(numVert);
            int randomNumber2 = random.nextInt(numVert);
            if(randomNumber1 != randomNumber2){
                if(!map.getNetwork().hasEdge(randomNumber1, randomNumber2)){
                    int distance = randomDistance();
                    map.getNetwork().addEdge(randomNumber1, randomNumber2, distance);
                    count++;
                }
            }
        }
    }


    public int lerInt() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            String temp = br.readLine();
            if(Integer.parseInt(temp)>0){
                return Integer.parseInt(temp);
            }
            else{
                System.out.println("Valor tem que ser acima de 0");
                return lerInt();
            }
        }catch(NumberFormatException e){
            System.out.println("Valor invalido! Insira novamente: ");
            return lerInt();
        }
    }
    
    
    public void initiatePlayer(){
        for(int i = 0; i < 2; i++){
            System.out.println("Insira o nome do jogador " + (i + 1) + ": ");
            String name = "";
            try{
                name = ler();
            }catch(IOException e){
                System.out.println("Erro na leitura do nome do jogador!");
            }
            Flag flag = new Flag();
            if(i==1){
                flag.setColour("RED");
            }
            else {
                flag.setColour("BLUE");
            }
            Player player = new Player(name, flag);
            players.add(player);
        }
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



    public void chooseFlags() throws IOException{
        for(int i = 0; i < players.size(); i++){
            System.out.println("Player " + players.get(i).getName() + " choose a flag: ");
            int index = lerInt();
            if(index >= map.getNetwork().size()){
                System.out.println("Invalid index!");
                i--;
            }
            else{
                if(map.getLocation(index).getFlag() == null){
                    map.getLocation(index).setFlag(players.get(i).getFlag());
                    map.getLocation(index).setHasFlag(true);
                    players.get(i).setFlag(map.getLocation(index).getFlag());
                    players.get(i).getFlag().setIndex(index);
                }
                else{
                    System.out.println("This vertex already has a flag!");
                    i--;
                }
            }
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
                    Bot bot = new Bot(players.get(i), players.get(i).getFlag().getIndex(),j+1);
                    map.getLocation(players.get(i).getFlag().getIndex()).addBot(bot);
                    this.players.get(i).addBot(bot);
                }
            }
        }
    }
    
    
    public void play(Player player) throws IOException{

        int mov = movBot(player.getBotTurn());
        if(mov == -1){
            System.out.println("Algoritmo invalido");
        }
        if(mov != player.getBotTurn().getLocation()){
            getMap().getLocation(mov).addBot(player.getBotTurn());
            getMap().getLocation(player.getBotTurn().getLocation()).removeBot(player.getBotTurn());
            player.getBotTurn().setLocation(mov);
            player.getBotTurn().setTurn(false);
            if(player.getBotTurn() == null){
                player.setTurnTrue();
            }
        }
        else{
            System.out.println("mesmo sitio");
        }
    }

    //funçao movBot que recebe bot e devolve o movimento que vai fazer consuante o algoritmo que ele escolheu
    public int movBot(Bot bot){
        if(bot.getMovEnum().equals(MovEnum.SHORTESTPATH)){
            return bot.getLocation();//MovementAlgoritms.shortestPath(bot, getMap());
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
    public boolean Win(Bot bot){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i) != bot.getPlayer()){
                if(players.get(i).getFlag().getIndex() == bot.getLocation()){
                    return true;
                }
            }
        }
        return false;
    }
    
    //funçao para o player escolher o algoritmo utilizado pelo bot
    public void chooseAlgoritms() throws IOException{
        System.out.println("Algoritms: ");
        System.out.println("1 - Shortest Path");
        System.out.println("2 - Random Path");
        System.out.println("3 - Greedy Path");
        System.out.println("4 - Dumb Path");
        for(int i = 0; i < players.size(); i++){
            System.out.println("Player " + players.get(i).getName() + " choose a algoritms: ");
            choice(players.get(i));
        }
    }

    public void choice(Player player) throws IOException{
        for(int j = 0; j < player.getBots().size(); j++){
            System.out.println("Bot " + player.getBots().get(j).getIndex() +  ": ");
            int index = lerInt();
            if(index == 1){
                player.getBots().get(j).setMov( MovEnum.SHORTESTPATH);
            }
            else if(index == 2){
                player.getBots().get(j).setMov(MovEnum.RANDOMPATH);
            }
            else if(index == 3){
                player.getBots().get(j).setMov(MovEnum.GREEDYPATH);
            }
            else if(index == 4){
                player.getBots().get(j).setMov(MovEnum.DUMBPATH);
            }
            else{
                System.out.println("Invalid index!");
                j--;
            }
        }
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
                    str += "Bot "+ map.getLocations()[i].getBots().get(j).getIndex()+ "" + map.getLocations()[i].getBots().get(j).getPlayer().getFlag().getColour() + "  ";
                }
            }
            str += "\n";
        }
        return str;
    }
}
