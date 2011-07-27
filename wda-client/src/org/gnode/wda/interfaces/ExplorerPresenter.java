package org.gnode.wda.interfaces;


import com.google.gwt.event.shared.HandlerManager;

public interface ExplorerPresenter {
	public ExplorerView getView();
	public HandlerManager getBus();
}
