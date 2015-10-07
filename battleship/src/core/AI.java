package core;


import java.util.ArrayList;
import java.util.Random;

public class AI extends Player {

	private ArrayList<Field> attackingBoat;
    private final ArrayList<Field> possibleShot;
    private boolean state = true;
    
    private Random random;
	
	
	public AI(){
		super();
		
		state = false;
        random = new Random();
        possibleShot = new ArrayList<Field>();
        attackingBoat = new ArrayList<Field>();

        for (int i = 0; i < board.BOARD_SIZE; i++) {
                for (int j = 0; j < board.BOARD_SIZE; j++) {
                        possibleShot.add(new Field(i, j));
                }
        }

		name = "Poèítaè";

	}
	
	//umisti nahodne lod do desky
	public void placeShip(int o, int x, int y, boolean a) {
		Random random = new Random();

		if (board.setShipOnBoard(ships[currentShip], random.nextInt(9),
				random.nextInt(9), random.nextBoolean())) {
			setNextShip();
		}

	}
	
	
	
	private Field getNeighbour() {
		Field f;
		//soused vlevo
        f = new Field(attackingBoat.get(0).getRow(), attackingBoat.get(0).getColumn() - 1);
        if (isShotable(f) != -1 && isLegalMove(f)) {
                return f;
        }
        
        //soused vpravo
        f = new Field(attackingBoat.get(0).getRow(), attackingBoat.get(0).getColumn() + 1);
        if (isShotable(f) != -1 && isLegalMove(f)) {
                return f;
        }
        
        //soused nahore
        f = new Field(attackingBoat.get(0).getRow() + 1, attackingBoat.get(0).getColumn());
        if (isShotable(f) != -1 && isLegalMove(f)) {
                return f;
        }
        
        //soused dole
        f = new Field(attackingBoat.get(0).getRow() - 1, attackingBoat.get(0).getColumn());
        if (isShotable(f) != -1 && isLegalMove(f)) {
                return f;
        }
        return null;
}
	
	

	public boolean nextShot(Board board) throws Exception{
		Field f;
        boolean hit;

		if (state) {
			if (attackingBoat.size() == 1) {
				f = getNeighbour();
				if (getNeighbour() != null && isShotable(getNeighbour()) != -1) {
					possibleShot.remove(isShotable(getNeighbour()));
					hit = board.shot(f.getRow(), f.getColumn());
					if (hit) {
						attackingBoat.add(f);
						if ((getDirection() && attackingBoat.get(0).getColumn() > attackingBoat
								.get(1).getColumn())
								|| !getDirection()
								&& attackingBoat.get(0).getRow() > attackingBoat
										.get(1).getRow()) {
							attackingBoat.add(attackingBoat.remove(0));

						}
						
						return true;
					} else {// nezasazen

						return false;
					}
				} else {
					state = false;
					nextShot(board);

				}

			} else {// +1
				f = getField();
				checkAndRemove();
				System.out.println(f.getRow() + " " + f.getColumn());
				if (f != null) {
					f = possibleShot.remove(isShotable(f));
					hit = board.shot(f.getRow(), f.getColumn());
					if (hit) {
						return true;
					} else {
						if (f.getRow() == attackingBoat.get(0).getRow()
								&& f.getColumn() == attackingBoat.get(0)
										.getColumn()) {
							attackingBoat.remove(0);
						} else {
							attackingBoat.remove(attackingBoat.size() - 1);
						}

						return false;
					}

				} else { // p == null
					state = false;
				}
			}
		}

		if (!state) {
			int index = random.nextInt(possibleShot.size());
			f = possibleShot.remove(index);
			hit = board.shot(f.getRow(), f.getColumn());
			if (hit) {
				state = true;
				attackingBoat.clear();
				attackingBoat.add(f);
				return true;
			}
			return false;
		}

		return false;
	}

	
	
	
	public void makeShot(int x, int y,Player enemy) throws Exception{
		if (nextShot(enemy.getBoard())) {

		} else {

		}
	}
	
	
	private void checkAndRemove() {
        for (int i = 1; i < attackingBoat.size(); i++) {
                if (attackingBoat.get(i).getRow() == attackingBoat.get(i - 1).getRow()
                                && attackingBoat.get(i).getColumn() == attackingBoat.get(i - 1).getColumn()) {
                        attackingBoat.remove(i);
                }
        }
}
	private Field getField() {
        if (getDirection()) {
        	Field f = attackingBoat.get(0);
        	f.setColumn(f.getColumn() - 1);
                if (isShotable(f) != -1 && isLegalMove(f)) {
                        attackingBoat.add(0, f);
                        return f;
                } else {
                        f = attackingBoat.get(attackingBoat.size() - 1);
                        f.setColumn(f.getColumn() + 1);
                        if (isShotable(f) != -1 && isLegalMove(f)) {
                                attackingBoat.add(f);
                                return f;
                        } else {
                                return null;
                        }
                }
        } else {
                Field f = attackingBoat.get(0);
                f.setRow(f.getRow() - 1);
                if (isShotable(f) != -1 && isLegalMove(f)) {
                        attackingBoat.add(0, f);
                        return f;
                } else {
                        f = attackingBoat.get(attackingBoat.size() - 1);
                        f.setRow(f.getRow() + 1);
                        if (isShotable(f) != -1 && isLegalMove(f)) {
                                attackingBoat.add(f);
                                return f;
                        } else {
                                return null;
                        }
                }
        }
}
	
	private boolean getDirection() {
        return (attackingBoat.get(0).getRow() == attackingBoat.get(1).getRow());
	}
	
	private boolean isLegalMove(Field f) {
        return (f.getRow() >= 0 && f.getRow() < board.BOARD_SIZE 
        		&& f.getColumn() >= 0 && f.getColumn() < board.BOARD_SIZE);
	}
	
	private int isShotable(Field f) {
        for (int i = 0; i < possibleShot.size(); i++) {
                if (possibleShot.get(i).getRow() == f.getRow() && possibleShot.get(i).getColumn() == f.getColumn()) {
                        return i;
                }
        }
        return -1;
	}

	
}