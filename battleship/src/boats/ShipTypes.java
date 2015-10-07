package boats;

public enum ShipTypes {
	AIRCRAFT("Aircraft", 5),
	BATTLESHIP("Battleship", 4),
	SUBMARINE("Submarine", 3),
	DESTROYER("Destroyer", 3),
	PATROL("Patrol", 2);
	
	private String symbol;
	private int size;
	
	private ShipTypes(String symbol, int size){
		this.symbol = symbol;
		this.size = size;
	}

	public String getSymbol() {
		return symbol;
	}

	public int getSize() {
		return size;
	}
	
	
	


}
