import java.util.ArrayList;
public class Ingredient {
	
	private String Name;
	private int count;
	private float probability;
	
	public String toString(){
		return Name+": "+String.valueOf(count);
	}
	public Ingredient(String name, int count){
		this.Name=name;
		this.count=count;
	}
	
	public Ingredient(String name){
		this.Name=name;
		this.count=1;
	}
	
	public Ingredient(String name, float prob){
		this.Name=name;
		this.probability=prob;
	}
	
	public boolean equals(String name){
		return name.equalsIgnoreCase(this.Name);
	}
	
	public boolean equals(Ingredient ingred){
		return this.Name.equals(ingred.getName());
	}
	
	public void addCount(){
		this.count++;
	}
	
	public int getCount(){
		return this.count;
	}
	
	public String getName(){
		return this.Name;
	}
	
	public float getProb(){
		return this.probability;
	}
	
	
}
