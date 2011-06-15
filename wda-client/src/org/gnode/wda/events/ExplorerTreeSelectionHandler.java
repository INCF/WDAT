package org.gnode.wda.events;

import org.gnode.wda.data.NEObject;

import com.google.gwt.event.shared.EventHandler;

public interface ExplorerTreeSelectionHandler extends EventHandler{
	public void onExplorerTreeSelection(NEObject selection);
}