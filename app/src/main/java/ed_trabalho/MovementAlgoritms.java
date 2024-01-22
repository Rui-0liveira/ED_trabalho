package ed_trabalho;

import java.util.Iterator;
import java.util.Random;
import ClassImplementation.ArrayList;


public class MovementAlgoritms {

    
    public static int shortestPath(Bot bot, Maps map, Locations flag){
        int startIndex = bot.getLocation();
        int targetIndex = flag.getIndex();
        Iterator<Locations> iterator = map.getNetwork().iteratorShortestPath(startIndex, targetIndex);

        while (iterator.hasNext()) {
            Locations nextIndex = iterator.next();

            if (!nextIndex.getHasBot()) {
                return nextIndex.getIndex();
            } 
        }   
        return startIndex;
    }
        
    

   
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
            return getClosestVertex(bot, availableVertices, map);
        } else {
            return currentVertex;
        }
    }

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



    public static int getDumbMove(Bot bot, Maps map) {
        int currentVertex = bot.getLocation();
        ArrayList<Integer> availableVertices = findAvailableVertices(currentVertex, map);

        if (!availableVertices.isEmpty()) {
            return getLongestVertex(bot, availableVertices, map);
        } else {
            return currentVertex;
        }
    }

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



    private static ArrayList<Integer> findAvailableVertices(int currentVertex, Maps map) {
        ArrayList<Integer> availableVertices = new ArrayList<>();
        for (int i = 0; i < map.getNetwork().getAdjMatrix().length; i++) {
            if (map.getNetwork().getAdjMatrix()[currentVertex][i] > 0 && map.getNetwork().getAdjMatrix()[currentVertex][i] < 16) { 

                if (!map.getLocations()[i].getHasBot()) {
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

}