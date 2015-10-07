package lecture7;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IngredientEditController {
	private Stage stage;
	private Ingredient ing;
	
	@FXML private TextField txtName;
	@FXML private TextField txtUnit;
	@FXML private TextField txtAmount;
	private final static Logger LOGGER = Logger.getLogger(RecipesMainController.class.getName()); 
	
	public void okBtnAction(){
		if(isSet()){
		ing.setAmount(Integer.valueOf(txtAmount.getText()));
		ing.setUnit(txtUnit.getText());
		ing.setIngredient(txtName.getText());
		}
		
		else
			LOGGER.warning("Nelze uložit, nic nevyplnìno");
	}
	
	public void cancelBtnAction(){
		stage.close();
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setIngredient(Ingredient i) {
		if(ing != null){
			this.ing = i;
			txtName.setText(i.getIngredient());
			txtUnit.setText(i.getUnit());
			txtAmount.setText(Integer.toString(i.getAmount()));
		}
		else
			ing = new Ingredient();
		
	}
	
	public Ingredient getIngredient(){
		return ing;
	}
	
	public boolean isSet(){
		if(txtName.getText() != "" && txtUnit.getText() != "" && txtAmount.getText().matches("\\d+"))
			return true;
		
		return false;
	}
}
