package org.gnode.wda.client;

import java.util.HashMap;

public class Token {
	// This is a helper class. It is used to manipulate the 
	// History token for any practical purposes.
	
	HashMap<String, String> data;
	
	public Token(String token_string) {
		String [] key_value_pairs = token_string.split("&");
		this.data = new HashMap<String, String>();
		
		for ( String pair : key_value_pairs ) {
			String key = pair.split("=", 2)[0];
			if (key.equals(""))
				continue;
			String value = pair.split("=", 2)[1];
			
			this.data.put(key, value);
		}
	}
	
	public String getOption(String key) {
		if (this.data.containsKey(key)) 
			return this.data.get(key);
		return "";
	}
	
	public void setOption(String key, String value) {
		this.data.put(key, value );
	}
	
	public void updateOption(String key, String value, Boolean remove) {
		/* 
		 * updates the value of an option based on its existing values. 
		 * if the remove flag is switched on, the value is removed from a csv
		 * list of values.
		 * 
		 * if the remove flag is off, the value is appended to the csv that
		 * the key corresponds to.
		 */
		
		String currentValue = this.getOption(key);
	
		if (remove) {
			if (currentValue.contains(value)) {
				String updateValue = "";
				
				for ( String v : currentValue.split(",")) {
					if ( !v.equals(value)) 
						updateValue += "," + v;
				}
				
				this.data.put(key, updateValue.substring(1) ); // to remove the first ,
				return;
			}
		} else {
			// an addition has been requested.
			String updateValue = currentValue;
			
			if ( ! currentValue.contains(value)) {
				// the value isn't yet in the map
				updateValue += "," + value;
			}
			
			this.data.put(key, updateValue);
		}
	}

	public String getToken() {
		// regenerating the token.
		String token = "";
		for (String key : this.data.keySet()) {
			token += "&" + key + "=" + this.data.get(key);
		}
		
		// remove the first & and return
		return token.substring(1);
	}	
}