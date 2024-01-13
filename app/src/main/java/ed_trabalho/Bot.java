package ed_trabalho;

public class Bot {
    private boolean turn;
    private MovEnum mov;

    //Constructors
    public Bot(){
        turn = false;
        mov = null;
    }

    //Metodo de movimento do bot
    public void move(){

    }

    //Getters and Setters
    public void setTurn(boolean turn){
        this.turn = turn;
    }
    public boolean getTurn(){
        return turn;
    }
    public void setMov(MovEnum mov){
        this.mov = mov;
    }
    public MovEnum getMov(){
        return mov;
    }
}
