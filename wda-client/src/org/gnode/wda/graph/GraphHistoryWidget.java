package org.gnode.wda.graph;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GraphHistoryWidget extends Composite {
	ScrollPanel main;
	VerticalPanel list;
	
	public GraphHistoryWidget () {
		// This widget will contain a list of the recently plotted items.
		// Would also allow you to go to a particular plot.
		this.main = new ScrollPanel();
		this.main.setAlwaysShowScrollBars(false);
	
		// Initialize the panel that will contain the entries.
		this.list = new VerticalPanel();
		this.main.add(list);
		
		initWidget(main);
	}
	
	
	
	public void addItem(String neo_id, String neo_type, String name) {
		PlottableEntry entry = new PlottableEntry(neo_id, neo_type, name,
												 "230px");
		this.list.insert((IsWidget)entry, 0);
	}
	
	private class PlottableEntry extends Label {
		private String neo_id;
		private Boolean selected;
		
		public PlottableEntry(final String neo_id, final String neo_type, String name, String width) {
			// neo_id, neo_type, name are self-explanatory.
			// Width is required because this class has no knowledge of its parents
			super(name);
			this.setWidth(width);
			this.setStylePrimaryName("graph-history-tab");
			this.setHorizontalAlignment(ALIGN_RIGHT);
			this.neo_id = neo_id;
			
			this.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					History.newItem("plot:" + "obj=" + neo_id + "&type=" + neo_type);
				}
			});
		}
	
		public void setSelected(Boolean value) {
			this.selected = value;
			this.setStyleDependentName("selected", value);
		}
		
		public String getNeoId() {
			return this.neo_id;
		}
	}

	public boolean contains(String neoId) {
		for (int i = 0; i < this.list.getWidgetCount(); i++ ){
			Widget w = this.list.getWidget(i);
			PlottableEntry p = (PlottableEntry)w;
			
			if ( p.getNeoId().equals(neoId)) {
				return true;
			}
		}
		
		return false;
	}



	public void setSelection(String neoId) {
		for (int i = 0; i < this.list.getWidgetCount(); i++ ) {
			Widget w = this.list.getWidget(i);
			PlottableEntry p = (PlottableEntry)w;
			
			if (p.getNeoId().equals(neoId)) {
				p.setSelected(true);
			}
			else {
				p.setSelected(false);
			}
		}
	}
}
