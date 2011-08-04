package org.gnode.wda.explorer;


import java.util.List;

import org.gnode.wda.data.NeoObject;
import org.gnode.wda.interfaces.DataSource;

import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TreeWidget extends Composite {
	VerticalPanel main;
	Tree t;
	TreeItem root;
	DataSource ds;

	public TreeWidget(DataSource ds) {
		this.main = new VerticalPanel();
		
		this.t = new Tree();
		this.ds = ds;
		this.root = new TreeItem("/");
		this.t.addItem(this.root);
		
		initWidget(main);
		main.add(t);
	}
	
	public void setOpenHandler(OpenHandler<TreeItem> handler) {
		// This will handle the click on the + buttons of the tree.
		this.t.addOpenHandler(handler);
	}
	
	public void setSelectionHandler(SelectionHandler<TreeItem> handler) {
		// This will handle the seletion of the treeitems.
		this.t.addSelectionHandler(handler);
	}
	
	public void setChildren(TreeItem parent, List<NeoObject> children) {
		// This will cause the root list to be refreshed when 
		// we are supplying a new top-level type. For updating lists
		// the parents should already be empty.
		parent.removeItems();
		if (children.size() == 0) {
			parent.addItem(new TreeItem("<i>(empty)</i>"));
			return;
		}
		for ( NeoObject child : children ) {
			TreeItem addendum = new TreeItem(child.name);
			addendum.setTitle(child.type);
			addendum.addItem(new TreeItem("<i>loading...</i>"));
			parent.addItem(addendum);
		}
	}

	public void clearNodeChildren(TreeItem titem) {
		// This method will clear the children of a particular
		// tree Item. This should be used most commonly to clear 
		// the "contents" marker of a container element.
		titem.removeItems();
	}
	
	public void openRoot() {
		// Open the root element. Required by the Explorer class.
		this.t.getItem(0).setState(true);
		// Since it is the first one added, getItem(0) should return
		// the root element.
	}
}
