package org.gnode.wda.selector;

import org.gnode.wda.events.ContainerSelectedHandler;
import org.gnode.wda.events.ExplorerInvocationEvent;
import org.gnode.wda.events.ExplorerInvocationHandler;
import org.gnode.wda.interfaces.LeavesView;
import org.gnode.wda.interfaces.NEOExplorer;
import org.gnode.wda.interfaces.SelectorPresenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class SelectorPresenterImpl 
		implements SelectorPresenter, ExplorerInvocationHandler, ContainerSelectedHandler 
	{
	
	LeavesView leaves;
	NEOExplorer explorer;
	HandlerManager eventBus;
	PopupPanel explorerPanel;
	
	public SelectorPresenterImpl(LeavesView leaves,
								 NEOExplorer explorer,
								 HandlerManager eventBus) {
		this.leaves = leaves;
		this.explorer = explorer;
		this.eventBus = eventBus;
	}
	
	@Override
	public void onContainerSelection() {
		explorerPanel.hide();
	}

	@Override
	public void onExplorerInvoke() {
		explorerPanel = new PopupPanel(false);
		explorerPanel.setPopupPosition(5, 5);
		explorerPanel.setGlassEnabled(true);
		explorerPanel.setWidget((IsWidget)this.getExplorer());
		explorerPanel.show();
	}

	@Override
	public void setLeavesView(LeavesView view) {
		this.leaves = view;
	}

	@Override
	public LeavesView getLeavesView() {
		return this.leaves;
	}

	@Override
	public void setExplorer(NEOExplorer explorer) {
		this.explorer = explorer;
	}

	@Override
	public NEOExplorer getExplorer() {
		return this.explorer;
	}
}