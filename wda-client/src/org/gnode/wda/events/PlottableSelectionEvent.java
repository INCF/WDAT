package org.gnode.wda.events;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class PlottableSelectionEvent extends GwtEvent<PlottableSelectionHandler> {
	List<String> neo_id;
	List<String> type;
	boolean append;
	
	public PlottableSelectionEvent(List<String> neoIds ) {
		this.neo_id = neoIds;
	}
	public static final Type<PlottableSelectionHandler> TYPE = new 
		Type<PlottableSelectionHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PlottableSelectionHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PlottableSelectionHandler handler) {
		handler.onPlottableSelection(this.neo_id);
	}
}
