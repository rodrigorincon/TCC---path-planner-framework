package bestPath;

import util.Path;
import java.util.LinkedList;
import util.Constants;
import util.Point;
import graph.Graph;
import graph.Node;

public class Djikstra extends BestPath{

	public Djikstra(){
	}
	
	@Override
	public Path definePath(Node initial, Node goal) {
		//define the node queues visited and visiting in this loop
		LinkedList<Node> visited = new LinkedList<Node>();
		PriorityList frontier = new PriorityList();
		
		//important vars
		Node current_node, neighbor_node;
		PriorityNode current_priority_node=null, priority_neighbor;
		boolean goal_founded=false;
		int i, size_now=0, new_size=0;
		
		frontier.addNode(initial, null, 0);
		while(!frontier.isEmpty()){

			current_priority_node = frontier.popNode();
			current_node = current_priority_node.getNode();
			if(current_node.equals(goal)){
				goal_founded = true;
				break;
			}
			//run all neighbors
			for(i=0; i<current_node.getNumNeighbors(); i++){
				neighbor_node = current_node.getEdge(i).getDestiny();
				//if the neighbor already exists in frontier, store, else priority_neighbor = NULL
				priority_neighbor = frontier.researchNode(neighbor_node);
				//if the neighbors isn't in both queues, add in frontier
				if( !visited.contains(neighbor_node) && priority_neighbor==null)
					frontier.addNode(neighbor_node, current_priority_node, current_node.getEdge(i).getDistance());
				//if the neighbor is only in frontier
				else if(priority_neighbor!=null){
					size_now = priority_neighbor.getSizePath();
					new_size = current_priority_node.getSizePath()+current_node.getEdge(i).getDistance();
					if(new_size < size_now){
						priority_neighbor.setSizePath(new_size);
						priority_neighbor.setPrev(current_priority_node);
					}
				}
			}
			visited.add(current_node);
		}
		Path returns = null;
		if(goal_founded){
			LinkedList<Point> path_returns = new LinkedList<Point>();
			for(PriorityNode temp=current_priority_node; temp!=null; temp = temp.getPrev()){
				String[] coordinates= temp.getNode().getCoordinates();
				Point p = new Point(Integer.parseInt(coordinates[Constants.COLUMN]),Integer.parseInt(coordinates[Constants.LINE]));
				path_returns.add(0, p);
			}
			int length_path = current_priority_node.getSizePath();
			returns = new Path(path_returns,length_path);
		}
		visited.clear();
		visited = null;
		frontier.clear();
		frontier = null;
		return returns;
	}

	

	
}
