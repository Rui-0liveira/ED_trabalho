package ed_trabalho;

public class Bot{
    private int index;
    private boolean turn;
    private MovEnum movEnum;
    private MovementAlgoritms mov;
    private int location;
    //Constructors
    public Bot(int index){
        this.index = index;
        this.turn = true;
        this.mov = null;
    }

    public Bot(int location, int index){
        this.index = index;
        this.turn = true;
        this.mov = null;
        this.location = location;
    }

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
