import java.io.*;

import javax.swing.JTree;
import java.util.*;

public class Visuals {

	private static ArrayList<Recipe> Recipes = new ArrayList<Recipe>();
	private static ArrayList<Ingredient> IngredientList = new ArrayList<Ingredient>();
	private static ArrayList<RecipeList> RegionalList = new ArrayList<RecipeList>();
	private static String inputFileLocation = "/Users/Joshua/Dropbox/School/440/Project/Recipes.csv";
	private static String outputFileLocation = "/Users/Joshua/Dropbox/School/440/Project/Ingredient_Frequency_Regional.csv";

	public static void main(String[] args) {
		readRecipes();
		IngredientList = getIngredients();
		Hashtable<String,ArrayList<String>> recipeHash=new Hashtable<String,ArrayList<String>>();

		// printAllRecipes();
		ArrayList<RecipeList> regionals = GetRegionalRecipes();
		writeToFile(regionals);
		Collections.sort(IngredientList, new Comparator<Ingredient>() {
			@Override
			public int compare(Ingredient in1, Ingredient in2) {
				return ((Integer) in2.getCount()).compareTo(((Integer) in1
						.getCount()));
			}
		});

		/*Collections.sort(Recipes, new Comparator<Ingredient>() {
			@Override
			public int compare(Ingredient in1, Ingredient in2) {
				return ((Integer) in2.getCount()).compareTo(((Integer) in1
						.getCount()));
			}
		});*/

		/*for(Ingredient ing:IngredientList){
		subDivide(Recipes, ing);
		}*/


		String fileLocation="/Users/Joshua/Dropbox/School/440/Project/subLists.csv";
		try{
			FileWriter writer=new FileWriter(fileLocation);
			//subDivide(Recipes, new ArrayList<Ingredient>(IngredientList.subList(0,2)), writer);
			subDivide(Recipes,IngredientList,writer);
			writer.flush();
			writer.close();
		}
		catch(IOException e){}




	}
	/*
	 * for(Recipe rec:Recipes){ } Collections.sort(rec.Ingredients,new
	 * Comparator<Ingredient>(){
	 * 
	 * @Override public int compare(Ingredient in1, Ingredient in2){ return
	 * ((Integer)in2.getCount()).compareTo(((Integer)in1.getCount())); } });
	 * } String
	 * fileLocation="/Users/Joshua/Dropbox/School/440/Project/SortedRecipes.csv"
	 * ; try { FileWriter writer = new FileWriter(fileLocation); for (Recipe
	 * rec:Recipes) { writer.append(rec+"\n"); System.out.println(rec); }
	 * writer.flush(); writer.close(); } catch (IOException e) {
	 * 
	 * }
	 */

	/*
	 * ArrayList<Recipe> recs=new ArrayList<Recipe>(Recipes); int index=0;
	 * while(index<IngredientList.size()&&recs.size()>1){ int count=0;
	 * for(Recipe rec:recs){ if(rec.contains(IngredientList.get(index))){
	 * count++; } } }
	 * 
	 * 
	 * for(Ingredient ing:IngredientList){
	 * 
	 * }*
	 */


	
	
	
	
	
	
	static ArrayList<Recipe> subDivide(ArrayList<Recipe> recipes, ArrayList<Ingredient> ingredients, FileWriter writer) {
		if(recipes.size()>20000){
			for(Ingredient ingred:ingredients){
				if(ingredients.indexOf(ingred)<ingredients.size()&&ingred.getCount()>10000){
					System.out.print(ingred.getName()+", ");
					subDivide(subList(recipes, ingred, writer),new ArrayList<Ingredient>(ingredients.subList(ingredients.indexOf(ingred)+1, ingredients.size())), writer);

				}

			}
			System.out.println("\n\n\n");
		}
		
		return null;

	}

