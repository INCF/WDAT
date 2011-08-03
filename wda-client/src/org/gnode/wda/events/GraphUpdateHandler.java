package org.gnode.wda.events;


import java.util.HashMap;

import com.google.gwt.event.shared.EventHandler;

public interface GraphUpdateHandler extends EventHandler{
	public void onGraphUpdate(String graph_type, String neo_id, String neo_type, HashMap<String, String> params);
}