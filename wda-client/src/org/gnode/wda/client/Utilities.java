package org.gnode.wda.client;

import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.History;


public class Utilities {
	public static String getOption(String token, String key) {
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
			if (item.length() == 0) 
				continue;
			else 
				rtn.add(item);
		}
		
		return rtn;
	}

	public static String removeObjectFromGraphUrl(String neo_id) {
		String currentToken = History.getToken();
		String [] options = currentToken.split("&");
		String objParams = "";
		String rtn = "";
		
		for ( String item : options ) {
			if ( item.startsWith("obj") ) {
				String [] parts = item.split(neo_id);
				
				if ( parts.length == 2 ) {
					objParams += parts[0] + parts[1].substring(1);
				} else {
					objParams += parts[0];
				}
			}
		}
		
		for ( String item : options ) {
			if ( ! item.startsWith("obj") ) {
				rtn += item;
			} else {
				rtn += "&" + objParams;
			}
		}
		
		return rtn;
	}
	public static String updateGraphUrlWithAnotherObject(String neo_id) {
		String currentToken = History.getToken();
		String [] options = currentToken.split("&");
		String objParams = "";
		String rtn = "";
		
		// Disassemble the token.
		for (String item : options ) {
			if (item.startsWith("obj")) {
				objParams = item;
				break;
			}
		}
		
		String objects = objParams.split("=", 2)[1].toLowerCase();
		if (objects.contains(neo_id)) {
			return currentToken;
		}
		
		objects += "," + neo_id;
		
		// Reassemble the token. I miss python !
		for (String item : options) {
			if ( ! item.startsWith("obj") ) {
				rtn += "&" + item;
			} else {
				rtn += "&obj=" + objects;
			}
		}
		
		return rtn;
	}
}