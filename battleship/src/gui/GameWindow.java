package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.AI;
import core.File;
import core.Game;
import core.Human;
import core.Player;
import core.SingleGame;


public class GameWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JMenuBar mainMenu;
	private JMenu menuFile;
	private JMenu menuGame;
	private JMenu menuHelp;
	private Board board;
	JMenuItem menuItemOpen;
	JMenuItem menuItemNewGame;
	private Game game;
	
	public GameWindow(Game g){
		super();
		this.setTitle("Lodì");

		// po uzavreni okna, budou prostredky s nim spojene odstraneny
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout());
		this.game = g;
		board = new Board(game);
		mainMenu = new JMenuBar();

		menuFile = new JMenu("Soubor");
		menuFile.setMnemonic(KeyEvent.VK_S);
		
		menuItemOpen = new JMenuItem("Naèíst...");
		menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItemOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "Battleship", "battleship");
				c.setFileFilter(filter);
				c.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int rVal = c.showOpenDialog(GameWindow.this);

				if (rVal == JFileChooser.APPROVE_OPTION) {
					SingleGame game = new SingleGame();
					try {
						List<Player> l = File.load(c.getSelectedFile().getAbsolutePath());
						game.setME(l.get(0));
						game.setEnemy(l.get(1));
						GameWindow w = new GameWindow(game);
						w.setStart();
						w.setVisible(true);
						close();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
				
			}
				
			
		});
		JMenuItem menuItemSave = new JMenuItem("Uložit...");
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		
		menuItemSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
			     c.setFileSelectionMode(JFileChooser.FILES_ONLY);
			     FileNameExtensionFilter filter = new FileNameExtensionFilter(
					        "Battleship", "battleship");
					c.setFileFilter(filter);
			      int rVal = c.showSaveDialog(GameWindow.this);
			      if (rVal == JFileChooser.APPROVE_OPTION) {
			        try {
						File.save(c.getSelectedFile().getAbsolutePath(), (Human)game.getME(), (AI)game.getEnemy());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			        
			      }
				
			}
		});
		
		
		
		JMenuItem menuItemExit = new JMenuItem("Konec");
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		menuItemExit.addActionListener((ActionEvent e) -> {
			// ukonceni prace s oknem
				setVisible(false);
				dispose();
			});

		menuFile.add(menuItemOpen);
		menuFile.add(menuItemSave);
		menuFile.add(menuItemExit);


		menuGame = new JMenu("Hra");
		menuGame.setMnemonic(KeyEvent.VK_R);
		
		menuItemNewGame = new JMenuItem("Nová hra");
		menuItemNewGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		menuItemNewGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				NewGameWindow newGame = new NewGameWindow(false);
				newGame.setVisible(true);
				System.out.println(newGame.getClose());
				if(newGame.getClose()){
					System.out.println("close");
					close();
					
				}
			}
		});
		menuGame.add(menuItemNewGame);
		
		menuHelp = new JMenu("Nápovìda");
		
		
		mainMenu.add(menuFile);
		mainMenu.add(menuGame);
		mainMenu.add(menuHelp);
		setJMenuBar(mainMenu);
		
			

		
		BorderLayout layout = new BorderLayout();
		layout.setHgap(40);
		layout.setVgap(20);
		setLayout(layout);
		add(board, BorderLayout.CENTER);

		setPreferredSize(new Dimension(700, 400));
		pack();
	}
	
	public void setStart(){
		board.setStart(false);
	}
	
	private void close(){
		this.setVisible(false);
		dispose();
	}
	
}
