package lecture4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class JDial extends JComponent {

	private static final long serialVersionUID = 1L;

	private JDisplayDial display;
	private List<JDialButton> buttons;
	private ArrayList<ActionListener> actions;

	public JDial() {

		display = new JDisplayDial();
		actions = new ArrayList<ActionListener>();
		buttons = new ArrayList<>();

		for (String i : Arrays.asList("7", "8", "9", "4", "5", "6", "1", "2",
				"3", "0")) {
			buttons.add(new JDialButton(i));

		}

		buttons.add(new JDialButton("C"));
		buttons.add(new JDialButton("E"));
		
		EnterAction enter = new EnterAction();
		ClearAction clear = new ClearAction();

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				for (JDialButton b : buttons) {
					if (b.getR().contains(e.getX(), e.getY())) {
						if ((b.getText().matches("\\d"))) {
							display.addText(b.getText());
						}
						else{
							switch(b.getText()){
							case "E":
								enter.actionPerformed(null);
								break;
							case "C":
								clear.actionPerformed(null);
								break;
							}
						}
				}
			}
			repaint();
		}});

		setNumberKeys();

	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setBackground(Color.BLACK);
		g2.setFont(new Font("Arial",  Font.BOLD, 10 * (getWidth() + getHeight())/200));
		
		setDisplay(g2);

		for (int i = 0; i < buttons.size(); i++) {

			JDialButton btn = buttons.get(i);
			initButton(btn, i);
			
			
			g2.setColor(Color.GRAY);
			g2.fillRoundRect(btn.getR().x, btn.getR().y, btn.getR().width,
					btn.getR().height, 45, 45);
			g2.setColor(Color.black);
			g2.setColor(Color.WHITE);
			g2.drawString(btn.getText(), (int) (btn.getX() - btn.getR().getWidth() * 0.1), (int) (btn.getY() + btn.getR().getHeight() * 0.22 ));

		}

	}

	private void initButton(JDialButton b, int i) {
		int cell = 3;
		int row = buttons.size() / 3;

		int w = (int) (getWidth() * 0.333);
		int h = (getHeight() - (getHeight() / 4)) / row;

		row = i / 3;
		cell = i % 3;

		b.setR(new Rectangle( (cell * w ) + getWidth()/250 + 1,  getHeight() / 4 + (row * h), w - getWidth()/250 - 1, h - getHeight()/250 ));
	}

	private void setDisplay(Graphics2D g2) {
		display.setX(this.getWidth());
		display.setY(getHeight() / 4);
		display.setRec(new Rectangle(getWidth()/150, getHeight()/100, getWidth() - getWidth()/75, getHeight() / 4 - getHeight()/50 ));
		
		g2.setColor(Color.WHITE);
		g2.fillRoundRect(display.getR().x, display.getR().y, display.getR().width, display.getR().height, 40, 40);
		g2.setColor(Color.GRAY);
		g2.drawRoundRect(display.getR().x, display.getR().y, display.getR().width, display.getR().height, 40, 40);
		
		g2.setColor(Color.BLACK);
		g2.drawString(display.getText(), (int) (display.getX() * 0.2), (int) (display.getY() * 0.7 ));
		
	}
	
	public void addActionListener(ActionListener actionListener) {
		actions.add(actionListener);
	}
	
	private class EnterAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(actions.size());
			for(ActionListener a : actions) {
				a.actionPerformed(e);
				
			}
		}
	}
	
	public String getDisplayText(){
		return display.getText();
	}
	
	private class ClearAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			display.setText("");
		}
	}
	
	private void setNumberKeys(){
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), "1");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0), "1");
		getActionMap().put("1", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.addText("1");
				repaint();
			}
		});

		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), "2");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, 0), "2");
		getActionMap().put("2", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.addText("2");
				repaint();
			}
		});
		
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), "3");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3, 0), "3");
		getActionMap().put("3", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.addText("3");
				repaint();
			}
		});
		
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), "4");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0), "4");
		getActionMap().put("4", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.addText("4");
				repaint();
			}
		});
		
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0), "5");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5, 0), "5");
		getActionMap().put("5", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.addText("5");
				repaint();
			}
		});
		
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0), "6");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD6, 0), "6");
		getActionMap().put("6", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.addText("6");
				repaint();
			}
		});
		
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0), "7");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD7, 0), "7");
		getActionMap().put("7", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.addText("7");
				repaint();
			}
		});
		
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0), "8");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD8, 0), "8");
		getActionMap().put("8", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.addText("8");
				repaint();
			}
		});
		
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0), "9");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9, 0), "9");
		getActionMap().put("9", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.addText("9");
				repaint();
			}
		});
		
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
		getActionMap().put("enter", new EnterAction());
		
	}
	
}
