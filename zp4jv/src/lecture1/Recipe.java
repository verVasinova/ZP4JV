package lecture1;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
	int servings;
	String name;
	int cooktime;
	List<Ingredient> ingredients;
	List<String> directions;
	
		
	public Recipe() {
		super();
		servings = 0;
		name = "a";
		ingredients = new ArrayList<>();;
		directions = new ArrayList<>();;
	}

	public int getServings() {
		return servings;
	}
	public void setServings(int servings) {
		this.servings = servings;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getCooktime() {
		return cooktime;
	}

	public void setCooktime(int cooktime) {
		this.cooktime = cooktime;
	}



	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	
	public List<String> getDirections() {
		return directions;
	}

	public void setDirections(List<String> directions) {
		this.directions = directions;
	}

	public void addIngredient(int amount, String unit, String ingredient){
		ingredients.add(new Ingredient(amount, unit, ingredient));
	}
	
	public void addStep(String step){
		directions.add(step);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Porci: " + servings+ "\n");
		result.append("Nazev: " + name + "\n");
		result.append("Doba vareni: " + cooktime + "\n");
		
		for(Ingredient i : ingredients){
			result.append("- " + i.getAmount() + " " + i.getUnit() + " " + i.getIngredient() + "\n");
		}
		
		for(String s : directions){
			result.append(s + "\n");
		}
		
		
		return result.toString();
	}

}
