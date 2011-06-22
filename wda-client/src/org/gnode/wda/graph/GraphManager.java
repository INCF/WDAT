package org.gnode.wda.graph;

import org.gnode.wda.interfaces.GraphPresenter;
import org.gnode.wda.interfaces.GraphView;

import com.google.gwt.event.shared.HandlerManager;

public class GraphManager implements GraphPresenter{
	HandlerManager eventBus;
	HandlerManager localBus;
	GraphView dumbView;
	StaticPanelWidget staticPanel;
	MasterPanelWidget masterPanel;
	DetailPanelWidget detailPanel;
	GraphHistoryWidget historyPanel;
	
	
	public GraphManager(HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.staticPanel = new StaticPanelWidget();
		this.masterPanel = new MasterPanelWidget();
		this.detailPanel = new DetailPanelWidget();
		this.historyPanel = new GraphHistoryWidget();
		
		this.dumbView = new GraphViewWidget(this.historyPanel,
										    this.staticPanel,
										    this.masterPanel,
										    this.detailPanel);
	}


	@Override
	public GraphView getView() {
		// TODO Auto-generated method stub
		return this.dumbView;
	}
	
}
