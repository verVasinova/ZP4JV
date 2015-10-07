package lecture6;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogIngredient {
	private Stage stage;
	private TextField txtAmount = new TextField();
	private TextField txtUnit = new TextField();
	private TextField txtName = new TextField();
	private Button btnOk;
	private Button btnCancel;
	private HBox buttons;
	
	public DialogIngredient(Stage primaryStage, int selectIndex, ObservableList<Ingredient> ingredientsTable){
		stage = new Stage();
		stage.initOwner(primaryStage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Pøidat/editovat surovinu");

		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setHgap(20);
		root.setVgap(10);
		

		root.add(new Text("Poèet:"), 1, 0);
		root.add(txtAmount, 2, 0);

		root.add(new Text("Jednotka:"), 1, 1);
		root.add(txtUnit, 2, 1);

		root.add(new Text("Surovina:"), 1, 2);
		root.add(txtName, 2, 2);
		
		if(selectIndex != -1){
			txtAmount.appendText(String.valueOf(ingredientsTable.get(selectIndex).getAmount()));
			txtUnit.appendText(ingredientsTable.get(selectIndex).getUnit());
			txtName.appendText(ingredientsTable.get(selectIndex).getIngredient());
		}		

		
		setButtons(selectIndex, ingredientsTable);
		root.add(buttons, 0, 3, 3, 2);

		stage.setScene(new Scene(root, 350, 200));
		stage.centerOnScreen();
		stage.show();
		
	}
	
	private void setButtons(int selectIndex, ObservableList<Ingredient> ingredientsTable){
		btnOk = new Button("Ok");
		btnOk.setDefaultButton(true); // enter -> zpusobi stisknuti
		btnOk.setOnAction(e -> {
			if(selectIndex != -1){
			ingredientsTable.get(selectIndex).setAmount(Integer.valueOf(txtAmount.getText()));
			ingredientsTable.get(selectIndex).setUnit(txtUnit.getText());
			ingredientsTable.get(selectIndex).setIngredient(txtName.getText());
			}
			
			else
				ingredientsTable.add(new Ingredient(Integer.valueOf(txtAmount.getText()),txtUnit.getText(), txtName.getText()));
			
			stage.close();
		});
		

		btnCancel = new Button("Cancel");
		btnCancel.setCancelButton(true);
		btnCancel.setOnAction(e -> {
			stage.close();
		});
		
		
		buttons = new HBox(10);
		buttons.setAlignment(Pos.CENTER_RIGHT);
		buttons.getChildren().addAll(btnOk, btnCancel);
		
	}
}
