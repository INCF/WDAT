package org.gnode.wda.client;

import org.gnode.wda.interfaces.SelectorPresenter;
import org.gnode.wda.selector.LeavesViewImpl;
import org.gnode.wda.selector.NEOExplorerImpl;
import org.gnode.wda.selector.SelectorPresenterImpl;
import org.gnode.wda.events.ContainerSelectedEvent;
import org.gnode.wda.events.ContainerSelectedHandler;
import org.gnode.wda.events.ExplorerInvocationEvent;
import org.gnode.wda.events.ExplorerInvocationHandler;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class AppController {
	final String nodesID = "leaf_panel";
	
	private HandlerManager eventBus;
	private SelectorPresenter selPresenter; 
	
	public AppController(HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.selPresenter = new SelectorPresenterImpl(
								new LeavesViewImpl(eventBus),
								new NEOExplorerImpl(eventBus),
								eventBus
								);
	}
	
	public void setupUI() {
		RootPanel.get(nodesID).add((Widget)this.selPresenter.getLeavesView());
	}
	
	public void setupEvents() {
		this.eventBus.addHandler(ExplorerInvocationEvent.TYPE, (ExplorerInvocationHandler)selPresenter);
		this.eventBus.addHandler(ContainerSelectedEvent.TYPE, (ContainerSelectedHandler)selPresenter);
		this.eventBus.fireEvent(new ExplorerInvocationEvent());
	}
}
