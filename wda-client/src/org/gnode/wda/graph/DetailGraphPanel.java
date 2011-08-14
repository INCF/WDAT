package org.gnode.wda.graph;

import ca.nanometrics.gflot.client.event.PlotHoverListener;
import ca.nanometrics.gflot.client.options.LineSeriesOptions;
import ca.nanometrics.gflot.client.options.PointsSeriesOptions;


public class DetailGraphPanel extends BaseGraphPanel {

	public DetailGraphPanel(Integer width, Integer height) {
		super(width, height);
		this.options.setDefaultLineSeriesOptions(new LineSeriesOptions().setLineWidth(1).setShow(true));
		this.options.setDefaultPointsOptions(new PointsSeriesOptions().setRadius(2).setShow(true));
		this.options.setDefaultShadowSize(0);
		
		// set it to hoverable
		this.options.setGridOptions(this.gridOptions.setHoverable(true));
		
	}
	
	public void addHoverListener(PlotHoverListener listener) {
		this.plot.addHoverListener(listener, true);
	}
	

}
