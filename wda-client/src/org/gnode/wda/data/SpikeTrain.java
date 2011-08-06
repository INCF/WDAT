package org.gnode.wda.data;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class SpikeTrain extends NeoData {
	private Quantity t_start;
	private QuantityList times;
	private Vector<Waveform> waveforms;
	
	public void setSegment(String segment) {
		this.parents.put("segment", segment);
	}

	public String getSegment() {
		return this.parents.get("segment");
	}

	public void setUnit(String unit) {
		this.parents.put("unit", unit);
	}

	public String getUnit() {
		return this.parents.get("unit");
	}

	public void setT_start(Quantity t_start) {
		this.t_start = t_start;
	}

	public Quantity getT_start() {
		return t_start;
	}

	public void setTimes(QuantityList times) {
		this.times = times;
	}

	public QuantityList getTimes() {
		return times;
	}

	public void setWaveforms(Vector<Waveform> waveforms) {
		this.waveforms = waveforms;
	}

	public Vector<Waveform> getWaveforms() {
		return waveforms;
	}

	public SpikeTrain(JSONObject obj) {
		this.setNeo_id(obj.get("neo_id").isString().stringValue()); 
	
		// Parents assignments 
		List<String> parent_keys = Arrays.asList("segment", "unit");
		this.parseParents(obj, parent_keys);
		
		JSONObject jt_start = obj.get("t_start").isObject();
		this.setT_start(new Quantity(jt_start.get("units").isString().stringValue(),
								 jt_start.get("data").isNumber().doubleValue()));
		
		JSONObject jtimes = obj.get("times").isObject();
		this.setTimes(new QuantityList(jtimes.get("units").isString().stringValue(),
									  new Vector<Double>()));
		JSONArray times_data = jtimes.get("data").isArray();
		for (int i=0; i < times_data.size(); i++) {
			this.getTimes().addData(times_data.get(i).isNumber().doubleValue());
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
			
			this.getWaveforms().add(wf);
		}
	}
}
 