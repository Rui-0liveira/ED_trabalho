package ed_trabalho;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws NumberFormatException, IOException {
        
        Game game = new Game();

        //mapa criado
        /*Falta !!!!!!!!!!!!!!!!!!!!!!
        opção para importar mapa por ficheiro
        */
        
        
        //inicia os dados dos jogadores
        game.initiatePlayer();

        //cria mapa
        game.createMap();
        System.out.println(game.getMap().getNetwork().toString());

        //escolher bandeiras
        game.chooseFlags();
        System.out.println(game.getPlayerByName("r").getFlag().getIndex());
        /*
         * 
         * escolher numero de bots
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