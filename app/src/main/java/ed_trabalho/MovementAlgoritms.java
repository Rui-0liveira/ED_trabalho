package ed_trabalho;

/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

import java.util.Iterator;
import java.util.Random;

import ClassImplementation.ArrayList;

/**
 * A classe Player representa um jogador no jogo.
 * Cada jogador tem um ID, um nome, um número de bots, uma lista de bots e uma
 * bandeira.
 */
public class MovementAlgoritms {

    /**
     * O método moveShortestPath move o bot para a localização da bandeira usando o
     * caminho mais curto.
     * Se o bot já está na localização da bandeira, ele permanece no lugar.
     * Se o próximo vértice no caminho mais curto tem um bot, o bot permanece no
     * lugar.
     * Se não há caminho para a bandeira, o método retorna -1.
     *
     * @param bot  O bot que está sendo movido.
     * @param map  O mapa no qual o bot está se movendo.
     * @param flag A localização da bandeira.
     * @return O índice do próximo vértice no caminho mais curto, ou o índice atual
     *         do bot se o próximo vértice tem um bot, ou -1 se não há caminho para
     *         a bandeira.
     */
    public static int moveShortestPath(Bot bot, Maps map, Locations flag) {
        Maps map1 = map;
        int index1 = bot.getLocation();
        int index2 = flag.getIndex();

        if (map1 == null) {
            return -1;
        }
        Iterator<Locations> iterator = map.getNetwork().iteratorShortestPath(index1, index2);
        if (iterator.hasNext()) {
            iterator.next();
            Locations currentLocation = iterator.next();

            if (currentLocation.getHasFlag() || !currentLocation.getHasBot()) {
                return currentLocation.getIndex();
            } else {

                map1.removeLocal(currentLocation);

                return moveShortestPath(bot, map, flag);
            }
        } else

        {
            return index1;
        }
    }

    /**
     * O método moveShortestPath move o bot para a localização da bandeira usando o
     * caminho mais curto.
     * Se o bot já está na localização da bandeira, ele permanece no lugar.
     * Se o próximo vértice no caminho mais curto tem um bot, o bot permanece no
     * lugar.
     * Se não há caminho para a bandeira, o método retorna -1.
     *
     * @param bot  O bot que está sendo movido.
     * @param map  O mapa no qual o bot está se movendo.
     * @param flag A localização da bandeira.
     * @return O índice do próximo vértice no caminho mais curto, ou o índice atual
     *         do bot se o próximo vértice tem um bot, ou -1 se não há caminho para
     *         a bandeira.
     */
    public static int moveRandomly(Bot bot, Maps map) {
        int currentVertex = bot.getLocation();
        ArrayList<Integer> availableVertices = findAvailableVertices(currentVertex, map);

        if (!availableVertices.isEmpty()) {
            int newVertex = getRandomVertex(availableVertices);
            if (newVertex == currentVertex) {
                return moveRandomly(bot, map);
            } else {
                return newVertex;
            }
        }
        return currentVertex;
    }

    /**
     * O método getGreedyMove move o bot para o vértice disponível mais próximo da
     * bandeira.
     * Se não houver vértices disponíveis, o método retorna o índice atual do bot.
     *
     * @param bot O bot que está sendo movido.
     * @param map O mapa no qual o bot está se movendo.
     * @return O índice do vértice mais próximo da bandeira, ou o índice atual do
     *         bot se não houver vértices disponíveis.
     */
    public static int getGreedyMove(Bot bot, Maps map) {
        int currentVertex = bot.getLocation();
        int lastVisited = bot.getLastLocation();
        ArrayList<Integer> availableVertices = findAvailableVertices(currentVertex, map, lastVisited);

        if (!availableVertices.isEmpty()) {
            int nextVertex = getClosestVertex(bot, availableVertices, map);
            bot.setLastLocation(currentVertex);
            return nextVertex;
        } else {
            return currentVertex;
        }
    }

