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

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class MasterDetailPanelWidget extends Composite {
	FlowPanel main;
	PlotOptions options;
	
	public MasterDetailPanelWidget () {
		this.main = new FlowPanel();
		this.main.setVisible(true);
		//this.main.getElement().setAttribute("style", "width:600px;height:600px;");
		
		// Initialize the model
		this.options = new PlotOptions();
		this.options.setDefaultLineSeriesOptions(new LineSeriesOptions().setLineWidth(1).setShow(true));
		this.options.setDefaultPointsOptions(new PointsSeriesOptions().setRadius(2).setShow(true));
		this.options.setDefaultShadowSize(0);
		this.options.setYAxisOptions(new AxisOptions().setAutoscaleMargin(1.2));
		
		initWidget(this.main);
	}
	
	public void setData(AnalogSignal signal) {
		this.main.clear();
		
		PlotWithOverviewModel model = new PlotWithOverviewModel(PlotModelStrategy.defaultStrategy());
		
		SeriesHandler series = model.addSeries(signal.getNeo_id());
		
		Double time = signal.getT_start().getData();
		
		for (Double point : signal.getSignal().getData()) {
			series.add(new DataPoint(time, point));
			time += signal.getSampling_rate().getData();
		}
		
		PlotWithOverview plot = new PlotWithOverview(model, this.options);
		
		// Calculate width of the plot
		Integer width = Window.getClientWidth() - 300;
		
		plot.setWidth(width);
		
		this.main.add(plot);
	}	
}
