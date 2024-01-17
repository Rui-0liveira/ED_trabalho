package ed_trabalho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import ClassImplementation.LinkedList;
import java.util.Scanner;

import org.checkerframework.checker.units.qual.t;

import java.util.InputMismatchException;

public class Game {
    private Map map;
    private LinkedList<Player> players;
    private LinkedList<Round> rounds;

    public Game(){
        this.map = new Map();
        this.players = new LinkedList<Player>();
        this.rounds = new LinkedList<Round>();
    }

    public void createMap() throws NumberFormatException, IOException{
        
        int numVert = lerInt();

        for(int i = 0; i < numVert; i++){
            Locations newLocation = new Locations();
            map.addLocal(newLocation);
        }
        
        float numArestas;
        System.out.println("Insira 1 se o mapa for bidirecional ou 0 se for direcional: ");
        int temp = lerInt();
        boolean bidirecional = true;
        if(temp == 0){
            bidirecional = false;
        }
        if(!bidirecional){
            System.out.println("Insira a densidade de arestas: ");
            float densidade = lerInt();
            float x = densidade/100;
            numArestas = (numVert * (numVert - 1)) * (x);
        }
        else{
            //falta verificar se ele insere um numero de arestas possivel
            System.out.println("Insira o numero de arestas: ");
            numArestas = lerInt();
        }
        
        int count = 0;
        while(count < numArestas){
            Random random = new Random();
            int randomNumber1 = random.nextInt(numVert);
            int randomNumber2 = random.nextInt(numVert);
            if(randomNumber1 != randomNumber2){
                if(!map.getNetwork().hasEdge(randomNumber1, randomNumber2)){
                    int distance = randomDistance();
                    if (bidirecional) {
                        map.getNetwork().addEdge(randomNumber2, randomNumber1, distance);
                    }
                    else{
                        map.getNetwork().addEdgeDiretional(randomNumber1, randomNumber2, distance);
                    }
                    count++;
                }
            }
        }
    }

    
    public int lerInt() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            return Integer.parseInt(br.readLine());
        }catch(NumberFormatException e){
            System.out.println("Valor invalido! Insira novamente: ");
            return 1;
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

    public String ler() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        return br.readLine();
    } 
    

    public int randomDistance() {
        Random random = new Random();
        int randomNumber = random.nextInt(15) + 1;
        return randomNumber;
    }

    public Map getMap() {
        return map;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }
    /*
     * funçao que recebe o nome de um player e retorna o player
     */
    public Player getPlayerByName(String name){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getName().equals(name)){
                return players.get(i);
            }
        }
        throw new InputMismatchException("Player not found!");
    }

    public LinkedList<Round> getRounds() {
        return rounds;
    }

    /*
     * funçao que pede a cada jogador para escolher onde quer colocar a sua bandeira
     */
    public void chooseFlags() throws IOException{
        for(int i = 0; i < players.size(); i++){
            System.out.println("Player " + players.get(i).getName() + " choose a flag: ");
            int index = lerInt();
            if(map.getNetwork().getVertex(index).getFlag() == null){
                map.getNetwork().getVertex(index).setFlag(players.get(i).getFlag());
                map.getNetwork().getVertex(index).setHasFlag(true);
                players.get(i).setFlag(map.getNetwork().getVertex(index).getFlag());
                players.get(i).getFlag().setIndex(index);
            }
            else{
                System.out.println("This vertex already has a flag!");
                i--;
            }
        }
    }

    /*funçao que pede ao utilizador o numero de bots que quer ambos os jogadores tem o mesmo numero de bots
    * percorre cada jogador e adiciona os bots á localizaçao onde cada jogador tem a sua bandeira
    * so pode haver 1 bot para cada 10 vertices existentes no mapa
    */
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
                    map.getNetwork().getVertex(players.get(i).getFlag().getIndex()).addBot(new Bot(players.get(i),j+1));
                }
            }
        }
    }
    
    /*
     * funçao que tostring do jogo
     * quando chamada deve mostrar o mapa com as bandeiras e a posiçao dos bots
     */
    public String toString(){
        String str = "";
        for(int i = 0; i < map.getNetwork().size(); i++){
            str += "Vertex " + i + ": ";
            if(map.getNetwork().getVertex(i).getHasFlag()){
                str += "Flag: " + map.getNetwork().getVertex(i).getFlag().getColour() + " ";
            }
            if(map.getNetwork().getVertex(i).getHasBot()){
                for(int j = 0; j < map.getNetwork().getVertex(i).getBots().size(); j++){
                    str += "Bot "+ map.getNetwork().getVertex(i).getBots().get(j).getIndex() + "" + map.getNetwork().getVertex(i).getBots().get(j).getPlayer().getFlag().getColour() + "  ";
                }
            }
            str += "\n";
        }
        return str;
    }
}
