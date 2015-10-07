package lecture4;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyForm extends JFrame {
	

	private static final long serialVersionUID = 1L;
	JDial dial;
	public static void main(String[] args) {
		MyForm form = new MyForm();
		form.setVisible(true);
	}
	
	public MyForm() {
		 dial = new JDial();
		dial.addActionListener(new ConsoleOutputActionListener());
		dial.addActionListener(new MessageBoxActionListener());
		getContentPane().add(dial);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setPreferredSize(new Dimension(400, 400));
		pack();
	}
	
	private class ConsoleOutputActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(dial.getDisplayText());
		}
	}

	private class MessageBoxActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "Enter","",  JOptionPane.INFORMATION_MESSAGE);
			System.out.println("MessageBox");
		}
	}

}
