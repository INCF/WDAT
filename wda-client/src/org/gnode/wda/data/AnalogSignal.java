package org.gnode.wda.data;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class AnalogSignal extends NeoData {
	public String name;
	
	// Parents. Contain neo IDs
	public String analogsignalarray;
	public String recordingchannel;
	public String segment;

	// data 
	public Quantity t_start;

	public Quantity sampling_rate;
	
	public QuantityList signal;
	
	public AnalogSignal(JSONObject response) {
		// Assumes response_obj is a verified object
		this.neo_id = response.get("neo_id").isString().stringValue();
		this.name 	= response.get("name").isString().stringValue();
		
		// parents assignments
		this.analogsignalarray = response.get("analogsignalarray").isString().stringValue();
		this.recordingchannel  = response.get("recordingchannel").isString().stringValue();
		this.segment 		   = response.get("segment").isString().stringValue();
		
		// data assignments
		JSONObject jt_start = response.get("t_start").isObject();
		this.t_start = new Quantity(jt_start.get("units").isString().stringValue(), 
								    jt_start.get("data").isNumber().doubleValue());
		
		JSONObject jsampling_rate = response.get("sampling_rate").isObject();
		this.sampling_rate = new Quantity(jsampling_rate.get("units").isString().stringValue(),
										  jsampling_rate.get("data").isNumber().doubleValue());
		
		JSONObject jsignal = response.get("signal").isObject();
		this.signal = new QuantityList(jsignal.get("units").isString().stringValue());
		
		JSONArray jsignal_val = jsignal.get("data").isArray();
		for (int i = 0; i < jsignal_val.size(); i++)
			this.signal.addData(jsignal_val.get(i).isNumber().doubleValue());
	}
}
