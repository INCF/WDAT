package org.gnode.wda.data;

import java.util.Vector;

public class QuantityList {
	private String units;
	private Vector<Double> data;

	
	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Vector<Double> getData() {
		return data;
	}

	public void setData(Vector<Double> data) {
		this.data = data;
	}

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
