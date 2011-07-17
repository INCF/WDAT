package org.gnode.wda.data;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class IRSAAnalogSignal extends NeoData {
	public String name;
	
	// Parents. Contain neo IDs
	public String recordingchannel;
	public String segment;

	// data 
	public Quantity t_start;

	public QuantityList times;
	
	public QuantityList signal;
	
	public IRSAAnalogSignal(JSONObject response) {
		// Assumes response_obj is a verified object
		this.neo_id = response.get("neo_id").isString().stringValue();
		this.name 	= response.get("name").isString().stringValue();
		
		// parents assignments
		this.recordingchannel  = response.get("recordingchannel").isString().stringValue();
		this.segment 		   = response.get("segment").isString().stringValue();
		
		// data assignments
		JSONObject jt_start = response.get("t_start").isObject();
		this.t_start = new Quantity(jt_start.get("units").isString().stringValue(), 
								    jt_start.get("data").isNumber().doubleValue());
		
		
		JSONObject jsignal = response.get("signal").isObject();
		this.signal = new QuantityList(jsignal.get("units").isString().stringValue());
		
		JSONArray jsignal_val = jsignal.get("data").isArray();
		for (int i = 0; i < jsignal_val.size(); i++)
			this.signal.addData(jsignal_val.get(i).isNumber().doubleValue());
		
		JSONObject jtimes = response.get("times").isObject();
		this.times = new QuantityList(jtimes.get("units").isString().stringValue());
		
		JSONArray jtimes_val = jtimes.get("data").isArray();
		for (int i = 0; i < jtimes_val.size(); i++)
			this.times.addData(jtimes_val.get(i).isNumber().doubleValue());

	}
}
