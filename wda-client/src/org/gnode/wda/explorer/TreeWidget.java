package org.gnode.wda.explorer;

import java.util.List;

import org.gnode.wda.data.NEObject;
import org.gnode.wda.interfaces.DataSource;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class TreeWidget extends Composite {
	ScrollPanel main;
	Tree t;
	DataSource ds;

	public TreeWidget(DataSource ds) {
		this.main = new ScrollPanel();
		main.setAlwaysShowScrollBars(false);
		this.t = new Tree();
		this.ds = ds;
		
		this.setTreeData();
		
		initWidget(main);
		main.add(t);
	}
	
	public void setTreeData() {
		this.t.addItem(create_node("/", null, ds.getRoot()));
	}
	
	public TreeItem create_node(String node_name, String uid, List<NEObject> root) {
		TreeItem node = new TreeItem(node_name);
		node.setTitle(uid);
		for(NEObject item : root) {
			node.addItem(create_node(item.name, item.uid, this.ds.getContainerChildrenOf(item)));
		}
		return node;
	}
}
