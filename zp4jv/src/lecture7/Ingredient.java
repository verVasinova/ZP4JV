package lecture7;

public class Ingredient {
	int amount;
	String unit;
	String ingredient;
	
	public Ingredient(int amount, String unit, String ingredient) {
		super();
		this.amount = amount;
		this.unit = unit;
		this.ingredient = ingredient;
	}

	public Ingredient() {
		// TODO Auto-generated constructor stub
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	@Override
	public String toString() {
		return "\n" + amount + " " + unit + " " + ingredient;
	}

}
