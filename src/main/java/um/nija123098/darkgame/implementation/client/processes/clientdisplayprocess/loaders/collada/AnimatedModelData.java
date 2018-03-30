package um.nija123098.darkgame.implementation.client.processes.clientdisplayprocess.loaders.collada;

public class AnimatedModelData {

	private final JointsData joints;
	private final MeshData mesh;
	
	protected AnimatedModelData(MeshData mesh, JointsData joints){
		this.joints = joints;
		this.mesh = mesh;
	}
	
	public JointsData getJointsData(){
		return joints;
	}
	
	public MeshData getMeshData(){
		return mesh;
	}
	
}
