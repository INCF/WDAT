package org.gnode.wda.graph;

import org.gnode.wda.data.NEObject;
import org.gnode.wda.events.PlottableSelectionEvent;
import org.gnode.wda.events.PlottableSelectionHandler;
import org.gnode.wda.interfaces.DataSource;
import org.gnode.wda.interfaces.GraphPresenter;
import org.gnode.wda.interfaces.GraphView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;

public class GraphManager implements GraphPresenter, PlottableSelectionHandler{
	HandlerManager localBus;
	DataSource ds;
	GraphView dumbView;
	StaticPanelWidget staticPanel;
	MasterPanelWidget masterPanel;
	DetailPanelWidget detailPanel;
	GraphHistoryWidget historyPanel;
	
	
	public GraphManager(DataSource ds) {
		this.ds = ds;
		this.localBus = new HandlerManager(null);
		this.staticPanel = new StaticPanelWidget();
		this.masterPanel = new MasterPanelWidget();
		this.detailPanel = new DetailPanelWidget();
		this.historyPanel = new GraphHistoryWidget();
		
		this.dumbView = new GraphViewWidget(this.historyPanel,
										    this.staticPanel,
										    this.masterPanel,
										    this.detailPanel);
		
		this.setupEventTriggers();
		this.setupEventHandlers();
	}


	private void setupEventTriggers() {
		// TODO Auto-generated method stub
	}


	private void setupEventHandlers() {
		localBus.addHandler(PlottableSelectionEvent.TYPE, this);
	}


	@Override
	public GraphView getView() {
		return this.dumbView;
	}


	@Override
	public void onPlottableSelection(NEObject selection) {
		//TODO create the graph, render it. downsampling and stuff.
	}


	@Override
	public HandlerManager getBus() {
		return this.localBus;
	}
	
}
