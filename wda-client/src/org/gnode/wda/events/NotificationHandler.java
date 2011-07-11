package org.gnode.wda.events;

import com.google.gwt.event.shared.EventHandler;

public interface NotificationHandler extends EventHandler{
	public void onNotification(String message);
}