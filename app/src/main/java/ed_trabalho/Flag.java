package ed_trabalho;

public class Flag {
    private String colour;
    private int index;

    //Constructors
    public Flag(String colour){
        this.colour = colour;
        this.index = -1;
    }
    public Flag(){
        this.colour = null;
        this.index = -1;
    }

    //Getters and Setters
    public String  getColour(){
        return colour;
    }
    public void setColour(String  colour){
        this.colour = colour;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
