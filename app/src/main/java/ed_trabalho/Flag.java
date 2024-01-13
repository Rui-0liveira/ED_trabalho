package ed_trabalho;

public class Flag {
    private FlagColour colour;
    private Player player;

    //Constructors
    public Flag(FlagColour colour, Player player){
        this.colour = colour;
        this.player = player;
    }

    //Getters and Setters
    public FlagColour getColour(){
        return colour;
    }
    public void setColour(FlagColour colour){
        this.colour = colour;
    }
    public Player getPlayer(){
        return player;
    }
    public void setPlayer(Player player){
        this.player = player;
    }
}
