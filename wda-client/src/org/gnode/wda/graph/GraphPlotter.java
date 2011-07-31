package org.gnode.wda.graph;

import org.gnode.wda.data.AnalogSignal;

import ca.nanometrics.gflot.client.DataPoint;
import ca.nanometrics.gflot.client.SeriesHandler;

public class GraphPlotter {
	/*
	 * 
	 * GraphPlotter is a unified interface for the creation of series from various 
	 * kinds of signals. There shouldn't be any JSON parsing here. That will be 
	 * handled at the dataSource.
	 * 
	 */

	SeriesHandler series;
	
	public GraphPlotter(AnalogSignal analogsignal, SeriesHandler series) {
		
		Double t = analogsignal.getT_start().getData();
	
		// Assuming the time is in miliseconds and the sampling rate in Hz 
		Double increment_t = 1000 / analogsignal.getSampling_rate().getData();
		
		
		for ( Double point : analogsignal.getSignal().getData()) {
			series.add(new DataPoint(t, point));
			t += increment_t;
		}
	}
}
