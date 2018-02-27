import java.util.*;
public class Region{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String Name;
	private ArrayList<IngredientList> Recipes=new ArrayList<IngredientList>();
	private ArrayList<Ingredient> Ingredients=new ArrayList<Ingredient>();
	
	public Region(String name){
		this.Name=name;
	}
	
	public Region(IngredientList recipe){
		this.Name=recipe.getRegion();
		Recipes.add(recipe);
		for(Ingredient ingred:recipe){
			if(!Ingredients.contains(ingred)){
				Ingredients.add(ingred);
			}
		}
		Collections.sort(Ingredients, new Comparator<Ingredient>() {
			@Override
			public int compare(Ingredient in1, Ingredient in2) {
				return ((Integer) in2.getCount()).compareTo(((Integer) in1
						.getCount()));
			}
		});
	}
	
	public Region(ArrayList<Ingredient> recipe, String name){
		this.Name=name;
		
	}
	
	public void addRecipe(Recipe recipe){
		IngredientList newRecipe=new IngredientList(recipe.Region);
		for(String ingred:recipe.Ingredients){
			//System.out.print(ingred+", ");
			newRecipe.add(addIngredient(ingred));
		}
		//System.out.println();
		Recipes.add(newRecipe);
	}
	
	public Ingredient addIngredient(String ingredient) {
		boolean ingredientAdded = false;
		// check if ingredient is already present and add count accordingly
		for (Ingredient ing : Ingredients) {
			if (!ingredientAdded) {
				if (ing.equals(ingredient)) {
					ing.addCount();
					ingredientAdded = true;
					Collections.sort(Ingredients, new Comparator<Ingredient>() {
						@Override
						public int compare(Ingredient in1, Ingredient in2) {
							return ((Integer) in2.getCount()).compareTo(((Integer) in1
									.getCount()));
						}
					});
					return ing;
				}
			}
		}
		// add new ingredient to list if not already present
			Ingredient newIngred=new Ingredient(ingredient);
			Ingredients.add(newIngred);
			Collections.sort(Ingredients, new Comparator<Ingredient>() {
				@Override
				public int compare(Ingredient in1, Ingredient in2) {
					return ((Integer) in2.getCount()).compareTo(((Integer) in1
							.getCount()));
				}
			});
			return newIngred;
	}
	
	
	
	
	
	public boolean isRegion(String name){
		return Name.equalsIgnoreCase(name);
	}
	
	public boolean contains(String Ingredient){
		boolean Contains=false;
		for(Ingredient ingred:Ingredients){
			if (ingred.equals(Ingredient)){
				Contains=true;
			}
		}
		return Contains;
	}
	
	public boolean isRegion(Recipe recipe){
		return Name.equalsIgnoreCase(recipe.Region);
	}
	
	public String getRegion(){
		return this.Name;
	}
	
	public ArrayList<Ingredient> getIngredients(){
		return this.Ingredients;
	}
	
	public ArrayList<IngredientList> getRecipes(){
		return this.Recipes;
	}

}