	static ArrayList<Recipe> subList(ArrayList<Recipe> recipes, Ingredient ingredient, FileWriter writer){
		ArrayList<Recipe> sublist=new ArrayList<Recipe>();
		
		try{writer.append("\n\n\n"+ingredient+"\n\n\n\n\n\n");}catch(IOException e){}
		for(Recipe rec:recipes){
			if(rec.contains(ingredient)){
				sublist.add(rec);
				try {
					writer.append(rec.Ingredients.toString().replaceAll("[\\[\\]]", "")+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NullPointerException e){

				}
			}
		}
		//System.out.println(Integer.toString(sublist.size())+" recipes with "+ingredient.getName());
		return sublist;
	}


	
	


	// Read recipes from file to ArrayList "Recipes"
	static void readRecipes() {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {

			br = new BufferedReader(new FileReader(inputFileLocation));
			while ((line = br.readLine()) != null) {

				String[] currentLine = line.split(cvsSplitBy);
				Recipe currentRecipe = new Recipe(currentLine[0]);
				/*
				 * String[] recipe=new String[currentLine.length-1];
				 * System.arraycopy(currentLine,1,recipe,0,recipe.length-2);
				 */
				String[] recipe = java.util.Arrays.copyOfRange(currentLine, 1,
						currentLine.length);
				ArrayList<String> ingredients = new ArrayList<String>();
				for (String a : recipe) {
					ingredients.add(a);
				}
				currentRecipe.setIngredients(ingredients);
				Recipes.add(currentRecipe);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	static void getCommonIngredients(RecipeList recipes) {

	}

	static void writeToFile(ArrayList<RecipeList> regionals) {
		try {
			FileWriter writer = new FileWriter(outputFileLocation);
			// writer.append(String.valueOf(Recipes.size()) +
			// ",Total Recipes \n");
			// writer.append(String.valueOf(list.size()) +
			// ",Total Ingredients \n");
			for (RecipeList lists : regionals) {
				writer.append("\n" + lists.Region + ", Count, Percent\n");
				System.out.println(lists.Region + " " + lists.size());
				for (Ingredient a : lists) {
					double percent=(double)((int)((((double)a.getCount())/((double)lists.size()))*10000))/100;
					writer.append(a.toString() + "," + percent + "\n");
					//System.out.println(a + "\n");
				}
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {

		}

	}

	// Categorize ingredients according to region
	static ArrayList<RecipeList> GetRegionalRecipes() {
		ArrayList<RecipeList> RegionalRecipes = new ArrayList<RecipeList>();

		for (Recipe rec : Recipes) {
			boolean added = false;
			for (RecipeList recList : RegionalRecipes) {
				if (rec.Region.equalsIgnoreCase(recList.Region)) {
					for (String ing : rec.Ingredients) {
						recList.addIngredient(ing);
						added = true;
					}
				}
			}
			if (added == false) {
				RecipeList newList = new RecipeList(rec.Region);
				for (String ing : rec.Ingredients) {
					newList.addIngredient(ing);
				}
				RegionalRecipes.add(newList);
				added = true;
				System.out.println("add");
			}
		}
		/*
		 * for(RecipeList a:RegionalRecipes){ System.out.println(a.get(2)); }
		 */
		return RegionalRecipes;
	}

	// Make list and count of all ingredients, regardless of region
	static ArrayList<Ingredient> getIngredients() {
		ArrayList<Ingredient> Ingredients = new ArrayList<Ingredient>();
		for (Recipe rec : Recipes) {
			for (String ing : rec.Ingredients) {
				addIngredient(Ingredients, ing);
			}
		}
		return Ingredients;
	}

	// Add ingredient to list. If already on the list, add 1 to count.
	static void addIngredient(ArrayList<Ingredient> Ingredients,
			String ingredient) {
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

	static void printAllRecipes() {
		for (Recipe a : Recipes) {
			ArrayList<String> ingredients = a.Ingredients;
			// System.out.println(a.Region);
			for (String ingredient : ingredients) {
				System.out.println(ingredient);
			}
			System.out.println();
		}
	}

}
