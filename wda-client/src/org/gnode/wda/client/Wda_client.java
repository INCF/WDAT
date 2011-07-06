package org.gnode.wda.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

public class Wda_client implements EntryPoint {
	public void onModuleLoad() {
		// Check for cookie setup.
		String sessionid = Cookies.getCookie("sessionid");
		if ( sessionid == null ) {
			Window.open("/proxy/account/login/?next=http%3A%2F%2F127.0.0.1%3A8888%2FWda_client.html%3Fgwt.codesvr%3D127.0.0.1%3A9997", "_self", "");
			return;
		}		
		
		AppController ac = new AppController();
		
		ac.setupUI();
		ac.setupEvents();
	}
}
