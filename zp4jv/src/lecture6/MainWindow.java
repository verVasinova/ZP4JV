package lecture6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;

public class MainWindow extends Application {

	private static Map<String, Recipe> recipes = new HashMap<>();
	
	private ObservableList<String> recipesNames = FXCollections
			.observableList(new ArrayList<String>());

	private ListView<String> listRecipes;
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// ----------------------------------------------------------------------------
		
		loadRecipes();
		listRecipes = new ListView<String>();
		setRecipesNames();
		listRecipes.setItems(recipesNames);

		listRecipes.setOnMouseClicked(e -> {
			if (e.getClickCount() > 1)
				addEditRecipeWindow(primaryStage, listRecipes.getSelectionModel()
						.getSelectedItem(), listRecipes.getSelectionModel().getSelectedIndex());

		});
		
		
		Menu menuFile = new Menu("_Soubor");
		menuFile.setMnemonicParsing(true);

		MenuItem mnuNew = new MenuItem("_Uložit");
		mnuNew.setAccelerator(new KeyCodeCombination(KeyCode.S,
				KeyCombination.CONTROL_DOWN));

		MenuItem mnuExit = new MenuItem("_Konec");
		mnuExit.setAccelerator(new KeyCodeCombination(KeyCode.X,
				KeyCombination.CONTROL_DOWN));
		mnuExit.setOnAction(e -> {
			primaryStage.close();
		});

		menuFile.getItems().addAll(mnuNew, new SeparatorMenuItem(), mnuExit);

		Menu menuRecipe = new Menu("_Recept");
		menuRecipe.setMnemonicParsing(true);

		MenuItem mnuAddRecipe = new MenuItem("_Pøidat...");
		mnuAddRecipe.setMnemonicParsing(true);
		mnuAddRecipe.setAccelerator(new KeyCodeCombination(KeyCode.A,
				KeyCombination.CONTROL_DOWN));
		mnuAddRecipe.setOnAction(e -> addEditRecipeWindow(primaryStage, null, -1));

		MenuItem mnuEditRecipe = new MenuItem("_Upravit...");
		mnuEditRecipe.setMnemonicParsing(true);
		mnuEditRecipe.setAccelerator(new KeyCodeCombination(KeyCode.E,
				KeyCombination.CONTROL_DOWN));
		mnuEditRecipe.setOnAction(e -> {
			addEditRecipeWindow(primaryStage, listRecipes.getSelectionModel()
					.getSelectedItem(), listRecipes.getSelectionModel().getSelectedIndex());
		});
		MenuItem mnuDeleteRecipe = new MenuItem("_Odstranit");
		mnuDeleteRecipe.setMnemonicParsing(true);
		mnuDeleteRecipe.setAccelerator(new KeyCodeCombination(KeyCode.D,
				KeyCombination.CONTROL_DOWN));
		
		mnuDeleteRecipe.setOnAction(e -> {
			if (listRecipes.getSelectionModel().getSelectedItem() != null) {
				recipesNames.remove(listRecipes.getSelectionModel()
						.getSelectedIndex());
				recipes.remove(listRecipes.getSelectionModel()
						.getSelectedItem());
			}

		});
		menuRecipe.getItems().addAll(mnuAddRecipe, mnuEditRecipe, mnuDeleteRecipe);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menuFile, menuRecipe);


		BorderPane root = new BorderPane();
		root.setTop(menuBar);
		root.setCenter(listRecipes);

		primaryStage.setTitle("Recepty");
		primaryStage.setScene(new Scene(root, 500, 350));
		primaryStage.show();

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

		System.out.println(xmlFiles.length);
		for (File f : xmlFiles) {
			Recipe r = dom.loadRecipe(new FileInputStream(f));
			recipes.put(r.getName(), r);
		}

	}

	private void setRecipesNames() {
		for (String s : recipes.keySet()) {
			recipesNames.add(s);
		}
	}


	private void addEditRecipeWindow(Stage primaryStage, String name, int index) {
		@SuppressWarnings("unused")
		DialogAddEditRecipe d = new DialogAddEditRecipe(primaryStage, name, index, recipesNames, recipes);
		
	}

	
	
	
}
