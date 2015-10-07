package core;

public interface Game {
    
    public abstract boolean insertBoat(int i, boolean direction, int row, int column);
    
    public abstract boolean play(int row, int column) throws Exception;
    
    public abstract void playAI() throws Exception;
    
    public abstract Player getME();
    
    public abstract Player getEnemy();  
    
    public abstract  void setME(Player p);
    
    public abstract void setEnemy(Player p);   

}
