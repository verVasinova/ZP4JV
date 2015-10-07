package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import core.Game;

public class SingleBoardAttack extends SingleBoard implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private final Game game;
	private boolean start = false;

	public SingleBoardAttack(Game game) {
		super(game.getEnemy());
		this.game = game;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point point = convertMouseInput(e.getPoint());
				try {
					if (start && game.play(point.y, point.x) && !game.getME().isWinner() && !game.getEnemy().isWinner()) {
						actions.get(1).actionPerformed(null);
						paintImmediately(new Rectangle(getWidth(), getHeight()));
						Thread.sleep(1000);
						actions.get(0).actionPerformed(null);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}

		});

	}
	
	public void setStart(boolean a){
		start = a;
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g);
		paintBoard(g2);

	}

	public void paintBoard(Graphics2D g2) {
		for (int y = 0; y < boardSize; y++) {
			for (int x = 0; x < boardSize; x++) {
				g2.drawRect(x * (getWidth() / boardSize) + 1, y
						* (getHeight() / boardSize) + 1,
						getWidth() / boardSize, getHeight() / boardSize);

				if (board.getField(y, x).isShot()
						&& board.getField(y, x).hasBoat()) {
					paintCell(x, y, g2, new Color(204, 51, 255));

				}

				else if (board.getField(y, x).isShot()
						&& !board.getField(y, x).hasBoat()) {
					paintCell(x, y, g2, new Color(153, 204, 255));

				} else {
					paintCell(x, y, g2, new Color(0, 153, 255));
				}

				g2.setColor(Color.black);

			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
