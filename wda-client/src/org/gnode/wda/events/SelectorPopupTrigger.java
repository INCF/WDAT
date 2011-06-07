package org.gnode.wda.events;

import com.google.gwt.event.shared.GwtEvent;

public class SelectorPopupTrigger extends GwtEvent<SelectorPopupHandler> {

	
	public static final Type<SelectorPopupHandler> TYPE = new 
		Type<SelectorPopupHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SelectorPopupHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SelectorPopupHandler handler) {
		handler.onTrigger();		
	}
}
