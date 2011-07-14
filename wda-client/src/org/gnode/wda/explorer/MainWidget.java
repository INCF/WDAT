package org.gnode.wda.explorer;

import java.util.List;

import org.gnode.wda.data.NeoObject;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

public class MainWidget extends Composite {
	private ScrollPanel main;
	private CellTable<NeoObject> table;
	
	public MainWidget() {
		this.main = new ScrollPanel();
		this.main.setAlwaysShowScrollBars(false);
		
		this.table = new CellTable<NeoObject>();
		
		// Setup table 
		TextColumn<NeoObject> nameColumn = new TextColumn<NeoObject>() {
			@Override
			public String getValue(NeoObject object) {
				return object.name;
			}
		};
		this.table.addColumn(nameColumn, "NEO Id");
		this.table.setColumnWidth(nameColumn, "250px");
		
		TextColumn<NeoObject> typeColumn = new TextColumn<NeoObject>() {
			@Override
			public String getValue(NeoObject object) {
				return object.type;
			}
		};
		this.table.addColumn(typeColumn, "NEO Type");
		this.table.setColumnWidth(typeColumn, "400px");
		
		this.table.setRowCount(0);
		
		// Setup main widget
		main.add(this.table);
		initWidget(this.main);
	}
	
	public void setData(List<NeoObject> data) {
		this.table.setRowCount(data.size());
		this.table.setRowData(data);
	}
}
