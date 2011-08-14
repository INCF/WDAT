package org.gnode.wda.data;

import java.util.TreeMap;

import org.gnode.wda.interfaces.GraphDataAdapter;

public class Segment extends NeoData implements GraphDataAdapter{
	
	public Segment() {
		
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
}
