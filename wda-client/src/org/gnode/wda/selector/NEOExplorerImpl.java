package org.gnode.wda.selector;

import org.gnode.wda.interfaces.NEOExplorer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class NEOExplorerImpl extends Composite implements NEOExplorer{
	HandlerManager explorerEB;
	

	private static NEOExplorerImplUiBinder uiBinder = GWT
			.create(NEOExplorerImplUiBinder.class);

	interface NEOExplorerImplUiBinder extends
			UiBinder<Widget, NEOExplorerImpl> {
	}

	@UiField
	Button button;
	@UiField
	SplitLayoutPanel explorerWrapper;
	
	public NEOExplorerImpl(HandlerManager eventBus) {
		explorerEB = new HandlerManager(null);
		
		
		initWidget(uiBinder.createAndBindUi(this));
		
		explorerWrapper.setPixelSize(Window.getClientWidth() - 25, 600);
		explorerWrapper.addWest(new Label("Tree"), 200);
		explorerWrapper.addEast(new Label("Preview"), 200);
		explorerWrapper.add(new Label("Main"));
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}
}
