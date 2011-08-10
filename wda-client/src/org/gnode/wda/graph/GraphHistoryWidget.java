package org.gnode.wda.graph;


import org.gnode.wda.client.Utilities;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
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
	
	public void addItem(String neo_id, String neo_type, String name, Boolean pre_selected) {
		// First check whether item already is in the list, if so, just set it to selected.
		if (this.contains(neo_id)) {
			for (int i = 0; i < this.list.getWidgetCount(); i++) {
				Widget w = this.list.getWidget(i);
				PlottableEntry p = (PlottableEntry)w;
				
				if ( p.getNeoId().equals(neo_id) ) {
					p.setSelected(true);
					return;
				}
			}
		}
		
		
		PlottableEntry entry = new PlottableEntry(neo_id, neo_type, name,
												 "230px", pre_selected);
		this.list.insert((IsWidget)entry, 0);
	}
	
	private class PlottableEntry extends Composite {
		private String neo_id;
		private HorizontalPanel main;
		private Label name_label;
		private Boolean selected;
		private CheckBox select;
		
		public PlottableEntry(final String neo_id, final String neo_type, String name, String width, Boolean pre_selected) {
			// neo_id, neo_type, name are self-explanatory.
			// Width is required because this class has no knowledge of its parents
			this.main = new HorizontalPanel();
			
			this.main.setWidth(width);
			this.main.setStylePrimaryName("graph-history-tab");
			this.main.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
			this.main.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			this.neo_id = neo_id;
			
			this.name_label = new Label(name);
			this.select = new CheckBox();
			this.select.setEnabled(false);
			this.main.add(name_label);
			this.main.add(select);
			
			this.main.addDomHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if ( selected ) {
						// remove the obj id from the token
						String newToken = Utilities.removeObjectFromGraphUrl(neo_id);
						History.newItem(newToken);
					} else {
						// add to the token
						String newToken = Utilities.updateGraphUrlWithAnotherObject(neo_id);
						History.newItem(newToken);
					}
				}
			}, ClickEvent.getType());
		
			initWidget(this.main);
			
			this.setSelected(pre_selected);
		}
	
		public void setSelected(Boolean value) {
			this.selected = value;
			this.select.setValue(value);
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

	public void clearSelections() {
		for (int i = 0; i < this.list.getWidgetCount(); i++ ) {
			Widget w = this.list.getWidget(i);
			PlottableEntry p = (PlottableEntry)w;
			
			p.setSelected(false);
		}
	}

}
