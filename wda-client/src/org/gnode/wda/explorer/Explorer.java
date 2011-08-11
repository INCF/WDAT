package org.gnode.wda.explorer;

import java.util.List;


import org.gnode.wda.client.Utilities;
import org.gnode.wda.data.NeoObject;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TreeItem;

/*
 * Explorer is the presenter for the explore tab. It enables you to browse
 * your way through a neo data structure and then when you make a plot selection,
 * conveys the event to the top EventBus as defined in the appcontroller class.
 */
public class Explorer implements ExplorerPresenter, ValueChangeHandler<String> {
	HandlerManager localBus;
	ExplorerView dumbView; // Named dumbview for reminding me.
	MainWidget main;
	TreeWidget tree;
	TopLevelSelectorWidget tlsw;
	//NotificationArea notification;
	DataSource ds; 
	
	public Explorer(DataSource ds) {
		this.localBus = new HandlerManager(null);
	
		this.ds = ds; 
		
		// initialize all widgets
		this.main = new MainWidget();
		this.tree = new TreeWidget(this.ds);
		this.tlsw = new TopLevelSelectorWidget();
		//this.notification = new NotificationArea();
		
		// initialize the view that contains all the widgets
		// this view is so dumb, that it only has a constructor.
		this.dumbView = new ExplorerViewWidget(this.main, this.tree, this.tlsw);//, this.notification);

		this.setupEventTriggers();
		this.setupEventHandlers();
	}

	public void setupEventTriggers() {
	}
	
	public void setupEventHandlers() {
		History.addValueChangeHandler(this);
		
		
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
				
				setTreeChildren(event.getTarget());
				
				event.getTarget().setState(true);
			
			}
		});
		
		this.tree.setSelectionHandler(new SelectionHandler<TreeItem> () {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				// Unless the item is root,
				if (event.getSelectedItem().getTitle().equals(""))
					return;
				
				String parent_neo_id = event.getSelectedItem().getText();
				String newToken = Utilities.setValue("container", parent_neo_id);
				History.newItem(newToken);
			}
		});
		
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
		// History change handler
		if ( Utilities.getOption(event.getValue(), "view").equals("explore")) {
			String container = Utilities.getOption(event.getValue(), "container");
			if ( ! container.equals("") ) {
				setMainItems(container);
			}
		}
	}
	
	public void setTopLevel(final String type) {
		this.ds.getType(type, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() == 200) {
					List<NeoObject> selected = ds.parseType(response, type);
					
					tree.setChildren(tree.root, selected);
					
					tree.openRoot();
					// You would want the root to open up once the elements are loaded.
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
	
	public void setTreeChildren(final TreeItem titem) {
		this.ds.getChildren(titem.getText(), new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() == 200) {
					List<NeoObject> selected = ds.parseChildren(response, titem.getTitle());
					
					// we need to filter out all the plottable objects here. 
					tree.setChildren(titem, NeoObject.getContainersOnly(selected));
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void setMainItems(final String parent_neo_id) {
		this.ds.getChildren(parent_neo_id, new RequestCallback() {
			@Override
			public void onError(Request request, Throwable exception) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() == 200) {
					String neo_type = parent_neo_id.split("_", 2)[0];
					List<NeoObject> selected = ds.parseChildren(response, neo_type);
					
					main.setData(NeoObject.getPlottablesOnly(selected));
				}
			}
		});
	}
}
