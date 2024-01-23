package ed_trabalho;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

/**
 * A classe Bot representa um bot no jogo.
 * Cada bot tem um índice, um turno, um movimento e uma localização.
 */
public class Bot {
    private int index;
    private boolean turn;
    private MovEnum movEnum;
    private MovementAlgoritms mov;
    private int location;
    private int lastLocation;

    /**
     * Construtor para a classe Bot com um índice específico.
     * Inicializa o bot com o turno ativado e sem movimento.
     *
     * @param index O índice do bot.
     */
    public Bot(int index){
        this.index = index;
        this.turn = true;
        this.mov = null;
    }

    /**
     * Construtor para a classe Bot com uma localização e um índice específicos.
     * Inicializa o bot com o turno ativado e sem movimento.
     *
     * @param location A localização do bot.
     * @param index O índice do bot.
     */
    public Bot(int location, int index){
        this.index = index;
        this.turn = true;
        this.mov = null;
        this.location = location;
        this.lastLocation = -1;
    }

    /**
     * Retorna o índice do bot.
     *
     * @return O índice do bot.
     */
    public int getIndex(){
        return index;
    }

    /**
     * Define o índice do bot.
     *
     * @param index O índice a ser definido para o bot.
     */
    public void setIndex(int index){
        this.index = index;
    }

    /**
     * Define o turno do bot.
     *
     * @param turn O turno a ser definido para o bot.
     */
    public void setTurn(boolean turn){
        this.turn = turn;
    }

    /**
     * Retorna o turno do bot.
     *
     * @return O turno do bot.
     */
    public boolean getTurn(){
        return turn;
    }

    /**
     * Define o movimento do bot.
     *
     * @param mov O movimento a ser definido para o bot.
     */
    public void setMov(MovEnum mov){
        this.movEnum = mov;
    }

    /**
     * Retorna o movimento do bot.
     *
     * @return O movimento do bot.
     */
    public MovEnum getMovEnum(){
        return movEnum;
    }

    /**
     * Define o algoritmo de movimento do bot.
     *
     * @param mov O algoritmo de movimento a ser definido para o bot.
     */
    public void setMov(MovementAlgoritms mov){
        this.mov = mov;
    }

    /**
     * Retorna o algoritmo de movimento do bot.
     *
     * @return O algoritmo de movimento do bot.
     */
    public MovementAlgoritms getMov(){
        return mov;
    }

    /**
     * Retorna a localização do bot.
     *
     * @return A localização do bot.
     */
    public int getLocation(){
        return location;
    }

    /**
     * Define a localização do bot.
     *
     * @param location A localização a ser definida para o bot.
     */
    public void setLocation(int location){
        this.location = location;
    }

    /**
     * Retorna a última localização do bot.
     *
     * @return A última localização do bot.
     */
    public int getLastLocation(){
        return lastLocation;
    }

    /**
     * Define a última localização do bot.
     *
     * @param lastLocation A última localização a ser definida para o bot.
     */
    public void setLastLocation(int lastLocation){
        this.lastLocation = lastLocation;
    }
}
