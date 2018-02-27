import java.util.ArrayList;
public class FeatureVector extends ArrayList<int[]>{

	private String Region;
	private ArrayList<String> Features;
	
	public FeatureVector(){
		super();
	}
	public FeatureVector(String region){
		super();
		this.Region=region;
	}
	
	public FeatureVector(ArrayList<String> features){
		super();
		this.Features=features;
	}
	
	public FeatureVector(String region, ArrayList<String> features){
		super();
		this.Region=region;
		this.Features=features;
	}
	
	public String getRegion(){
		return this.Region;
	}
	
	public ArrayList<String> getFeatures(){
		return this.Features;
	}
	
	public String toString(){
		 return super.toString();
	}
}
