package org.gnode.wda.client;

import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.History;


public class Utilities {
	public static String getOption(String token, String key) {
		Token t = new Token(token);
		return t.getOption(key);
	}
	
	public static List<String> parseCSV(String token) {
		Vector<String> rtn = new Vector<String>();
		
		for (String item : token.split(",") ) {
			if (item.length() == 0) 
				continue;
			else 
				rtn.add(item);
		}
		
		return rtn;
	}

	public static String setValue(String key, String value) {
		Token t = new Token(History.getToken());
		t.setOption(key, value);
		
		return t.getToken();
	}
	public static String removeObjectFromGraphUrl(String neo_id) {
		// convenience methods. does what it says. 
		Token t = new Token(History.getToken());
		t.updateOption("obj", neo_id, true);
		
		return t.getToken();
	}
	
	public static String updateGraphUrlWithAnotherObject(String neo_id) {
		// convenience methods. does what it says. 
		Token t = new Token(History.getToken());
		t.updateOption("obj", neo_id, false);
		
		return t.getToken();
	}
}