package org.gnode.wda.client;

import org.gnode.wda.explorer.Explorer;
import org.gnode.wda.interfaces.ExplorerPresenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;

public class AppController {
	HandlerManager eventBus;
	ExplorerPresenter explorer;
	
	public AppController(HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.explorer = new Explorer(this.eventBus);
	}

	public void setupUI() {
		RootPanel.get().add((IsWidget)this.explorer.getView());
	}
	
	public void setupEvents() {
		
	}
}
