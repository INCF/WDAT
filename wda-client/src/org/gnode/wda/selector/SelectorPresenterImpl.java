package org.gnode.wda.selector;

import org.gnode.wda.interfaces.SelectorNodesDisplay;
import org.gnode.wda.interfaces.SelectorPresenter;
import org.gnode.wda.interfaces.SelectorTreeDisplay;

public class SelectorPresenterImpl implements SelectorPresenter {
	SelectorNodesDisplay nodes;
	SelectorTreeDisplay tree;
	
	public SelectorPresenterImpl(SelectorNodesDisplay nodes,
			SelectorTreeDisplay tree) {
		this.nodes = nodes;
		this.tree = tree;
	}


	@Override
	public void setNodesDisplay(SelectorNodesDisplay snd) {
		this.nodes= snd;
	}

	@Override
	public SelectorNodesDisplay getNodesDisplay() {
		return this.nodes;
	}

	@Override
	public void setTreeDisplay(SelectorTreeDisplay std) {
		this.tree = std;
	}

	@Override
	public SelectorTreeDisplay getTreeDisplay() {
		return tree;
	}

}
