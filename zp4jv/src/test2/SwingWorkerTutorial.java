package test2;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class SwingWorkerTutorial extends JFrame {

	private static final long serialVersionUID = -5894245064728900823L;
	
	private JButton 	btnStart;
	private JButton 	btnStop;
	private JTextField 	txtInput;
	private JLabel 		lblOutput;
	
	private SwingWorker<Integer, Void> sw;
	
	public SwingWorkerTutorial() {
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
				sw = new Worker(arg);                                 // vytvori worker
				sw.execute();	                                      // zacne provadet vypocet
			}
		});

		//
		// tato udalost bude provedena v momente, kdy uzivatel explicitne prerusi vypocet
		//
		btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sw.cancel(true);                                      // prerusi vypocet 
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
	 * Trida, ktera bude na pozadani provadet deletrvajici vypocet. 
	 * V tomto pripade, zavola funkci fib() s argumentem arg.
	 * 
	 * Vysledek vypoctu je predan uzivatelskemu rozhrani pomoci funkci displayResult()
	 * a displayAbort(), v pripade, ze vypocet byl prerusen.
	 */
	public class Worker extends SwingWorker<Integer, Void> {
		
		private int arg;
		
		public Worker(int arg) {
			super();
			this.arg = arg;
		}
		
		@Override
		protected Integer doInBackground() throws Exception {
			// provadi narocny vypocet; tato funkce (pripadne funkce vnorene)
			// NESMI PRIMO pracovat s uzivatelskym rozhranim
			return fib(this, arg);
		}
		
		
		
		@Override
		protected void done() {
			try {
				if (this.isCancelled()) displayAbort();
				else displayResult(this.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Vypocte fibonnaciho cislo, slouzi k demonstraci narocneho vypoctu
	 */
	public static int fib(SwingWorker<Integer, Void> w, int n) {
		if (w.isCancelled()) return -1;
		if (n == 0) return 0;
		if (n == 1) return 1;
		return fib(w, n - 1) + fib(w, n - 2);
	}

	public static void main(String [] args) {
		JFrame frame = new SwingWorkerTutorial();
		frame.setVisible(true);
	}
}