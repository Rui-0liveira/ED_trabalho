package ed_trabalho;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

import java.io.IOException;
import java.util.Random;

import GUI.GUI;

public class Main {
    public static void main(String[] args) throws NumberFormatException, IOException {
        //new GUI();

 
        Game game = new Game();
        
        //inicia os dados dos jogadores
        String name1 = "";
        String name2 = "";
        try{
            System.out.println("Insira o nome do jogador 1: ");
            name1 = game.ler();
            System.out.println("Insira o nome do jogador 2: ");
            name1 = game.ler();
        }catch(IOException e){
            System.out.println("Erro na leitura do nome do jogador!");
        }
        game.initiatePlayer(name1,name2);
        System.out.println("Insira 1 para importar map/ 2 para Mapa direcional / 3 para mapa bidirecional ");
        int op = game.lerInt();
        if(op == 1){
            game.getMap().importMap("map.json");
        }
        else if(op == 2){
            System.out.println("Numero de Vertices: ");
            int numVertices = game.lerInt();
            System.out.println("Numero de Arestas: ");
            float numArestas = game.lerInt();
            game.createMap(numVertices, numArestas);
            game.getMap().exportMap();
        }else if(op == 3){
            System.out.println("Numero de Vertices: ");
            int numVertices = game.lerInt();
            System.out.println("Numero de Arestas: ");
            float numArestas = game.lerInt();
            game.createBiMap(numVertices, numArestas);
            game.getMap().exportMap();
        }
        else{
            System.out.println("Opçao invalida");
        }
        
        System.out.println(game.getMap().getNetwork().toString());

        //escolher bandeiras
        game.chooseFlags(1, 9);


        //por um bot na localizaçao onde esta a bandeira dos dois jogador
        game.addBots();
        //System.out.println("\n\n\n" + game.toString());
        
        


        Random random = new Random();
        int randomNumber = random.nextInt(2);
        System.out.println("Player que começa: "+ game.getPlayers().get(randomNumber).getName());
        int contador=0;
        do{
            System.out.println("jogada "+ contador);
            Bot bot = game.getPlayers().get(randomNumber).getBotTurn();
            Player player  = game.getPlayers().get(randomNumber);
            game.play(game.getPlayers().get(randomNumber));
            
            if(randomNumber == 1){
                randomNumber = 0;
            }
            else{
                randomNumber = 1;
            }
            System.out.println("Bot " + bot.getIndex() + " moveu para " + bot.getLocation());
            if(game.Win(bot, player)){
                System.out.println("Bot "+ bot.getIndex() + " " + player.getName()+ " ganhou");
                break;
            }
            if(contador == 50){
                System.out.println("Empate");
                break;
            }
            contador++;
        }while(true);
        //print da matriz adjacente
        System.out.println(game.getMap().getNetwork().printmatriz());



    }
}