

package org.gnode.wda.data;

import java.util.TreeMap;

import org.gnode.wda.interfaces.DatapointSource;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class IRSAAnalogSignal extends NeoData implements DatapointSource{
	public String name;
	
	// Parents. Contain neo IDs
	public String recordingchannel;
	public String segment;

	// data 
	private Quantity t_start;

	private QuantityList times;
	
	private QuantityList signal;
	
	public IRSAAnalogSignal(JSONObject response) {
		// Assumes response_obj is a verified object
		this.setNeo_id(response.get("neo_id").isString().stringValue());
		this.name 	= response.get("name").isString().stringValue();
		
		// parents assignments
		//this.recordingchannel  = response.get("recordingchannel").isString().stringValue();
		//this.segment 		   = response.get("segment").isString().stringValue();
		
		// data assignments
		JSONObject jt_start = response.get("t_start").isObject();
		this.setT_start(new Quantity(jt_start.get("units").isString().stringValue(), 
								    jt_start.get("data").isNumber().doubleValue()));
		
		
		JSONObject jsignal = response.get("signal").isObject();
		this.setSignal(new QuantityList(jsignal.get("units").isString().stringValue()));
		
		JSONArray jsignal_val = jsignal.get("data").isArray();
		for (int i = 0; i < jsignal_val.size(); i++)
			this.getSignal().addData(jsignal_val.get(i).isNumber().doubleValue());
		
		JSONObject jtimes = response.get("times").isObject();
		this.setTimes(new QuantityList(jtimes.get("units").isString().stringValue()));
		
		JSONArray jtimes_val = jtimes.get("data").isArray();
		for (int i = 0; i < jtimes_val.size(); i++)
			this.getTimes().addData(jtimes_val.get(i).isNumber().doubleValue());

	}

	@Override
	public TreeMap<Double, Double> getDatapointSeries(int index) {
		if (index != 0)
			return null;
		
		TreeMap<Double, Double> tm = new TreeMap<Double, Double>();
		
		for (int i = 0; i < this.getTimes().getData().size(); i++) {
			tm.put(
				this.getTimes().getData().get(i), 
				this.getSignal().getData().get(i)
			);
		}
		
		return tm;
	}
	

	@Override
	public int getDatapointSeriesCount() {
		return 1; // Has to, since there is only one channel on an irsaanalogsignal
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
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

	public void setSignal(QuantityList signal) {
		this.signal = signal;
	}

	public QuantityList getSignal() {
		return signal;
	}
}
