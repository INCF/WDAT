package org.gnode.wda.events;

import com.google.gwt.event.shared.GwtEvent;

public class ContainerSelectedEvent extends GwtEvent<ContainerSelectedHandler> {

	
	public static final Type<ContainerSelectedHandler> TYPE = new 
		Type<ContainerSelectedHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ContainerSelectedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ContainerSelectedHandler handler) {
		handler.onContainerSelection();
	}
}
