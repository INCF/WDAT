package org.gnode.wda.explorer;

import org.gnode.wda.interfaces.ExplorerView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class ExplorerViewWidget extends Composite implements ExplorerView {
	SplitLayoutPanel split;
	VerticalPanel centre;
	
	public ExplorerViewWidget(BreadcrumbsWidget breads,
							  TreeWidget tree,
							  MainWidget main_widget) {
		
		split = new SplitLayoutPanel();
		split.addWest((IsWidget)tree, 250);
		split.addNorth(breads, 30);
		split.add(main_widget);
		split.setPixelSize(Window.getClientWidth() -25, Window.getClientHeight() - 50);
		
		initWidget(split);
	}
}
