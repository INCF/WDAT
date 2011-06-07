package org.gnode.wda.selector;

import org.gnode.wda.interfaces.SelectorNodesDisplay;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class SelectorNodesDisplayImpl extends Composite implements SelectorNodesDisplay {

	final String select_other_root_text = "Select Another Branch";
	private static SelectorNodesDisplayImplUiBinder uiBinder = GWT
			.create(SelectorNodesDisplayImplUiBinder.class);

	interface SelectorNodesDisplayImplUiBinder extends
			UiBinder<Widget, SelectorNodesDisplayImpl> {
	}
	
	@UiField
	Button select_other_root_button;

	public SelectorNodesDisplayImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		select_other_root_button.setText(select_other_root_text);
	}

	void attachTreeTrigger(ClickHandler handler) {
		this.select_other_root_button.addClickHandler(handler);
	}
}
