package org.gnode.wda.client;

import java.util.List;
import java.util.Vector;

public class Utilities {
	public static String getFragmentType(String token) {
		if (token.startsWith("explore:")) return "explore";
		if (token.startsWith("plot:")) return "plot";
		return "";
	}
	
	public static String getOption(String token, String key) {
		token = token.split(":", 2)[1];
		String [] options = token.split("&");
		
		for (String item : options) {
			if (item.startsWith(key)) {
				return item.split("=", 2)[1].toLowerCase();
			}
		}
		return "";
	}
	
	public static List<String> parseCSV(String token) {
		Vector<String> rtn = new Vector<String>();
		
		for (String item : token.split(",") ) {
			rtn.add(item);
		}
		
		return rtn;
	}
}