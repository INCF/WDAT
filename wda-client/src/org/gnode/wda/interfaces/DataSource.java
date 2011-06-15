package org.gnode.wda.interfaces;

import org.gnode.wda.data.NEObject;

import java.util.List;

public interface DataSource{
	public List<NEObject> getRoot();
	public NEObject getElementByUID(String uid);
	public List<NEObject> getContainerChildrenOf(NEObject parent);
	public List<NEObject> getChildrenOf(NEObject parent);
	public NEObject getParentOf(NEObject child);
	public List<NEObject> getPathOf(NEObject child);
}
