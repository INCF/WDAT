package org.gnode.wda.data;

import java.util.HashMap;
import java.util.List;

import org.gnode.wda.interfaces.DataSource;

public class GnodeDataSource implements DataSource{

	@Override
	public boolean authenticate(String sid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NEObject getObject(String sid, String oid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getData(String sid, String oid, HashMap<String, String> params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<NEObject> getChildren(String sid, String oid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NEObject> getParents(String sid, String oid) {
		// TODO Auto-generated method stub
		return null;
	}

}
