package org.gnode.wda.events;

import org.gnode.wda.data.NeoObject;

import com.google.gwt.event.shared.GwtEvent;

public class ExplorerTreeSelectionEvent extends GwtEvent<ExplorerTreeSelectionHandler> {
	NeoObject selection;
	public ExplorerTreeSelectionEvent(NeoObject selection) {
		this.selection = selection;
	}
	public static final Type<ExplorerTreeSelectionHandler> TYPE = new 
		Type<ExplorerTreeSelectionHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ExplorerTreeSelectionHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ExplorerTreeSelectionHandler handler) {
		handler.onExplorerTreeSelection(this.selection);
	}
}
