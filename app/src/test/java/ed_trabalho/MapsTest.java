package ed_trabalho;

import org.junit.Test;
import static org.junit.Assert.*;

public class MapsTest {
    
    /**
     * Teste do método addFlag
     * Verifica se a bandeira foi adicionada corretamente
     */
    @Test
    public void testAddFlag() {
        Maps maps = new Maps();
        maps.addLocal(new Locations(0));
        Flag flag = new Flag(FlagColour.RED);

        maps.addFlag(0, flag);

        assertEquals(flag, maps.getLocations()[0].getFlag());
        assertTrue(maps.getLocations()[0].getHasFlag());
    }
    
    /**
     * Teste do método removeFlag
     * Verifica se a bandeira foi removida corretamente
     */
    @Test
    public void testRemoveFlag() {
        Maps maps = new Maps();
        maps.addLocal(new Locations(0));
        Flag flag = new Flag(FlagColour.RED);

        maps.addFlag(0, flag);

        assertEquals(flag, maps.getLocations()[0].getFlag());
        assertTrue(maps.getLocations()[0].getHasFlag());

        maps.removeFlag(0);

        assertNull(maps.getLocations()[0].getFlag());
        assertFalse(maps.getLocations()[0].getHasFlag());
    }
    
    /**
     * Teste do método getLocations
     * Verifica se o array de locais é retornado corretamente
     */
    @Test
    public void testGetLocations() {
        Maps maps = new Maps();
        Locations location1 = new Locations(1);
        Locations location2 = new Locations(2);

        maps.addLocal(location1);
        maps.addLocal(location2);

        Locations[] locations = maps.getLocations();

        assertEquals(2, locations.length);
        assertEquals(location1, locations[0]);
        assertEquals(location2, locations[1]);
    }

    
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