package org.gnode.wda.interfaces;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

import java.util.HashMap;
import java.util.List;

import org.gnode.wda.data.NeoObject;


public interface DataSource{
	/* This interface needs to be implemented by any object that fetches
	 * data from a server for purposes of the wda-client. 
	 * For now, DataSource is heavily inclined to support NEO exclusively.
	 * 
	 * WDA at a later point should be able to support arbitrary data
	 * sources. Think of rewriting DataSource in a more DataStorageSystem 
	 * agnostic perspective.
	 * 
	 * Since GWT supports (rightly, so) only Asynchronous callbacks, all get
	 * functions return void. The callback parameter encapsulates what to
	 * do with the returned data. 
	 */
	
	// This seems like a good idea but as of now, all the authentication 
	// is handled by the Wda_client entrypoint class. TODO Use this feature.  
	public void authenticate();
	
	// get a single object
	public void getObject(String oid, RequestCallback callback);
	
	// get data for a single object
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
