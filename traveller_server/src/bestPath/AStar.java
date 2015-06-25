package bestPath;

import util.Path;
import java.util.ArrayList;
import util.Constants;
import util.Point;
import graph.Node;

public class AStar extends BestPath{

	public AStar(){
	}
	
	@Override
	public Path definePath(Node initial, Node goal) {
		//define the node queues visited and visiting in this loop
		ArrayList<Node> visited = new ArrayList<Node>();
		PriorityList frontier = new PriorityList();
		
		//important vars
		Node current_node, neighbor_node;
		AStarPriorityNode current_priority_node=null, priority_neighbor;
		boolean goal_founded=false;
		int i;
		float size_now=0, new_size=0;
		
		AStarPriorityNode initial_astar_node = new AStarPriorityNode(initial, null, 0, goal);
		frontier.addNode(initial_astar_node,null);
		while(!frontier.isEmpty()){

			current_priority_node = (AStarPriorityNode) frontier.popNode();
			current_node = current_priority_node.getNode();
			if(current_node.equals(goal)){
				goal_founded = true;
				break;
			}
			//run all neighbors
			for(i=0; i<current_node.getNumNeighbors(); i++){
				neighbor_node = current_node.getEdge(i).getDestiny();
				//if the neighbor already exists in frontier, store, else priority_neighbor = NULL
				priority_neighbor = (AStarPriorityNode) frontier.researchNode(neighbor_node);
				//if the neighbors isn't in both queues, add in frontier
				if( !visited.contains(neighbor_node) && priority_neighbor==null){
					AStarPriorityNode astar_prior_node = new AStarPriorityNode(neighbor_node, current_priority_node, current_node.getEdge(i).getDistance(), goal);
					frontier.addNode(astar_prior_node, current_priority_node );
				}
				//if the neighbor is only in frontier
				else if(priority_neighbor!=null){
					size_now = priority_neighbor.getTraveledDistance();
					new_size = current_priority_node.getTraveledDistance()+current_node.getEdge(i).getDistance();
					if(new_size < size_now){
						priority_neighbor.setTraveledDistance(new_size);
						priority_neighbor.setPrev(current_priority_node);
					}
				}
			}
			visited.add(current_node);
		}
		Path returns = null;
		if(goal_founded){
			ArrayList<Point> path_returns = new ArrayList<Point>();
			for(AStarPriorityNode temp=current_priority_node; temp!=null; temp = (AStarPriorityNode) temp.getPrev()){
				String[] coordinates= temp.getNode().getCoordinates();
				Point p = new Point(Float.parseFloat(coordinates[Constants.COLUMN]),Float.parseFloat(coordinates[Constants.LINE]));
				path_returns.add(0, p);
			}
			float length_path = current_priority_node.getTraveledDistance();
			returns = new Path(path_returns,length_path);
		}
		visited.clear();
		visited = null;
		frontier.clear();
		frontier = null;
		return returns;
	}

	

	
}
