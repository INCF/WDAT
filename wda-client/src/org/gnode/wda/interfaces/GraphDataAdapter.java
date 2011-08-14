package org.gnode.wda.interfaces;

import java.util.TreeMap;

public interface GraphDataAdapter {
	public int getDatapointSeriesCount();
	public TreeMap<Double, Double> getDatapointSeries(int index);
	
	public int getMarkingSeriesCount();
	public Double getFrom(int index);
	public Double getTo(int index);
	
	public String getName();
	public void setName(String name);
	public String getNeo_id();
}
