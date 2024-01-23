package ed_trabalho;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

/**
 * A classe Flag representa uma bandeira no jogo.
 * Cada bandeira tem uma cor e um índice.
 */
public class Flag {
    private String colour;
    private int index;

    /**
     * Construtor que cria uma nova bandeira com a cor especificada.
     *
     * @param colour A cor da bandeira.
     */
    public Flag(String colour){
        this.colour = colour;
        this.index = -1;
    }

    /**
     * Construtor que cria uma nova bandeira sem cor.
     */
    public Flag(){
        this.colour = null;
        this.index = -1;
    }

    /**
     * Retorna a cor desta bandeira.
     *
     * @return A cor desta bandeira.
     */
    public String  getColour(){
        return colour;
    }

    /**
     * Define a cor desta bandeira.
     *
     * @param colour A cor a ser definida.
     */
    public void setColour(String  colour){
        this.colour = colour;
    }

    /**
     * Retorna o índice desta bandeira.
     *
     * @return O índice desta bandeira.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Define o índice desta bandeira.
     *
     * @param index O índice a ser definido.
     */
    public void setIndex(int index) {
        this.index = index;
    }

}
