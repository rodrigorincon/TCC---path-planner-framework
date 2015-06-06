package bestPath;

import util.Constants;
import graph.Node;

public class AStarPriorityNode extends PriorityNode{
	private float traveled_distance;
	private float heuristic_distace;
	
	public AStarPriorityNode(Node node, AStarPriorityNode prev, float distance_between_this_and_prev, Node goal){
		super(node, prev, distance_between_this_and_prev);
		if(prev!=null)
			this.traveled_distance = prev.getTraveledDistance()+distance_between_this_and_prev;
		else
			this.traveled_distance = distance_between_this_and_prev;
		setHeuristicDistance(goal);
	}

	public float getTraveledDistance() {
		return traveled_distance;
	}
	public void setTraveledDistance(float new_traveled_distance) {
		this.traveled_distance = new_traveled_distance;
	}
	public void setHeuristicDistance(Node goal){
		String[] coord = goal.getCoordinates();
		int goal_line = Integer.parseInt(coord[Constants.LINE]);
		int goal_col = Integer.parseInt(coord[Constants.COLUMN]);
		coord = node.getCoordinates();
		int local_line = Integer.parseInt(coord[Constants.LINE]);
		int local_col = Integer.parseInt(coord[Constants.COLUMN]);
		this.heuristic_distace = (float)Math.sqrt( Math.pow(goal_line-local_line,2) + Math.pow(goal_col-local_col,2) );
	}
	public float getHeuristicDistance(){
		return heuristic_distace;
	}
	public float getEstimatedSizePath(){
		return heuristic_distace+traveled_distance;
	}
	@Override
	public float getSizePath(){
		return getEstimatedSizePath();
	}
	
}
