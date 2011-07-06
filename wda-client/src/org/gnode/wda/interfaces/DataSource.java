package org.gnode.wda.interfaces;

import org.gnode.wda.data.NEObject;

import java.util.HashMap;
import java.util.List;

public interface DataSource{
	public boolean authenticate(String sid);
	
	// get a single object
	public NEObject getObject(String sid, String oid);
	
	// get data for a single object
	// I don't know the return type yet. 
	public void getData(String sid, String oid, HashMap<String, String> params );
	
	// get children for a single object
	public List<NEObject> getChildren(String sid, String oid);
	
	// get parents of a single object
	public List<NEObject> getParents(String sid, String oid);
	
}
