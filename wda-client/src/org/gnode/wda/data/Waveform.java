package org.gnode.wda.data;

import java.util.Vector;

public class Waveform {
	public Integer channel_index;
	public String waveform__unit;
	public Vector<Double> waveform_data;
	
	public Waveform(Integer channel_index,
					String waveform__unit,
					Vector<Double> waveform_data)
	{
		this.channel_index = channel_index;
		this.waveform__unit = waveform__unit;
		this.waveform_data = waveform_data;
	}
	public void addData(Double item) {
		this.waveform_data.add(item);
	}
}
