package org.gnode.wda.explorer;

import java.util.List;

import org.gnode.wda.data.NeoObject;
import org.gnode.wda.events.NotificationHandler;
import org.gnode.wda.events.NotificationEvent;
import org.gnode.wda.interfaces.DataSource;
import org.gnode.wda.interfaces.ExplorerPresenter;
import org.gnode.wda.interfaces.ExplorerView;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.TreeItem;

/*
 * Explorer is the presenter for the explore tab. It enables you to browse
 * your way through a neo data structure and then when you make a plot selection,
 * conveys the event to the top EventBus as defined in the appcontroller class.
 */
public class Explorer implements ExplorerPresenter, ValueChangeHandler<String>, NotificationHandler{
	HandlerManager localBus;
	ExplorerView dumbView; // Named dumbview for reminding me.
	MainWidget main;
	TreeWidget tree;
	TopLevelSelectorWidget tlsw;
	NotificationArea notification;
	DataSource ds; 
	
	public Explorer(DataSource ds) {
		this.localBus = new HandlerManager(null);
	
		this.ds = ds; 

		// initialize all widgets
		this.main = new MainWidget();
		this.tree = new TreeWidget(this.ds);
		this.tlsw = new TopLevelSelectorWidget();
		this.notification = new NotificationArea();
		
		// initialize the view that contains all the widgets
		// this view is so dumb, that it only has a constructor.
		this.dumbView = new ExplorerViewWidget(this.main, this.tree, this.tlsw, this.notification);

		this.setupEventTriggers();
		this.setupEventHandlers();
	}

	public void setupEventTriggers() {
	}
	
	public void setupEventHandlers() {
		History.addValueChangeHandler(this);
		this.localBus.addHandler(NotificationEvent.TYPE, this);
		
		this.tlsw.setChangeHandler(new ChangeHandler(){
			@Override
			public void onChange(ChangeEvent event) {
				setTopLevel(tlsw.getSelection().toLowerCase());
			}
		});
		
		this.tree.setOpenHandler(new OpenHandler<TreeItem>(){
			@Override
			public void onOpen(OpenEvent<TreeItem> event) {
				// Unless the item is root, 
				if (event.getTarget().getTitle().equals("")) 
					return;
				
				setChildren(event.getTarget());
				
				event.getTarget().setState(true);
			
			}
		});
		
		this.tree.setSelectionHandler(new SelectionHandler<TreeItem> () {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				// Unless the item is root,
				if (event.getSelectedItem().getTitle().equals(""))
					return;
				
				setChildren(event.getSelectedItem());
			}
		});
		
		this.localBus.fireEvent(new NotificationEvent("Setup Event Handlers"));
	}
	
	@Override
	public ExplorerView getView() {
		return this.dumbView;
	}
	
	@Override
	public HandlerManager getBus() {
		// TODO Auto-generated method stub
		return this.localBus;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		/*
		if (Utilities.getFragmentType(event.getValue()) == "explore") {
			String top = Utilities.getOption(event.getValue(), "top_level");
			if (top != "" && top != "select") {
				setTopLevel(top);
			}
		}
		else {
			// somehow, if this else block doesn't exist, the appcontroller 
			// valuechangehandler is not triggered.
		}*/
	}
	
	public void setTopLevel(final String type) {
		this.localBus.fireEvent(new NotificationEvent("Getting Data"));
		this.ds.getType(type, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() == 200) {
					localBus.fireEvent(new NotificationEvent("Received data."));
					List<NeoObject> selected = ds.parseType(response, type);
					
					tree.setChildren(tree.root, selected);
				} else {
					// TODO Communicate that data isn't being loaded. 
				}
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	public void setChildren(final TreeItem titem) {
		this.localBus.fireEvent(new NotificationEvent("Getting data"));
		
		this.ds.getChildren(titem.getText(), new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() == 200) {
					localBus.fireEvent(new NotificationEvent("Received data"));
					List<NeoObject> selected = ds.parseChildren(response, titem.getTitle());
					
					// we need to filter out all the plottable objects here. 
					tree.setChildren(titem, NeoObject.getContainersOnly(selected));
					main.setData(NeoObject.getPlottablesOnly(selected));
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onNotification(String message) {
		this.notification.publish(message);
	}
}
