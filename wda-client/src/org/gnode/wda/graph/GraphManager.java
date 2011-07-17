package org.gnode.wda.graph;

import org.gnode.wda.client.Utilities;
import org.gnode.wda.data.AnalogSignal;
import org.gnode.wda.data.NeoObject;
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
	MasterPanelWidget masterPanel;
	DetailPanelWidget detailPanel;
	GraphHistoryWidget historyPanel;
	
	
	public GraphManager(DataSource ds) {
		this.ds = ds;
		this.localBus = new HandlerManager(null);
		this.staticPanel = new StaticPanelWidget();
		this.masterPanel = new MasterPanelWidget();
		this.detailPanel = new DetailPanelWidget();
		this.historyPanel = new GraphHistoryWidget();
		
		this.dumbView = new GraphViewWidget(this.historyPanel,
										    this.staticPanel,
										    this.masterPanel,
										    this.detailPanel);
		
		History.addValueChangeHandler(this);
		this.setupEventTriggers();
		this.setupEventHandlers();
	}


	private void setupEventTriggers() {
		// TODO Auto-generated method stub
	}


	private void setupEventHandlers() {
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
		if (Utilities.getFragmentType(event.getValue()) == "plot") {
			String neo_id = Utilities.getOption(event.getValue(), "obj");
			String neo_type = Utilities.getOption(event.getValue(), "type");
			this.addHistory(neo_id);		
			this.draw(neo_id, neo_type);
		} else {
			// pass
		}
	}
	
	public void addHistory(String neo_id) {
		// add to the history panel.
	}
	
	public void draw(String neo_id, final String type) {
		this.ds.getData(neo_id, null, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() == 200) {
					JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
					if (type.equalsIgnoreCase("analogsignal")) {
						AnalogSignal analog = new AnalogSignal(obj);
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

