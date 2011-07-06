package org.gnode.wda.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TestLoginPanel extends Composite implements ClickHandler{
	VerticalPanel main;
	TextBox user;
	PasswordTextBox pass;
	Button login;
	
	public TestLoginPanel() {
		main = new VerticalPanel();
		initWidget(main);
		
		
		user = new TextBox();
		pass = new PasswordTextBox();
		login = new Button("Login");
		login.addClickHandler((ClickHandler)this);
		
		main.add(user);
		main.add(pass);
		main.add(login);
	}

	@Override
	public void onClick(ClickEvent event) {
		String user = this.user.getValue();
		String pass = this.pass.getValue();
		
		String loginurl = "/proxy/account/login/";
		RequestBuilder req = new RequestBuilder(RequestBuilder.POST, loginurl);
			
		try {
			req.sendRequest("username="+user+"&password="+pass,
				new RequestCallback() {
					
					@Override
					public void onResponseReceived(Request request, Response response) {
						Window.open(Window.Location.getHref(),
									"_self",
									"");
					}
					
					@Override
					public void onError(Request request, Throwable exception) {
						// TODO Auto-generated method stub
						Window.alert("Bad request");
					}
				});
			
		} catch (RequestException e){
			// Couldn't connect
		}
	}
}
