package org.gnode.wda.explorer;

import java.util.List;

import org.gnode.wda.data.FakeSelectorDataSource;
import org.gnode.wda.data.NEObject;
import org.gnode.wda.events.ExplorerTreeSelectionEvent;
import org.gnode.wda.events.ExplorerTreeSelectionHandler;
import org.gnode.wda.interfaces.DataSource;
import org.gnode.wda.interfaces.ExplorerPresenter;
import org.gnode.wda.interfaces.ExplorerView;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.TreeItem;

public class Explorer implements ExplorerPresenter, ExplorerTreeSelectionHandler{
	HandlerManager eventBus;
	HandlerManager localBus;
	ExplorerView dumbView; // Named dumbview for reminding me.
	BreadcrumbsWidget breads;
	MainWidget main;
	TreeWidget tree;
	DataSource ds; 
	
	public Explorer(HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.localBus = new HandlerManager(null);
	
		this.ds = new FakeSelectorDataSource();
		this.breads = new BreadcrumbsWidget();
		this.main = new MainWidget();
		this.tree = new TreeWidget(ds);
		this.dumbView = new ExplorerViewWidget(this.breads, this.tree, this.main);
		
		this.setupEvents();
	}

	public void setupEvents() {
		this.localBus.addHandler(ExplorerTreeSelectionEvent.TYPE, this);
		
		this.tree.t.addSelectionHandler(new SelectionHandler<TreeItem>(){
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				NEObject selectedNEO = ds.getElementByUID(event.getSelectedItem().getTitle());
				localBus.fireEvent(new ExplorerTreeSelectionEvent(selectedNEO));
			}
			
		});
	}
	
	@Override
	public ExplorerView getView() {
		return this.dumbView;
	}
	
	@Override
	public void setBreadcrumbs(List<NEObject> path){
		this.breads.setBreadcrumbs(path);
	}

	@Override
	public void onExplorerTreeSelection(NEObject selection) {
		this.breads.setBreadcrumbs(this.ds.getPathOf(selection));
		this.main.setContents(this.ds.getChildrenOf(selection));
	}
}