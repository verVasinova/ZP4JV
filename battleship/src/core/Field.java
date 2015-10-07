package core;

import boats.Ship;

public class Field{

	private int row;
	private int column;
	private Ship ship;
	private boolean shot;

	public Field(int r, int c) {
		row = r;
		column = c;
		shot = false;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Ship getShip() {
		return ship;
	}

	// zasahne policko, pokud policko obsahuje cast lode, tak zasahne i lod
	public void hitField() {
		shot = true;
		if (ship != null) {
			
			ship.hitPartOfShip(row, column);
		}
	}
	
	//metoda vraci, zda na policku lezi cast lod
	public boolean hasBoat() {
		return (ship != null);
	}

	/* vrací, zda bylo policko zasazeno */
	public boolean isShot() {
		return shot;
	}
}