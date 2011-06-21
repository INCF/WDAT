package org.gnode.wda.explorer;

import org.gnode.wda.data.NEObject;
import org.gnode.wda.events.ExplorerTreeSelectionEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;

import java.util.List;

public class BreadcrumbsWidget extends Composite{
	HorizontalPanel main;
	
	public BreadcrumbsWidget() {
		main = new HorizontalPanel();
		main.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		initWidget(main);
	}
	
	public void flush(){
		main.clear();
	}
	
	@SuppressWarnings("deprecation")
	public void setBreadcrumbs(List<NEObject> list, final HandlerManager explorerEB) {
		this.flush();
		Hyperlink btn;
		
		for (final NEObject item : list) {
			btn = new Hyperlink("> " + item.name + " >", item.uid);
			btn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					explorerEB.fireEvent(new ExplorerTreeSelectionEvent(item));
				}
				
			});
			main.add(btn);
		}
	}
}
