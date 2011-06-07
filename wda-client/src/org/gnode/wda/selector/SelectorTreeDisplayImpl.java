package org.gnode.wda.selector;

import org.gnode.wda.interfaces.SelectorTreeDisplay;

import com.google.gwt.event.shared.HandlerManager;

public class SelectorTreeDisplayImpl implements SelectorTreeDisplay {
	HandlerManager eventBus;
	
	public SelectorTreeDisplayImpl(HandlerManager eventBus) {
		this.eventBus = eventBus;
	}

}
