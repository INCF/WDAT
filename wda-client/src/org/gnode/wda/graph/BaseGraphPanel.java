package org.gnode.wda.graph;

import org.gnode.wda.interfaces.GraphDataAdapter;

import ca.nanometrics.gflot.client.DataPoint;
import ca.nanometrics.gflot.client.PlotModel;
import ca.nanometrics.gflot.client.SeriesHandler;
import ca.nanometrics.gflot.client.SimplePlot;
import ca.nanometrics.gflot.client.event.SelectionListener;
import ca.nanometrics.gflot.client.options.GridOptions;
import ca.nanometrics.gflot.client.options.Marking;
import ca.nanometrics.gflot.client.options.Markings;
import ca.nanometrics.gflot.client.options.PlotOptions;
import ca.nanometrics.gflot.client.options.Range;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

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
	List<String> neo_ids; // let each graph panel maintain its own list 
						  // of plotted elements. Will be updated on 
						  // calling the addSeries method.
	HashMap<String, SeriesHandler> series_map;
	Markings markings;
	GridOptions gridOptions;
	
	public BaseGraphPanel(Integer width, Integer height) {
		this.height = height;
		this.width = width;
		this.neo_ids = new Vector<String>();
	
		// Create and display the vertical panel. Displaying is necessary since
		// gflot doesn't draw on it if it is not displayed.
		this.main = new VerticalPanel();
		this.main.setVisible(true);
		this.main.setHeight(this.height + "px");
		this.main.setWidth(this.width + "px");
		
		// setup the plot options
		this.options = new PlotOptions();
		this.series_map = new HashMap<String, SeriesHandler>();
		
		initWidget(main);
	
		this.model = new PlotModel();
		this.markings = new Markings();
		this.gridOptions = new GridOptions();
	}
	
	public void draw() {
		this.main.clear();
		this.gridOptions = this.gridOptions.setMarkings(this.markings);
		this.options.setGridOptions(this.gridOptions);
		
		this.plot = new SimplePlot(this.model, this.options);
		this.plot.setHeight(this.height -5);
		this.plot.setWidth(this.width - 5);
		
		this.main.add(plot);
	}

	public void addSeries(String label, GraphDataAdapter gda, String cssColor) {
		if (this.neo_ids.contains(gda.getNeo_id())) {
			return;
		}

		for (int i = 0; i< gda.getMarkingSeriesCount(); i++) {
			Double to = gda.getTo(i);
			Double from = gda.getFrom(i) ;
			Marking m = new Marking();
			m.setX(new Range(from, to));
			m.setColor("#51967A");
		
			this.markings.addMarking(m);
		}
		
		
		for (int i =0; i < gda.getDatapointSeriesCount(); i++) {
			SeriesHandler series;
			if (cssColor == null) 
				series = this.model.addSeries(label);
			else 
				series = this.model.addSeries(label, cssColor);
			
			TreeMap<Double, Double> hm = gda.getDatapointSeries(i);
			for ( Double index : hm.keySet()) {
				series.add(new DataPoint(index, hm.get(index)));
			}
			this.series_map.put(gda.getNeo_id(), series);
		}
	}
	
	public void removeSeries(String neo_id) {
		// Removes a series from the model
		this.model.removeSeries(this.series_map.get(neo_id));
		this.series_map.remove(neo_id);
	}
	
	public void clear() {
		this.model = new PlotModel();
		this.main.clear();
	}
	
	public void addSelectionListener(SelectionListener listener) {
		this.plot.addSelectionListener(listener);
	}
}
