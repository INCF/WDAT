package org.gnode.wda.client;

import org.gnode.wda.data.GnodeDataSource;
import org.gnode.wda.explorer.Explorer;
import org.gnode.wda.graph.GraphManager2;
import org.gnode.wda.interfaces.DataSource;
import org.gnode.wda.interfaces.ExplorerPresenter;
import org.gnode.wda.interfaces.GraphPresenter;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class AppController implements ValueChangeHandler<String>{
	/* AppController is a top-level presenter module. 
	 * Handles interacion between Graph Explorer and 
	 * maybe later, other modules of the application. 
	 * 
	 * Currently, appcontroller only switches between tabs based on
	 * URL Fragments. 
	 */
	DataSource ds;
	ExplorerPresenter explorer;
	GraphPresenter graph;
	TabPanel tabs;
	String sid;
	
	public AppController() {
		this.ds = new GnodeDataSource();
		this.explorer = new Explorer(ds);
		this.graph = new GraphManager2(ds);
		this.tabs = new TabPanel();
	}

	public void setupUI() {
		/* Setting up of the UI is quarantined off to its own function
		 * and is not directly called from the constructor so that later
		 * if there are some calculations that need to be passed on to the
		 * UI, it can be done in the entry point class.
		 */
		this.tabs.add((IsWidget)this.graph.getView(), "Graph");
		this.tabs.add((IsWidget)this.explorer.getView(), "Explorer");
		
		RootPanel.get().add(this.tabs);
		
		// Based on the url fragment, change the tab
		String hash = History.getToken(); 
		if (Utilities.getOption(hash, "view") == "") {
			// If there is no view selected, default to explore. 
			History.newItem("view=explore");
			this.tabs.selectTab(1);
		} 
		
		if (Utilities.getOption(hash, "view").equals("explore") ) {
			this.tabs.selectTab(1);
		}
		else if (Utilities.getOption(hash, "view").equals("graph") ) {
			this.tabs.selectTab(0);
		}
		
		History.fireCurrentHistoryState(); // This is required to support page refresh.
										   // browsers don't fire History change events
										   // on page refresh.
	}
	
	public void setupEvents() {
		History.addValueChangeHandler(this);
		// Only for tab selection.
		this.tabs.addSelectionHandler(new SelectionHandler<Integer>() {
			
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				Token t = new Token(History.getToken());
				
				if (event.getSelectedItem() == 1) {
					// explore view selected.
					t.setOption("view", "explore");
				} else if (event.getSelectedItem() == 0) {
					// graph selected
					t.setOption("view", "graph");
				}
				
				History.newItem(t.getToken(), false);
			}
		});
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// This value change handler only changes the tab. 
		// Other presenter level handlers handle their own stuff.
		if (Utilities.getOption(event.getValue(), "view").equals("graph")) {
			this.tabs.selectTab(0);
		}
		if (Utilities.getOption(event.getValue(), "view").equals("explore")) {
			this.tabs.selectTab(1);
		}
	}
}
