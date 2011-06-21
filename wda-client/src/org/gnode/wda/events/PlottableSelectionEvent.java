package org.gnode.wda.events;

import org.gnode.wda.data.NEObject;

import com.google.gwt.event.shared.GwtEvent;

public class PlottableSelectionEvent extends GwtEvent<PlottableSelectionHandler> {
	NEObject selection;
	public PlottableSelectionEvent(NEObject selection) {
		this.selection = selection;
	}
	public static final Type<PlottableSelectionHandler> TYPE = new 
		Type<PlottableSelectionHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PlottableSelectionHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PlottableSelectionHandler handler) {
		handler.onPlottableSelection(this.selection);
	}
}
