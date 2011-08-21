

package org.gnode.wda.data;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.gnode.wda.interfaces.GraphDataAdapter;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class IRSAAnalogSignal extends NeoData implements GraphDataAdapter{
	private String name;
	
	// data 
	private Quantity t_start;

	private QuantityList times;
	
	private QuantityList signal;
	
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

	public void setName(String name) {
		this.name = name;
	}

	public IRSAAnalogSignal(JSONObject response) {
		// Assumes response_obj is a verified object
		this.setNeo_id(response.get("neo_id").isString().stringValue());
		this.setName(response.get("name").isString().stringValue());
		
		// parents assignments
		List<String> parent_keys = Arrays.asList("recordingchannel", "segment");
		this.parseParents(response, parent_keys);
		
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
			Double time = this.convertUnits(this.getTimes().getUnits(), "ms", this.getTimes().getData().get(i));
			Double signal = this.convertUnits(this.getSignal().getUnits(), "mV", this.getSignal().getData().get(i));
			
			tm.put(
				time,
				signal
			);
		}
		
		return tm;
	}
	

	@Override
	public int getDatapointSeriesCount() {
		return 1; // Has to, since there is only one channel on an irsaanalogsignal
	}

	@Override
	public Double getFrom(int index) {
		return null;
	}

	@Override
	public int getMarkingSeriesCount() {
		// An IrSaAnalogsignal has no markings so return 0
		return 0;
	}

	@Override
	public Double getTo(int index) {
		return null;
	}
	
}
