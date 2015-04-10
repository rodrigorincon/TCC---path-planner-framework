package pathPlanner;

import graph.Graph;
import map.Map;

public abstract class PathPlanner {

	protected Map map;
	protected Graph graph_returned;
	
	public abstract Graph resolution();
	
	public Graph defineEdges(){
		//TODO
		return null;
	}
	
	public void setMap(Map map){
		this.map = map;
	}
}
