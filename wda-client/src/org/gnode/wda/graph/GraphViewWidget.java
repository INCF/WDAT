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
	StaticGraphPanel staticg;
	MasterGraphPanel masterg;
	DetailGraphPanel detailg;

	public GraphViewWidget(GraphHistoryWidget historyPanel,
			StaticGraphPanel staticg, MasterGraphPanel masterg, DetailGraphPanel detailg) {
		this.historyPanel = historyPanel;
		
		// take care of arguments
		this.staticg = staticg;
		this.masterg = masterg;
		this.detailg = detailg;
		
	
		// create and populate the graph card
		this.graphCard = new VerticalPanel();
		this.graphCard.add(this.detailg);
		this.graphCard.add(this.masterg);
		this.graphCard.add(this.staticg);
		
		this.split = new SplitLayoutPanel();
		split.setPixelSize(Window.getClientWidth() -25, Window.getClientHeight() - 50);

		this.split.addWest((IsWidget)this.historyPanel, 250);
		this.split.add(this.graphCard);
		
		initWidget(split);
	}
}
