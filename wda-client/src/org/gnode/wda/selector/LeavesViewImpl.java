package org.gnode.wda.selector;

import org.gnode.wda.events.ExplorerInvocationEvent;
import org.gnode.wda.interfaces.LeavesView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LeavesViewImpl extends Composite implements LeavesView {

	final String select_other_root_text = "Select Another Branch";
	private static LeavesViewImplUiBinder uiBinder = GWT
			.create(LeavesViewImplUiBinder.class);

	interface LeavesViewImplUiBinder extends
			UiBinder<Widget, LeavesViewImpl> {
	}
	
	HandlerManager eventBus;
	@UiField
	Button select_other_root_button;

	public LeavesViewImpl(HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		select_other_root_button.setText(select_other_root_text);
		this.eventBus = eventBus;
	}
	
	@UiHandler("select_other_root_button")
	void onClick(ClickEvent e) {
		this.eventBus.fireEvent(new ExplorerInvocationEvent());
	}
}