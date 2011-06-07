package org.gnode.wda.client;

import org.gnode.wda.interfaces.SelectorPresenter;
import org.gnode.wda.selector.SelectorNodesDisplayImpl;
import org.gnode.wda.selector.SelectorPresenterImpl;
import org.gnode.wda.selector.SelectorTreeDisplayImpl;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class AppController {
	final String nodesID = "leaf_panel";
	
	private HandlerManager eventBus;
	private SelectorPresenter selPresenter; 
	
	public AppController(HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.selPresenter = new SelectorPresenterImpl(
											new SelectorNodesDisplayImpl(),
											new SelectorTreeDisplayImpl()
											);
	}
	
	public void setupUI() {
		RootPanel.get(nodesID).add((Widget)this.selPresenter.getNodesDisplay());
	}
	
	public void setupEvents() {
		
	}

}
