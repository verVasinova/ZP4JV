package lecture7;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RecipeEditController{
	
	private Stage stage;
	private Recipe recipe;
	ObservableList<Ingredient> ingredients = FXCollections.observableList(new ArrayList<>());
	
	@FXML private TextField txtName;
	@FXML private TextField txtServings;
	@FXML private TextField txtCooktime;
	@FXML private TableView<Ingredient> table;
	@FXML private Text lbNotification;
	@FXML private TextArea steps;
	
	
	private final static Logger LOGGER = Logger.getLogger(RecipesMainController.class.getName()); 
	
	@FXML public void cancelBtnAction() {
		stage.close();
	}
	
	@FXML public void okBtnAction() {
		if(isSet()){
		recipe.setName(txtName.getText());
		recipe.setCooktime(Integer.valueOf(txtCooktime.getText()));
		recipe.setServings(Integer.valueOf(txtServings.getText()));
		if(ingredients.size() > 0)
		recipe.setIngredients(ingredients.subList(0, ingredients.size()));
		
		List<String> step = new ArrayList<>();
		for(String s : steps.getText().split("\n")){
			step.add(s);
		}
		
		recipe.setDirections(step);;
		stage.close();
		}
		
		else{
			LOGGER.warning("Nelze uložit, nic není vyplnìno");
		}
	}
	
	public void setStage(Stage stage) throws SecurityException, FileNotFoundException, IOException {
		this.stage = stage;
		
	}
	
	public void setRecipe(Recipe r){
		if(r != null){
			recipe = r;
			txtName.setText(r.getName());
			txtServings.setText(Integer.toString(r.getServings()));
			txtCooktime.setText(Integer.toString(r.getCooktime()));
			
			ingredients = FXCollections
					.observableList(recipe.getIngredients());
			table.setItems(ingredients);
			
			for(String s : recipe.getDirections()){
				steps.appendText(s + "\n");
			}
		}
		
		else{
			recipe = new Recipe();
		}
		
	}
	
	public Recipe getRecipe(){
		return recipe;
	}
	
	public void addBtnAction() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ingredient-edit.fxml"));
		Parent root = loader.load();
		IngredientEditController controller = loader.getController();
		
		Scene scene = new Scene(root,300, 300);
		
		Stage stage2 = new Stage();
		stage2.initOwner(stage);
		stage2.initModality(Modality.WINDOW_MODAL);
		stage2.setTitle("Pøidat surovinu");
		stage2.setScene(scene);
		
		controller.setStage(stage2);
		controller.setIngredient(null);
		stage2.showAndWait();
		
		if(controller.isSet()){
			ingredients.add(controller.getIngredient());
		}
		else{
			LOGGER.warning("zrušeno");
		}
	}

	public void editBtnAction() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ingredient-edit.fxml"));
		Parent root = loader.load();
		IngredientEditController controller = loader.getController();
		
		Scene scene = new Scene(root,300, 300);
		
		Stage stage2 = new Stage();
		stage2.initOwner(stage);
		stage2.initModality(Modality.WINDOW_MODAL);
		stage2.setTitle("Upravit surovinu");
		stage2.setScene(scene);
		
		controller.setStage(stage2);
		controller.setIngredient(table.getSelectionModel().getSelectedItem());
		stage2.showAndWait();
		
		ingredients.set(table.getSelectionModel().getSelectedIndex(),controller.getIngredient());
	}

	public void deleteBtnAction() {
		ingredients.remove(table.getSelectionModel().getSelectedIndex());
	}
	
	public boolean isSet(){
		if(txtName.getText() != "" && txtCooktime.getText().matches("\\d+") && txtServings.getText().matches("\\d+"))
			return true;
		
		return false;
	}

}
