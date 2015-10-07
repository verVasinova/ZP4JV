package gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import core.Game;

public class Board extends JPanel {

	private static final long serialVersionUID = 1L;
	private Game game;
	SingleBoard boardME;
	SingleBoard boardEnemy;

	private JButton btnStart;
	
	private JLabel player;
	private JLabel enemy;
	private JLabel lblCount;
	private JLabel lblCountOfShots;
	
	private JLabel lblState;
	
	private  JPanel boardsPanel = new JPanel();
	private JPanel kontextPanel = new JPanel();
	private JToolBar toolbar = new JToolBar();

	public Board(Game game) {
		this.setGame(game);

		boardEnemy = new SingleBoardAttack(game);
		boardEnemy.addActionListener(new RepaintAction());
		boardEnemy.addActionListener(new StatusAction());
		boardME = new SingleBoardDeffence(game);
		
		player = new JLabel(game.getME().getName());
		enemy = new JLabel(game.getEnemy().getName());
		setBoardsPanel();
		setInfoPanel();
		setButtons();
		
		BorderLayout layout = new BorderLayout();
		setLayout(layout);


		add(toolbar, BorderLayout.NORTH);
		add(boardsPanel, BorderLayout.CENTER);
		add(kontextPanel, BorderLayout.SOUTH);
				
	}
	private void setInfoPanel(){
		lblCountOfShots = new JLabel("Poèet støel: ");
		lblCount = new JLabel(String.valueOf(game.getME().getCountOfShots()));
		kontextPanel.setLayout(new BorderLayout(20,0));
		
		JPanel countPanel = new JPanel();
		GridLayout layoutCounts = new GridLayout(1, 3);
		layoutCounts.setHgap(20);
		countPanel.setLayout(layoutCounts);
		
		
		lblState = new JLabel("Vítejte ve høe NÁMOØNÍ BITVA!");
		countPanel.add(lblCountOfShots);
		countPanel.add(lblCount);
		
		kontextPanel.add(lblState, BorderLayout.WEST);
	    kontextPanel.add(countPanel,BorderLayout.EAST);
	}
	
	private void setButtons() {
		btnStart = new JButton("Start");
		toolbar.setRollover(true);
		toolbar.add(btnStart);
	    toolbar.addSeparator();
		btnStart.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!game.getME().hasShipToPlace()) {
					setStart(false);
				}
			}
		});
	}
	
	private void setBoardsPanel() {
		GroupLayout l = new GroupLayout(boardsPanel);

		l.setAutoCreateContainerGaps(true);
		l.setAutoCreateGaps(true);

		l.setHorizontalGroup(l
				.createSequentialGroup()
				.addGroup(
						l.createParallelGroup().addComponent(enemy, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(boardEnemy,  0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(
						l.createParallelGroup().addComponent(player,  0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(boardME,  0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		l.setVerticalGroup(l
				.createSequentialGroup()
				.addGroup(
						l.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(enemy)
								.addComponent(player))
				.addGroup(
						l.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(boardEnemy)
								.addComponent(boardME)));
		boardsPanel.setLayout(l);

	}

	private class RepaintAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			boardME.repaint();
		}
	}
	
	private class StatusAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			lblCount.setText(String.valueOf(game.getME().getCountOfShots()));
			lblCount.paintImmediately(0,0, getWidth(), getHeight());
			
			lblState.setText(game.getME().getStatus());
			lblState.paintImmediately(0,0, getWidth(), getHeight());

		}
	}
	protected void paintComponent(Graphics g) {
		//Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g);
		
		
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public void setStart(boolean b){
		boardEnemy.setStart(!b);
		boardME.setStart(!b);
		btnStart.setEnabled(b);

	}
	

}
