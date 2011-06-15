package org.gnode.wda.explorer;

import org.gnode.wda.data.NEObject;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import java.util.List;

public class BreadcrumbsWidget extends Composite{
	HorizontalPanel main;
	
	public BreadcrumbsWidget() {
		main = new HorizontalPanel();
		initWidget(main);
	}
	
	public void flush(){
		main.clear();
	}
	
	public void setBreadcrumbs(List<NEObject> list) {
		this.flush();
		
		for (NEObject item : list) {
			main.add(new Button(item.name));
			main.add(new Button(" >> "));
		}
	}
}
