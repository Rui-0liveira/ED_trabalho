package ed_trabalho;

import org.junit.Test;
import static org.junit.Assert.*;

public class MapsTest {
    /**
     * Teste do importe de um mapa por um arquivo JSON
     * Verifica se o número de nós e arestas é correto
     */
    @Test
    public void testImportMap() {
        Maps maps = new Maps();
        maps.importMap("./mapTest.json");

        assertEquals(5, maps.getNetwork().size());
        assertEquals(10, maps.getNetwork().getAdjMatrix().length);
    }

    /**
     * Teste de adicionar um local ao mapa
     * Verifica se o local foi adicionado corretamente
     */
    @Test
    public void testAddLocation() {
        Maps maps = new Maps();
        Locations location = new Locations(1);

        maps.addLocal(location);

        assertTrue(maps.getNetwork().hasvertex(location));
    }


    /**
     * Teste de remover um local do mapa
     * Verifica se o local foi removido corretamente
     */
    @Test
    public void testRemoveLocal() {

        Maps maps = new Maps();
        Locations location = new Locations(1);

        maps.addLocal(location);
        assertTrue(maps.getNetwork().hasvertex(location));

        maps.removeLocal(location);
        assertFalse(maps.getNetwork().hasvertex(location));
    }
}