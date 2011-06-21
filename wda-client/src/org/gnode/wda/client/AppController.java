package org.gnode.wda.client;

import org.gnode.wda.data.NEObject;
import org.gnode.wda.events.ExplorerInvocationHandler;
import org.gnode.wda.events.PlottableSelectionEvent;
import org.gnode.wda.events.PlottableSelectionHandler;
import org.gnode.wda.explorer.Explorer;
import org.gnode.wda.interfaces.ExplorerPresenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class AppController implements ExplorerInvocationHandler, PlottableSelectionHandler{
	HandlerManager eventBus;
	ExplorerPresenter explorer;
	TabPanel tabs;
	
	public AppController(HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.explorer = new Explorer(this.eventBus);
		this.tabs = new TabPanel();
	}

	public void setupUI() {
		this.tabs.add(new Label("Plotting area"), "Graph");
		this.tabs.add((IsWidget) this.explorer.getView(), "Explorer");
		this.tabs.selectTab(1);
		RootPanel.get().add(this.tabs);
	}
	
	public void setupEvents() {
		eventBus.addHandler(PlottableSelectionEvent.TYPE, this);
	}

	@Override
	public void onPlottableSelection(NEObject selection) {
		// TODO Auto-generated method stub
		this.tabs.selectTab(0);
	}

	@Override
	public void onExplorerInvoke() {
		// TODO Auto-generated method stub
	}
}
