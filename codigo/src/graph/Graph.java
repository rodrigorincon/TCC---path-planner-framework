package graph;

import java.util.ArrayList;

public class Graph {

	private ArrayList<Node> nodes;
	
	public Graph(){
		nodes = new ArrayList<Node>();
	}
	
	public int getNumNodes(){
		return nodes.size();
	}
	
	/** verify if the node already exists in graph for don't make duplicates */
	public boolean nodeAlreadyExists(Node node){
		for(int i=0; i<nodes.size(); i++){
			if(nodes.get(i).getInfo().equals(node.getInfo()))
				return true;
		}
		return false;
	}
	/** verify if the node already exists in graph for don't make duplicates */
	public boolean nodeAlreadyExists(String infoNode){
		for(int i=0; i<nodes.size(); i++){
			if(nodes.get(i).getInfo().equals(infoNode) )
				return true;
		}
		return false;
	}
	
	public Node getNode(String infoNode){
		for(int i=0; i<nodes.size(); i++){
			if(nodes.get(i).getInfo().equals(infoNode))
				return nodes.get(i);
		}
		return null;
	}
	
	public Node getNode(int i){
		return nodes.get(i);
	}
	
	public void addNode(Node node){
		if(!nodeAlreadyExists(node))
			nodes.add(node);
	}
	
	public Node addNode(String info){
		Node new_node = null;
		if(!nodeAlreadyExists(info)){
			new_node = new Node(info);
			nodes.add(new_node);
		}
		return new_node;
	}
	
	public void removeNode(Node node){
		nodes.remove(node);
		node.removeEdges();
	}
	public void removeNode(String info){
		Node exclude = getNode(info);
		if(exclude!=null)
			removeNode(exclude);
	}
	
	public Edge addEdge(Node node1, Node node2, int dist){
		return node1.addEdge(node2, dist);
	}
	
	public void removeEdge(Node node1, Node node2){
		node1.removeEdge(node2);
	}
	
	public void remove(){
		while(!nodes.isEmpty()){
			nodes.get(0).removeEdges();
			nodes.remove(0);
		}
	}
	
	public void print(){
		for(int i=0; i<nodes.size(); i++){
			System.out.println("---NODE "+nodes.get(i).getInfo()+"---");
			nodes.get(i).print();
		}
	}
}
