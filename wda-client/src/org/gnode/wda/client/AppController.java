package org.gnode.wda.client;

import org.gnode.wda.data.FakeSelectorDataSource;
import org.gnode.wda.data.NEObject;
import org.gnode.wda.events.ExplorerInvocationHandler;
import org.gnode.wda.events.ExplorerTreeSelectionEvent;
import org.gnode.wda.events.PlottableSelectionEvent;
import org.gnode.wda.events.PlottableSelectionHandler;
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

public class AppController implements ExplorerInvocationHandler, PlottableSelectionHandler, ValueChangeHandler<String>{
	DataSource ds;
	ExplorerPresenter explorer;
	GraphPresenter graph;
	TabPanel tabs;
	
	public AppController() {
		this.ds = new FakeSelectorDataSource();
		this.explorer = new Explorer(ds);
		this.graph = new GraphManager(ds);
		this.tabs = new TabPanel();
	}

	public void setupUI() {
		this.tabs.add((IsWidget)this.graph.getView(), "Graph");
		this.tabs.add((IsWidget)this.explorer.getView(), "Explorer");
		this.tabs.selectTab(1);
		RootPanel.get().add(this.tabs);
	}
	
	public void setupEvents() {
		History.addValueChangeHandler(this);
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

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if (event.getValue().startsWith("plot:")) {
			this.tabs.selectTab(0);
			NEObject neo = this.ds.getElementByUID(event.getValue().split(":")[1]);
			this.graph.getBus().fireEvent(new PlottableSelectionEvent(neo));
		}
		if (event.getValue().split(":")[0].equals("explore")) {
			this.tabs.selectTab(1);
			NEObject neo = this.ds.getElementByUID(event.getValue().split(":")[1]);
			this.explorer.getBus().fireEvent(new ExplorerTreeSelectionEvent(neo));
		}
	
	}
}
