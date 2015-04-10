package bestPath;

import util.Path;
import graph.Graph;
import graph.Node;

public abstract class BestPath {
	
	protected Graph graph;
		
	public void setGraph(Graph graph){
		this.graph = graph;
	}
	
	public Path definePath(Node initial, String goal){
		Node goal_node = graph.getNode(goal);
		return definePath(initial, goal_node);
	}

	public Path definePath(String initial, String goal){
		Node initial_node = graph.getNode(initial);
		Node goal_node = graph.getNode(goal);
		return definePath(initial_node, goal_node);
	}
	
	public abstract Path definePath(Node initial, Node goal);
}
