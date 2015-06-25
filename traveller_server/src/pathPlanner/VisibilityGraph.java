package pathPlanner;

import java.util.List;

import util.Constants;
import util.Point;
import graph.Graph;
import graph.Node;

public class VisibilityGraph  extends PathPlanner{

	int init_line_x;
	int init_line_y;
	int final_line_x;
	int final_line_y;
	
	float carWidth;
	float cellWidth;
	
	public VisibilityGraph(float carWidth, float cellWidth){
		this.carWidth = carWidth;
		this.cellWidth = cellWidth;
	}
	
	private void addNodes(){
		List<Point> vertices = map.getVertices();
		for(int i=0; i<vertices.size(); i++)
			graph_returned.addNode(vertices.get(i).toString());
		Point initial_point = new Point(map.getInitialPoint()[Constants.COLUMN], map.getInitialPoint()[Constants.LINE]);
		Point goal_point = new Point(map.getGoalPoint()[Constants.COLUMN], map.getGoalPoint()[Constants.LINE]);
		graph_returned.addNode(initial_point.toString());
		graph_returned.addNode(goal_point.toString());
	}
	
	private boolean pointIsFinalLine(int x, int y){
		return (final_line_x == x && final_line_y == y) || (init_line_x == x && init_line_y == y);
	}
	
	private boolean haveFreeRouteLine(int x1, int y1, int x2, int y2){
		if(Math.abs(x1 - x2) <= 1 && Math.abs(y1 - y2) <= 1){
			boolean a = pointIsFinalLine(x2,y2) || map.getPosition(y2, x2) == map.getFREE();
			return a;
		}
		int diff_x = Math.abs(x1 - x2); 
		int middle_x = diff_x/2 + Math.min(x1, x2);
		int diff_y = (int)Math.abs(y1 - y2);
		int middle_y = diff_y/2 + Math.min(y1, y2);
		boolean b= haveFreeRouteLine(x1, y1, middle_x, middle_y) ? haveFreeRouteLine(middle_x, middle_y, x2, y2) : false;
		return b;
	}
	
	private void addEdges(){
		for(int i=0; i<graph_returned.getNumNodes(); i++){
			Node actual = graph_returned.getNode(i);
			for(int j=i+1; j<graph_returned.getNumNodes(); j++){
				Node temp = graph_returned.getNode(j);
				int actual_x = (int) actual.getIntCoordinates()[Constants.COLUMN];
				int temp_x = (int) temp.getIntCoordinates()[Constants.COLUMN];
				int actual_y = (int) actual.getIntCoordinates()[Constants.LINE];
				int temp_y = (int) temp.getIntCoordinates()[Constants.LINE];
				init_line_x = actual_x;
				init_line_y = actual_y;
				final_line_x = temp_x;
				final_line_y = temp_y;
				if( haveFreeRouteLine(actual_x, actual_y, temp_x, temp_y) || NeighborVerticesOfSameObstacle(actual_x, actual_y, temp_x, temp_y))
					actual.addEdge(temp, (float)Math.sqrt( Math.pow(actual_x-temp_x, 2)+Math.pow(actual_y-temp_y, 2) ));
			}
		}
	}
	
	private boolean NeighborVerticesOfSameObstacle(int x1, int y1, int x2, int y2) {
		return map.NeighborVerticesOfSameObstacle(x1,y1,x2,y2);
	}

	@Override
	public Graph resolution() {
		if(map.isObstaclesExpanded()==false){
			map.setObstaclesExpanded(true);
			map.expandObstacles(carWidth, cellWidth);
		}
		graph_returned = new Graph();
		addNodes();
		addEdges();
		return graph_returned;
	}

}
