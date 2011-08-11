package org.gnode.wda.explorer;

import org.gnode.wda.interfaces.ExplorerView;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class ExplorerViewWidget extends Composite implements ExplorerView {
	private SplitLayoutPanel split;
	private StackLayoutPanel leftStack;
	private VerticalPanel treePanel;
	
	public ExplorerViewWidget(MainWidget main,
								TreeWidget tree,
								TopLevelSelectorWidget tlsw) {
	
		// assignments 
		this.split = new SplitLayoutPanel();
		this.leftStack = new StackLayoutPanel(Unit.EM);
		this.treePanel = new VerticalPanel();
		
		
		// create the tree card for the stackpanel
		this.treePanel.setSize("250px", "");
		this.treePanel.add(tlsw);
		this.treePanel.setCellHeight(tlsw, "20px");
		ScrollPanel scroller_for_tree_widget = new ScrollPanel(tree);
		this.treePanel.add(scroller_for_tree_widget);
		
		// populate the stackpanel
		leftStack.add(this.treePanel, new Label("Tree "), 2);
		//leftStack.add(new Label("filters"), new Label("filters"), 2);
		
		// populate the splitPanel
		split.addWest((IsWidget)leftStack, 250);
//		split.addSouth(notification, 100);
		split.add(main);
		split.setPixelSize(Window.getClientWidth() -25, Window.getClientHeight() - 50);
		
		initWidget(split);
	}
}
