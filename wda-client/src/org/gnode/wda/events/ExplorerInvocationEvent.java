package org.gnode.wda.events;

import com.google.gwt.event.shared.GwtEvent;

public class ExplorerInvocationEvent extends GwtEvent<ExplorerInvocationHandler> {

	
	public static final Type<ExplorerInvocationHandler> TYPE = new 
		Type<ExplorerInvocationHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ExplorerInvocationHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ExplorerInvocationHandler handler) {
		handler.onExplorerInvoke();		
	}
}
