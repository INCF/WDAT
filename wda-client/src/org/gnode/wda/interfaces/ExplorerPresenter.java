package org.gnode.wda.interfaces;

import java.util.List;

import org.gnode.wda.data.NEObject;

import com.google.gwt.event.shared.HandlerManager;

public interface ExplorerPresenter {
	public ExplorerView getView();
	void setBreadcrumbs(List<NEObject> path);
	public HandlerManager getBus();
}
