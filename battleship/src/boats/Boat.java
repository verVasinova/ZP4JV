package boats;

import java.io.Serializable;
import java.util.ArrayList;


public abstract class Boat implements Serializable{

        private int size;
        private String name;
        private Block[] blocks;
        private int positionX;
        private int positionY;
        private boolean direction;


        public Boat(int mySize, String myName) {
                size = mySize;
                name = myName;
                blocks = new Block[size];
                for (int i = 0; i < blocks.length; i++) {
                        blocks[i] = new Block();
                }

        }
        
        public int getX(){
                return positionX;
        }
        
        public int getY(){
                return positionY;
        }
        
        public boolean getDirection(){
                return direction;
        }
        

        
 
        
        public int getSize() {
                return size;
        }

        public String getName() {
                return name;
        }

        public boolean isSink() {
                for (Block block : blocks) {
                        if (!block.getCondition()) {
                                return false;
                        }
                }
                return true;
        }

        public void hitBlock(int x, int y) {
                if (direction) {
                        blocks[x - positionX].hit();

                } else {
                        blocks[y - positionY].hit();

                }
        }

        public void setPosition(int positionX, int positionY, boolean direction) {
                this.positionX = positionX;
                this.positionY = positionY;
                this.direction = direction;

        }
}