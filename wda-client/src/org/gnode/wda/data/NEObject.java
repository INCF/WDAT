package org.gnode.wda.data;

import java.util.Arrays;
import java.util.List;

public class NEObject{
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
	
	public NEObject(String name, String _type) {
		super();
		this.name = name;
		this.type = NEObject.Type.fromString(_type);
	}
	public NEObject(String name, Type type) {
		super();
		this.name = name;
		this.type = type;
	}
	
	public boolean isPlottable(){
		if (this.type == NEObject.Type.EVENT ||
			this.type == NEObject.Type.EPOCH ||
			this.type == NEObject.Type.SPIKE ||
			this.type == NEObject.Type.SPIKETRAIN ||
			this.type == NEObject.Type.ANALOGSIGNAL) {
			return true;
		}
		return false;
	}
	
	public static List<NEObject.Type> getContainers() {
		return Arrays.asList(NEObject.Type.ANALOGSIGNALARRAY,
					  		NEObject.Type.BLOCK,
					  		NEObject.Type.EPOCHARRAY,
					  		NEObject.Type.EVENTARRAY,
					  		NEObject.Type.IRSANALOGSIGNALARRAY,
					  		NEObject.Type.RECORDINGCHANNEL,
					  		NEObject.Type.RECORDINGCHANNELGROUP,
					  		NEObject.Type.SEGMENT
					  	    );
	}
}
