package controller;

import util.Path;
import map.Map;
import graph.Graph;
import pathPlanner.PathPlanner;
import util.Constants;
import bestPath.BestPath;

public class PathPlannerController {

	private PathPlanner algorithm;
	private BestPath graph_solucionator;
	private Map map;
	
	public PathPlannerController(PathPlanner algorithm, BestPath best_path){
		this.algorithm = algorithm;
		this.graph_solucionator = best_path;
	}
	
	/** receive the boolean map and the value of free spaces (if true or false is a free 
	 * space) and the initial and final points */
	public void defineMap(boolean[][] map, boolean free_value, int init_x, int init_y, 
			int final_x, int final_y){
		this.map = new Map(map, free_value);
		this.map.setInitialPoint(init_x, init_y);
		this.map.setFinalPoint(final_x, final_y);
		algorithm.setMap(this.map);
	}
	
	public Path execute(int robotWidth, int cellWidth){
		map.expandObstacles(robotWidth, cellWidth);
		Graph graph_of_free_space = algorithm.resolution();
		graph_solucionator.setGraph(graph_of_free_space);
		int[] initial_point = map.getInitialPoint();
		int[] goal_point = map.getGoalPoint();
		map.cleanUp();
		Path path = graph_solucionator.definePath( initial_point[Constants.LINE]+","+initial_point[Constants.COLUMN], 
			goal_point[Constants.LINE]+","+goal_point[Constants.COLUMN]);
		return path;
	}
		
}
