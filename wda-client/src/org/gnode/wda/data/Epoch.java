package org.gnode.wda.data;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.json.client.JSONObject;

public class Epoch extends NeoData{
	
	private String label;
	private Quantity time;
	private Quantity duration;
	
	public void setDuration(Quantity duration) {
		this.duration = duration;
	}

	public Quantity getDuration() {
		return duration;
	}

	public void setTime(Quantity time) {
		this.time = time;
	}

	public Quantity getTime() {
		return time;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public String getEpocharray() {
		return this.parents.get("epocharray");
	}
	
	public void setEpocharray(String epocharray) {
		this.parents.put("epocharray", epocharray);
	}
	
	public String getSegment() {
		return this.parents.get("segment");
	}
	
	public void setSegment(String segment) {
		this.parents.put("segment", segment);
	}
	
	public Epoch(JSONObject obj) {
		this.setNeo_id(obj.get("neo_id").isString().stringValue());
		
		// setting parents 
		List<String> parent_keys = Arrays.asList("epocharray", "segment");
		this.parseParents(obj, parent_keys);
		
		this.setLabel(obj.get("label").isString().stringValue());
		
		JSONObject jtime = obj.get("time").isObject();
		this.setTime(new Quantity(jtime.get("units").isString().stringValue(),
								 jtime.get("data").isNumber().doubleValue()));
			
		JSONObject jduration = obj.get("duration").isObject();
		this.setDuration(new Quantity(jduration.get("units").isString().stringValue(),
								  	 jduration.get("data").isNumber().doubleValue()));
	
	}
}
