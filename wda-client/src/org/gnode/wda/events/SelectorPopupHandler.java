package org.gnode.wda.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

public class SelectorPopupHandler implements EventHandler{
	PopupPanel pp;
	public SelectorPopupHandler (PopupPanel pp) {
		this.pp = pp;
	}
	public void onTrigger() {
		this.pp.setPopupPosition((Window.getClientWidth() - 960 )/2, 15);
		this.pp.setPixelSize(760, 500);
		this.pp.show();
	}
}