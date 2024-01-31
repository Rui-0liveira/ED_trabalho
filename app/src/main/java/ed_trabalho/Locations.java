package ed_trabalho;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

import ClassImplementation.LinkedList;

/**
 * A classe Locations representa uma localização em um mapa.
 * Cada localização tem um índice e pode conter vários bots.
 */
public class Locations {
    private boolean hasFlag;
    private boolean hasBot;
    private Flag flag;
    private LinkedList<Bot> bot;
    private int index;
    private static int contador;


    /**
     * Construtor padrão para a classe Locations.
     * Inicializa o local sem bandeira e sem bot.
     */
    public Locations(){
        this.hasFlag = false;
        this.hasBot = false;
        this.flag = null;
        this.bot = new LinkedList<Bot>();
        this.index = contador;
        contador++;
    }

    /**
     * Construtor para a classe Locations com um índice específico.
     * Inicializa o local sem bandeira e sem bot.
     *
     * @param index O índice do local.
     */
    public Locations(int index){
        this.hasFlag = false;
        this.hasBot = false;
        this.flag = null;
        this.bot = new LinkedList<Bot>();
        this.index = index;
        contador++;
    }

    /**
     * Verifica se o local está livre (sem bot).
     *
     * @return true se o local estiver livre, false caso contrário.
     */
    public boolean isLocationFree(){
        return !hasBot;
    }

    /**
     * Retorna se o local tem uma bandeira.
     *
     * @return true se o local tem uma bandeira, false caso contrário.
     */
    public boolean getHasFlag(){
        return hasFlag;
    }

    /**
     * Define se o local tem uma bandeira.
     *
     * @param hasFlag true se o local tem uma bandeira, false caso contrário.
     */
    public void setHasFlag(boolean hasFlag){
        this.hasFlag = hasFlag;
    }

    /**
     * Retorna se o local tem um bot.
     *
     * @return true se o local tem um bot, false caso contrário.
     */
    public boolean getHasBot(){
        return hasBot;
    }

    /**
     * Define se o local tem um bot.
     *
     * @param hasBot true se o local tem um bot, false caso contrário.
     */
    public void setHasBot(boolean hasBot){
        this.hasBot = hasBot;
    }

    /**
     * Retorna a bandeira do local.
     *
     * @return A bandeira do local.
     */
    public Flag getFlag(){
        return flag;
    }

    /**
     * Define a bandeira do local.
     *
     * @param flag A bandeira a ser definida para o local.
     */
    public void setFlag(Flag flag){
        this.flag = flag;
    }

    /**
     * Retorna o índice do local.
     *
     * @return O índice do local.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Retorna a lista de bots nesta localização.
     *
     * @return A lista de bots nesta localização.
     */
    public LinkedList<Bot> getBots(){
        return bot;
    }

    /**
     * Retorna o primeiro bot nesta localização, ou null se não houver bots.
     *
     * @return O primeiro bot nesta localização, ou null se não houver bots.
     */
    public Bot getBot(){
        if(bot.size() == 0){
            return null;
        }
        return bot.get(0);
    }

    /**
     * Adiciona um bot a esta localização.
     *
     * @param bot O bot a ser adicionado.
     */
    public void addBot(Bot bot){
        this.bot.add(bot);
        this.hasBot = true;
    }

    /**
     * Remove um bot desta localização.
     * Se não houver mais bots nesta localização, a propriedade hasBot é definida como false.
     *
     * @param bot O bot a ser removido.
     */
    public void removeBot(Bot bot){
        this.bot.remove(bot);
        if(this.bot.size() == 0){
            this.hasBot = false;
        }
    }
}
