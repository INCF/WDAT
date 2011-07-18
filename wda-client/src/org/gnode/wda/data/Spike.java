package org.gnode.wda.data;

import java.util.Vector;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class Spike extends NeoData {
	public String segment;
	public String unit;
	
	public Quantity time;
	public Quantity left_sweep;
	public Quantity sampling_rate;
	
	public Waveform waveform;
	
	public Spike(JSONObject obj) {
		this.neo_id = obj.get("neo_id").isString().stringValue(); 
		this.segment = obj.get("segment").isString().stringValue();
		this.unit = obj.get("unit").isString().stringValue();
		
		JSONObject jtime = obj.get("time").isObject();
		this.time = new Quantity(jtime.get("units").isString().stringValue(),
								 jtime.get("data").isNumber().doubleValue());
		
		JSONObject jsampling_rate = obj.get("sampling_rate").isObject();
		this.sampling_rate = new Quantity(jsampling_rate.get("units").isString().stringValue(),
										  jsampling_rate.get("data").isNumber().doubleValue());
		
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
 