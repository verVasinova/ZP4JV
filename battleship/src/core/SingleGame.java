package core;

public class SingleGame implements Game {

	private Human player1;
	private Player player2;

	public SingleGame() {
		player1 = new Human();

		while (player1.hasShipToPlace()) {
			player1.placeShipRandom();
		}
		
		player2 = new AI();
		
		while (player2.hasShipToPlace()) {
			player2.placeShipRandom();
		}
		
	}

	@Override
	public boolean insertBoat(int i, boolean d, int r, int c) {
		
		if (player1.hasShipToPlace()) {
			player1.placeShip(i, r, c, d);
			return true;
		}
		return false;

	}

	@Override
	public boolean play(int row, int column) throws Exception {

		if (!player1.isWinner() && !player2.isWinner()) {

			if (player2.isValidMove(row, column) ) {
				player1.makeShot(row, column, player2);
				player1.setCountOfShots();
				player2.makeShot(-1, -1, player1);
				return true;
			}
			
			
			return false;
		}
		System.out.println("výhra");
		return false;
	}
	
	public void playAI() throws Exception{
		player2.makeShot(-1, -1, player1);
	}

	@Override
	public Player getME() {
		return player1;
	}

	@Override
	public Player getEnemy() {
		return player2;
	}

	@Override
	public void setME(Player p) {
		player1 = (Human) p;
		
	}

	@Override
	public void setEnemy(Player p) {
		player2 = (AI)p;
		
	}

}
