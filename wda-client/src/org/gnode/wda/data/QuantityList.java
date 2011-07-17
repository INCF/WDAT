package org.gnode.wda.data;

import java.util.Vector;

public class QuantityList {
	public String units;
	public Vector<Double> data;
	
	public QuantityList(String units, Vector<Double> data) {
		this.units = units;
		this.data = data;
	}
	
	public QuantityList(String units){
		this.units = units;
		this.data = new Vector<Double>();
	}
	
	public void addData(Double data) {
		this.data.addElement(data);
	}
}
