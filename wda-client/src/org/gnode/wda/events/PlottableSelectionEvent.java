package org.gnode.wda.events;

import com.google.gwt.event.shared.GwtEvent;

public class PlottableSelectionEvent extends GwtEvent<PlottableSelectionHandler> {
	String neo_id;
	String type;
	public PlottableSelectionEvent(String neo_id, String type) {
		// Type refers to neo_type
		this.neo_id = neo_id;
		this.type = type;
	}
	public static final Type<PlottableSelectionHandler> TYPE = new 
		Type<PlottableSelectionHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PlottableSelectionHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PlottableSelectionHandler handler) {
		handler.onPlottableSelection(this.neo_id, this.type);
	}
}
