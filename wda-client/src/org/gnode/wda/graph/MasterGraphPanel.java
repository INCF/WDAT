package org.gnode.wda.graph;

import ca.nanometrics.gflot.client.event.SelectionListener;
import ca.nanometrics.gflot.client.options.SelectionOptions;

public class MasterGraphPanel extends BaseGraphPanel{

	public MasterGraphPanel(Integer width, Integer height) {
		super(width, height);
		this.options.setSelectionOptions(new SelectionOptions().setMode(
				SelectionOptions.X_SELECTION_MODE).setDragging(true));
	}
	
	public void addSelectionListener(SelectionListener listener) {
		this.plot.addSelectionListener(listener);
	}

}
