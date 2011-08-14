package ca.nanometrics.gflot.client.options;

import ca.nanometrics.gflot.client.util.JSONObjectWrapper;

/**
 * 
 * @author Mohamed M. El-Kalioby
 * @since Septmeber 21, 2009
 */
public class Range extends JSONObjectWrapper {

	public Range() {
		super();
	}

	public Range(Double from, Double to) {
		super();
		put("from", from);
		put("to", to);

	}

	public Range(Integer from, Integer to) {
		super();
		put("from", from);
		put("to", to);

	}

	public void setFrom(Double from) {
		put("from", from);
	}

	public void setTo(Double to) {
		put("to", to);
	}

}
