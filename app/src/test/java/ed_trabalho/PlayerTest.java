package ed_trabalho;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {
    /**
     * Teste do get o primeiro bot com turno true
     * Verifica se o bot retornado é o correto
     */
    @Test
    public void testGetBotTurn() {
        Flag flag = new Flag(FlagColour.BLUE);
        Player player = new Player("Player1", flag);
        Bot bot1 = new Bot(1);
        Bot bot2 = new Bot(2);

        player.addBot(bot1);
        player.addBot(bot2);

        bot1.setTurn(false);
        assertNotEquals(bot1, player.getBotTurn());

        bot1.setTurn(false);
        bot2.setTurn(true);
        assertEquals(bot2, player.getBotTurn());
    }

    /**
     * Teste de mudar o turno de todos os bots do jogador para true
     * Verifica se todos os bots estão com o turno true
     */
    @Test
    public void testSetTurnTrue() {
        Flag flag = new Flag(FlagColour.BLUE);
        Player player = new Player("Player1", flag);
        Bot bot1 = new Bot(1);
        Bot bot2 = new Bot(2);

        player.addBot(bot1);
        player.addBot(bot2);

        bot1.setTurn(false);
        bot2.setTurn(false);

        player.setTurnTrue();

        assertTrue(bot1.getTurn());
        assertTrue(bot2.getTurn());
    }
}