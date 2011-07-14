package org.gnode.wda.interfaces;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

import java.util.HashMap;
import java.util.List;

import org.gnode.wda.data.NeoObject;


public interface DataSource{
	public void authenticate();
	
	// get a single object
	public void getObject(String oid, RequestCallback callback);
	
	// get data for a single object
	// I don't know the return type yet. 
	public void getData(String oid, HashMap<String, String> params, RequestCallback callback);
	
	// get children for a single object
	public void getChildren(String oid, RequestCallback callback);
	
	// get parents of a single object
	public void getParents(String oid, RequestCallback callback);

	// get objects of a particular type.
	// the select url handle.
	public void getType(String type, RequestCallback callback);

	
	// This will contain json evaluation and construction of NEObjects
	// from them
	public List<NeoObject> parseType(Response response, String type);

	public List<NeoObject> parseChildren(Response response, String type);
}
