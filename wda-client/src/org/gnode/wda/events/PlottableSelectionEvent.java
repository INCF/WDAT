package org.gnode.wda.events;

import org.gnode.wda.data.NeoObject;

import com.google.gwt.event.shared.GwtEvent;

public class PlottableSelectionEvent extends GwtEvent<PlottableSelectionHandler> {
	NeoObject selection;
	public PlottableSelectionEvent(NeoObject selection) {
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
