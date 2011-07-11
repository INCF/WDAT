package org.gnode.wda.events;

import com.google.gwt.event.shared.GwtEvent;

public class NotificationEvent extends GwtEvent<NotificationHandler> {
	String message;
	
	public NotificationEvent(String message) { 
		this.message = message;
	}
	
	public static final Type<NotificationHandler> TYPE = new 
		Type<NotificationHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<NotificationHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NotificationHandler handler) {
		handler.onNotification(this.message);		
	}
}
