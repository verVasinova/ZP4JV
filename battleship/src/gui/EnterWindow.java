package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.File;
import core.Player;
import core.SingleGame;

public class EnterWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnQuit;
	private JButton btnLoad;
	private JButton btnNewGame;
	
	private JPanel buttonPanel;

	
	public static void main(String[] args) {
        EnterWindow w = new EnterWindow();
        w.setVisible(true);
    }
	
	public EnterWindow(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Lodì");
		
		setNewGame();
		setLoad();
		setQuit();
		
		setLayoutPanel();
		setLayout(new BorderLayout());
		this.add(buttonPanel, BorderLayout.CENTER);	

		setPreferredSize(new Dimension(400, 400));
		pack();
	}

	private void setNewGame(){
		btnNewGame = new JButton("Nová hra");
		btnNewGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
				NewGameWindow newGame = new NewGameWindow(true);
				newGame.setVisible(true);
				dispose();
			}
		});
	}
	
	private void setLoad() {
		btnLoad = new JButton("Naèíst hru");
		btnLoad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Battleship", "battleship");
				c.setFileFilter(filter);
				c.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int rVal = c.showOpenDialog(EnterWindow.this);

				if (rVal == JFileChooser.APPROVE_OPTION) {
					SingleGame game = new SingleGame();
					try {
						List<Player> l = File.load(c.getSelectedFile()
								.getAbsolutePath());
						game.setME(l.get(0));
						game.setEnemy(l.get(1));
						GameWindow w = new GameWindow(game);
						w.setStart();
						w.setVisible(true);
						setVisible(false);
						dispose();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		});
	}
	
	private void setQuit(){
		btnQuit = new JButton("Konec");
		btnQuit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}

	private void setLayoutPanel(){
		buttonPanel = new JPanel();
		//SpringLayout layout = new SpringLayout();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc4 = new GridBagConstraints();
		gc4.gridx = 0;
		gc4.gridy = 0;
		gc4.ipady = 20; //zvetsi komponentu o 10px na vysku
		gc4.anchor = GridBagConstraints.CENTER; //bunka zarovnana vlevo
		gc4.fill = GridBagConstraints.BOTH; //komponenta natazena do obou rozmeru
		gc4.insets = new Insets(10, 10, 10, 10);
		gc4.weighty = 1;
		
		GridBagConstraints gc3 = new GridBagConstraints();
		gc3.gridx = 0;
		gc3.gridy = 2;
		gc3.ipady = 20; //zvetsi komponentu o 10px na vysku
		gc3.anchor = GridBagConstraints.CENTER; //bunka zarovnana vlevo
		gc3.fill = GridBagConstraints.BOTH; //komponenta natazena do obou rozmeru
		gc3.weightx = 1;
		gc3.weighty = 1;
		gc3.insets = new Insets(10, 10, 10, 10);
		
		GridBagConstraints gc2 = new GridBagConstraints();
		gc2.gridx = 0;
		gc2.gridy = 3;
		gc2.ipady = 20; //zvetsi komponentu o 10px na vysku
		gc2.anchor = GridBagConstraints.CENTER; //bunka zarovnana vlevo
		gc2.fill = GridBagConstraints.BOTH; //komponenta natazena do obou rozmeru
		gc2.weightx = 1;
		gc2.insets = new Insets(10, 10, 10, 10);
		gc2.weighty = 1;

		buttonPanel.add(btnNewGame, gc4);
		buttonPanel.add(btnLoad, gc3);
		buttonPanel.add(btnQuit,gc2);
	}
	
}
