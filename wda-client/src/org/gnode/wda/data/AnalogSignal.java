package org.gnode.wda.data;

import java.util.List;
import java.util.Vector;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class AnalogSignal extends NeoData {
	public String name;
	
	// Parents. Contain neo IDs
	public String analogsignalarray;
	public String recordingchannel;
	public String segment;

	// data 
	public String t_start_units;
	public double t_start_value;
	
	public String sampling_rate_units;
	public double sampling_rate_value;
	
	public String signal_units;
	public List<Double> signal_value;
	
	public AnalogSignal(JSONObject response) {
		// Assumes response_obj is a verified object
		this.neo_id = response.get("neo_id").isString().stringValue();
		this.name 	= response.get("name").isString().stringValue();
		
		// parents assignments
		this.analogsignalarray = response.get("analogsignalarray").isString().stringValue();
		this.recordingchannel  = response.get("recordingchannel").isString().stringValue();
		this.segment 		   = response.get("segment").isString().stringValue();
		
		// data assignments
		JSONObject t_start = response.get("t_start").isObject();
		this.t_start_units = t_start.get("units").isString().stringValue();
		this.t_start_value = t_start.get("data").isNumber().doubleValue();
		
		JSONObject sampling_rate = response.get("sampling_rate").isObject();
		this.sampling_rate_units = sampling_rate.get("units").isString().stringValue();
		this.sampling_rate_value = sampling_rate.get("data").isNumber().doubleValue();
		
		JSONObject signal = response.get("signal").isObject();
		this.signal_units = signal.get("units").isString().stringValue();
		JSONArray signal_val = signal.get("data").isArray();
		Vector<Double> rtn = new Vector<Double>();
		for (int i = 0; i < signal_val.size(); i++)
			rtn.add(signal_val.get(i).isNumber().doubleValue());
		this.signal_value = (List<Double>)rtn;
	}
}
