package org.gnode.wda.explorer;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.Date; 

public class NotificationArea extends Composite {
	ScrollPanel main;
	VerticalPanel notifications;
	
	public NotificationArea () {
		this.main = new ScrollPanel();
		this.main.setAlwaysShowScrollBars(false);
		
		notifications = new VerticalPanel();
		this.main.add(notifications);
		
		initWidget(main);
	}
	
	public void publish(String message) {
		Date date = new Date();
		@SuppressWarnings("deprecation")
		String time = date.getHours() + ":" + date.getMinutes() + ":" + (date.getSeconds() < 10 ? "0" : "") + date.getSeconds(); 
		
		Label lbl = new Label(time + " -> " + message);
		lbl.setStyleName("notification-message");
		
		this.notifications.insert(lbl, 0);
	}
}
