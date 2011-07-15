package org.gnode.wda.explorer;

import java.util.List;

import org.gnode.wda.data.NeoObject;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
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
		
		ButtonCell btnCell = new ButtonCell();
		Column<NeoObject, String> plotColumn = new Column<NeoObject, String>(btnCell){
			@Override
			public String getValue(NeoObject object) {
				return "plot";
			}};
		// Adding the handler for the button;
		plotColumn.setFieldUpdater(new FieldUpdater<NeoObject, String>() {
			@Override
			public void update(int index, NeoObject object, String value) {
				History.newItem("plot:" + object.name + "&type=" + object.type);
			}
		});
		this.table.addColumn(plotColumn, "Actions");
		
			
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
