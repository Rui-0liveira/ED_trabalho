package ed_trabalho;

public class Flag {
    private FlagColour colour;
    private int index;

<<<<<<< Updated upstream
    //Constructors
    public Flag(String colour){
=======
    /**
     * Construtor que cria uma nova bandeira com a cor especificada.
     *
     * @param colour A cor da bandeira.
     */
    public Flag(FlagColour colour){
>>>>>>> Stashed changes
        this.colour = colour;
        this.index = -1;
    }
    public Flag(){
        this.colour = null;
        this.index = -1;
    }

<<<<<<< Updated upstream
    //Getters and Setters
    public String  getColour(){
        return colour;
    }
    public void setColour(String  colour){
=======
    /**
     * Retorna a cor desta bandeira.
     *
     * @return A cor desta bandeira.
     */
    public FlagColour getColour(){
        return colour;
    }

    /**
     * Define a cor desta bandeira.
     *
     * @param colour A cor a ser definida.
     */
    public void setColour(FlagColour colour){
>>>>>>> Stashed changes
        this.colour = colour;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
