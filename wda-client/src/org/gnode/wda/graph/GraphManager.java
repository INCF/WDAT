package org.gnode.wda.graph;

import org.gnode.wda.client.Utilities;
import org.gnode.wda.data.AnalogSignal;
import org.gnode.wda.data.IRSAAnalogSignal;
import org.gnode.wda.events.PlottableSelectionEvent;
import org.gnode.wda.events.PlottableSelectionHandler;
import org.gnode.wda.interfaces.DataSource;
import org.gnode.wda.interfaces.DatapointSource;
import org.gnode.wda.interfaces.GraphPresenter;
import org.gnode.wda.interfaces.GraphView;

import ca.nanometrics.gflot.client.SeriesHandler;

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

public class GraphManager implements GraphPresenter, ValueChangeHandler<String>, PlottableSelectionHandler{
	HandlerManager localBus;
	DataSource ds;
	GraphView dumbView;
	StaticGraphPanel staticg;
	DetailGraphPanel detailg;
	MasterGraphPanel masterg;
	GraphHistoryWidget historyPanel;
	
	
	public GraphManager(DataSource ds) {
		this.ds = ds;
		this.localBus = new HandlerManager(null);
		
		// Calculate the widths and heights of the graph panels Using proportions
		Integer graphsHeight = Window.getClientHeight() - 50;
		Integer graphsWidth = Window.getClientWidth() - 300;
		
		Integer staticHeight = (int) (0.2 * graphsHeight);
		Integer masterHeight = (int) (0.25 * graphsHeight); 
		Integer detailHeight = (int) (0.55 * graphsHeight);
		
		
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
		this.detailg.showLoading(true);
		
		// Step 3 : procure object parse to 
		this.ds.getData(neoId, null, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				detailg.showLoading(false);
			
				// parse response into JSON object now.
				JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
				DatapointSource analog = new IRSAAnalogSignal(obj);
				
				staticg.clear();
				staticg.addSeries(analog.getName(), analog);
				staticg.draw();
				staticg.setupEventTriggers();
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}

