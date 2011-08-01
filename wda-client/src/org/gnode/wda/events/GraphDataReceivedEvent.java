package org.gnode.wda.events;

import com.google.gwt.event.shared.GwtEvent;

public class GraphDataReceivedEvent extends GwtEvent<GraphDataReceivedHandler> {
	String panel_type;
	
	
	public GraphDataReceivedEvent() {
		// Type refers to neo_type
	}
	public static final Type<GraphDataReceivedHandler> TYPE = new 
		Type<GraphDataReceivedHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<GraphDataReceivedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GraphDataReceivedHandler handler) {
	}
}
