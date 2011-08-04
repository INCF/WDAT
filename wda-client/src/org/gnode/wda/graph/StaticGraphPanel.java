package org.gnode.wda.graph;

import ca.nanometrics.gflot.client.event.SelectionListener;
import ca.nanometrics.gflot.client.options.SelectionOptions;

public class StaticGraphPanel extends BaseGraphPanel {
	/*
	 * This represents a panel for the static mode of the graph.
	 * Most of the plotting and refreshing is handled by the BaseGraphPanel.
	 * This serves mostly as a source of events for the selection.
	 */
	
	public StaticGraphPanel(Integer width, Integer height) {
		super(width, height);
		this.options.setSelectionOptions(new SelectionOptions().setMode(
				SelectionOptions.X_SELECTION_MODE).setDragging(true));
	}

	
}