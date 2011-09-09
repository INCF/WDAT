package org.gnode.wda.data;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.gnode.wda.interfaces.DataSource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

public class GnodeDataSource implements DataSource{
	/* 
	 * An g-node specific implementation of the DataSource interface.
	 */
	String getObjectUrl;
	String getObjectDataUrl;
	String getChildrenUrl;
	String getParentsUrl;
	
	public GnodeDataSource() {
		String prefix; 				
		
		if (GWT.isProdMode()) {
			prefix = "/";
		} else {
			prefix = "/proxy/";
		}
		
		getObjectUrl 		= prefix + "neo/";
		getObjectDataUrl		= prefix + "neo/data/";
		getChildrenUrl		= prefix + "neo/children/";
		getParentsUrl		= prefix + "neo/parents/";
	}
		
	@Override
	public void authenticate() {
		// TODO Auto-generated method stub
		return;
	}

	private void transport(String url, String requestData, RequestCallback callback) {
		// All network requests pass through this function. 
		// Would help monitor network activity by logging all
		// requests or sending out notifications.
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
	public void getData(String full_oid, HashMap<String, String> params,
			RequestCallback callback) {
		
		// Weirdly enough, requestData is not appended to the request. Will 
		// do so manually
		String requestData = "";
		for (String key : params.keySet())
			requestData += URL.encode(key) + "=" + URL.encode(params.get(key)) + "&";
		
		
		String type = full_oid.split("_")[0];
		String oid = full_oid.split("_")[1]; 
		String url = this.getObjectUrl + type + "/" + oid + "?" + requestData;
		// Since now we are using the new URL style /neo/analogsignal/12?params
		
		
		this.transport(url, requestData, callback);
	}

	@Override
	public void getChildren(String full_oid, RequestCallback callback) {
		String type = full_oid.split("_")[0]; // Extracting block from block_12
		String oid = full_oid.split("_")[1]; // Extracting 12 from block_12
		String url = this.getObjectUrl + type + "/" + oid + "/";
		// Since now the urls are like /neo/block/12 rather than the older /neo/children/block_12
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
		String url = this.getObjectUrl + type + "/"; 
		// Since now the urls are like /neo/block and not the older /neo/select/block 
		String requestData = "";
		
		this.transport(url, requestData, callback);
	}

	@Override
	public List<NeoObject> parseType(Response response, String type) {
		// ParseType parses a JSON response for a request to get all
		// the objects of a specified type. via the /neo/select/<type>/ handle
		
		// Assume that the response codes are already evaluated.
		Vector<NeoObject> rtn = new Vector<NeoObject>();

		JSONObject root = null;
		try {
			// for some reason, parseStrict throws a JSONException
			JSONValue value = JSONParser.parseLenient(response.getText());
			root = value.isObject();
			
			if (root == null) {
				// In case there is array encapsulation.
				root = value.isArray().get(0).isObject();
			}
			
			JSONArray selected = root.get("selected").isArray();
			return neo_from_jsonarray(selected, type);
			
		} catch (JSONException e) {
			Window.alert("JSON Exception");
		}
		return (List<NeoObject>)rtn;
	}
	
	public List<NeoObject> parseChildren(Response response, String type) {
		// Assume that the response code have been checked
		Vector<NeoObject> rtn = new Vector<NeoObject>();
		
		JSONObject root = null;
		try {
			JSONValue value = JSONParser.parseLenient(response.getText());
			root = value.isObject();
			
			if (root == null) {
				// in case array encapsulation
				root = value.isArray().get(0).isObject();
			}
			
			for (String childtype : NeoObject.getChildrenTypes(type)) {
				JSONArray children = root.get(childtype).isArray();
				if (children != null) 
					rtn.addAll(neo_from_jsonarray(children, childtype));
			}
		} catch (JSONException e) {
			Window.alert("JSON Exception");
		}
		return (List<NeoObject>)rtn;
	}
	
	private List<NeoObject> neo_from_jsonarray(JSONArray array, String type) {
		Vector<NeoObject> rtn = new Vector<NeoObject>();
		
		if (array == null) {
			return (List<NeoObject>)rtn;
		} else {
			for ( int i = 0; i < array.size(); i++) {
				JSONString uid = array.get(i).isString();
				rtn.add(new NeoObject(uid.stringValue(), type));
			}
		}
		return (List<NeoObject>)rtn;
	}
}

