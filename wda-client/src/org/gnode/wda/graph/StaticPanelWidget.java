package org.gnode.wda.graph;

import org.gnode.wda.data.AnalogSignal;

import ca.nanometrics.gflot.client.DataPoint;
import ca.nanometrics.gflot.client.Axis;
import ca.nanometrics.gflot.client.PlotModel;
import ca.nanometrics.gflot.client.SeriesHandler;
import ca.nanometrics.gflot.client.SimplePlot;
import ca.nanometrics.gflot.client.options.AxisOptions;
import ca.nanometrics.gflot.client.options.PlotOptions;
import ca.nanometrics.gflot.client.options.TickFormatter;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class StaticPanelWidget extends Composite {
	FlowPanel main;
	
	public StaticPanelWidget () {
		this.main = new FlowPanel();
		this.main.setPixelSize(1000, 100);
		// this.main.getElement().setAttribute("style", "width:500px;height:100px;");

		
		initWidget(this.main);
	}
	
	public void setGraph(final AnalogSignal analog) {
		PlotModel model = new PlotModel();
		PlotOptions options = new PlotOptions();
		
		options.setXAxisOptions(new AxisOptions().setTicks(5).setTickFormatter(new TickFormatter() {
            public String formatTickValue(double tickValue, Axis axis) {
            	return tickValue + " " + analog.getT_start().getUnits();
            }
    	}));

		
		SeriesHandler handler = model.addSeries(analog.getName());
		
		Double time = analog.getT_start().getData();
        // add data
		for (Double point : analog.getSignal().getData()) {
			handler.add(new DataPoint(time, point));
			time += analog.getSampling_rate().getData();
		}
		
        // create the plot
        SimplePlot plot = new SimplePlot(model, options);

        // put it on a panel
        this.main.add(plot);
	}
}
