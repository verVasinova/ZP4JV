package boats;

import java.io.Serializable;

public class Block implements Serializable{
        
        private boolean shot;
        
        public Block(){
                this.shot = false;
        }
        
        public void hit(){
                this.shot = true;
        }
        
        public boolean getCondition(){
                return shot;
        }
}