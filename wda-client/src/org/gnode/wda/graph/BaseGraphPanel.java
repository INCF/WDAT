package org.gnode.wda.graph;

import org.gnode.wda.interfaces.DatapointSource;

import ca.nanometrics.gflot.client.DataPoint;
import ca.nanometrics.gflot.client.PlotModel;
import ca.nanometrics.gflot.client.SeriesHandler;
import ca.nanometrics.gflot.client.SimplePlot;
import ca.nanometrics.gflot.client.event.SelectionListener;
import ca.nanometrics.gflot.client.options.PlotOptions;

import java.util.TreeMap;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class BaseGraphPanel extends Composite {
	/* 
	 * This class abstracts away the commmon elements in Static, Master
	 * and Detail Panels. Offering methods to update information within
	 * the plots. All of them will be implementing a SimplePlot or if the
	 * need be, a PlotWithInteractiveLegend. That seems most promising.
	 * 
	 */
	int width, height;
	VerticalPanel main;
	SimplePlot plot;
	PlotModel model;
	PlotOptions options;
	
	
	public BaseGraphPanel(Integer width, Integer height) {
		this.height = height;
		this.width = width;
	
		// Create and display the vertical panel. Displaying is necessary since
		// gflot doesn't draw on it if it is not displayed.
		this.main = new VerticalPanel();
		this.main.setVisible(true);
		this.main.setHeight(this.height + "px");
		this.main.setWidth(this.width + "px");
		
		// setup the plot options
		this.options = new PlotOptions();
		
		initWidget(main);
		
		this.model = new PlotModel();
	}
	
	public void draw() {
		this.main.clear();
		
		this.plot = new SimplePlot(this.model, this.options);
		this.plot.setHeight(this.height -5);
		this.plot.setWidth(this.width - 5);
		
		this.main.add(plot);
	}
	
	public void addSeries(String label, DatapointSource dps) {
		
		for (int i =0; i < dps.getDatapointSeriesCount(); i++) {
			SeriesHandler series = this.model.addSeries(label);
			TreeMap<Double, Double> hm = dps.getDatapointSeries(i);
			for ( Double index : hm.keySet()) {
				series.add(new DataPoint(index, hm.get(index)));
			}
		}
	}
	
	public void removeSeries(SeriesHandler series) {
		// Removes a series from the model
		this.model.removeSeries(series);
	}
	
	public void clear() {
		this.model = new PlotModel();
		this.main.clear();
	}
	
	public void addSelectionListener(SelectionListener listener) {
		this.plot.addSelectionListener(listener);
	}
}
