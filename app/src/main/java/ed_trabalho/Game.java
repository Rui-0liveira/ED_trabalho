package ed_trabalho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import ClassImplementation.LinkedList;

public class Game {
    private LinkedList<Map> maps;
    private LinkedList<Player> players;
    private LinkedList<Round> rounds;

    public Game(){
        this.maps = new LinkedList<Map>();
        this.players = new LinkedList<Player>();
        this.rounds = new LinkedList<Round>();
    }

    public void createMap() throws NumberFormatException, IOException{
        Map map = new Map();
        initiatePlayer();
        
        int numVert = lerInt();

        for(int i = 0; i < numVert; i++){
            Locations newLocation = new Locations();
            map.addLocal(newLocation);
        }
        for(int i = 0; i < numVert; i++){
            System.out.println("Insira o numero de vertices adjacentes ao vertice " + i + ": ");
            int numAdj = lerInt();
            for(int j = 0; j < numAdj; j++){
                System.out.println("Insira o indice do vertice adjacente: ");
                int index = lerInt();
                int distance = randomDistance();
                map.getNetwork().addEdge(i, index, distance);
            }
        }

    }

    
    public int lerInt() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        try{
            return Integer.parseInt(br.readLine());
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
            Flag flag = new Flag("RED");
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
}
