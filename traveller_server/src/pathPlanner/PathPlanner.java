package pathPlanner;

import graph.Graph;
import map.Map;

public abstract class PathPlanner {

	protected Map map;
	protected Graph graph_returned;
	
	public abstract Graph resolution();
	
	public void setMap(Map map){
		this.map = map;
	}

	public static PathPlanner create(String algorithm, float carWidth, float cellWidth) {
		if(algorithm.equals("VisibilityGraph"))
			return new VisibilityGraph(carWidth, cellWidth);
		if(algorithm.equals("Voronoi"))
			return new Voronoi();
		if(algorithm.equals("Quadtree"))
			return new Quadtree(carWidth, cellWidth);
		if(algorithm.equals("Wavefront"))
			return new WaveFront();
		return null;
	}
}
