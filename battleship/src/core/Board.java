package core;

import boats.Ship;
public class Board  {

	public final int BOARD_SIZE = 10;
	private Field[][] board;

	//inicializace desky
	public Board() {
		board = new Field[BOARD_SIZE][BOARD_SIZE];

		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				board[row][column] = new Field(row, column);
			}
		}
	}
	
	
	//kotroluje, zda na policku neni lod
	public boolean isFree(int x, int y) {
		return board[x][y].hasBoat();
	}
	
	//vrati policko desky
	public Field getField(int r, int c) {
		return board[r][c];
	}
	
	//zasahne policko na desce, pokud bylo zasazeno policko, na kterem je lod, vraci true,
	//pokud je mimo vraci false
	public boolean shot(int r, int c) throws Exception{
		if (!board[r][c].isShot() && board[r][c].hasBoat()) {
			board[r][c].hitField();
			return true;
		}

		else if (!board[r][c].isShot() && !board[r][c].hasBoat()) {
			board[r][c].hitField();
			
		}
		//throw new Exception("Zde už jste støílel!");
		return false;
	}
	
	

	//kontroloje, zda muze byt lod umistena, zda se lode neprekryvaji nebo nedotykaji
	public boolean validPosition(Ship ship, final int r, final int c, boolean horizontal) {
		int size = ship.getType().getSize();

		if (horizontal) {
			
			if (r + size <= BOARD_SIZE) {
				for (int i = r; i < r + size; i++) {
					if (board[i][c].hasBoat()) {
						return false;
					}
					
					if (c != 0 && board[i][c - 1].hasBoat()) {
						return false;
					}
					
					if (c != 9 && board[i][c + 1].hasBoat()) {
						return false;
					}
			}
				if ( r != 0 && board[r - 1][c].hasBoat()) {
					return false;
				}
				
				if ((r + size + 1) < BOARD_SIZE && board[r + (size + 1)][c].hasBoat()) {
					return false;
				}
				return true;	} 
			else {
				return false;
			}
		} 
		else {
			if (c + size <= BOARD_SIZE) {
				for (int i = c; i < c + size; i++) {
					if (board[r][i].hasBoat()) {
						return false;
					}
					
					if (r != 0 && board[r - 1][i].hasBoat() ) {
						return false;
					}
					
					if (r != 9 && board[r + 1][i].hasBoat()) {
						return false;
					}
	
				}
				
				if ( c != 0 && board[r][c - 1].hasBoat()) {
					return false;
				}
				if ( (c + size + 1) < BOARD_SIZE && board[r][c + size].hasBoat()) {
					return false;
				}

				

				return true;	
			} else {
				return false;
			}
		}
	}

	//zkontroluje umisteni lode, pokud souradnice vyhovuji, prida lod do desky a vrati true,
	//jinak vrati false
	public boolean setShipOnBoard(Ship ship, int r, int c, boolean horizontal) {
		if (validPosition(ship, r, c, horizontal)) {
			ship.setRow(r);
			ship.setColumn(c);
			
			for (int i = 0; i < ship.getType().getSize(); i++) {
				if (horizontal) {
					board[r + i][c ].setShip(ship);
				} else {
					board[r][c + i].setShip(ship);
				}
			}
			return true;
		}
		return false;
	}
	
	public void deleteShip(Ship ship){
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				getField(row, column).setShip(null);
			}
		}
	}
}
