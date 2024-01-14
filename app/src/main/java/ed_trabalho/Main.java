package ed_trabalho;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws NumberFormatException, IOException {
        
        Game game = new Game();

        //mapa criado
        /*Falta
        opção para importar mapa por ficheiro
        decidir o tipo do mapa(bidirecional ou não)
        decidir densidade das arestas(numero de arestas tem que seguir a regra (N* (N -1)) * 0.5, sendo N o numero de vertices e 0.5 a densidade)
        */ 
        game.createMap();
        System.out.println(game.getMap().getNetwork().toString());
        /*
         * escolher bandeira
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