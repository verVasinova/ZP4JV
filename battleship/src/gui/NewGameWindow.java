package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import core.SingleGame;

public class NewGameWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnOk;
	private JButton btnCancel;

	private JPanel buttonPanel = new JPanel();
	private JPanel inputPanel = new JPanel();


	private JLabel lbName;
	private JTextField txtName;
	private boolean close = true;
	
	public NewGameWindow(boolean a) {
		this.setTitle("Zadejte jméno hráèe");
		
		close = a;
		btnOk = new JButton("Ok");
		btnCancel = new JButton("Zrušit");

		GridLayout btnLayout = new GridLayout(1, 2);
		buttonPanel.setLayout(btnLayout);
		buttonPanel.add(btnOk);
		buttonPanel.add(btnCancel);

		lbName = new JLabel("Jméno:");
		txtName = new JTextField();
	
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				close = true;
				if(!txtName.getText().equals("")){
					close = true;
					SingleGame game = new SingleGame();
					game.getME().setName(txtName.getText());
					GameWindow w = new GameWindow(game);
					w.setVisible(true);
					setVisible(false);
					
					dispose();
				}

				else
					JOptionPane.showMessageDialog(null, "Vyplòtì jméno hráèe!", "Info ", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if(close){
					EnterWindow w = new EnterWindow();
			        w.setVisible(true);
				}
				close = true;
				dispose();
			}
		});
		
		setInputPanelLayout();
		this.setSize(400, 100);
		this.setLayout(new BorderLayout());

		add(buttonPanel, BorderLayout.SOUTH);
		add(inputPanel, BorderLayout.CENTER);
		
		this.setVisible(true);

	}
	
	public boolean getClose(){
		return close;
	}

	private void setInputPanelLayout() {
		GroupLayout layout = new GroupLayout(inputPanel);

		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(lbName))
				.addGroup(layout.createParallelGroup().addComponent(txtName)));

		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup()
								.addComponent(lbName).addComponent(txtName)));


		inputPanel.setLayout(layout);

	}
}
