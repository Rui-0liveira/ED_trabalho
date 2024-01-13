package ed_trabalho;
public class Main {
    public static void main(String[] args) {
        Map map = new Map();

        Locations local1 = new Locations();
        Locations local2 = new Locations();
        Locations local3 = new Locations();
        System.out.println(local1.toString());
        /*map.addLocal(local1);
        map.addLocal(local2);
        map.addLocal(local3);

        map.addEdge(local1.getIndex(), local2.getIndex(), 2);
        map.addEdge(local1.getIndex(), local3.getIndex(), 2);
        map.addEdge(local2.getIndex(), local3.getIndex(), 2);

        System.out.println(map.toString());*/
    }
}