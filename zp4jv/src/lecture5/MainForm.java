package lecture5;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainForm extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton btnShuffle;
	private JButton btnPause;
	private JButton btnResume;
	
	private JPanel buttonPanel;
	private SortScreen screen;
	
	private SortingThread sort;
	
	public static void main(String[] args){
		MainForm f = new MainForm();
		f.setVisible(true);
		
	}
	
	public MainForm(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Selection sort");
		
		btnShuffle = new JButton("Shuffle");
		btnPause = new JButton("Pause");
		btnResume = new JButton("Resume");
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3));
		buttonPanel.add(btnShuffle);
		buttonPanel.add(btnPause);
		buttonPanel.add(btnResume);
		
		screen = new SortScreen();
		
		btnShuffle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPause.setEnabled(true);
				btnShuffle.setEnabled(false);
				sort = new SortingThread();
				sort.start();

				
			}
		});
		
		
		btnPause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPause.setEnabled(false);
				btnResume.setEnabled(true);
				btnShuffle.setEnabled(true);
				sort.paused();
			}
		});
		
		
		btnResume.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPause.setEnabled(true);
				btnShuffle.setEnabled(false);
				btnResume.setEnabled(false);
				sort.resumed();
				
				
			}}

		);
		btnPause.setEnabled(false);
		btnResume.setEnabled(false);

		
		setLayout(new BorderLayout());
		this.add(buttonPanel, BorderLayout.NORTH);
		this.add(screen, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(400, 400));
		pack();
		
	}
	

	private class SortingThread extends Thread {
		private int[] points;
		private boolean paused = false;
		
		public SortingThread(){
			super();
			points = new int[100];
			
			for(int i = 0; i < points.length; i++){
				points[i] = i;
			}
			
			shufflePoints();
			screen.setPoints(points);
		}
		
		public void run(){
				selectionSort();
		}

		
		public synchronized boolean isPaused() {
			return paused;
		}
		
		public synchronized void paused() {
			this.paused = true;

		}
		
		public synchronized void resumed() {
			this.paused = false;
		}
		
		
		
		private void shufflePoints(){
			Random r = new Random();
			for(int i = points.length - 1; i > 0; i--){
				int index =  r.nextInt(100);
				int a = points[index];
				points[index] = points[i];
				points[i] = a;			
			}
		}
		
		private void selectionSort() {
			
			for (int i = 0; i < points.length - 1; i++) {
				if (!isPaused()) {
					int maxIndex = i;
					for (int j = i + 1; j < points.length; j++) {
						if (points[j] > points[maxIndex])
							maxIndex = j;
					}
					int tmp = points[i];
					points[i] = points[maxIndex];
					points[maxIndex] = tmp;

					screen.repaint();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

				else {
					i--;
					while (isPaused()) {
						System.out.println("Thread is suspended");
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

}
