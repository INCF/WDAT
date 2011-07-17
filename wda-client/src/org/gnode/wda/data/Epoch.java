package org.gnode.wda.data;

import com.google.gwt.json.client.JSONObject;

public class Epoch extends NeoData{
	public String epocharray;
	public String segment;
	
	public String label;
	public Quantity time;
	public Quantity duration;
	
	public Epoch(JSONObject obj) {
		this.neo_id = obj.get("neo_id").isString().stringValue();
		this.epocharray = obj.get("epocharray").isString().stringValue();
		this.segment = obj.get("segment").isString().stringValue();
		this.label = obj.get("label").isString().stringValue();
		
		JSONObject jtime = obj.get("time").isObject();
		this.time = new Quantity(jtime.get("units").isString().stringValue(),
								 jtime.get("data").isNumber().doubleValue());
			
		JSONObject jduration = obj.get("duration").isObject();
		this.duration = new Quantity(jduration.get("units").isString().stringValue(),
								  	 jduration.get("data").isNumber().doubleValue());
	
	}
}
