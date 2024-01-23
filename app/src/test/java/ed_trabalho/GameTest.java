package ed_trabalho;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

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

    @Test
    public void testCreateBiMap(){
        Game game = new Game();
        int numVert = 5;
        float densidade = 50;
        
        try {
            game.createBiMap(numVert, densidade);
        } catch (Exception e) {
            fail("createMap threw an exception: " + e.getMessage());
        }
        assertEquals(numVert, game.getMap().getLocations().length);

        float numArestas = (numVert * (numVert - 1)) * (densidade / 50);
        assertEquals(numArestas/2, game.getMap().getNetwork().getAdjMatrix().length, 0.01); 
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

        assertEquals(FlagColour.BLUE, game.getPlayers().get(0).getFlag().getColour());
        assertEquals(FlagColour.RED, game.getPlayers().get(1).getFlag().getColour());
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

    @Test
    public void testRandomPlayer() {
        Game game = new Game();
        Flag flag1 = new Flag(FlagColour.BLUE);
        Flag flag2 = new Flag(FlagColour.RED);
        Player player1 = new Player("Player 1", flag1);
        Player player2 = new Player("Player 2", flag2);

        game.getPlayers().add(player1);
        game.getPlayers().add(player2);

        boolean player1Returned = false;
        boolean player2Returned = false;

        for (int i = 0; i < 100; i++) {
            Player randomPlayer = game.randomPlayer();

            assertTrue(randomPlayer == player1 || randomPlayer == player2);

            if (randomPlayer == player1) {
                player1Returned = true;
            } else if (randomPlayer == player2) {
                player2Returned = true;
            }
        }

        assertTrue(player1Returned);
        assertTrue(player2Returned);
    }


    @Test
    public void testChooseFlags() throws IOException {
        
        Game game = new Game();
        game.createMap(5, 100);
        game.initiatePlayer("player1", "player2");
        assertEquals(-1, game.chooseFlags(-1, 0));
        assertEquals(-1, game.chooseFlags(0, game.getMap().getNetwork().size()));

        assertEquals(0, game.chooseFlags(0, 0));

        assertEquals(1, game.chooseFlags(0, 1));
        assertTrue(game.getMap().getLocation(0).getHasFlag());
        assertTrue(game.getMap().getLocation(1).getHasFlag());
        assertEquals(game.getPlayers().get(0).getFlag(), game.getMap().getLocation(0).getFlag());
        assertEquals(game.getPlayers().get(1).getFlag(), game.getMap().getLocation(1).getFlag());
    }

    @Test
    public void testPlay() throws IOException {
        Game game = new Game();
        game.createMap(5, 50);
        Flag flag1 = new Flag(FlagColour.BLUE);
        Player player = new Player("Player 1", flag1);
        Bot bot = new Bot(0);
        player.addBot(bot);
        game.getMap().getLocation(bot.getLocation()).addBot(bot);
        game.getMap().getLocation(bot.getLocation()).setHasBot(true);
        game.getPlayers().add(player);
        game.getPlayers().get(0).getBotTurn().setMov(MovEnum.RANDOMPATH);
        int newLocation = game.play(player);

        assertTrue(game.getMap().getLocation(bot.getLocation()).getHasBot());

        assertEquals(bot, game.getMap().getLocation(newLocation).getBot());

        assertEquals(newLocation, bot.getLocation());
    }

    
}
