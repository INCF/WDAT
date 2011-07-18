package org.gnode.wda.data;

import java.util.Vector;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class SpikeTrain extends NeoData {
	public String segment;
	public String unit;
	
	public Quantity t_start;
	public QuantityList times;
	public Vector<Waveform> waveforms;
	
	public SpikeTrain(JSONObject obj) {
		this.neo_id = obj.get("neo_id").isString().stringValue(); 
		this.segment = obj.get("segment").isString().stringValue();
		this.unit = obj.get("unit").isString().stringValue();
		
		JSONObject jt_start = obj.get("t_start").isObject();
		this.t_start = new Quantity(jt_start.get("units").isString().stringValue(),
								 jt_start.get("data").isNumber().doubleValue());
		
		JSONObject jtimes = obj.get("times").isObject();
		this.times = new QuantityList(jtimes.get("units").isString().stringValue(),
									  new Vector<Double>());
		JSONArray times_data = jtimes.get("data").isArray();
		for (int i=0; i < times_data.size(); i++) {
			this.times.addData(times_data.get(i).isNumber().doubleValue());
		}
										  
		
		JSONArray jwaveforms = obj.get("waveforms").isArray();
		for (int i=0; i< jwaveforms.size(); i++) {
			JSONObject jwaveform = jwaveforms.get(i).isObject();
			Waveform wf =  new Waveform((int)jwaveform.get("channel_index").isNumber().doubleValue(), 
									  jwaveform.get("waveform__unit").isString().stringValue(),
									  new Vector<Double>());
			JSONArray waveform_data_array = jwaveform.get("waveform_data").isArray();
			for ( int j = 0; j < waveform_data_array.size() ; j++ ) {
				wf.addData(waveform_data_array.get(j).isNumber().doubleValue());
			}
			
			this.waveforms.add(wf);
		}
	}
}
 