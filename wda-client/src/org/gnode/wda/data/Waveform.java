package org.gnode.wda.data;

import java.util.Vector;

public class Waveform {
	private Integer channel_index;
	private String waveform__unit;
	private Vector<Double> waveform_data;
	
	public void setWaveform_data(Vector<Double> waveform_data) {
		this.waveform_data = waveform_data;
	}
	public Vector<Double> getWaveform_data() {
		return waveform_data;
	}
	public void setWaveform__unit(String waveform__unit) {
		this.waveform__unit = waveform__unit;
	}
	public String getWaveform__unit() {
		return waveform__unit;
	}
	public void setChannel_index(Integer channel_index) {
		this.channel_index = channel_index;
	}
	public Integer getChannel_index() {
		return channel_index;
	}
	public Waveform(Integer channel_index,
					String waveform__unit,
					Vector<Double> waveform_data)
	{
		this.setChannel_index(channel_index);
		this.setWaveform__unit(waveform__unit);
		this.setWaveform_data(waveform_data);
	}
	public void addData(Double item) {
		this.getWaveform_data().add(item);
	}
}
