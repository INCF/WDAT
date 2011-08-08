package org.gnode.wda.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.gnode.wda.client.Utilities;
import org.gnode.wda.data.AnalogSignal;
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

public class GraphManager2 implements GraphPresenter, 
									  ValueChangeHandler<String>,
									  PlottableSelectionHandler {
	HandlerManager localBus;
	DataSource ds;
	GraphView dumbView;
	StaticGraphPanel staticg;
	DetailGraphPanel detailg;
	MasterGraphPanel masterg;
	GraphHistoryWidget historyPanel;
	
	// Downsampling parameter will be a constant for this instance 
	// so declare it as a class property.
	Integer downsample;
	
	List<String> currentGraphs;
	// currentGraphs contains NEO ids of all the graphs that are 
	// *already* plotted in the graph panels.

	List<String> currentTypes;
	// Corresponds to the currentGraphs list
	
	Double masterStart, masterEnd;
	Double detailStart, detailEnd;
	// These contain the current selections within the master and detail
	// panels. 
	
	public GraphManager2(DataSource ds) {
		this.ds = ds;
		this.localBus = new HandlerManager(null);
		
		// Calculate the widths and heights of the graphPanels using 
		// proportions
		Integer graphsHeight = Window.getClientHeight() - 50;
		Integer graphsWidth = Window.getClientWidth() - 300;
		
		Integer staticHeight = (int) (0.2 * graphsHeight);
		Integer masterHeight = (int) (0.25 * graphsHeight);
		Integer detailHeight = (int) (0.55 * graphsHeight);
		
		this.downsample = this.computeDownsample(graphsWidth);
		
		this.staticg = new StaticGraphPanel(graphsWidth, staticHeight);
		this.masterg = new MasterGraphPanel(graphsWidth, masterHeight);
		this.detailg = new DetailGraphPanel(graphsWidth, detailHeight);
		this.historyPanel = new GraphHistoryWidget();
		
		this.dumbView = new GraphViewWidget(this.historyPanel,
											this.staticg,
											this.masterg,
											this.detailg);
		
		// Initialize these to avoid NullPointer conflicts.
		this.currentGraphs = new Vector<String>();
		this.currentTypes = new Vector<String>();
		this.detailEnd = 0.0;
		this.detailStart = 0.0;
		this.masterEnd = 0.0;
		this.masterStart = 0.0;
		
		// Setup listeners and handlers 
		History.addValueChangeHandler(this);
		this.getBus().addHandler(PlottableSelectionEvent.TYPE, this);
		
	}

	public Integer computeDownsample(Integer width) {
		Integer criticalDownsamling = 1000;
		
		if ( width > criticalDownsamling ) {
			return 1000;
		}
		
		return width;
	}
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// Generate list of neos being requested to be plotted. 
		List<String> neo_ids = Utilities.parseCSV(
										Utilities.getOption(event.getValue(), "obj")
										);
		List<String> neo_types = Utilities.parseCSV(
										Utilities.getOption(event.getValue(), "type")
										);
		
		// fire the required event. 
		this.getBus().fireEvent(new PlottableSelectionEvent(neo_ids, neo_types));
	}
	@Override
	public HandlerManager getBus() {
		return this.localBus ;
	}

	@Override
	public GraphView getView() {
		return this.dumbView;
	}

	@Override
	public void onPlottableSelection(final List<String> neo_ids, final List<String> types ) {
		List<String> requests = new Vector<String>();
		// this list will contain the neo's that need to be fetched. ie aren't already
		// plotted in staticg
		
		// Check whether any of the already ploted signals needs to be removed
		// and then remove them.
		for (String neo : this.currentGraphs) {
			if ( ! neo_ids.contains(neo) ) {
				this.currentTypes.remove(this.currentGraphs.indexOf(neo));
				this.currentGraphs.remove(neo);
			}
		}
		
		// for each neo_id that isn't already a part of the currentGraphs, add 
		// them to currentGraphs
		for ( String neo : neo_ids ) {
			if ( ! this.currentGraphs.contains(neo) ) {
				this.currentGraphs.add(neo);
				this.currentTypes.add(types.get(neo_ids.indexOf(neo)));
				requests.add(neo);
			}
		}
	
		// request parameter initialization
		HashMap<String, String> requestData = new HashMap<String, String>();
		requestData.put("downsample", "" + this.downsample);
		
		// Updating staticg s.
		for ( final String neo : requests ) {
			this.ds.getData(neo, requestData, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
					
					DatapointSource dps;
					if ( types.get(neo_ids.indexOf(neo)).equals("analogsignal") ) {
						dps = new AnalogSignal(obj);
					} 
					else {
						// TODO The default case. Type not specified.
						return;
					}
					
					staticg.addSeries(dps.getName(), dps);
					staticg.draw();
					staticg.addSelectionListener(new graphSelectionListener("static"));		
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
		 * Clearly, there is a lot of code repetition. update methods for 
		 * master and detail vary only in the instance variable being called.
		 * 
		 * However, this is done to separate the two. If new developments cause
		 * a different API to be implemented, this repetition can also be viewed 
		 * as a flexibility ensurer. 
		 * 
		 */
		String graph_type;
		public graphSelectionListener(String graph_type){
			// graph_type can be either "static" or "master" depending
			// on the panel being listened to.
			this.graph_type = graph_type;
		}
		@Override
		public void selected(double x1, double y1, double x2, double y2) {
			if (this.graph_type.equals("static")) {
				masterg.clear();
				// Obvously, this needs to be done.Static doesn't change when 
				// the same neo_obj is reloaded. master will change. 
				// TODO show loading
				
				
				HashMap<String, String> requestData = new HashMap<String, String>();
				requestData.put("downsample", "" + downsample);
				requestData.put("start_time", "" + x1);
				requestData.put("end_time", "" + x2);
			
				for ( final String neo : currentGraphs ) {
					ds.getData(neo, requestData, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							DatapointSource dps;
							JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
							
							if ( currentTypes.get(currentGraphs.indexOf(neo)).equals("analogsignal")) {
								dps = new AnalogSignal(obj);
							}
							else {
								// default case
								return;
							}
							
							masterg.addSeries(dps.getName(), dps);
							masterg.draw();
							masterg.addSelectionListener(new graphSelectionListener("master"));
						}
						
						@Override
						public void onError(Request request, Throwable exception) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			}
			else if (this.graph_type.equals("master")) {
				detailg.clear();
				// Obvously, this needs to be done.Static doesn't change when 
				// the same neo_obj is reloaded. master will change. 
				// TODO show loading
				
				
				HashMap<String, String> requestData = new HashMap<String, String>();
				requestData.put("downsample", "" + downsample);
				requestData.put("start_time", "" + x1);
				requestData.put("end_time", "" + x2);
			
				for ( final String neo : currentGraphs ) {
					ds.getData(neo, requestData, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							DatapointSource dps;
							JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
							
							if ( currentTypes.get(currentGraphs.indexOf(neo)).equals("analogsignal")) {
								dps = new AnalogSignal(obj);
							}
							else {
								// default case
								return;
							}
							
							detailg.addSeries(dps.getName(), dps);
							detailg.draw();
							detailg.addSelectionListener(new graphSelectionListener("master"));
						}
						
						@Override
						public void onError(Request request, Throwable exception) {
							// TODO Auto-generated method stub
							
						}
					});
				}	
			}
		}
	}
}
