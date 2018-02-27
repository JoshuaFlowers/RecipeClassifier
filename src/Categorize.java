import java.io.*;
import java.util.*;

public class Categorize {

	public static ArrayList<IngredientList> regions;
	public static String main(IngredientList rec, ArrayList<String> Regs) {
		String inputFile = "/Users/Joshua/Dropbox/School/440/Project/Ingredient_Frequency_Regional_Probabilities.csv";
		regions = getRegions(inputFile);
		return categorize(rec,Regs);
	}

	static ArrayList<IngredientList> getRegions(String input) {
		ArrayList<IngredientList> regionsList = new ArrayList<IngredientList>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(input));
			String line = "";
			IngredientList currentRegion = new IngredientList();
			while ((line = br.readLine()) != null) {
				String[] next = line.split(",");
				if (next[0].charAt(0) == '*') {
					regionsList.add(currentRegion);
					currentRegion = new IngredientList(next[0].replaceAll(
							"[\\*]", ""));
					next = br.readLine().split(",");
				}
				int factor = 1;
				currentRegion.add(new Ingredient(next[0], (Float
						.parseFloat(next[1]) * factor)));
			}
			regionsList.add(currentRegion);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return regionsList;
	}

	static String categorize(IngredientList recipe, ArrayList<String> regs) {
		String region = null;
		float highestProb = 0;
		for (IngredientList reg : regions) {
			if (regs.contains(reg.getRegion())) {
				//System.out.println(reg.getRegion());
				float prob = 1;
				boolean fitsRegion = true;
				for (Ingredient ingred : recipe) {
					if (!reg.contains(ingred)) {
						fitsRegion = false;
					}
				}
				if (fitsRegion) {
					for (Ingredient ingred : recipe) {

						for (Ingredient a : reg) {
							if (a.equals(ingred)) {
								prob *= a.getProb();
							}
						}
						if (prob > highestProb) {
							highestProb = prob;
							region = reg.getRegion();
						}
					}
				}

			}
		}

		return region;

	}
}
