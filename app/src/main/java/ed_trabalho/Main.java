package ed_trabalho;

import java.io.IOException;

import GUI.GUI;

public class Main {
    public static void main(String[] args) throws NumberFormatException, IOException {
        new GUI();
<<<<<<< Updated upstream

<<<<<<< Updated upstream
=======
>>>>>>> Stashed changes
/* 

        Game game = new Game();
=======
 
        /*Game game = new Game();
>>>>>>> Stashed changes
        
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

<<<<<<< Updated upstream

<<<<<<< Updated upstream
*/
=======
*/

>>>>>>> Stashed changes
=======
 */
>>>>>>> Stashed changes
    }
}