package org.gnode.wda.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class NeoObject{
		
	public String name;
	public String type;
	
	public NeoObject(String name, String _type) {
		super();
		this.name = name;
		this.type = _type;
	}
	
	public boolean isPlottable(){
		if (this.type == "epoch" ||
			this.type == "event" ||
			this.type == "spike" ||
			this.type == "spiketrain" ||
			this.type == "segment" ||
			this.type == "irsaanalogsignal" || 
			this.type == "analogsignal" ) {
			return true;
		}
		return false;
	}
	
	public boolean isContainer() {
		// Because some containers are plottable too.
		if (this.type == "analogsignalarray" ||
			this.type == "block" ||
			this.type == "epocharray" ||
			this.type == "eventarray" ||
			this.type == "irsaanalogsignalarray" ||
			this.type == "recordingchannel" ||
			this.type == "recordingchannelgroup" ||
			this.type == "segment" ) {
			return true;
		}
		return false;
	}
	
	public static List<String> getContainers() {
		return Arrays.asList("analogsignalarray", "block", "epocharray", "eventarray",
							"irsaanalogsignalarray", "recordingchannel", "recordingchannelgroup", "segment");
	}
	
	public static List<String> getPlottables() {
			return Arrays.asList("analogsignal", "irsaanalogsignal", "epoch", "event", "spike", "spiketrain", "segment");
	}
	
	public static List<String> getChildrenTypes(String type) {
		//TODO this is very time intensive. Move this to constructor.
		HashMap<String, List<String>> hm = new HashMap<String, List<String>>();
		hm.put("block", Arrays.asList("segment", "recordingchannelgroup"));
		hm.put("segment", Arrays.asList("analogsignal", "irsaanalogsignal",
				"analogsignalarray", "spiketrain", "spike", "event", "eventarray",
				"epoch", "epocharray"));
		hm.put("eventarray", Arrays.asList("event"));
		hm.put("epocharray", Arrays.asList("epoch"));
		hm.put("recordingchannelgroup", Arrays.asList("recordingchannel", "analogsignalarray"));
		hm.put("recordingchannel", Arrays.asList("unit", "analogsignal", "irsaanalogsignal"));
		hm.put("unit", Arrays.asList("spiketrain", "spike"));
		hm.put("analogsignalarray", Arrays.asList("analogsignal"));
	
		return hm.get(type);
	}

	public static List<NeoObject> getContainersOnly(List<NeoObject> selected) {
		Vector<NeoObject> rtn = new Vector<NeoObject>();
		for (NeoObject item : selected ) {
			if ( item.isContainer() ) 
				rtn.add(item);
		}
		return (List<NeoObject>)rtn;
	}
	
	public static List<NeoObject> getPlottablesOnly(List<NeoObject> selected) {
		Vector<NeoObject> rtn = new Vector<NeoObject>();
		for (NeoObject item : selected ) {
			if ( item.isPlottable() ) 
				rtn.add(item);
		}
		return (List<NeoObject>)rtn;
	}

	public String getNeo_id() {
		return name;
	}
}
