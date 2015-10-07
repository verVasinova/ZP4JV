package lecture7;

import java.io.FileInputStream;
import java.util.logging.LogManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RecipesMainWindow extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("recipes-main.fxml"));
		Parent root = loader.load();
		
		RecipesMainController controller = loader.getController();
		controller.setPrimaryStage(primaryStage);
		
		Scene scene = new Scene(root, 500, 350);

		primaryStage.setTitle("Recepty");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
