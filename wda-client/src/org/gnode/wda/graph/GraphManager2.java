package org.gnode.wda.graph;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.gnode.wda.client.Resources;
import org.gnode.wda.client.Utilities;
import org.gnode.wda.data.AnalogSignal;
import org.gnode.wda.data.Epoch;
import org.gnode.wda.data.Event;
import org.gnode.wda.data.IRSAAnalogSignal;
import org.gnode.wda.data.NeoObject;
import org.gnode.wda.events.PlottableSelectionEvent;
import org.gnode.wda.events.PlottableSelectionHandler;
import org.gnode.wda.interfaces.DataSource;
import org.gnode.wda.interfaces.GraphDataAdapter;
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
	HandlerManager eventBus;
	DataSource ds;
	GraphView dumbView;
	StaticGraphPanel staticg;
	DetailGraphPanel detailg;
	MasterGraphPanel masterg;
	GraphHistoryWidget historyPanel;
	
	// Downsampling parameter will be a constant for this instance 
	// so declare it as a class property.
	Integer downsample;
	
	// List<String> currentGraphs;
	// REWRITE : This is not a good idea. There are race conditions involved in async workflows! REWRITE
	// currentGraphs contains NEO ids of all the graphs that are 
	// *already* plotted in the graph panels.

	private HashMap<String, Double> t_starts;
	private HashMap<String, Double> t_ends;
	// These maps intend to store the values of analogsignal's times. The values
	// will be updated only when the static views are being rendered. This is done
	// because static requests don't supply limiting t parameters so the entire signal
	// is fetched. This isn't the case in master or detail views. 
	
	Double masterStart, masterEnd;
	Double detailStart, detailEnd;
	// These contain the current selections within the master and detail
	// panels. 
	
	public GraphManager2(DataSource ds, HandlerManager eventBus) {
		this.ds = ds;
		this.eventBus = eventBus;
		
		// Calculate the widths and heights of the graphPanels using 
		// proportions
		Integer graphsHeight = Window.getClientHeight() - 50;
		Integer graphsWidth = Window.getClientWidth() - 300;
		
		Integer staticHeight = (int) (Resources.staticRatio * graphsHeight);
		Integer masterHeight = (int) (Resources.masterRatio * graphsHeight);
		Integer detailHeight = (int) (Resources.detailRatio * graphsHeight);
		
		this.downsample = Resources.computeDownsample(graphsWidth);
		
		this.staticg = new StaticGraphPanel(graphsWidth, staticHeight);
		this.masterg = new MasterGraphPanel(graphsWidth, masterHeight);
		this.detailg = new DetailGraphPanel(graphsWidth, detailHeight);
		this.historyPanel = new GraphHistoryWidget();
		
		this.dumbView = new GraphViewWidget(this.historyPanel,
											this.staticg,
											this.masterg,
											this.detailg);
		
		// Initialize these to avoid NullPointer conflicts.
		this.detailEnd = 0.0;
		this.detailStart = 0.0;
		this.masterEnd = 0.0;
		this.masterStart = 0.0;
		
		this.t_ends = new HashMap<String, Double> ();
		this.t_starts = new HashMap<String, Double>();
		
		// Setup listeners and handlers 
		History.addValueChangeHandler(this);
		this.getBus().addHandler(PlottableSelectionEvent.TYPE, this);
		
	}

	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if (Utilities.getOption(event.getValue(), "view").equals("graph") ) {
			// Generate list of neos being requested to be plotted. 
			List<String> neo_ids = Utilities.parseCSV(
											Utilities.getOption(event.getValue(), "obj")
											);
			
			// fire the required event. 
			this.getBus().fireEvent(new PlottableSelectionEvent(neo_ids));
		}
	}
	@Override
	public HandlerManager getBus() {
		return this.eventBus ;
	}

	@Override
	public GraphView getView() {
		return this.dumbView;
	}

	@Override
	public void onPlottableSelection(final List<String> neo_ids) {
		// Update the history widget
		this.historyPanel.clearSelections();
		
		for (String neo : neo_ids ) {
			String neo_type = neo.split("_", 2)[0];
			this.historyPanel.addItem(neo, neo_type, neo, true);
		}
		
		// Clear the static graphs.
		staticg.clear();
	
		// request parameter initialization
		final HashMap<String, String> requestData = new HashMap<String, String>();
		requestData.put("downsample", "" + this.downsample);
		
		// Updating staticg s.
		for ( final String neo : neo_ids) {
			final String neo_type = neo.split("_", 2)[0];
			final String cssColor = Resources.colors.get(neo_ids.indexOf(neo));
			// this may be used to render unicolored segments
			
			if (neo_type.equals("segment")) {
				final String immutable_container_name = neo;
				// this will later be used to put in the name of the segment being plotted. Since
				// that action will be done within the callback, needs to be final
				
				this.ds.getChildren(neo, new RequestCallback() {
					@Override
					public void onError(Request request, Throwable exception) {
						// TODO Auto-generated method stub
						
					}
					@Override
					public void onResponseReceived(Request request, Response response) {
						List<NeoObject> children = ds.parseChildren(response, "segment");
						String mutable_container_name = immutable_container_name;
						// this is updated each time
						
						for (NeoObject neo : NeoObject.getPlottablesOnly(children)) {
							final String neo_id = neo.getNeo_id();
							final String neo_type = neo_id.split("_", 2)[0];
							final String container_name = mutable_container_name;
							// This will be the actual value used while plotting
							
							ds.getData(neo_id, requestData, new RequestCallback() {
								@Override
								public void onError(Request request,
										Throwable exception) {
									// TODO Auto-genjerated method stub
									
								}

								@Override
								public void onResponseReceived(Request request,
										Response response) {
									JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
									
									GraphDataAdapter gda = create_gda_of_type(obj, neo_type);
									
									if (gda == null) {
										return;
									} 
									
									gda.setName(container_name);
									
									// updating the t_starts and t_ends values. Note that only datapoint
									// series are being used because they are the ones that require t_starts
									// and t_ends in the requestData
									if (neo_type.equals("analogsignal") || neo_type.equals("irsaanalogsignal")) {
										for (int i = 0; i < gda.getDatapointSeriesCount(); i++) {
											TreeMap<Double, Double> dps = gda.getDatapointSeries(i);
											
											Object [] times_array = dps.keySet().toArray();
											t_starts.put(neo_id, (Double) times_array[0]); // the first element
											t_ends.put(neo_id, (Double) times_array[times_array.length - 1]); // the last element
										}
									}
									
									staticg.addSeries(gda.getName(), gda, cssColor);
									staticg.draw();
									staticg.addSelectionListener(new graphSelectionListener("static"));
								}
							});
							
							mutable_container_name = "";
						}
					}
				});
			}
			
			else {
				/* 
				 * Since this is not a container element being plotted, proceed ordinarily.
				 */
				this.ds.getData(neo, requestData, new RequestCallback() {
					@Override
					public void onResponseReceived(Request request, Response response) {
						if (response.getStatusCode() == 200) {
							JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
							
							GraphDataAdapter gda;
							
							gda = create_gda_of_type(obj, neo_type);
							
							if (gda == null) {
								return;
							}
							
							// updating the t_starts and t_ends values. Note that only datapoint
							// series are being used because they are the ones that require t_starts
							// and t_ends in the requestData
							if (neo_type.equals("analogsignal") || neo_type.equals("irsaanalogsignal")) {
								for (int i = 0; i < gda.getDatapointSeriesCount(); i++) {
									TreeMap<Double, Double> dps = gda.getDatapointSeries(i);
									
									Object [] times_array = dps.keySet().toArray();
									t_starts.put(neo, (Double) times_array[0]); // the first element
									t_ends.put(neo, (Double) times_array[times_array.length - 1]); // the last element
								}
							}
							
							// update the name as shown in the history panel
							historyPanel.updateItem(neo, gda.getName());
							
							staticg.addSeries(gda.getName(), gda, null);
							staticg.draw();
							staticg.addSelectionListener(new graphSelectionListener("static"));
						}
					}
					@Override
					public void onError(Request request, Throwable exception) {
						// 	TODO Auto-generated method stub
						
					}
				});
			}
		}
	}
	
	private class graphSelectionListener implements SelectionListener {
		/*
		 * Clearly, there is a lot of code repetition. update methods for 
		 * master and detail vary only in the instance variable being called.
		 * 
		 * However, this is done to separate the two. If new developments cause
		 * a different API to be implemented, this repetition can also be viewed 
		 * as a flexibile design-decision. 
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
				List<String> currentGraphs = Utilities.parseCSV(Utilities.getOption(History.getToken(), "obj"));
				
				final HashMap<String, String> requestData = new HashMap<String, String>();
				requestData.put("downsample", "" + downsample);
				requestData.put("start_time", "" + x1);
				requestData.put("end_time", "" + x2);
		
				// update the current selection fields.
				masterStart = x1;
				masterEnd   = x2;
				
				
				for (final String neo : currentGraphs ) {
					String neo_type = neo.split("_",2)[0];
					
					if (neo_type.equals("segment")) {
						final String cssColor = Resources.colors.get(currentGraphs.indexOf(neo));
						// Will be used to render unicolored segments. 
						
						ds.getChildren(neo, new RequestCallback(){
							@Override
							public void onError(Request request,
									Throwable exception) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onResponseReceived(Request request,
									Response response) {
								masterg.clear();
								List<NeoObject> seg_children = ds.parseChildren(response, "segment");
								String seg_name = neo;
								String seg_name_to_be_changed = seg_name;
								
								for (NeoObject child : seg_children) {
									String child_neo = child.getNeo_id();
									final String child_neo_type = child_neo.split("_", 2)[0];
									final String seg_name_to_be_fed = seg_name_to_be_changed;
					/*				TODO
									// updating the requestData params based on the object to be requested. 
									if (child_neo_type.equals("analogsignal") || child_neo_type.equals("irsaanalogsignal")){
									}
					*/				
									ds.getData(child_neo, requestData, new RequestCallback() {
										
										@Override
										public void onResponseReceived(Request request, Response response) {
											
											JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
											
											GraphDataAdapter gda = create_gda_of_type(obj, child_neo_type);
											
											if (gda == null) {
												return;
											} 
											
											gda.setName(seg_name_to_be_fed);
											
											masterg.addSeries(gda.getName(), gda, cssColor);
											masterg.draw();
											masterg.addSelectionListener(new graphSelectionListener("master"));
										}
										
										@Override
										public void onError(Request request, Throwable exception) {
											// TODO Auto-generated method stub
											
										}
									});
									
									seg_name_to_be_changed = "";
								}
								
							}
						});
						
						
						return; 
						// To ensure the system doesn't progress into plotting non-container-plottables
					}
					
					
					ds.getData(neo, requestData, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode() == 200) {
								GraphDataAdapter dps;
								JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
								String neo_type = neo.split("_", 2)[0];
								
								dps = create_gda_of_type(obj, neo_type);
								if (dps == null) {
									return;
								}
								masterg.addSeries(dps.getName(), dps, null);
								masterg.draw();
								masterg.addSelectionListener(new graphSelectionListener("master"));
							}
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
				
				List<String> currentGraphs = Utilities.parseCSV(Utilities.getOption(History.getToken(), "obj"));
				
				
				final HashMap<String, String> requestData = new HashMap<String, String>();
				requestData.put("downsample", "" + downsample);
				requestData.put("start_time", "" + x1);
				requestData.put("end_time", "" + x2);
			
				// update the current selection fields.
				detailStart = x1;
				detailEnd	= x2;
				
				for ( final String neo : currentGraphs ) {
					String neo_type = neo.split("_", 2)[0];
					
					if (neo_type.equals("segment")) {
						final String cssColor = Resources.colors.get(currentGraphs.indexOf(neo));
						// Will be used to render unicolored segments. 
						
						ds.getChildren(neo, new RequestCallback(){
							@Override
							public void onError(Request request,
									Throwable exception) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onResponseReceived(Request request,
									Response response) {
								detailg.clear();
								List<NeoObject> seg_children = ds.parseChildren(response, "segment");
								String seg_name = neo;
								String seg_name_to_be_changed = seg_name;
								
								for (NeoObject child : seg_children) {
									String child_neo = child.getNeo_id();
									final String child_neo_type = child_neo.split("_", 2)[0];
									final String seg_name_to_be_fed = seg_name_to_be_changed;
									
									ds.getData(child_neo, requestData, new RequestCallback() {
										
										@Override
										public void onResponseReceived(Request request, Response response) {
											
											JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
											
											GraphDataAdapter gda = create_gda_of_type(obj, child_neo_type);
											
											if (gda == null) {
												return;
											} 
											
											gda.setName(seg_name_to_be_fed);
											
											detailg.addSeries(gda.getName(), gda, cssColor);
											detailg.draw();
											detailg.addSelectionListener(new graphSelectionListener("static"));
										}
										
										@Override
										public void onError(Request request, Throwable exception) {
											// TODO Auto-generated method stub
											
										}
									});
									
									seg_name_to_be_changed = "";
								}
								
							}
						});
						
						
						return; 
						// To ensure the system doesn't progress into plotting non-container-plottables
					}
					ds.getData(neo, requestData, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode() == 200) {
								GraphDataAdapter dps;
								JSONObject obj = JSONParser.parseLenient(response.getText()).isObject();
								String neo_type = neo.split("_",2)[0];
								
								dps = create_gda_of_type(obj, neo_type);
								if (dps == null) {
									return;
								}
								
								detailg.addSeries(dps.getName(), dps, null);
								detailg.draw();
							}
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
	
	private GraphDataAdapter create_gda_of_type(JSONObject obj, String neo_type) {
		/* 
		 * To be used only for non-container type plottable objects.
		 */
		
		if (neo_type.equals("analogsignal") ) {
			return new AnalogSignal(obj);
		} 
		else if (neo_type.equals("irsaanalogsignal") ) {
			return new IRSAAnalogSignal(obj);
		}
		else if (neo_type.equals("epoch")) {
			return new Epoch(obj);
		}
		else if (neo_type.equals("event")) {
			return new Event(obj);
		}
		return null;
	}
}
