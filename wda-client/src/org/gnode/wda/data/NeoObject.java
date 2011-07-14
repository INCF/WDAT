package org.gnode.wda.data;

import java.util.Arrays;
import java.util.List;

public class NeoObject{
	public enum Type {
		BLOCK, SEGMENT, EVENT, EVENTARRAY, EPOCH, EPOCHARRAY,
		UNIT, SPIKETRAIN, ANALOGSIGNAL, ANALOGSIGNALARRAY,
		IRSANALOGSIGNALARRAY, SPIKE, RECORDINGCHANNELGROUP,
		RECORDINGCHANNEL;
		
		public static Type fromString(String type) {
			type = type.toLowerCase();
			
			if (type == "block") return Type.BLOCK;
			if (type == "segment") return Type.SEGMENT;
			if (type == "event") return Type.EVENT;
			if (type == "eventarray") return Type.EVENTARRAY;
			if (type == "epoch") return Type.EPOCH;
			if (type == "epocharray") return Type.EPOCHARRAY;
			if (type == "unit") return Type.UNIT;
			if (type == "spiketrain") return Type.SPIKETRAIN;
			if (type == "analogsignal") return Type.ANALOGSIGNAL;
			if (type == "recordingchannelgroup") return Type.RECORDINGCHANNELGROUP;
			if (type == "analogsignalarray") return Type.ANALOGSIGNALARRAY;
			if (type == "irsanalogsignalarray") return Type.IRSANALOGSIGNALARRAY;
			if (type == "spike") return Type.SPIKE;
			if (type == "recordingchannel") return Type.RECORDINGCHANNELGROUP;
			
			return null;
		}
	}

	public String name;
	public Type type;
	
	public NeoObject(String name, String _type) {
		super();
		this.name = name;
		this.type = NeoObject.Type.fromString(_type);
	}
	public NeoObject(String name, Type type) {
		super();
		this.name = name;
		this.type = type;
	}
	
	public boolean isPlottable(){
		if (this.type == NeoObject.Type.EVENT ||
			this.type == NeoObject.Type.EPOCH ||
			this.type == NeoObject.Type.SPIKE ||
			this.type == NeoObject.Type.SPIKETRAIN ||
			this.type == NeoObject.Type.ANALOGSIGNAL) {
			return true;
		}
		return false;
	}
	
	public static List<NeoObject.Type> getContainers() {
		return Arrays.asList(NeoObject.Type.ANALOGSIGNALARRAY,
					  		NeoObject.Type.BLOCK,
					  		NeoObject.Type.EPOCHARRAY,
					  		NeoObject.Type.EVENTARRAY,
					  		NeoObject.Type.IRSANALOGSIGNALARRAY,
					  		NeoObject.Type.RECORDINGCHANNEL,
					  		NeoObject.Type.RECORDINGCHANNELGROUP,
					  		NeoObject.Type.SEGMENT
					  	    );
	}
}
