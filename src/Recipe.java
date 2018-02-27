import java.util.ArrayList;
public class Recipe {
	
	public String Region;
	public ArrayList<String> Ingredients;
	
	public Recipe(){
		
	}
	
	public Recipe(String region, ArrayList<String> ingredients){
		this.Region=region;
		this.Ingredients=ingredients;
	}
	
	public Recipe(String region){
		this.Region=region;
	}
	
	public void setRegion(String region){
		this.Region=region;
	}
	
	public void setIngredients(ArrayList<String> ingredients){
		this.Ingredients=ingredients;
	}
	
	public boolean contains(Ingredient ingredient){
		return Ingredients.contains(ingredient.getName());
	}
	
	public boolean contains(String ingredient){
		return Ingredients.contains(ingredient);
	}
	
}
