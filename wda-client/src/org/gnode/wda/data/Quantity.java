package org.gnode.wda.data;

public class Quantity {
	private String units;
	private Double data;
	
	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Double getData() {
		return data;
	}

	public void setData(Double data) {
		this.data = data;
	}

	public Quantity(String units, Double data) {
		this.units = units;
		this.data = data;
	}
}
