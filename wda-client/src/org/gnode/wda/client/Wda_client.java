package org.gnode.wda.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;

public class Wda_client implements EntryPoint {
	public void onModuleLoad() {
		HandlerManager eventBus = new HandlerManager(null);
		
		AppController ac = new AppController(eventBus);
		
		ac.setupUI();
	}
}
