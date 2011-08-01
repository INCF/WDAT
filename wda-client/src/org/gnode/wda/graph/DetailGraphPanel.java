package org.gnode.wda.graph;

import com.google.gwt.user.client.ui.Label;

public class DetailGraphPanel extends BaseGraphPanel {

	public DetailGraphPanel(Integer width, Integer height) {
		super(width, height);
		
	}

	public void showLoading(boolean b) {
		this.main.clear();
		
		if (b) 
			this.main.add(new Label("loading..."));
	}

}
