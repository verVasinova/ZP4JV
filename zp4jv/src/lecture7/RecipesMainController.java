package lecture7;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class RecipesMainController implements Initializable{
	private final static Logger LOGGER = Logger.getLogger(RecipesMainController.class.getName()); 
	
	private Stage primaryStage;
	private static Map<String, Recipe> recipes = new HashMap<>();
	ObservableList<String> recipesNames = FXCollections
			.observableList(new ArrayList<String>());
	
	@FXML
	private ListView<String> listRecipes;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		LOGGER.setLevel(Level.INFO); 
		try {
			loadRecipes();
		} catch (Exception e) {
			LOGGER.severe("Soubory nenaèteny!");
		}
		listRecipes.setItems(recipesNames);
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	@FXML
	public void listClickAction(MouseEvent e) throws IOException {
		if (e.getClickCount() > 1)
    		editAction();
	}
	
	@FXML
	public void addAction() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("recipe-editing-window.fxml"));
		Parent root = loader.load();
		RecipeEditController controller = loader.getController();
		
		Scene scene = new Scene(root, 500, 450);
		
		Stage stage = new Stage();
		stage.initOwner(primaryStage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Pøidat recept");
		stage.setScene(scene);
		
		controller.setStage(stage);
		controller.setRecipe(null);
		
		stage.showAndWait();
		
		if(controller.isSet()){
			recipes.put(controller.getRecipe().getName(), controller.getRecipe());
			recipesNames.add( controller.getRecipe().getName());
		}
		else{
			LOGGER.warning("Nic neulozeno");
		}
	}
	
	public void editAction() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("recipe-editing-window.fxml"));
		Parent root = loader.load();
		RecipeEditController controller = loader.getController();
		
		Scene scene = new Scene(root, 500, 450);
		
		Stage stage = new Stage();
		stage.initOwner(primaryStage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Upravit recept");
		stage.setScene(scene);
		
		controller.setStage(stage);

		controller.setRecipe(recipes.get(listRecipes.getSelectionModel().getSelectedItem()));
		
		stage.showAndWait();
		
		recipes.replace(controller.getRecipe().getName(), controller.getRecipe());
		recipesNames.set(listRecipes.getSelectionModel().getSelectedIndex(), controller.getRecipe().getName());
	}
	
	public void deleteAction(){
		recipes.remove(listRecipes.getSelectionModel().getSelectedItem());
		recipesNames.remove(listRecipes.getSelectionModel().getSelectedIndex());
		LOGGER.info(listRecipes.getSelectionModel().getSelectedItem() + " - recept smazán");
	}
	
	@FXML
	public void exitAction() {
		primaryStage.close();
	}
	
	private void loadRecipes() throws FileNotFoundException, Exception {
		File[] xmlFiles = null;
		File dir = new File("./");
		DOMRecipeReaderWriter dom = new DOMRecipeReaderWriter();

		xmlFiles = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File folder, String name) {
				return name.toLowerCase().endsWith(".xml");
			}
		});

		LOGGER.info("naèteno " + xmlFiles.length + " položek");
		for (File f : xmlFiles) {
			Recipe r = dom.loadRecipe(new FileInputStream(f));
			recipes.put(r.getName(), r);
			recipesNames.add(r.getName());
		}

	}

}
