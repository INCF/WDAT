package org.gnode.wda.events;

import org.gnode.wda.data.NeoObject;

import com.google.gwt.event.shared.EventHandler;

public interface PlottableSelectionHandler extends EventHandler{
	public void onPlottableSelection(NeoObject selection);
}