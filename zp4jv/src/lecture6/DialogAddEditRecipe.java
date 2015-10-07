package lecture6;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogAddEditRecipe {
	
	private Stage stage;
	
	private TextField txtName = new TextField();
	private TextField txtServings = new TextField();
	private TextField txtCooktime = new TextField();
	private Button btnOk;
	private Button btnCancel;
	private HBox buttons;
	
	private TableView<Ingredient> table;
	
	ObservableList<Ingredient> ingredientsTable = FXCollections
			.observableList(new ArrayList<Ingredient>());
	
	public DialogAddEditRecipe(Stage primaryStage, String name, int index, ObservableList<String> recipesNames, Map<String, Recipe> recipes){
		stage = new Stage();
		stage.initOwner(primaryStage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Pøidat/editovat recept");

		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setHgap(20);
		root.setVgap(10);

		ColumnConstraints column3 = new ColumnConstraints(400);
		column3.setPercentWidth(50);

		root.getColumnConstraints().addAll(new ColumnConstraints(10),
				new ColumnConstraints(100), column3, new ColumnConstraints(10)); 
		
		root.add(new Text("Název:"), 1, 0);
		root.add(txtName, 2, 0);

		root.add(new Text("Poèet porcí:"), 1, 1);
		root.add(txtServings, 2, 1);

		root.add(new Text("Doba pøípravy:"), 1, 2);
		root.add(txtCooktime, 2, 2);

		
		if (name != null) {
			Recipe r = recipes.get(name);
			txtName.appendText(r.getName());
			txtServings.appendText(String.valueOf(r.getServings()));
			txtCooktime.appendText(String.valueOf(r.getCooktime()));
			ingredientsTable.addAll(recipes.get(name).getIngredients());
		}


		table = setIngredientTable();
		table.setItems(ingredientsTable);
		root.add(table, 1, 4, 2, 3);
		
		
		HBox buttonsIngredient = setButtonsIngredients(stage, table);
		root.add(buttonsIngredient, 1, 7, 2, 2);
		
		root.add(new Text("Postup:"), 1, 9, 2, 1);
		
		TextArea areaSteps = new TextArea();
		if(name!= null){
			for(String s : recipes.get(name).getDirections()){
				areaSteps.appendText(s + "\n");
			}
		}
		
		root.add(areaSteps, 1,  10, 2, 4);
		
		btnCancel = new Button("Cancel");
		btnCancel.setCancelButton(true);
		btnCancel.setOnAction(e -> {
			for(Ingredient ing : recipes.get(name).getIngredients())
				System.out.println(ing.getIngredient());
			ingredientsTable = FXCollections
					.observableList(new ArrayList<Ingredient>());
			stage.close();
		});
		
		btnOk = new Button("Ok");
		btnOk.setDefaultButton(true); // enter -> zpusobi stisknuti
		btnOk.setOnAction(e -> {
			Recipe r = new Recipe();
			r.setName(txtName.getText());
			r.setCooktime(Integer.valueOf(txtCooktime.getText()));
			r.setServings(Integer.valueOf(txtServings.getText()));
			
			List<String> steps  = new ArrayList<>();
			for(String s : areaSteps.getText().split("\n")){
				steps.add(s);
			}
			r.setDirections(steps);
			
			if(name == null){
				
				r.setIngredients(getIngredientsFromTable(table));
				recipesNames.add(txtName.getText());
				recipes.put(txtName.getText(), r);
				
			}
			
			else{
				for(Ingredient ing : getIngredientsFromTable(table))
					System.out.println(ing.getIngredient());
				r.setIngredients(ingredientsTable.subList(0, ingredientsTable.size()));
				recipesNames.set(index, txtName.getText());
				recipes.put(txtName.getText(), r);
				
			}

			stage.close();
		});
		

		buttons = new HBox(10);
		buttons.setAlignment(Pos.CENTER_RIGHT);
		buttons.getChildren().addAll(btnOk, btnCancel);

		root.add(buttons, 0, 15, 3, 2);

		stage.setScene(new Scene(root, 450, 550));
		stage.centerOnScreen();
		stage.show();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TableView<Ingredient> setIngredientTable() {
		TableView<Ingredient> table = new TableView<Ingredient>();
		table.setEditable(true);
		table.setPrefSize(200, 150);

		TableColumn amountCol = new TableColumn("Poèet");
		TableColumn unitCol = new TableColumn("Jednotka");
		TableColumn nameCol = new TableColumn("Surovina");
		amountCol.prefWidthProperty().bind(table.widthProperty().divide(3));
		unitCol.prefWidthProperty().bind(table.widthProperty().divide(3));
		nameCol.prefWidthProperty().bind(table.widthProperty().divide(3.1));

		amountCol
				.setCellValueFactory(new PropertyValueFactory<Ingredient, String>(
						"amount"));
		unitCol.setCellValueFactory(new PropertyValueFactory<Ingredient, String>(
				"unit"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Ingredient, String>(
				"ingredient"));

		
		table.getColumns().addAll(amountCol, unitCol, nameCol);
		return table;
	}
	
	private List<Ingredient> getIngredientsFromTable(TableView<Ingredient> table){
		List<Ingredient> list = new ArrayList<>();
		for(Ingredient i : table.getItems()){
			list.add(i);
		}
		return list;
	}
	
	private HBox setButtonsIngredients(Stage stage, TableView<Ingredient> table) {
		// tlaèítka pro editaci tabulky
		//...............................................................
		Button btnAddIngredient = new Button("Pøidat");
		btnAddIngredient.setOnAction(e -> {
			newAddEditIngredientWindow(stage, -1);
		});
		Button btnEditIngredient = new Button("Upravit");
		btnEditIngredient.setOnAction(e -> {
			newAddEditIngredientWindow(stage,  table.getSelectionModel().getSelectedIndex());
			table.setVisible(false);
			table.setVisible(true);
		});
		Button btnDeleteIngredient = new Button("Smazat");
		btnDeleteIngredient.setOnAction(e -> {
			ingredientsTable.remove(table.getSelectionModel().getSelectedIndex());
		});
		
		btnAddIngredient.setMaxWidth(Double.MAX_VALUE);
		btnEditIngredient.setMaxWidth(Double.MAX_VALUE);
		btnDeleteIngredient.setMaxWidth(Double.MAX_VALUE);
		
		HBox buttonsIngredient = new HBox(10);
		buttonsIngredient.setAlignment(Pos.CENTER);
		HBox.setHgrow(btnAddIngredient, Priority.ALWAYS);
		HBox.setHgrow(btnEditIngredient, Priority.ALWAYS);
		HBox.setHgrow(btnDeleteIngredient, Priority.ALWAYS);
		buttonsIngredient.getChildren().addAll(btnAddIngredient, btnEditIngredient, btnDeleteIngredient);
		//...............................................................
		return buttonsIngredient;
	}
	
	public void newAddEditIngredientWindow(Stage primaryStage, int selectIndex){
		@SuppressWarnings("unused")
		DialogIngredient d = new DialogIngredient(primaryStage, selectIndex, ingredientsTable);
	}
}
