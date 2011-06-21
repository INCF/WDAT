package org.gnode.wda.events;

import org.gnode.wda.data.NEObject;

import com.google.gwt.event.shared.EventHandler;

public interface PlottableSelectionHandler extends EventHandler{
	public void onPlottableSelection(NEObject selection);
}