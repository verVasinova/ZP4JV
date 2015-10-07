package gui;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;


import java.awt.event.ActionListener;
import java.util.ArrayList;







import javax.swing.JPanel;

import core.Board;
import core.Player;

public class SingleBoard extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int boardSize;
	Board board;
	protected ArrayList<ActionListener> actions;
	
	public SingleBoard(Player p) {
		actions = new ArrayList<ActionListener>();
		this.board = p.getBoard();
		boardSize = board.BOARD_SIZE;
	}
	
	protected void addActionListener(ActionListener actionListener) {
		actions.add(actionListener);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

	}
	
	protected Point convertMouseInput(Point point) {
		return new Point(point.x / (getWidth() / 10), point.y
				/ (getHeight() / 10));
	}


	
	public void paintCell(int x, int y, Graphics2D g2, Color color){
		g2.setColor(color);
		g2.fillRect(x * (getWidth() / boardSize) + 2, y
				* (getHeight() / boardSize) + 2, getWidth()
				/ boardSize - 1, getHeight() / boardSize - 1);
	}

	public void setShipIndex(int i) {
	}
	
	public boolean isInsert(){
		return false;
		
	}
	
	public void setIsInsert(boolean a){}
	
	public void setStart(boolean b){}
	
	public int getCount(){
		return 0;
	}

}
