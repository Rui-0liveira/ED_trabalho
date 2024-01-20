package ed_trabalho;

public class Bot{
    private int index;
    private boolean turn;
    private MovEnum movEnum;
    private Player player;
    private MovementAlgoritms mov;
    private int location;
    //Constructors
    public Bot(Player player, int index){
        this.index = index;
        this.turn = true;
        this.mov = null;
        this.player = player;
    }

    public Bot(Player player, int location, int index){
        this.index = index;
        this.turn = true;
        this.mov = null;
        this.player = player;
        this.location = location;
    }


    //Metodo de movimento do bot
    /* 
    public void move(){
        if(movEnum == MovEnum.SHORTESTPATH){
            mov.shortestPath();
        }
        else if(movEnum == MovEnum.RANDOMPATH){
            mov.randomPath();
        }
        else if(movEnum == MovEnum.GREEDYPATH){
            mov.greedyPath();
        }
    }
    */

    //Getters and Setters
    public int getIndex(){
        return index;
    }
    public void setIndex(int index){
        this.index = index;
    }

    public void setTurn(boolean turn){
        this.turn = turn;
    }

    public boolean getTurn(){
        return turn;
    }

    public void setMov(MovEnum mov){
        this.movEnum = mov;
    }

    public MovEnum getMovEnum(){
        return movEnum;
    }

    public Player getPlayer(){
        return player;
    }

    public void setMov(MovementAlgoritms mov){
        this.mov = mov;
    }

    public MovementAlgoritms getMov(){
        return mov;
    }

    public int getLocation(){
        return location;
    }

    public void setLocation(int location){
        this.location = location;
    }
     
}
