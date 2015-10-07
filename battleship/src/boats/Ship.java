package boats;


public class Ship {

	private int row;
	private int column;
	private ShipTypes type;
	private SegmentOfShip[] ship;

	private boolean horizontal;

	public Ship(ShipTypes type) {
		this.type = type;

		ship = new SegmentOfShip[type.getSize()];
		for (int i = 0; i < ship.length; i++) {
			ship[i] = new SegmentOfShip();
		}
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

	public ShipTypes getType() {
		return type;
	}

	public void setType(ShipTypes type) {
		this.type = type;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public boolean isSunk() {
		for (SegmentOfShip p : ship) {
			if (!p.getState()) {
				return false;
			}
		}
		return true;
	}
	
	public static ShipTypes getShipType(int size){
		ShipTypes[] ships = {ShipTypes.AIRCRAFT,ShipTypes.BATTLESHIP,
							ShipTypes.DESTROYER, ShipTypes.PATROL, ShipTypes.SUBMARINE};
		for(ShipTypes s : ships){
			if(size == s.getSize()){
				return s;
			}
		}
		
		return null;
	}
	
	public void hitPartOfShip(final int x, final int y){
		if(horizontal){
			ship[y - column].hit();
		}
		else{
			ship[x - row].hit();
		}
	}
	
	private static class SegmentOfShip {
		private boolean shot;
		
		public SegmentOfShip(){
			shot = false;
		}
		
		public void hit(){
			this.shot = true;
		}
		
		public boolean getState(){
			return shot;
		}

}
}