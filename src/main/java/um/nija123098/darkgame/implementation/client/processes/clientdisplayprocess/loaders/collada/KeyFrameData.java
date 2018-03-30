package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.loaders.collada;

import java.util.ArrayList;
import java.util.List;

public class KeyFrameData {

	public final float time;
	public final List<JointTransformData> jointTransforms = new ArrayList<JointTransformData>();
	
	protected KeyFrameData(float time){
		this.time = time;
	}
	
	protected void addJointTransform(JointTransformData transform){
		jointTransforms.add(transform);
	}
	
}
