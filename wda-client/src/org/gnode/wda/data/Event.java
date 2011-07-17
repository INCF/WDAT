package org.gnode.wda.data;

import com.google.gwt.json.client.JSONObject;

public class Event extends NeoData{
	// Parents
	public String segment;
	public String eventarray;
	
	// Attributes
	public Quantity time;
	public String label;

	public Event(JSONObject obj) {
		this.neo_id = obj.get("neo_id").isString().stringValue(); 
		// Assign parents
		this.segment = obj.get("segment").isString().stringValue();
		this.eventarray = obj.get("eventarray").isString().stringValue();
		
		// Assign attributes.
		this.label = obj.get("label").isString().stringValue();
		JSONObject jtime = obj.get("time").isObject();
		this.time = new Quantity(jtime.get("units").isString().stringValue(),
								 jtime.get("data").isNumber().doubleValue());
	}
}
