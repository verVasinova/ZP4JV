package lecture3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

public class AddEditDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JPanel buttonPanel = new JPanel();
	private JPanel inputPanel = new JPanel();
	private JPanel ingredientsPanel = new JPanel();
	private JPanel buttonIngredientPanel = new JPanel();

	private JButton btnOk;
	private JButton btnCancel;

	private JTextField txtName;
	private JTextField txtServings;
	private JTextField txtCooktime;

	private JLabel lbName;
	private JLabel lbServings;
	private JLabel lbCooktime;

	private JLabel lbIngredients;
	private JLabel lbSteps;
	private JTextArea txtAreaSteps;

	private Recipe result;

	private JTable table;
	private MyTableModel tableModel;
	private String[] columns = new String[] { "Poèet", "Jednotka", "Název" };
	private Object[][] values = new Object[0][3];

	private JButton btnAddIngredient;
	private JButton btnEditIngredient;
	private JButton btnDeleteIngredient;

	public AddEditDialog(JFrame parentFrame, Recipe r) {
		super(parentFrame);

		this.setTitle("Pøidání/editace receptu");

		lbName = new JLabel("Název receptu:");
		lbServings = new JLabel("Poèet porcí:");
		lbCooktime = new JLabel("Èas pøípravy:");
		lbIngredients = new JLabel("Seznam surovin");
		lbSteps = new JLabel("Postup:");

		txtName = new JTextField();
		txtName.setColumns(20);
		
		txtServings = new JTextField();
		txtServings.setColumns(20);
		
		txtCooktime = new JTextField();
		txtCooktime.setColumns(20);

		
		txtAreaSteps = new JTextArea();

		tableModel = new MyTableModel();
		table = new JTable(tableModel);

		setInputPanelLayout();

		if (r != null) {
			result = r;
			txtName.setText(r.getName());
			txtServings.setText(Integer.toString(r.getServings()));
			txtCooktime.setText(Integer.toString(r.getCooktime()));
			List<Ingredient> ing = r.getIngredients();

			for (int i = 0; i < r.getIngredients().size(); i++) {
				tableModel.addRow(ing.get(i).getAmount(), ing.get(i).getUnit(),
						ing.get(i).getIngredient());
			}

			for (String s : r.getDirections()) {
				txtAreaSteps.append(s + "\n");
			}

		} else {
			result = null;
		}

		btnAddIngredient = new JButton("Pøidat surovinu");
		btnEditIngredient = new JButton("Upravit surovinu");
		btnDeleteIngredient = new JButton("Smazat surovinu");

		btnAddIngredient.addActionListener(new AddIngredientAction());
		btnEditIngredient.addActionListener(new EditIngredientAction());
		btnDeleteIngredient.addActionListener(new DeleteIngredientAction());

		buttonIngredientPanel.setLayout(new FlowLayout());
		buttonIngredientPanel.add(btnAddIngredient);
		buttonIngredientPanel.add(btnEditIngredient);
		buttonIngredientPanel.add(btnDeleteIngredient);

		setButtonPanel();


		setIngredientPanel();

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(inputPanel, BorderLayout.NORTH);
		getContentPane().add(ingredientsPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		setPreferredSize(new Dimension(450, 500));

		pack();

	}
	
	private void setIngredientPanel(){
		JScrollPane scPane = new JScrollPane(table);
		JScrollPane scPaneSteps = new JScrollPane(txtAreaSteps);
		
		GroupLayout l1 = new GroupLayout(ingredientsPanel);
		l1.setAutoCreateContainerGaps(true);
		l1.setAutoCreateGaps(true);

		l1.setHorizontalGroup(l1.createSequentialGroup().addGroup(
				l1.createParallelGroup()
						.addComponent(lbIngredients)
						.addComponent(scPane)
						.addComponent(buttonIngredientPanel,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lbSteps, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(scPaneSteps)));

		l1.setVerticalGroup(l1
				.createSequentialGroup()
				.addGroup(
						l1.createParallelGroup().addComponent(lbIngredients,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGroup(
						l1.createParallelGroup().addComponent(scPane, 100, 100,
								100))
				.addGroup(
						l1.createParallelGroup().addComponent(
								buttonIngredientPanel,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGroup(
						l1.createParallelGroup().addComponent(lbSteps,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGroup(l1.createParallelGroup().addComponent(scPaneSteps)));

		ingredientsPanel.setLayout(l1);
		
	}

	private void saveRecipe() {

		result.setName(txtName.getText());
		result.setServings(Integer.valueOf(txtServings.getText()));
		result.setCooktime(Integer.valueOf(txtCooktime.getText()));

		List<Ingredient> ing = new ArrayList<>();
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			ing.add(new Ingredient((int) tableModel.getValueAt(i, 0),
					(String) tableModel.getValueAt(i, 1), (String) tableModel
							.getValueAt(i, 2)));
		}

		result.setIngredients(ing);

		List<String> st = new ArrayList<>();
		for (String line : txtAreaSteps.getText().split("\\n")) {
			st.add(line);
		}

		result.setDirections(st);

	}

	private void setButtonPanel() {
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			if(txtServings.getText().matches("\\d+") && txtCooktime.getText().matches("\\d+")){
				if (result != null ) {
					saveRecipe();

				} else {
					result = new Recipe();
					saveRecipe();

				}
				setVisible(false);
			}}
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

	public Recipe getResult() {
		return result;
	}

	private void setInputPanelLayout() {

		GroupLayout layout = new GroupLayout(inputPanel);

		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup().addComponent(lbName)
								.addComponent(lbServings)
								.addComponent(lbCooktime))
				.addGroup(
						layout.createParallelGroup().addComponent(txtName)
								.addComponent(txtServings)
								.addComponent(txtCooktime)));
		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup()
								.addComponent(lbName)
								.addComponent(txtName,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
				.addGroup(
						layout.createParallelGroup()
								.addComponent(lbServings)
								.addComponent(txtServings,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
				.addGroup(
						layout.createParallelGroup().addComponent(lbCooktime)
								.addComponent(txtCooktime)));
		inputPanel.setLayout(layout);

	}

	private final class MyTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 8720445364835231767L;

		@Override
		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public int getRowCount() {
			return values.length;
		}

		@Override
		public Object getValueAt(int r, int c) {
			return values[r][c];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {

			return super.getColumnClass(columnIndex);
		}

		@Override
		public String getColumnName(int column) {
			return columns[column];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex >= 1)
				return true;
			return false;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			values[rowIndex][columnIndex] = aValue;
			fireTableCellUpdated(rowIndex, columnIndex);
		}

		public void addRow(int amount, String unit, String name) {
			values = Arrays.copyOf(values, values.length + 1);
			values[values.length - 1] = new Object[] { amount, unit, name };
			fireTableDataChanged();
		}

		public void removeRow(int row) {
			Object[][] values2 = new Object[values.length - 1][3];

			for (int i = 0; i < values2.length; i++) {
				if (i != row) {
					values2[i][0] = values[i][0];
					values2[i][1] = values[i][1];
					values2[i][2] = values[i][2];
				} else {
					values2[i][0] = values[i + 1][0];
					values2[i][1] = values[i + 1][1];
					values2[i][2] = values[i + 1][2];
				}
			}

			values = values2;
			fireTableDataChanged();
		}

	}

	private class AddIngredientAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			IngredientDialog dlg = new IngredientDialog(AddEditDialog.this,
					null);
			dlg.setModal(true);
			dlg.setVisible(true);
			dlg.dispose();
			if (dlg.getResult() != null) {
				tableModel.addRow(dlg.getResult().getAmount(), dlg.getResult()
						.getUnit(), dlg.getResult().getIngredient());

			}

		}
	}

	private class EditIngredientAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (table.getSelectedRow() != -1) {
				int row = table.getSelectedRow();
				Ingredient i = new Ingredient((int) tableModel.getValueAt(row,
						0), (String) tableModel.getValueAt(row, 1),
						(String) tableModel.getValueAt(row, 2));
				IngredientDialog dlg = new IngredientDialog(AddEditDialog.this,
						i);
				dlg.setModal(true);
				dlg.setVisible(true);
				dlg.dispose();

				tableModel.setValueAt(dlg.getResult().getAmount(), row, 0);
				tableModel.setValueAt(dlg.getResult().getUnit(), row, 1);
				tableModel.setValueAt(dlg.getResult().getIngredient(), row, 2);

			}

		}
	}

	private class DeleteIngredientAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (table.getSelectedRow() != -1) {
				int row = table.getSelectedRow();
				System.out.println(row);

				tableModel.removeRow(row);
				tableModel.fireTableDataChanged();
			}

		}
	}

}
