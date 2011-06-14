package org.gnode.wda.selector;

public class SelectorLWModel {
	public enum Type {
		BLOCK, SEGMENT, EVENT, EVENTARRAY, EPOCH, EPOCHARRAY,
		UNIT, SPIKETRAIN, ANALOGSIGNAL, ANALOGSIGNALARRAY,
		IRSANALOGSIGNALARRAY, SPIKE, RECORDINGCHANNELGROUP,
		RECORDINGCHANNEL
	}

	String name;
	String uid;
	String parent;
	Type type;
		
	public SelectorLWModel(String name, String uid, String parent, Type type) {
		super();
		this.name = name;
		this.uid = uid;
		this.parent = parent;
		this.type = type;
	}
	
	public boolean isPlottable(){
		if (this.type == SelectorLWModel.Type.EVENT ||
			this.type == SelectorLWModel.Type.EPOCH ||
			this.type == SelectorLWModel.Type.SPIKE ||
			this.type == SelectorLWModel.Type.SPIKETRAIN ||
			this.type == SelectorLWModel.Type.ANALOGSIGNAL) {
			return true;
		}
		return false;
	}
}
