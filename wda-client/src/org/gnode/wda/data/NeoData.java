package org.gnode.wda.data;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.json.client.JSONObject;

public abstract class NeoData {
	private String neo_id;
	protected HashMap<String, String> parents;
	
	public void setNeo_id(String neo_id) {
		this.neo_id = neo_id;
	}

	public String getNeo_id() {
		return neo_id;
	}
	
	public void parseParents(JSONObject obj, List<String> parent_keys) {
		// error checking and parsing of parents in a JSONObject. The
		// parent's accessor ids are defined in parent_keys
		this.parents = new HashMap<String, String>();
		
		for (String parent : parent_keys) {
			//try {
			//	this.parents.put(parent, obj.get(parent).isString().stringValue());
			//} 
			//catch (NullPointerException e) {
			//  this.parents.put(parent, null);
			//}
		}
	}
}
