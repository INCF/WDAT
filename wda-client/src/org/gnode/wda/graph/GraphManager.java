package org.gnode.wda.graph;

import org.gnode.wda.client.Utilities;
import org.gnode.wda.data.AnalogSignal;
import org.gnode.wda.data.Epoch;
import org.gnode.wda.data.Event;
import org.gnode.wda.data.IRSAAnalogSignal;
import org.gnode.wda.data.Spike;
import org.gnode.wda.data.SpikeTrain;
import org.gnode.wda.events.PlottableSelectionEvent;
import org.gnode.wda.events.PlottableSelectionHandler;
import org.gnode.wda.interfaces.DataSource;
import org.gnode.wda.interfaces.GraphPresenter;
import org.gnode.wda.interfaces.GraphView;

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

public class GraphManager implements GraphPresenter, ValueChangeHandler<String>{
	HandlerManager localBus;
	DataSource ds;
	GraphView dumbView;
	StaticPanelWidget staticPanel;
	MasterDetailPanelWidget masterDetailPanel;
	GraphHistoryWidget historyPanel;
	
	
	public GraphManager(DataSource ds) {
		this.ds = ds;
		this.localBus = new HandlerManager(null);
		this.staticPanel = new StaticPanelWidget();
		this.masterDetailPanel = new MasterDetailPanelWidget();
		this.historyPanel = new GraphHistoryWidget();
		
		this.dumbView = new GraphViewWidget(this.historyPanel,
										    this.staticPanel,
										    this.masterDetailPanel);
		
		History.addValueChangeHandler(this);
		this.setupEventTriggers();
		this.setupEventHandlers();
	}


	private void setupEventTriggers() {
		// TODO Auto-generated method stub
	}


	private void setupEventHandlers() {
		this.getBus().addHandler(PlottableSelectionEvent.TYPE, new PlottableSelectionHandler() {
			@Override
			public void onPlottableSelection(String neo_id, String neo_type) {
				if ( ! historyPanel.contains(neo_id)) {
					// Check to see if history already contains the item.
					historyPanel.addItem(neo_id, neo_type, neo_id);
				}
				historyPanel.setSelection(neo_id);
				draw(neo_id, neo_type);
			}
		});
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
	
	public void draw(String neo_id, final String type) {
		this.ds.getData(neo_id, null, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() == 200) {
					JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
					if (type.equalsIgnoreCase("analogsignal")) {
						AnalogSignal analog = new AnalogSignal(obj);
						// do analog plotting 
						masterDetailPanel.setData(analog);
						staticPanel.setGraph(analog);
					}
					if (type.equalsIgnoreCase("epoch")) {
						Epoch epoch = new Epoch(obj);
						// do epoch plotting 
						
					}if (type.equalsIgnoreCase("event")) {
						Event event = new Event(obj);
						// do event plotting
					}if (type.equalsIgnoreCase("irsaanalogsignal")) {
						IRSAAnalogSignal irsaanalogsignal = new IRSAAnalogSignal(obj);
						// process irsaa
					}if (type.equalsIgnoreCase("spike")) {
						Spike spike = new Spike(obj);
						// process spike
					}if (type.equalsIgnoreCase("spiketrain")) {
						SpikeTrain spiketrain = new SpikeTrain(obj);
						// process spiketrain
					}
				} else {
					// Convey that an error on the server occured
				}
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error receiving data");
			}
		});
	}
}

