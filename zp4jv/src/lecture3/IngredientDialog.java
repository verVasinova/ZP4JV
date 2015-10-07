package lecture3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IngredientDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel buttonPanel = new JPanel();
	private JPanel inputPanel = new JPanel();

	private JButton btnOk;
	private JButton btnCancel;

	private JLabel lbAmount;
	private JLabel lbUnit;
	private JLabel lbIngredient;

	private JTextField txtAmount;
	private JTextField txtUnit;
	private JTextField txtIngredient;

	private Ingredient result;

	public IngredientDialog(AddEditDialog addEditDialog, Ingredient i) {
		super(addEditDialog);

		this.setTitle("Pøidání/editace suroviny");

		lbAmount = new JLabel("Poèet:");
		lbUnit = new JLabel("Jednotka:");
		lbIngredient = new JLabel("Surovina:");

		txtAmount = new JTextField();
		txtUnit = new JTextField();
		txtIngredient = new JTextField();

		if (i != null) {
			result = i;
			txtAmount.setText(Integer.toString(i.getAmount()));
			txtUnit.setText(i.getUnit());
			txtIngredient.setText(i.getIngredient());

		} else {
			result = null;
		}

		setButtonPanel();
		setInputPanelLayout();

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(inputPanel, BorderLayout.NORTH);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		setPreferredSize(new Dimension(400, 200));

		pack();

	}

	private void setButtonPanel() {
		btnOk = new JButton("Ok");

		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String unit = txtUnit.getText();
				String amount = txtAmount.getText();
				String ing = txtIngredient.getText();

				if (unit != "" && amount != "" && ing != "" && amount.matches("\\d+")) {
					if (result != null) {
						result.setAmount(Integer.valueOf(amount));
						result.setUnit(unit);
						result.setIngredient(ing);

					} else {
						result = new Ingredient(Integer.valueOf(amount), unit,
								ing);
					}
					setVisible(false);
				}
			}
		});

		btnCancel = new JButton("Zrušit");

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		GridLayout btnLayout = new GridLayout(1, 2);
		buttonPanel.setLayout(btnLayout);
		buttonPanel.add(btnOk);
		buttonPanel.add(btnCancel);

	}

	private void setInputPanelLayout() {
		GroupLayout layout = new GroupLayout(inputPanel);

		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup().addComponent(lbAmount)
								.addComponent(lbUnit)
								.addComponent(lbIngredient))
				.addGroup(
						layout.createParallelGroup()
								.addComponent(txtAmount)
								.addComponent(txtUnit)
								.addComponent(txtIngredient,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, 200)));
		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup()
								.addComponent(lbAmount)
								.addComponent(txtAmount,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
				.addGroup(
						layout.createParallelGroup()
								.addComponent(lbUnit)
								.addComponent(txtUnit,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
				.addGroup(
						layout.createParallelGroup()
								.addComponent(lbIngredient)
								.addComponent(txtIngredient,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)));
		inputPanel.setLayout(layout);

	}

	public Ingredient getResult() {
		return result;
	}

}
