package core;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import boats.Ship;
import boats.ShipTypes;

public abstract class Player {
	protected Board board;
	protected Ship[] ships;
	protected int currentShip;
	public final static int NUMBER_OF_BOATS = 5;
	protected String name;
	protected String status = "";
	protected int countOfShots = 0;
	public Player() {
		board = new Board();
		
		ships = new Ship[NUMBER_OF_BOATS];
		ships[0] = new Ship(ShipTypes.AIRCRAFT);
		ships[1] = new Ship(ShipTypes.BATTLESHIP);
		ships[2] = new Ship(ShipTypes.DESTROYER);
		ships[3] = new Ship(ShipTypes.PATROL);
		ships[4] = new Ship(ShipTypes.SUBMARINE);
		currentShip = 0;

	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public Board getBoard() {
		return board;
	}

	public Board setBoard(Board b) {
		return board;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean hasShipToPlace() {
		return (currentShip < NUMBER_OF_BOATS);
	}

	public void setNextShip() {
		this.currentShip++;
	}

	public Ship getCurrentShip() {
		return ships[currentShip];
	}

	public Ship[] getShips() {
		return ships;
	}

	public Ship getShip(int i) {
		return ships[i];
	}

	public abstract void placeShip(int i, int x, int y, boolean b);

	public abstract void makeShot(int x, int y, Player c) throws Exception;

	// kontola vyhry
	public boolean isWinner() {
		for (Ship s : ships) {
			if (!s.isSunk()) {
				return false;
			}
		}
		return true;
	}

	public boolean isValidMove(int r, int c) {
			return !board.getField(r, c).isShot();
	}
	
	public boolean isValidPosition(int r, int c) {
		if(r >= 0 && r < board.BOARD_SIZE && c >= board.BOARD_SIZE && c < board.BOARD_SIZE)
			return true;
		
		return false;
	}

	public void placeShipRandom() {
		Random random = new Random();

		if (board.setShipOnBoard(ships[currentShip], random.nextInt(9),
				random.nextInt(9), random.nextBoolean())) {
			setNextShip();
		}

	}

	public int deleteShip(Ship ship){
		int index = 0;
		for(int i = 0; i < NUMBER_OF_BOATS;i++){
			if(ships[i].getType().equals(ship.getType()))
				index = i;
		}
		
		for (int row = 0; row < board.BOARD_SIZE; row++) {
			for (int column = 0; column < board.BOARD_SIZE; column++) {
				if(board.getField(row, column).getShip() == ship)
					board.getField(row, column).setShip(null);
			}
		}
		currentShip--;
		return index;
	}
	
	public int getCountOfShots(){
		return countOfShots;
	}
	
	public void setCountOfShots(int c){
		countOfShots = c;
	}
	
	

}
