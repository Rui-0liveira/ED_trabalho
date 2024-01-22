package ed_trabalho;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    /**
     * Teste da criação de um mapa
     * Verifica se o número de locais e arestas é correto
     */
    @Test
    public void testCreateMap() {
        Game game = new Game();
        try {
            game.createMap(5, 100);
        } catch (Exception e) {
            fail("createMap threw an exception: " + e.getMessage());
        }

        assertEquals(5, game.getMap().getLocations().length);

        int expectedEdges = (int) ((5 * (5 - 1)) * (50 / 100.0));
        int actualEdges = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                if (game.getMap().getNetwork().hasEdge(i, j)) {
                    actualEdges++;
                }
            }
        }
        assertEquals(expectedEdges, actualEdges);
    }

    /**
     * Teste da inicialização dos jogadores
     * Verifica se o número, nome e cores das bandeiras dos jogadores estam corretos
     */
    @Test
    public void testInitiatePlayer() {
        Game game = new Game();
        game.initiatePlayer("Player1", "Player2");

        assertEquals(2, game.getPlayers().size());

        assertEquals("Player1", game.getPlayers().get(0).getName());
        assertEquals("Player2", game.getPlayers().get(1).getName());

        assertEquals("BLUE", game.getPlayers().get(0).getFlag().getColour());
        assertEquals("RED", game.getPlayers().get(1).getFlag().getColour());
    }

    /**
     * Teste de uma função que retorna um número aleatório entre 1 e 15
     * Verifica se o número retornado está entre 1 e 15
     */
    @Test
    public void testRandomDistance() {
        Game game = new Game();
        for (int i = 0; i < 1000; i++) {
            int distance = game.randomDistance();
            assertTrue("Distance should be between 1 and 15, but was " + distance, distance >= 1 && distance <= 15);
        }
    }
}
