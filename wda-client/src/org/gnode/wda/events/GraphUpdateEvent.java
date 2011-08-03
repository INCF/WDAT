package org.gnode.wda.events;

import java.util.HashMap;

import com.google.gwt.event.shared.GwtEvent;

public class GraphUpdateEvent extends GwtEvent<GraphUpdateHandler> {
	String graph_type;
	String neo_id;
	String neo_type;
	HashMap<String, String> params;
	
	
	public GraphUpdateEvent(String graph_type, String neo_id, String neo_type, HashMap<String, String> params) {
		this.graph_type = graph_type;
		this.neo_id = neo_id;
		this.neo_type = neo_type;
		this.params = params;
	}
	public static final Type<GraphUpdateHandler> TYPE = new 
		Type<GraphUpdateHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<GraphUpdateHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GraphUpdateHandler handler) {
		handler.onGraphUpdate(this.graph_type, this.neo_id, this.neo_type, this.params);
	}
}
