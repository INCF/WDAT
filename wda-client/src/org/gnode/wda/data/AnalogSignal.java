package org.gnode.wda.data;

import org.gnode.wda.interfaces.DatapointSource;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class AnalogSignal extends NeoData implements DatapointSource {
	private String name;

	// data 
	private Quantity t_start;

	private Quantity sampling_rate;
	
	private QuantityList signal;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAnalogsignalarray() {
		return this.parents.get("analogsignalarray");
	}

	public void setAnalogsignalarray(String analogsignalarray) {
		this.parents.put("analogsignalarray", analogsignalarray);
	}

	public String getRecordingchannel() {
		return this.parents.get("recordingchannel");
	}

	public void setRecordingchannel(String recordingchannel) {
		this.parents.put("recordingchannel", recordingchannel);
	}

	public String getSegment() {
		return this.parents.get("segment");
	}

	public void setSegment(String segment) {
		this.parents.put("segment", segment);
	}

	public Quantity getT_start() {
		return t_start;
	}

	public void setT_start(Quantity t_start) {
		this.t_start = t_start;
	}

	public Quantity getSampling_rate() {
		return sampling_rate;
	}

	public void setSampling_rate(Quantity sampling_rate) {
		this.sampling_rate = sampling_rate;
	}

	public QuantityList getSignal() {
		return signal;
	}

	public void setSignal(QuantityList signal) {
		this.signal = signal;
	}

	
	public AnalogSignal(JSONObject response) {
		// Assumes response_obj is a verified object
		this.setNeo_id(response.get("neo_id").isString().stringValue());
		this.name 	= response.get("name").isString().stringValue();
		
		// parents assignments
		List<String> parent_keys = Arrays.asList("analogsignalarray", "recordingchannel", "segment");
		this.parseParents(response, parent_keys);
		
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

	@Override
	public TreeMap<Double, Double> getDatapointSeries(int index) {
		if (index != 0) // Can't be.
			return null;
		
		TreeMap<Double, Double> hm = new TreeMap<Double, Double>();
	
		Double t = this.getT_start().getData();
		Double t_increment = 1000 / this.getSampling_rate().getData();
		
		for ( Double point : this.getSignal().getData()) {
			hm.put(t, point );
			t += t_increment;
		}
		
		return hm;
	}

	@Override
	public int getDatapointSeriesCount() {
		// Since an analogsignal has only one channel
		return 1;
	}
}
