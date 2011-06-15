package org.gnode.wda.explorer;

import org.gnode.wda.interfaces.ExplorerView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class ExplorerViewWidget extends Composite implements ExplorerView {
	SplitLayoutPanel split;
	VerticalPanel main;
	
	public ExplorerViewWidget(BreadcrumbsWidget breads,
							  TreeWidget tree,
							  MainWidget main_widget) {
		main = new VerticalPanel();
		initWidget(main);

		main.add(breads);
		
		split = new SplitLayoutPanel();
		split.addWest((IsWidget)tree, 250);
		split.addEast(new Label("east"), 250);
		split.add((IsWidget)main_widget);
		
		split.setPixelSize(Window.getClientWidth() -25, Window.getClientHeight() - 50);
		main.add(split);
	}
}
