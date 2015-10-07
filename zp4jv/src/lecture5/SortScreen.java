package lecture5;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;


public class SortScreen extends JComponent {

	private static final long serialVersionUID = 1L;
	private int[] points;
	
	public SortScreen(){
		points = null;
	}
	
	public void setPoints(int[] array){
		this.points = array;
	}
	
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setBackground(Color.BLACK);
		if(points != null){
		for (int i = 0; i < points.length; i++){
			g2.fillOval(i * getWidth()/100, points[i] * getHeight()/100, 5, 5);
		}
		}
	}

}
