package org.gnode.wda.graph;

import org.gnode.wda.data.AnalogSignal;

import ca.nanometrics.gflot.client.DataPoint;
import ca.nanometrics.gflot.client.PlotModelStrategy;
import ca.nanometrics.gflot.client.PlotWithOverview;
import ca.nanometrics.gflot.client.PlotWithOverviewModel;
import ca.nanometrics.gflot.client.SeriesHandler;
import ca.nanometrics.gflot.client.options.AxisOptions;
import ca.nanometrics.gflot.client.options.LineSeriesOptions;
import ca.nanometrics.gflot.client.options.PlotOptions;
import ca.nanometrics.gflot.client.options.PointsSeriesOptions;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class MasterDetailPanelWidget extends Composite {
	FlowPanel main;
	PlotWithOverviewModel model;
	PlotOptions options;
	PlotWithOverview plot;
	
	public MasterDetailPanelWidget () {
		this.main = new FlowPanel();
		this.main.setVisible(true);
		//this.main.getElement().setAttribute("style", "width:600px;height:600px;");
		
		// Initialize the model
		this.model = new PlotWithOverviewModel(PlotModelStrategy.downSamplingStrategy(10));
		this.options = new PlotOptions();
		this.options.setDefaultLineSeriesOptions(new LineSeriesOptions().setLineWidth(1).setShow(true));
		this.options.setDefaultPointsOptions(new PointsSeriesOptions().setRadius(2).setShow(true));
		this.options.setDefaultShadowSize(0);
		this.options.setYAxisOptions(new AxisOptions().setAutoscaleMargin(1.2));
		
		initWidget(this.main);
		
		// Initialize the plot
		this.plot = new PlotWithOverview(this.model, this.options);
		
	}
	
	public void setData(AnalogSignal data) {
		this.model.clear();
		
		SeriesHandler series = this.model.addSeries(data.neo_id);
		
		Double time = data.t_start.data;
		
		for (Double point : data.signal.data ) {
			series.add(new DataPoint(time, point));
			time += ( 1 / data.sampling_rate.data );
		}
		
		this.main.add(plot);
		this.plot.redraw();
	}
}
