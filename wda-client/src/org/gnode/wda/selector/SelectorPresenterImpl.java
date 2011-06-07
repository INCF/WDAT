package org.gnode.wda.selector;

import org.gnode.wda.interfaces.SelectorNodesDisplay;
import org.gnode.wda.interfaces.SelectorPresenter;
import org.gnode.wda.interfaces.SelectorTreeDisplay;

import com.google.gwt.event.shared.HandlerManager;

public class SelectorPresenterImpl implements SelectorPresenter {
	SelectorNodesDisplay nodes;
	SelectorTreeDisplay tree;
	HandlerManager eventBus;
	
	public SelectorPresenterImpl(HandlerManager eventBus,
			SelectorNodesDisplay nodes,
			SelectorTreeDisplay tree) {
		this.eventBus = eventBus;
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
