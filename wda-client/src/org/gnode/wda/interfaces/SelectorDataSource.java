package org.gnode.wda.interfaces;

import org.gnode.wda.selector.SelectorLWModel;

import java.util.List;

public interface SelectorDataSource {
	public List<SelectorLWModel> getRoot();
	public List<SelectorLWModel> getContainerChildrenOf(SelectorLWModel parent);
}
