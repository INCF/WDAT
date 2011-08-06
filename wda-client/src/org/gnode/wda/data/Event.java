package org.gnode.wda.data;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.json.client.JSONObject;

public class Event extends NeoData{
	// Attributes
	private Quantity time;
	private String label;

	public void setSegment(String segment) {
		this.parents.put("segment", segment);
	}

	public String getSegment() {
		return this.parents.get("segment");
	}

	public void setEventarray(String eventarray) {
		this.parents.put("eventarray", eventarray);
	}

	public String getEventarray() {
		return this.parents.get("eventarray");
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

	public Event(JSONObject obj) {
		this.setNeo_id(obj.get("neo_id").isString().stringValue()); 
		
		// Assign parents
		List<String> parent_keys = Arrays.asList("eventarray", "segment");
		this.parseParents(obj, parent_keys);
		
		// Assign attributes.
		this.setLabel(obj.get("label").isString().stringValue());
		JSONObject jtime = obj.get("time").isObject();
		this.setTime(new Quantity(jtime.get("units").isString().stringValue(),
								 jtime.get("data").isNumber().doubleValue()));
	}
}
