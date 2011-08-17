package org.gnode.wda.data;

import java.util.List;
import java.util.TreeMap;

import org.gnode.wda.interfaces.GraphDataAdapter;

import com.google.gwt.json.client.JSONObject;

public class Segment extends NeoData implements GraphDataAdapter{
	
	public Segment(JSONObject obj) {
		/* Keep in mind, the obj passed to this constructor is different
		 * from those being passed to other NeoData subclasses. It contains
		 * the children response. Not the data response. Segment is not by
		 * itself plottable. It is the children within it that need to be plotted. 
		 */
		this.setNeo_id(obj.get("neo_id").isString().stringValue());
		
		// Assign attributes
		
	}
	@Override
	public TreeMap<Double, Double> getDatapointSeries(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDatapointSeriesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Double getFrom(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMarkingSeriesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getTo(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
	public List<String> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}
}
