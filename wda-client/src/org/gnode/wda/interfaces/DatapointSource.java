package org.gnode.wda.interfaces;

import java.util.TreeMap;

public interface DatapointSource {
	public int getDatapointSeriesCount();
	public TreeMap<Double, Double> getDatapointSeries(int index);
	
	public String getName();
	public String getNeo_id();
}
