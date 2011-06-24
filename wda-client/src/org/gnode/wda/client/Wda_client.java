package org.gnode.wda.client;

import com.google.gwt.core.client.EntryPoint;

public class Wda_client implements EntryPoint {
	public void onModuleLoad() {
		AppController ac = new AppController();
		
		ac.setupUI();
		ac.setupEvents();
	}
}
