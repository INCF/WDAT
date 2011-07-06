package org.gnode.wda.explorer;

import java.util.List;

import org.gnode.wda.data.NEObject;
import org.gnode.wda.interfaces.DataSource;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class TreeWidget extends Composite {
	ScrollPanel main;
	Tree t;
	String sid;
	DataSource ds;

	public TreeWidget(DataSource ds) {
		this.sid = Cookies.getCookie("sessionid");
		this.main = new ScrollPanel();
		main.setAlwaysShowScrollBars(false);
		this.t = new Tree();
		this.ds = ds;
		this.setTreeData();
		
		
		initWidget(main);
		main.add(t);
	}
	
	public void setTreeData() {
		//this.t.addItem(create_node("/", null, ds.getRoot()));
	}
	
	public TreeItem create_node(String node_name, String uid, List<NEObject> root) {
		TreeItem node = new TreeItem(node_name);
		node.setTitle(uid);
		for(NEObject item : root) {
			node.addItem(create_node(item.name, item.uid, this.ds.getChildren(this.sid, item.uid)));
		}
		return node;
	}

	public void setSelection(NEObject selection) {
		int count = this.t.getItemCount();
		this.t.setSelectedItem(this.t.getSelectedItem(), false);
		for ( int i = 0; i < count; i++ ) {
			//_setSelection(selection, this.t.getItem(i));
		}
	}
	
	@SuppressWarnings("unused")
	private void _setSelection(NEObject selection, TreeItem ti) {
		int count = ti.getChildCount();
		for ( int i = 0 ; i < count ; i++ ) {
			if (ti.getChild(i).getTitle().equals(selection.uid) ) {
				ti.getChild(i).setSelected(true);
				return;
			} else {
				_setSelection(selection, ti.getChild(i));
			}
		}
	}
}
