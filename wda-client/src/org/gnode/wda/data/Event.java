package org.gnode.wda.data;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.gnode.wda.interfaces.GraphDataAdapter;

import com.google.gwt.json.client.JSONObject;

public class Event extends NeoData implements GraphDataAdapter {
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

	/* Datapoint series stuff */
	@Override
	public TreeMap<Double, Double> getDatapointSeries(int index) {
		// There are no datapoints associated with an event.
		return null;
	}

	@Override
	public int getDatapointSeriesCount() {
		// There are no datapoints associated with an event
		return 0;
	}
	/* End Datapoint series stuff */
	
	
	/* Marking series functions */
	@Override
	public int getMarkingSeriesCount() {
		// There is only one marking in an epoch
		return 1;
	}

	@Override
	public Double getFrom(int index) {
		if (index != 0) {
			return null;
		}
		return this.getTime().getData();
	}
	@Override
	public Double getTo(int index) {
		if (index != 0) {
			return null;
		}
		return this.getTime().getData();
	}
	/* End Marking series functions */
	
	
	// Name stuff
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setName(String name) {
	}
}
