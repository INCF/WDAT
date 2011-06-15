package org.gnode.wda.data;

public class NEObject{
	public enum Type {
		BLOCK, SEGMENT, EVENT, EVENTARRAY, EPOCH, EPOCHARRAY,
		UNIT, SPIKETRAIN, ANALOGSIGNAL, ANALOGSIGNALARRAY,
		IRSANALOGSIGNALARRAY, SPIKE, RECORDINGCHANNELGROUP,
		RECORDINGCHANNEL
	}

	public String name;
	public String uid;
	public String parent;
	public Type type;
		
	public NEObject(String name, String uid, String parent, Type type) {
		super();
		this.name = name;
		this.uid = uid;
		this.parent = parent;
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
}
