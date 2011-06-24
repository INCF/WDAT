package org.gnode.wda.interfaces;

import com.google.gwt.event.shared.HandlerManager;

public interface GraphPresenter {
	public GraphView getView();

	public HandlerManager getBus();
}
