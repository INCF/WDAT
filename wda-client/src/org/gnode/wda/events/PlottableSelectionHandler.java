package org.gnode.wda.events;


import com.google.gwt.event.shared.EventHandler;

public interface PlottableSelectionHandler extends EventHandler{
	public void onPlottableSelection(String neoId, String type);
}