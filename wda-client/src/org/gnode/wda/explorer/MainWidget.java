package org.gnode.wda.explorer;

import org.gnode.wda.data.NEObject;

import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class MainWidget extends Composite {
	
	/* This widget handles the click events for its children 
	 * icon widgets. not for itself. 
	 * 
	 *  When I say main, I mean of primary importance.
	 * Not the top-level one. I know it is ambiguous
	 * but couldn't find anything more relevant.
	 */
	FlowPanel main; 
	ScrollPanel wrap;
	Vector<NeoIcon> children;
		
	
	public MainWidget() {
		main = new FlowPanel();
		wrap = new ScrollPanel();
		wrap.add(main);
		children = new Vector<NeoIcon>();
		
		initWidget(wrap);
	}
	
	
	public void flush() {
		main.clear();
	}
	
	public void setContents(List<NEObject> list) {
		this.flush();
		for( NEObject item : list ) {
			NeoIcon icon = new NeoIcon(item);
			this.main.add(icon);
			this.children.add(icon);
		}
	}
	
	public List<NeoIcon> getChildren() {
		return (List<NeoIcon>)this.children;
	}
}
