package org.gnode.wda.events;

import org.gnode.wda.data.NeoObject;

import com.google.gwt.event.shared.EventHandler;

public interface ExplorerTreeSelectionHandler extends EventHandler{
	public void onExplorerTreeSelection(NeoObject selection);
}