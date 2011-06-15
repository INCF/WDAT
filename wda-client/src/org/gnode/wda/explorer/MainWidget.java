package org.gnode.wda.explorer;

import org.gnode.wda.data.NEObject;

import java.util.List;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class MainWidget extends Composite {
	/* When I say main, I mean of primary importance.
	 * Not the top-level one. I know it is ambiguous
	 * but couldn't find anything more relevant.
	 */
	FlowPanel main; 
	ScrollPanel wrap;
		
	
	public MainWidget() {
		main = new FlowPanel();
		wrap = new ScrollPanel();
		wrap.add(main);
		
		initWidget(wrap);
	}
	
	
	public void flush() {
		main.clear();
	}
	
	public void setContents(List<NEObject> list) {
		this.flush();
		for( NEObject item : list ) {
			this.main.add(new Button(item.name));
		}
	}
}
