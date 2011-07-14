package org.gnode.wda.data;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.gnode.wda.interfaces.DataSource;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

public class GnodeDataSource implements DataSource{
	String prefix 				= "/proxy/";
	String getObjectUrl 		= prefix + "neo/";
	String getObjectDataUrl		= prefix + "neo/data/";
	String getChildrenUrl		= prefix + "neo/children/";
	String getParentsUrl		= prefix + "neo/parents/";
	String getTypeUrl			= prefix + "neo/select/";
		
	@Override
	public void authenticate() {
		// TODO Auto-generated method stub
		return;
	}

	private void transport(String url, String requestData, RequestCallback callback) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, 
													url);
		try {
			builder.sendRequest(requestData, callback);
		} catch (RequestException e) {
			// TODO something here!
		}
	}
	
	@Override
	public void getObject(String oid, RequestCallback callback) {
		String url = this.getObjectUrl + oid + "/";
		String requestData = "";
		
		this.transport(url, requestData, callback);
	}

	@Override
	public void getData(String oid, HashMap<String, String> params,
			RequestCallback callback) {
		String url = this.getObjectUrl + oid + "/";
		String requestData = "";
		
		this.transport(url, requestData, callback);
	}

	@Override
	public void getChildren(String oid, RequestCallback callback) {
		String url = this.getChildrenUrl + oid + "/";
		String requestData = "";
		
		this.transport(url, requestData, callback);
	}

	@Override
	public void getParents(String oid, RequestCallback callback) {
		String url = this.getParentsUrl + oid + "/";
		String requestData = "";
		
		this.transport(url, requestData, callback);
	}

	@Override
	public void getType(String type, RequestCallback callback) {
		String url = this.getTypeUrl + type + "/";
		String requestData = "";
		
		this.transport(url, requestData, callback);
	}

	@Override
	public List<NeoObject> parseType(Response response, String type) {
		// Assume that the response codes are already evaluated.
		Vector<NeoObject> rtn = new Vector<NeoObject>();

		JSONObject root = null;
		try {
			// for some reason, parseStrict throws a JSONException
			JSONValue value = JSONParser.parseLenient(response.getText());
			root = value.isObject();
			
			JSONArray selected = root.get("selected").isArray();
			if (selected == null) {
				return (List<NeoObject>)rtn;
			} else {
				// there are selections
				for ( int i = 0 ; i < selected.size() ; i++) {
					JSONString uid = selected.get(i).isString();
					rtn.add(new NeoObject(uid.stringValue(), type));
				}
			}
			return (List<NeoObject>)rtn;
			
		} catch (JSONException e) {
			Window.alert("JSON Exception");
		}
		return (List<NeoObject>)rtn;
	}
	
	public List<NeoObject> parseChildren(Response response) {
		// Assume that the response code have been checked
		Vector<NeoObject> rtn = new Vector<NeoObject>();
		
		JSONObject root = null;
		try {
			JSONValue value = JSONParser.parseLenient(response.getText());
			root = value.isObject();
			
		} catch (JSONException e) {
			Window.alert("JSON Exception");
		}
		return (List<NeoObject>)rtn;
	}
}
