package test2;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SwingThreads extends JFrame {

	private static final long serialVersionUID = -5894245064728900823L;
	
	private JButton 	btnStart;
	private JButton 	btnStop;
	private JTextField 	txtInput;
	private JLabel 		lblOutput;
	
	private FibThread thr;
	
	public SwingThreads() {
		super();
		
		/////////// inicializace grafickeho rozhrani ///////////
		
		this.setTitle("Demo");
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(300, 70);
		
		txtInput = new JTextField("30", 5);
		lblOutput = new JLabel("Vysledek:");

		btnStart = new JButton("start");
		btnStop = new JButton("stop");
		btnStop.setEnabled(false);
		
		this.add(txtInput);
		this.add(lblOutput);
		this.add(btnStart);
		this.add(btnStop);
		
		///////// konec inicializace grafickeho rozhrani ///////////
		
		//
		// tato udalost slouzi k zahajeni vypoctu
		//
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final int arg = Integer.parseInt(txtInput.getText()); // nacte vstup
				adjustGUI();                            			  // upravi UI
				thr = new FibThread(arg);							  // vytvori nove vlakno
				thr.start();										  // spusti vlakno
				
			}
		});

		//
		// tato udalost bude provedena v momente, kdy uzivatel explicitne prerusi vypocet
		//
		btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				thr.cancel();                                      // prerusi vypocet 
			}
		});
	}
	
	/**
	 * Upravi graficke rozhrani tak, aby signalizovalo, ze probiha vypocet
	 */
	public void adjustGUI() {
		lblOutput.setText("Wait!");
		btnStart.setEnabled(false);
		btnStop.setEnabled(true);
	}
	
	/**
	 * Zobrazi vysledek vypoctu
	 * @param value -- vysledna hodnota 
	 */
	public void displayResult(int value) {
		lblOutput.setText(Integer.toString(value));
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
	}
	
	/**
	 * Zobrazi, ze vypocet byl prerusen
	 */
	public void displayAbort() {
		lblOutput.setText("Aborted");
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
	}
	
	/**
	 * Vlakno realizujici vypocet
	 */
	private class FibThread extends Thread {
		
		private boolean cancelled = false;
		private int n;
		
		public FibThread(int n) {
			super();
			this.n = n;
		}

		@Override
		public void run() {
			final int result = fib(n);
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					if (isCancelled()) displayAbort();
					else displayResult(result);
				}
			});

		}
		
		private int fib(int n) {
			if (isCancelled()) return -1;
			if (n == 0) return 0;
			if (n == 1) return 1;
			return fib(n - 1) + fib(n - 2);
		}

		public synchronized boolean isCancelled() {
			return cancelled;
		}

		public synchronized void cancel() {
			this.cancelled = true;
		}		
		
		
	}

	public static void main(String [] args) {
		JFrame frame = new SwingThreads();
		frame.setVisible(true);
	}
}