package org.gnode.wda.graph;

import org.gnode.wda.interfaces.GraphView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GraphViewWidget extends Composite implements GraphView {
	SplitLayoutPanel split;
	GraphHistoryWidget historyPanel;
	VerticalPanel graphCard;
	StaticPanelWidget staticPanel;
	MasterDetailPanelWidget masterDetailPanel;

	public GraphViewWidget(GraphHistoryWidget historyPanel,
			StaticPanelWidget staticPanel, MasterDetailPanelWidget masterDetailPanel ) {
		this.staticPanel = staticPanel;
		this.masterDetailPanel = masterDetailPanel;
		this.historyPanel = historyPanel;
	
		// create and populate the graph card
		this.graphCard = new VerticalPanel();
		this.graphCard.add(this.masterDetailPanel);
		this.graphCard.add(this.staticPanel);
		
		this.split = new SplitLayoutPanel();
		split.setPixelSize(Window.getClientWidth() -25, Window.getClientHeight() - 50);

		this.split.addWest((IsWidget)this.historyPanel, 250);
		this.split.add(this.graphCard);
		
		initWidget(split);
	}
}
