package org.gnode.wda.events;

import org.gnode.wda.data.NEObject;

import com.google.gwt.event.shared.GwtEvent;

public class ExplorerTreeSelectionEvent extends GwtEvent<ExplorerTreeSelectionHandler> {
	NEObject selection;
	public ExplorerTreeSelectionEvent(NEObject selection) {
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
