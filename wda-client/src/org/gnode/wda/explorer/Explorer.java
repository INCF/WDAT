package org.gnode.wda.explorer;

import java.util.List;

import org.gnode.wda.data.NEObject;
import org.gnode.wda.events.ExplorerTreeSelectionEvent;
import org.gnode.wda.events.ExplorerTreeSelectionHandler;
import org.gnode.wda.interfaces.DataSource;
import org.gnode.wda.interfaces.ExplorerPresenter;
import org.gnode.wda.interfaces.ExplorerView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.TreeItem;

public class Explorer implements ExplorerPresenter, ExplorerTreeSelectionHandler{
	HandlerManager eventBus;
	HandlerManager localBus;
	ExplorerView dumbView; // Named dumbview for reminding me.
	BreadcrumbsWidget breads;
	MainWidget main;
	TreeWidget tree;
	DataSource ds; 
	String sid;
	
	public Explorer(DataSource ds) {
		this.localBus = new HandlerManager(null);
	
		this.ds = ds; 
		this.breads = new BreadcrumbsWidget();
		this.main = new MainWidget();
		this.tree = new TreeWidget(ds);
		this.dumbView = new ExplorerViewWidget(this.breads, this.tree, this.main);
		this.sid = Cookies.getCookie("sessionid");

		this.setupEventTriggers();
		this.setupEventHandlers();
	}

	public void setupEventTriggers() {
		for (final NeoIcon icon : this.main.getChildren()) {
			icon.setClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if ( icon.getNeo().isPlottable() ) {
						History.newItem("plot:" + icon.getNeo().uid);
					} else {
						History.newItem("explore:" + icon.getNeo().uid);
					}
				}
			});
		}
	}
	public void setupEventHandlers() {
		this.localBus.addHandler(ExplorerTreeSelectionEvent.TYPE, this);
		
		this.tree.t.addSelectionHandler(new SelectionHandler<TreeItem>(){
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				History.newItem("explore:" + event.getSelectedItem().getTitle());
			}
		});
	}
	
	@Override
	public ExplorerView getView() {
		return this.dumbView;
	}
	
	@Override
	public void setBreadcrumbs(List<NEObject> path){
		this.breads.setBreadcrumbs(path, this.localBus);
	}

	@Override
	public void onExplorerTreeSelection(NEObject selection) {
		//this.breads.setBreadcrumbs(this.ds.getPathOf(selection), this.localBus);
		this.main.setContents(this.ds.getChildren(this.sid, selection.uid));
		this.tree.setSelection(selection);
		this.setupEventTriggers();
	}

	@Override
	public HandlerManager getBus() {
		// TODO Auto-generated method stub
		return this.localBus;
	}
}
