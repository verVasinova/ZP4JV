package core;
import java.io.Serializable;
import java.util.ArrayList;

import boats.Boat;

public class Cell implements Serializable{

    private final int x;
    private final int y;
    private Boat boat;
    private boolean shot;


    public Cell(final int positionX, final int positionY) {
            this.x = positionX;
            this.y = positionY;
            this.shot = false;
    }
    
    public final int getX(){
            return x;
    }
    
    public final int getY(){
            return y;
    }

    public final void setBoat(final Boat boat) {
            this.boat = boat;
    }

    public final void hitCell() {
            shot = true;
            if(boat != null){
                    boat.hitBlock(x, y);
            }
    }
    
    public boolean hasBoat(){
            return (boat != null);
    }


    public boolean isShot() {
            return shot;
    }
}