    /**
     * O método getClosestVertex retorna o índice do vértice disponível que está
     * mais próximo da bandeira.
     *
     * @param bot               O bot que está sendo movido.
     * @param availableVertices Uma lista de índices de vértices disponíveis.
     * @param map               O mapa no qual o bot está se movendo.
     * @return O índice do vértice mais próximo da bandeira.
     */
    private static int getClosestVertex(Bot bot, ArrayList<Integer> availableVertices, Maps map) {
        int currentVertex = bot.getLocation();
        int closestVertex = -1;
        double minDistance = Double.MAX_VALUE;

        for (int neighbor : availableVertices) {
            if (map.getNetwork().getAdjMatrix()[currentVertex][neighbor] < minDistance) {
                minDistance = map.getNetwork().getAdjMatrix()[currentVertex][neighbor];
                closestVertex = neighbor;
            }
        }

        return closestVertex;
    }

    /**
     * O método getDumbMove move o bot para o vértice disponível mais distante.
     * Se não houver vértices disponíveis, o método retorna o índice atual do bot.
     *
     * @param bot O bot que está sendo movido.
     * @param map O mapa no qual o bot está se movendo.
     * @return O índice do vértice mais distante, ou o índice atual do bot se não
     *         houver vértices disponíveis.
     */
    public static int getDumbMove(Bot bot, Maps map) {
        int currentVertex = bot.getLocation();
        int lastVisited = bot.getLastLocation();
        ArrayList<Integer> availableVertices = findAvailableVertices(currentVertex, map, lastVisited);

        if (!availableVertices.isEmpty()) {
            int nextVertex = getLongestVertex(bot, availableVertices, map);
            bot.setLastLocation(currentVertex);
            return nextVertex;
        } else {
            return currentVertex;
        }
    }

    /**
     * O método getLongestVertex retorna o índice do vértice disponível que está
     * mais distante.
     *
     * @param bot               O bot que está sendo movido.
     * @param availableVertices Uma lista de índices de vértices disponíveis.
     * @param map               O mapa no qual o bot está se movendo.
     * @return O índice do vértice mais distante.
     */
    private static int getLongestVertex(Bot bot, ArrayList<Integer> availableVertices, Maps map) {
        int currentVertex = bot.getLocation();
        int longestVertex = -1;
        double maxDistance = Double.MIN_VALUE;

        for (int neighbor : availableVertices) {
            if (map.getNetwork().getAdjMatrix()[currentVertex][neighbor] > maxDistance) {
                maxDistance = map.getNetwork().getAdjMatrix()[currentVertex][neighbor];
                longestVertex = neighbor;
            }
        }

        return longestVertex;
    }

    /**
     * O método findAvailableVertices retorna uma lista de índices de vértices
     * disponíveis.
     * Um vértice está disponível se não tem um bot e há uma aresta do vértice atual
     * do bot para ele.
     *
     * @param currentVertex O índice do vértice atual do bot.
     * @param map           O mapa no qual o bot está se movendo.
     * @return Uma lista de índices de vértices disponíveis.
     */
    private static ArrayList<Integer> findAvailableVertices(int currentVertex, Maps map, int lastLocation) {
        ArrayList<Integer> availableVertices = new ArrayList<>();
        for (int i = 0; i < map.getNetwork().getAdjMatrix().length; i++) {
            if (map.getNetwork().getAdjMatrix()[currentVertex][i] > 0
                    && map.getNetwork().getAdjMatrix()[currentVertex][i] < 16) {

                if (!map.getLocations()[i].getHasBot() && map.getLocations()[i].getIndex() != lastLocation) {
                    availableVertices.add(i);
                }
            }
        }
        return availableVertices;
    }

    /**
     * O método getRandomVertex retorna um índice de vértice aleatório da lista de
     * vértices fornecida.
     *
     * @param vertices Uma lista de índices de vértices.
     * @return Um índice de vértice aleatório.
     */
    private static int getRandomVertex(ArrayList<Integer> vertices) {
        Random random = new Random();
        return vertices.get(random.nextInt(vertices.size()));
    }

    private static ArrayList<Integer> findAvailableVertices(int currentVertex, Maps map) {
        ArrayList<Integer> availableVertices = new ArrayList<>();
        for (int i = 0; i < map.getNetwork().getAdjMatrix().length; i++) {
            if (map.getNetwork().getAdjMatrix()[currentVertex][i] > 0
                    && map.getNetwork().getAdjMatrix()[currentVertex][i] < 16) {

                if (!map.getLocations()[i].getHasBot()) {
                    availableVertices.add(i);
                }
            }
        }
        return availableVertices;
    }

}
