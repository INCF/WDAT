package org.gnode.wda.explorer;

import org.gnode.wda.data.NEObject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NeoIcon extends Composite {
	VerticalPanel wrap;
	ClickHandler clickHandler;
	Label label;
	Image im;
	boolean selected;
	NEObject neo;
	
	public NeoIcon(NEObject object) {
		this.neo = object;
		wrap = new VerticalPanel();
		wrap.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		im = new Image();
		label = new Label(object.name);
		
		this.selected = false;
		wrap.addStyleName("explorer-icon-wrap");
		
		
		initWidget(wrap);

		if (this.neo.isPlottable()) {
			im.setUrl("plot.png");
		} else { 
			im.setUrl("container.png");
		}
		wrap.add(im);
		wrap.add(label);
	}
	
	public void setSelected(boolean sel) {
		this.selected = sel;
		if (sel) {
			this.wrap.setStyleName("explorer-icon-selected");
		} else {
			this.wrap.removeStyleName("explorer-icon-selected");
		}
	}
	public void setClickHandler(ClickHandler handler) {
		this.wrap.addDomHandler(handler, ClickEvent.getType());
	}

	public NEObject getNeo() {
		return this.neo;
	}
}
