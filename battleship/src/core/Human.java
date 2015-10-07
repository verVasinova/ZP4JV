package core;

public class Human extends Player {
	private int countOfShots = 0;
	//tah hrace proti nepriteli
		public void makeShot(int r, int c, Player enemy) throws Exception{
			
			if (enemy.board.shot(r, c)) {
				status = "Zasáhli jste nepøítelovu loï!";
			} else {
				status = "Nic jsi netrefil!";
			}

		}
		
		//umisteni lode na souradnice se zadanym smerem
		public void placeShip(int i, int r, int c, boolean horizontal) {
			if (!board.isFree(r, c)) {
				if (hasShipToPlace()) {
					if (board.setShipOnBoard(ships[i], r, c, horizontal)) {
						setNextShip();
					}
		}}}
		
		public int getCountOfShots(){
			return countOfShots;
		}
		
		public void setCountOfShots(){
			countOfShots++;
		}
		
		public void setCountOfShots(int c){
			countOfShots = c;
		}
}
