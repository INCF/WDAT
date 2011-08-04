package org.gnode.wda.graph;

import java.util.HashMap;

import org.gnode.wda.client.Utilities;
import org.gnode.wda.data.AnalogSignal;
import org.gnode.wda.events.GraphUpdateEvent;
import org.gnode.wda.events.GraphUpdateHandler;
import org.gnode.wda.events.PlottableSelectionEvent;
import org.gnode.wda.events.PlottableSelectionHandler;
import org.gnode.wda.interfaces.DataSource;
import org.gnode.wda.interfaces.DatapointSource;
import org.gnode.wda.interfaces.GraphPresenter;
import org.gnode.wda.interfaces.GraphView;

import ca.nanometrics.gflot.client.event.SelectionListener;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

public class GraphManager implements GraphPresenter, ValueChangeHandler<String>, PlottableSelectionHandler, GraphUpdateHandler{
	HandlerManager localBus;
	DataSource ds;
	GraphView dumbView;
	StaticGraphPanel staticg;
	DetailGraphPanel detailg;
	MasterGraphPanel masterg;
	GraphHistoryWidget historyPanel;
	// Downsampling resolution will be constant for a particular instance
	// of WDAT. Why not use a global variable.
	Integer downsampling;
	
	
	public GraphManager(DataSource ds) {
		this.ds = ds;
		this.localBus = new HandlerManager(null);
		
		// Calculate the widths and heights of the graph panels Using proportions
		Integer graphsHeight = Window.getClientHeight() - 50;
		Integer graphsWidth = Window.getClientWidth() - 300;
		
		Integer staticHeight = (int) (0.2 * graphsHeight);
		Integer masterHeight = (int) (0.25 * graphsHeight); 
		Integer detailHeight = (int) (0.55 * graphsHeight);
	
		this.downsampling = graphsWidth;
		
		this.staticg = new StaticGraphPanel(graphsWidth, staticHeight);
		this.masterg = new MasterGraphPanel(graphsWidth, masterHeight);
		this.detailg = new DetailGraphPanel(graphsWidth, detailHeight);
		this.historyPanel = new GraphHistoryWidget();
		
		this.dumbView = new GraphViewWidget(this.historyPanel,
										    this.staticg,
										    this.masterg,
										    this.detailg);
		
		History.addValueChangeHandler(this);
		this.setupEventTriggers();
		this.setupEventHandlers();
	}


	private void setupEventTriggers() {
		// TODO Auto-generated method stub
	}


	private void setupEventHandlers() {
		this.getBus().addHandler(PlottableSelectionEvent.TYPE, this); 
		this.getBus().addHandler(GraphUpdateEvent.TYPE, this);
	}


	@Override
	public GraphView getView() {
		return this.dumbView;
	}


	@Override
	public HandlerManager getBus() {
		return this.localBus;
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// History change handler
		if (Utilities.getFragmentType(event.getValue()) == "plot") {
			String neo_id = Utilities.getOption(event.getValue(), "obj");
			String neo_type = Utilities.getOption(event.getValue(), "type");
			this.getBus().fireEvent(new PlottableSelectionEvent(neo_id, neo_type));
		} else {
			// pass
		}
	}
	
	@Override
	public void onPlottableSelection(String neoId, String type) {
		// Handles the selection step of the plottable
		
		// Step 1 : add to history
		if (!this.historyPanel.contains(neoId))
			this.historyPanel.addItem(neoId, type, neoId);
		this.historyPanel.setSelection(neoId);
	
		// Step 2 : display loading text
		//this.detailg.showLoading(true);
		
		// Step 3 : procure object parse to JSON and create a datapointSource of it. 
		HashMap<String, String> requestData = new HashMap<String, String>();
		requestData.put("downsample", "" + this.downsampling);
		
		this.localBus.fireEvent(new GraphUpdateEvent("static", neoId,type, requestData));
	}


	@Override
	public void onGraphUpdate(String graphType, final String neoId, final String neoType,
			HashMap<String, String> params) {
		if (graphType.equalsIgnoreCase("static")) {
			staticg.clear();
			
			this.ds.getData(neoId, params, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					staticg.clear();
					
					JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
					final DatapointSource analog = new AnalogSignal(obj);
					
					staticg.clear();
					staticg.addSeries(analog.getName(), analog);
					staticg.draw();
					staticg.addSelectionListener(new graphSelectionListener("master",  neoId, neoType, ds, downsampling));
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub
				}
			});
			
		}
	}
	
	private class graphSelectionListener implements SelectionListener {
		/*
		 * A selection listener used on the any panel that forwards 
		 * selection events to the specified graph panel.
		 */
	
		String neo_id;
		String neo_type;
		DataSource ds;
		Integer downsampling;
		String update_panel;
		
		public graphSelectionListener(String which_panel_to_update, String neo_id, String neo_type, DataSource ds, Integer downsampling) {
			this.update_panel = which_panel_to_update;
			this.neo_id = neo_id;
			this.neo_type = neo_type;
			this.ds = ds;
			this.downsampling = downsampling;
		}
		@Override
		public void selected(double x1, double y1, double x2, double y2) {
			HashMap<String, String> requestData = new HashMap<String, String>();
			requestData.put("downsampling", "" + this.downsampling);
			requestData.put("start_time", "" + x1);
			requestData.put("end_time", "" + x2);
			
			this.ds.getData(this.neo_id, requestData, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					BaseGraphPanel gpanel;
					if (update_panel.equals("master"))
						gpanel = masterg;
					else 
						gpanel = detailg;
						
						
					gpanel.clear();
					
					JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
					DatapointSource analog = new AnalogSignal(obj);
					
					gpanel.addSeries(analog.getName(), analog);
					gpanel.draw();
					gpanel.addSelectionListener(new graphSelectionListener("detail",  neo_id, neo_type, ds, downsampling));
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
}

