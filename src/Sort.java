import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public class Sort {

	private static String inputFileLocation = "/Users/Joshua/Dropbox/School/440/Project/Recipes.csv";
	private static String ingredientOutputFileLocation = "/Users/Joshua/Dropbox/School/440/Project/Ingredient_Frequency_Regional_Probabilities.csv";
	private static String recipeOutputFileLocation = "/Users/Joshua/Dropbox/School/440/Project/Regional_Recipes_Sorted_Ingredients.csv";
	private static ArrayList<Recipe> Recipes = new ArrayList<Recipe>();
	private static ArrayList<Region> Regions = new ArrayList<Region>();
	private static ArrayList<Ingredient> allIngredients;
	private static ArrayList<FeatureVector> Features = new ArrayList<FeatureVector>();
	private static ArrayList<String> ingredientList = new ArrayList<String>();
	public static ArrayList<String> regionNames = new ArrayList<String>();
	

	public static void main(String args[]) {
		readRecipes();
		ArrayList<Recipe> testing = new ArrayList<Recipe>();
		// Separate testing data from training data
		for (int i = 0; i < Recipes.size() * .2; i++) {
			Random rand = new Random();
			int nextRand = rand.nextInt(Recipes.size());
			testing.add(Recipes.remove(i));
		}
		allIngredients = getAllIngredients();
		for (Ingredient i : allIngredients) {
			ingredientList.add(i.getName());
		}
		Regions = getRegionalRecipes();
		Features = getFeatureVectors();
		for (FeatureVector r : Features) {
			regionNames.add(r.getRegion());
		}
		
		

		// randomly test testing data

		String recSite="http://www.bettycrocker.com/recipes/asian-salmon-with-potatoes-and-broccoli-sheet-pan-dinner/06437123-298a-41cc-8f77-53dc54c89946";
		String siteContent=htmlGrab(recSite);
		
		
		//testData(testing);
		
		interact();
		
		//interactSite();
		



	}
	
	static void interactSite() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.println("Please enter web address of recipe");
			
			ArrayList<String> recipe = new ArrayList<String>();
			String line = new String();
			try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("This recipe appears to be from: "+ categorizeWebRec(htmlGrab(line)));
		}
	}
	
	static String categorizeWebRec(String Recipe){
		ArrayList<String> siteIngredients=new ArrayList<String>();
		for(Ingredient a:allIngredients){
			if(Recipe.contains(a.getName())){
				siteIngredients.add(a.getName());
			}
		}
		System.out.println("Ingredients: "+siteIngredients);
		return categorize(siteIngredients);
		
	}
	
	static String htmlGrab(String site){
	    URL url;
	    String contents="";
		try {
			url = new URL(site);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		    while (true) {
		      String line = reader.readLine();
		      if (line == null) {
		        break;
		      }

		      contents = contents + line;
		    }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contents;
	    
	}

	static void testData(ArrayList<Recipe> testing){
		Random rand = new Random();
		int correct = 0;
		for (int i = 1; i <= 100; i++) {
			int next = rand.nextInt(testing.size() - 1);
			Recipe nextRec = testing.get(next);
			System.out.println("Region: "+nextRec.Region);
			System.out.println(nextRec.Ingredients);
			String category = categorize(nextRec.Ingredients);
			System.out.println("FindMyiFood's Categorization: "+category);
			if (category.equals(nextRec.Region)) {
				correct++;
			}
			System.out.println();
		}
		System.out.println("Accuracy: " + correct + "/" + 100);
	}
	
	
	
	static void interact() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out
					.println("Please enter recipe: Ingredient1,Ingredient2,Ingredient3...");
			ArrayList<String> recipe = new ArrayList<String>();
			String[] line = new String[0];
			try {
				line = br.readLine().replaceAll(" ", "").split(",");
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < line.length; i++) {
				recipe.add(line[i]);
			}
			System.out.println("This recipe appears to be from: "
					+ categorize(recipe));
		}
	}

	static String categorize(ArrayList<String> Recipe) {
		ArrayList<Integer> indicies = new ArrayList<Integer>();
		ArrayList<Float> averages = new ArrayList<Float>();
		int[] counts = new int[Recipe.size()];
		for (String a : Recipe) {
			if (ingredientList.indexOf(a) > -1) {
				indicies.add(ingredientList.indexOf(a));
			} else {
				System.out.println("Sorry, we could not find " + a);
			}
			// System.out.println(ingredientList.indexOf(a));
		}
		ArrayList<Integer> minDists = new ArrayList<Integer>();
		int[] recVec = featurize(Recipe);
		// System.out.println(Arrays.toString(recVec));

		for (FeatureVector feat : Features) {
			int minDist = 100000;
			for (int[] vec : feat) {
				int dist = distance(recVec, vec);
				if (dist < minDist) {
					minDist = dist;
				}
			}
			minDists.add(minDist);
		}
		int minimum = Collections.min(minDists);
		int numOcc = 0;
		for (Integer i : minDists) {
			if (i.equals(minimum)) {
				numOcc++;
			}
		}
		if (numOcc > 1) {
			ArrayList<String> compRegs = new ArrayList<String>();
			for (int i = 0; i < minDists.size(); i++) {
				if (minDists.get(i).equals(minimum)) {
					compRegs.add(regionNames.get(i));
				}
			}
			IngredientList rec = new IngredientList();
			for (String ing : Recipe) {
				rec.add(new Ingredient(ing));
			}
			return Categorize.main(rec, compRegs);
		}
		return regionNames.get(minDists.indexOf(Collections.min(minDists)));
		/*
		 * for(FeatureVector feat:Features){ boolean recFit=true; float
		 * average=0; for(int[] vec:feat){ int sum=0; int index=0; for(Integer
		 * i:indicies){ sum+=vec[i.intValue()]; counts[index]+=vec[i]; index++;
		 * } // System.out.println(sum); average+=sum; } if(feat.size()>0){
		 * average/=feat.size();
		 * //System.out.println((float)(average)/((float)(feat.size()))); }
		 * for(int i=0;i<counts.length;i++){ if(counts[i]==0){ recFit=false; } }
		 * if(recFit){ averages.add(average); } } float maxAvg=0; for(Float
		 * i:averages){ System.out.println(i); if(i>maxAvg){ maxAvg=i; } }
		 * return regionNames.get(averages.indexOf(maxAvg));
		 */
	}

	static void getMaxProb(ArrayList<String> Recipe) {
		ArrayList<Float> probs = new ArrayList<Float>();
		for (Region reg : Regions) {

		}
	}

	static int distance(int[] in1, int[] in2) {
		int dist = 0;
		for (int i = 0; i < in1.length; i++) {
			dist += Math.pow((in1[i] - in2[i]), 2);
		}

		return dist;
	}

	static int[] featurize(ArrayList<String> Recipe) {
		int[] recFeature = new int[ingredientList.size()];
		Arrays.fill(recFeature, 0);
		for (String ing : Recipe) {
			try {
				recFeature[ingredientList.indexOf(ing)] = 1;
			} catch (ArrayIndexOutOfBoundsException a) {
				System.out.println("Index out of bounds");
			}
		}
		return recFeature;
	}

	static ArrayList<FeatureVector> getFeatureVectors() {
		ArrayList<FeatureVector> featureVectors = new ArrayList<FeatureVector>();
		for (Region reg : Regions) {
			FeatureVector features = new FeatureVector(reg.getRegion());
			for (IngredientList ingredList : reg.getRecipes()) {
				int[] newFeature = new int[allIngredients.size()];
				Arrays.fill(newFeature, 0);
				for (Ingredient ingred : ingredList) {
					newFeature[ingredientList.indexOf(ingred.getName())] = 1;
				}
				features.add(newFeature);
			}
			featureVectors.add(features);

		}
		return featureVectors;
	}

	static void readRecipes() {

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {

			br = new BufferedReader(new FileReader(inputFileLocation));
			while ((line = br.readLine()) != null) {

				String[] currentLine = line.split(cvsSplitBy);
				Recipe currentRecipe = new Recipe(currentLine[0]);
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

	static ArrayList<Ingredient> getAllIngredients() {
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

	// Categorize ingredients according to region
	static ArrayList<Region> getRegionalRecipes() {
		ArrayList<Region> regions = new ArrayList<Region>();

		for (Recipe rec : Recipes) {
			boolean added = false;

			for (Region region : regions) {
				if (region.isRegion(rec.Region)) {
					region.addRecipe(rec);
					added = true;
				}
			}

			if (!added) {
				Region newRegion = new Region(rec.Region);
				newRegion.addRecipe(rec);
				regions.add(newRegion);
				added = true;
			}
		}
		return regions;
	}

	static void writeRegionalIngredientsToFile(ArrayList<Region> Regions) {
		try {
			FileWriter writer = new FileWriter(ingredientOutputFileLocation);
			for (Region reg : Regions) {
				writer.append("*");
				/*for (IngredientList recipe : reg.getRecipes()) {
					// System.out.println(recipe);
				}*/
				writer.append(reg.getRegion() + "\n");
				for (Ingredient a : reg.getIngredients()) {
					float probability = (float) a.getCount()
							/ (float) reg.getRecipes().size();
					writer.append(a.getName() + "," + probability + "\n");
				}

			}
			writer.flush();
			writer.close();
		} catch (IOException e) {

		}

	}

	static void writeRegionalRecipesToFile(ArrayList<Region> Regions) {
		try {
			FileWriter writer = new FileWriter(recipeOutputFileLocation);
			for (Region reg : Regions) {
				writer.append("\n" + reg.getRegion() + "\n");
				for (IngredientList a : reg.getRecipes()) {
					writer.append(a.toString() + "\n");
				}
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {

		}
	}

}
