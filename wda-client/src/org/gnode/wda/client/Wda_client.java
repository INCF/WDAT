package org.gnode.wda.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

public class Wda_client implements EntryPoint {
	public void onModuleLoad() {
		// Check for cookie setup.
		String sessionid = Cookies.getCookie("sessionid");
		if ( sessionid == null ) {
			// TODO this redirection fragment needs to use Window.location to ensure that 
			// the user returns to the page he/she is trying to view in the first place.
			Window.open("/account/login/?next=%2Fwda%2Fwda-client%2Fwar%2FWda_client.html%3F#explore:", "_self", "");
			return;
		}		
		
		AppController ac = new AppController();
		// AppController is the highest level presenter. It just oversees graph/explorer
		// interactions and would be an application that is independant of the EntryPoint class.
		
		ac.setupUI();
		ac.setupEvents();
	}
}
