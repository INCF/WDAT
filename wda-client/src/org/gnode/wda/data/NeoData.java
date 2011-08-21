package org.gnode.wda.data;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.json.client.JSONObject;

public abstract class NeoData {
	private String neo_id;
	protected HashMap<String, String> parents;
	
	protected HashMap<String, Double> conversion_ratios = createConversionRatios();
	
	private HashMap<String, Double> createConversionRatios() {
		HashMap<String, Double> hm = new HashMap<String, Double> ();
		/* To add a new conversion unit, simply add a key value pair
		 * here in the following format
		 *      ("fromUnit-toUnit", conversionFactor)
		 *      
		 *      conversionFactor is the factor by which the quantity
		 *      in the from unit is to be multiplied to make it in the 
		 *      to unit.
		 */
		
		// Sampling rates  
		hm.put("Hz-Hz", 1.0);
		hm.put("kHz-Hz", 1000.0);
		hm.put("mHz-Hz", 1000000.0);
		hm.put("1/s-Hz", 1.0);
		
		// Time units 
		hm.put("s-ms", 1000.0);
		hm.put("ms-ms", 1.0);
		hm.put("mcs-ms", 0.001);
		
		// Signal
		hm.put("V-mV", 1000.0);
		hm.put("mV-mV", 1.0);
		hm.put("mcV-mV", 0.001);
		
		
		return hm;
	};
	
	public void setNeo_id(String neo_id) {
		this.neo_id = neo_id;
	}

	public String getNeo_id() {
		return neo_id;
	}
	
	public void parseParents(JSONObject obj, List<String> parent_keys) {
		// error checking and parsing of parents in a JSONObject. The
		// parent's accessor ids are defined in parent_keys
		this.parents = new HashMap<String, String>();
		
		//for (String parent : parent_keys) {
			//try {
			//	this.parents.put(parent, obj.get(parent).isString().stringValue());
			//} 
			//catch (NullPointerException e) {
			//  this.parents.put(parent, null);
			//}
		//}
	}
	
	protected Double convertUnits(String from, String to, Double value) {
		String conversion_string = from + "-" + to;
		return value * this.conversion_ratios.get(conversion_string);
	}
}
