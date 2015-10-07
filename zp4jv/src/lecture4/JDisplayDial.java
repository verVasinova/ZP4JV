package lecture4;

import java.awt.Rectangle;
import javax.swing.JComponent;

public class JDisplayDial extends JComponent{

	private static final long serialVersionUID = 1L;
	
	private StringBuilder text;
	private int x;
	private int y;
	private Rectangle r;
	
	public JDisplayDial(){
		text = new StringBuilder();
	}
	
	public void addText(String s){
		text.append(s);
	}
	
	
	public void setText(String s) {
		text.replace(0, text.length(), s);
	}
	
	public String getText(){
		return text.toString();
	}
	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Rectangle getR(){
		return r;
	}
	
	public void setRec(Rectangle r){
		this.r = r;
	}
	
	

	
}
