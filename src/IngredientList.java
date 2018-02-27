import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class IngredientList extends ArrayList<Ingredient>{

	private String Region;
	
	public IngredientList(){
		super();
	}
	
	public IngredientList(String Region){
		super();
		this.Region=Region;		
	}
	
	public boolean add(Ingredient ingred){
		boolean returnValue=super.add(ingred);
		Collections.sort(this, new Comparator<Ingredient>() {
			@Override
			public int compare(Ingredient in1, Ingredient in2) {
				return ((Integer) in2.getCount()).compareTo(((Integer) in1
						.getCount()));
			}
		});
		return returnValue;
	}
	
	public String getRegion(){
		return this.Region;
	}
	
	public void setRegion(String region){
		this.Region=region;
	}
	
	public boolean contains(Ingredient ingredient){
		boolean Contains=false;
		for(Ingredient ingred:this){
			if (ingred.equals(ingredient)){
				Contains=true;
			}
		}
		return Contains;
	}
	
	public boolean equals(IngredientList input){
		boolean equal=false;
		if(this.Region!=input.Region){
			return false;
		}
		if(this.size()!=input.size()){
			return false;
		}
		for(Ingredient i:input){
			if(!this.contains(i)){
				return false;
			}
		}
		return true;
	}
	
	public boolean contains(String Ingredient){
		boolean Contains=false;
		for(Ingredient ingred:this){
			if(ingred.equals(Ingredient)){
				Contains=true;
			}
		}
		return Contains;
	}
	
	public String toString(){
		return super.toString().replaceAll("[\\[\\]]", "");
//		String retString=new String();
//		for(Ingredient ing:this){
//			retString.concat(ing.getName()+", ");
//		}
//		return retString;
	}
	

}
