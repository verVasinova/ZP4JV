package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import core.Game;

public class SingleBoardDeffence extends SingleBoard {


	private static final long serialVersionUID = 1L;
	private final Game game;
	private boolean direction;
	private boolean state;
	private boolean start = false;

	private int indexShip = -1;
    private int x;
    private int y;
	
	public SingleBoardDeffence(Game game) {
		super(game.getME());
		this.game = game;
		direction = true;
		state = true;
		
		
		addMouseMotionListener(new MouseMotionAdapter() {
            @Override
			public void mouseMoved(MouseEvent e) {
				if (game.getME().hasShipToPlace()) {

					Point point = convertMouseInput(e.getPoint());
					x = point.x;
					y = point.y;
						repaint();

				}
    }
    });
		
		addMouseListener(new MouseAdapter() {

			public void mousePressed(final MouseEvent ev) {
				if (ev.getButton() == MouseEvent.BUTTON1) {
					Point point = convertMouseInput(ev.getPoint());
					if(!start){
					if (game.getME().hasShipToPlace()) {
						
							if (game.getME()
									.getBoard()
									.validPosition(
											game.getME().getShip(indexShip),
											point.x, point.y, direction)) {
								
								game.insertBoat(indexShip, direction, point.x,
										point.y);
								indexShip++;

								paintImmediately(0, 0, getWidth(), getHeight());
							}

					} 
					
					else if(game.getME().getBoard().getField(point.x, point.y).hasBoat()){
						indexShip = game.getME().deleteShip(game.getME().getBoard().getField(point.x, point.y).getShip());
						state = true;
						repaint();
					}
					
					else {
						state = false;
						repaint();
					}
					}
				} else if (ev.getButton() == MouseEvent.BUTTON3) {
					direction = !direction;
				}
			}
		});
	}	
	
	public void setShipIndex(int i){
		this.indexShip = i;
	}
	
	public void setStart(boolean a){
		start = a;
	}
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g);
		for (int y = 0; y < boardSize; y++) {
			for (int x = 0; x < boardSize; x++) {
				g2.drawRect(x * (getWidth() / boardSize) + 1, y
						* (getHeight() / boardSize) + 1,
						getWidth() / boardSize , getHeight() / boardSize  );

				if (board.getField(x, y).isShot()
						&& !board.getField(x, y).hasBoat()) {

					paintCell(x, y, g2, new Color(153, 204, 255));
				} else if (board.getField(x, y).isShot()
						&& board.getField(x, y).hasBoat()) {
					paintCell(x, y, g2, new Color(204, 51, 255));
				} else if (board.isFree(x, y))
					paintCell(x, y, g2, new Color(204, 153, 255));

				else
					paintCell(x, y, g2, new Color(0, 153, 255));

				g2.setColor(Color.black);

			}
		}

		if (state && game.getME().hasShipToPlace() && indexShip != - 1) {
			int s = game.getME().getShip(indexShip).getType().getSize();
			g2.setColor(Color.BLUE);
			if(x >= 0 && x < boardSize && y >= 0 && y <boardSize){
				if (direction) {
					g2.fillRect(x * (getWidth() / boardSize) + 2, y * (getHeight()
							/ boardSize) + 2, s * getWidth() / boardSize - 1,
							getHeight() / boardSize - 1) ;
				} else {
					g2.fillRect(x * (getWidth() / boardSize) +2, y * (getHeight()
							/ boardSize ) + 2, getWidth() / boardSize - 1, s
							* getHeight() / boardSize - 1);
				}
			}
			

		}

	}
}
