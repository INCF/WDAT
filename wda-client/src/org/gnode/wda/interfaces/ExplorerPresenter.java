package org.gnode.wda.interfaces;

import java.util.List;

import org.gnode.wda.data.NEObject;

public interface ExplorerPresenter {
	public ExplorerView getView();
	void setBreadcrumbs(List<NEObject> path);
}
