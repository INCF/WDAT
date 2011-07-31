package org.gnode.wda.graph;

import ca.nanometrics.gflot.client.PlotModel;
import ca.nanometrics.gflot.client.SeriesHandler;
import ca.nanometrics.gflot.client.SimplePlot;
import ca.nanometrics.gflot.client.options.PlotOptions;

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
		
		SimplePlot plot = new SimplePlot(this.model, this.options);
		plot.setHeight(this.height -5);
		plot.setWidth(this.width - 5);
		
		this.main.add(plot);
	}
	
	public SeriesHandler addSeries(String label) {
		SeriesHandler series = this.model.addSeries(label);
		return series;
	}
	
	public void removeSeries(SeriesHandler series) {
		// Removes a series from the model
		this.model.removeSeries(series);
	}
	
	public void clear() {
		this.model.clear();
		this.main.clear();
	}
}
