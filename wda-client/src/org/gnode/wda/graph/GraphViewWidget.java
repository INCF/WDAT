package org.gnode.wda.graph;

import org.gnode.wda.interfaces.GraphView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

public class GraphViewWidget extends Composite implements GraphView {
	SplitLayoutPanel split;
	GraphHistoryWidget historyPanel;
	StaticPanelWidget staticPanel;
	MasterPanelWidget masterPanel;
	DetailPanelWidget detailPanel;

	public GraphViewWidget(GraphHistoryWidget historyPanel,
			StaticPanelWidget staticPanel, MasterPanelWidget masterPanel,
			DetailPanelWidget detailPanel) {
		this.detailPanel = detailPanel;
		this.staticPanel = staticPanel;
		this.masterPanel = masterPanel;
		this.historyPanel = historyPanel;
		
		
		this.split = new SplitLayoutPanel();
		split.setPixelSize(Window.getClientWidth() -25, Window.getClientHeight() - 50);

		this.split.addWest((IsWidget)this.historyPanel, 250);
		
		initWidget(split);
	}
}
