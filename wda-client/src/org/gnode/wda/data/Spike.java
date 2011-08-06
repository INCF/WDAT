package org.gnode.wda.data;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class Spike extends NeoData {
	private Quantity time;
	private Quantity left_sweep;
	private Quantity sampling_rate;
	
	public Waveform waveform;
	
	public void setSampling_rate(Quantity sampling_rate) {
		this.sampling_rate = sampling_rate;
	}

	public Quantity getSampling_rate() {
		return sampling_rate;
	}

	public void setLeft_sweep(Quantity left_sweep) {
		this.left_sweep = left_sweep;
	}

	public Quantity getLeft_sweep() {
		return left_sweep;
	}

	public void setTime(Quantity time) {
		this.time = time;
	}

	public Quantity getTime() {
		return time;
	}

	public void setUnit(String unit) {
		this.parents.put("unit", unit);
	}

	public String getUnit() {
		return this.parents.get("unit");
	}

	public void setSegment(String segment) {
		this.parents.put("segment", segment);
	}

	public String getSegment() {
		return this.parents.get("segment");
	}

	public Spike(JSONObject obj) {
		this.setNeo_id(obj.get("neo_id").isString().stringValue()); 
		
		// Parents assignment
		List<String> parent_keys = Arrays.asList("segment", "unit");
		this.parseParents(obj, parent_keys);
		
		JSONObject jtime = obj.get("time").isObject();
		this.setTime(new Quantity(jtime.get("units").isString().stringValue(),
								 jtime.get("data").isNumber().doubleValue()));
		
		JSONObject jsampling_rate = obj.get("sampling_rate").isObject();
		this.setSampling_rate(new Quantity(jsampling_rate.get("units").isString().stringValue(),
										  jsampling_rate.get("data").isNumber().doubleValue()));
		
		JSONObject jwaveform = obj.get("waveforms").isArray().get(0).isObject();
		this.waveform = new Waveform((int)jwaveform.get("channel_index").isNumber().doubleValue(), 
									  jwaveform.get("waveform__unit").isString().stringValue(),
									  new Vector<Double>());
		JSONArray waveform_data_array = jwaveform.get("waveform_data").isArray();
		for ( int i = 0; i < waveform_data_array.size() ; i++ ) {
			this.waveform.addData(waveform_data_array.get(i).isNumber().doubleValue());
		}
	}
}
 