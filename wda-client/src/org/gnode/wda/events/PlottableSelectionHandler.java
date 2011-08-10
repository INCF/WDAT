package org.gnode.wda.events;


import java.util.List;

import com.google.gwt.event.shared.EventHandler;

public interface PlottableSelectionHandler extends EventHandler{
	public void onPlottableSelection(List<String> neoId);
}