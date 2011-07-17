package org.gnode.wda.client;

import org.gnode.wda.data.GnodeDataSource;
import org.gnode.wda.explorer.Explorer;
import org.gnode.wda.graph.GraphManager;
import org.gnode.wda.interfaces.DataSource;
import org.gnode.wda.interfaces.ExplorerPresenter;
import org.gnode.wda.interfaces.GraphPresenter;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class AppController implements ValueChangeHandler<String>{
	DataSource ds;
	ExplorerPresenter explorer;
	GraphPresenter graph;
	TabPanel tabs;
	String sid;
	
	public AppController() {
		this.ds = new GnodeDataSource();
		this.explorer = new Explorer(ds);
		this.graph = new GraphManager(ds);
		this.tabs = new TabPanel();
	}

	public void setupUI() {
		this.tabs.add((IsWidget)this.graph.getView(), "Graph");
		this.tabs.add((IsWidget)this.explorer.getView(), "Explorer");
		RootPanel.get().add(this.tabs);
		
		// Based on the url fragment, change the tab
		String hash = Window.Location.getHash();
		this.tabs.selectTab(1);
		if (hash.startsWith("#plot")) this.tabs.selectTab(0);
		if (hash.startsWith("#explore")) this.tabs.selectTab(1);
		History.fireCurrentHistoryState();
	}
	
	public void setupEvents() {
		History.addValueChangeHandler(this);
		// Only for tab selection.
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// This value change handler only changes the tab. 
		// Other presenter level handlers handle their own stuff.
		if (Utilities.getFragmentType(event.getValue()) == "plot") {
			this.tabs.selectTab(0);
		}
		if (Utilities.getFragmentType(event.getValue()) == "explore") {
			this.tabs.selectTab(1);
		}
	}
}
