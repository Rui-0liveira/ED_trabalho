package ed_trabalho;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws NumberFormatException, IOException {
        
        Game game = new Game();
        
        
        //inicia os dados dos jogadores
        game.initiatePlayer();
        System.out.println("Insira 1 para importar map ou 2 para criar um novo");
        int op = game.lerInt();
        if(op == 1){
            game.getMap().importMap("map.json");
        }
        else if(op == 2){
            game.createMap();
            game.getMap().exportMap();
        }
        else{
            System.out.println("Opçao invalida");
        }
        
        System.out.println(game.getMap().getNetwork().toString());

        //escolher bandeiras
        game.chooseFlags();


        //por um bot na localizaçao onde esta a bandeira dos dois jogador
        game.addBots();
        //System.out.println("\n\n\n" + game.toString());
        
        game.chooseAlgoritms();



        Random random = new Random();
        int randomNumber = random.nextInt(2);
        System.out.println("Player que começa: "+ game.getPlayers().get(randomNumber).getName());
        int contador=0;
        do{
            System.out.println("jogada "+ contador);
            Bot bot = game.getPlayers().get(randomNumber).getBotTurn();
            game.play(game.getPlayers().get(randomNumber));
            
            if(randomNumber == 1){
                randomNumber = 0;
            }
            else{
                randomNumber = 1;
            }
            System.out.println("Bot "+ bot.getPlayer().getFlag().getColour() + " " + bot.getIndex() + " moveu para " + bot.getLocation());
            if(game.Win(bot)){
                System.out.println("Jogador "+ bot.getPlayer().getName() + " ganhou");
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