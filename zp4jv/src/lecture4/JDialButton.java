package lecture4;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JComponent;

public class JDialButton extends JComponent {

	private static final long serialVersionUID = 1L;
	private Action action;

	private int x;
	private int y;
	private String text;
	private Rectangle r;

	public JDialButton(String s) {
		text = s;
	}

	public String getText() {
		return text;
	}

	public Rectangle getR() {
		return r;
	}

	public void setR(Rectangle r) {
		x = r.x + (r.width / 2);
		y = r.y + (r.height / 2);
		this.r = r;
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

	public void actionPerformed(ActionEvent e) {
		if (action != null) {
			action.actionPerformed(e);
		}
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	

}
