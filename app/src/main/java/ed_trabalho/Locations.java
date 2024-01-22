package ed_trabalho;

import ClassImplementation.LinkedList;


public class Locations {
    private boolean hasFlag;
    private boolean hasBot;
    private Flag flag;
    private LinkedList<Bot> bot;
    private int index;
    private static int contador;

    public Locations(){
        this.hasFlag = false;
        this.hasBot = false;
        this.flag = null;
        this.bot = new LinkedList<Bot>();
        this.index = contador;
        contador++;
    }
    public Locations(int index){
        this.hasFlag = false;
        this.hasBot = false;
        this.flag = null;
        this.bot = new LinkedList<Bot>();
        this.index = index;
        contador++;
    }

    public boolean isLocationFree(){
        if(hasBot == false){
            return true;
        }
        else{
            return false;
        }
    }

    //Getters and Setters
    public boolean getHasFlag(){
        return hasFlag;
    }
    
    public void setHasFlag(boolean hasFlag){
        this.hasFlag = hasFlag;
    }
    
    public boolean getHasBot(){
        return hasBot;
    }
    
    public void setHasBot(boolean hasBot){
        this.hasBot = hasBot;
    }
    
    public Flag getFlag(){
        return flag;
    }
    
    public void setFlag(Flag flag){
        this.flag = flag;
    }

    public int getIndex() {
        return index;
    }
    //funçao que devolve a lista de bots
    public LinkedList<Bot> getBots(){
        return bot;
    }

    //funcao que devolve o bot que esta na localizaçao
    public Bot getBot(){
        if(bot.size() == 0){
            return null;
        }
        return bot.get(0);
    }


    //funçao add bot
    public void addBot(Bot bot){
        this.bot.add(bot);
        this.hasBot = true;
    }

    //funçao remove bot
    public void removeBot(Bot bot){
        this.bot.remove(bot);
        if(this.bot.size() == 0){
            this.hasBot = false;
        }
    }
}
