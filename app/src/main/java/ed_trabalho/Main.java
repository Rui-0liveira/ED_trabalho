package ed_trabalho;

import java.io.IOException;

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
        System.out.println("\n\n\n" + game.toString());
        
        //game.getPlayerByName("Rui").getBotTurn().getMov();
        for(int i=0; i<10; i++){
            int novo = MovementAlgoritms.moveRandomly(game.getPlayerByName("r").getBotTurn(), game.getMap());
            System.out.println(novo);
            game.getMap().getLocations()[novo].addBot(game.getPlayerByName("r").getBotTurn());
            game.getMap().getLocations()[game.getPlayerByName("r").getBotTurn().getLocation()].removeBot(game.getPlayerByName("r").getBotTurn());
            game.getPlayerByName("r").getBotTurn().setLocation(novo);
            System.out.println(game.getPlayerByName("r").getBotTurn().getLocation());
            System.out.println(game.toString());
        }
        
        /*
         * 
         * por os algoritmos em cada um dos bots(so se pode repetir algoritmos caso ja tenhamos usado todos)
         * Algoritmos:
         * 1-caminho mais curto até a bandeira(nao confundir por aresta mais pequena naquele momento)
         * 2-?Depht First Search?, aquele da stack ele vai por caminho random até nao conseugir mais e depois volta a traz
         * 3-
         * 
         * 
         * começar jogo(o 1º a jogar é decidido aleatoriamente)
         */
    }
}