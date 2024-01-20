package ed_trabalho;

import java.util.Random;
import ClassImplementation.ArrayList;


public class MovementAlgoritms {

    public static void shortestPath(Bot bot, Maps map){}

   
    public static int moveRandomly(Bot bot, Maps map) {
        int currentVertex = bot.getLocation();

        ArrayList<Integer> availableVertices = findAvailableVertices(currentVertex, map);

        if (!availableVertices.isEmpty()) {
            int newVertex = getRandomVertex(availableVertices);
            return newVertex;
        }
        return currentVertex;
    }
     
    public static int getGreedyMove(Bot bot, Maps map) {
        int currentVertex = bot.getLocation();
        ArrayList<Integer> availableVertices = findAvailableVertices(currentVertex, map);

        if (!availableVertices.isEmpty()) {
            return getClosestVertex(bot, availableVertices, map.getNetwork().getAdjMatrix());
        } else {
            return currentVertex;
        }
    }

    private static int getClosestVertex(Bot bot, ArrayList<Integer> availableVertices, double[][] adjacencyMatrix) {
        int currentVertex = bot.getLocation();
        int closestVertex = -1;
        double minDistance = Double.MAX_VALUE;

        for (int neighbor : availableVertices) {
            if (adjacencyMatrix[currentVertex][neighbor] < minDistance) {
                minDistance = adjacencyMatrix[currentVertex][neighbor];
                closestVertex = neighbor;
            }
        }

        return closestVertex;
    }



    public static int getDumbMove(Bot bot, Maps map) {
        int currentVertex = bot.getLocation();
        ArrayList<Integer> availableVertices = findAvailableVertices(currentVertex, map);

        if (!availableVertices.isEmpty()) {
            return getLongestVertex(bot, availableVertices, map.getNetwork().getAdjMatrix());
        } else {
            return currentVertex;
        }
    }

    private static int getLongestVertex(Bot bot, ArrayList<Integer> availableVertices, double[][] adjacencyMatrix) {
        int currentVertex = bot.getLocation();
        int longestVertex = -1;
        double maxDistance = Double.MIN_VALUE;

        for (int neighbor : availableVertices) {
            if (adjacencyMatrix[currentVertex][neighbor] > maxDistance) {
                maxDistance = adjacencyMatrix[currentVertex][neighbor];
                longestVertex = neighbor;
            }
        }

        return longestVertex;
    }



    private static ArrayList<Integer> findAvailableVertices(int currentVertex, Maps map) {
        ArrayList<Integer> availableVertices = new ArrayList<>();
        for (int i = 0; i < map.getNetwork().getAdjMatrix().length; i++) {
            if (map.getNetwork().getAdjMatrix()[currentVertex][i] > 0 && map.getNetwork().getAdjMatrix()[currentVertex][i] < 16) { 

                if (!isVertexOccupied(map.getLocations()[i])) {
                    availableVertices.add(i);
                }
            }
        }
        return availableVertices;
    }

    private static int getRandomVertex(ArrayList<Integer> vertices) {
        Random random = new Random();
        return vertices.get(random.nextInt(vertices.size()));
    }

    private static boolean isVertexOccupied(Locations location) {
        if(location.getBot() != null)
            return true;
        else
            return false;
    }
    
}