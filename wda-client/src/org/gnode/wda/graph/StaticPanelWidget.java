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
            	return tickValue + " " + analog.t_start.units;
            }
    	}));

		
		SeriesHandler handler = model.addSeries(analog.name);
		
		Double time = analog.t_start.data;
        // add data
		for (Double point : analog.signal.data) {
			handler.add(new DataPoint(time, point));
			time += analog.sampling_rate.data;
		}
		
        // create the plot
        SimplePlot plot = new SimplePlot(model, options);

        // put it on a panel
        this.main.add(plot);
	}
}
