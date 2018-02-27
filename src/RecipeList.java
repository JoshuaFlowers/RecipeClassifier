import java.util.ArrayList;
public class RecipeList extends ArrayList<Ingredient>{
	
	String Region;
	//public Region Recipes;
	
	public RecipeList(String region){
		super();
		this.Region=region;
	}
	
	public RecipeList(Recipe recipe, String region){
		this.Region=region;
		for(String ingred:recipe.Ingredients){
			addIngredient(ingred);
		}
	}
	
	public RecipeList(Recipe recipe){
		this.Region=recipe.Region;
		for(String ingred:recipe.Ingredients){
			addIngredient(ingred);
		}
	}
	
	
	public String[] getRegions(){
		ArrayList<Ingredient> Regions=new ArrayList<Ingredient>();
		return null;
	}
	
	public String toString(){
		String retString=this.Region+"\n";
		for(Ingredient a:this){
			retString.concat(a+"\n");
		}
		return retString;
	}
	
	public void print(RecipeList lists){

			for (Ingredient a : lists) {
				System.out.println(a.toString()+"\n");
				System.out.println(a+"\n");
			}
		
	}
	
	public void addIngredient(String ingredient) {
		boolean ingredientAdded = false;
		// check if ingredient is already present and add count accordingly
		for (Ingredient ing : this) {
			if (!ingredientAdded) {
				if (ing.equals(ingredient)) {
					ing.addCount();
					ingredientAdded = true;
				}
			}
		}
		// add new ingredient to list if not already present
		if (!ingredientAdded) {
			this.add(new Ingredient(ingredient));
		}
	}
	
	public void addIngredient(ArrayList<Ingredient> Ingredients, String ingredient) {
		boolean ingredientAdded = false;
		// check if ingredient is already present and add count accordingly
		for (Ingredient ing : Ingredients) {
			if (!ingredientAdded) {
				if (ing.equals(ingredient)) {
					ing.addCount();
					ingredientAdded = true;
				}
			}
		}
		// add new ingredient to list if not already present
		if (!ingredientAdded) {
			Ingredients.add(new Ingredient(ingredient));
		}
	}

}
