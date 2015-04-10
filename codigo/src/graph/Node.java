package graph;

import java.util.ArrayList;

public class Node {

	ArrayList<Edge> neighbors;
	String info;
	
	public Node(String info) {
		this.neighbors = new ArrayList<Edge>();
		this.info = info;
	}
	public String getInfo(){
		return this.info;
	}
	public int getNumNeighbors(){
		return this.neighbors.size();
	}
	
	public boolean edgeAlreadyExists(Edge edge){
		for(int i=0; i<neighbors.size(); i++){
			if(neighbors.get(i).getDestiny().equals(edge.getDestiny()))
				return true;
		}
		return false;
	}
	
	public boolean edgeAlreadyExists(Node destiny){
		for(int i=0; i<neighbors.size(); i++){
			if(neighbors.get(i).getDestiny().equals(destiny))
				return true;
		}
		return false;
	}
		
	public Edge getEdge(Node destiny){
		for(int i=0; i<neighbors.size(); i++){
			if(neighbors.get(i).getDestiny().equals(destiny))
				return neighbors.get(i);
		}
		return null;
	}
	
	public Edge getEdge(int position){
		return neighbors.get(position);
	}
		
	public Edge addEdge(Node node, int dist){
		Edge new_edge = null;
		if(!edgeAlreadyExists(node)){
			new_edge = new Edge(node, dist);
			neighbors.add(new_edge);
			//add the same edge in the receive node to be bidirectional
			node.addEdge(this, dist);
		}
		return new_edge;
	}
	public void addEdge(Edge edge){
		neighbors.add(edge);
		//add the same edge in the receive node to be bidirectional
		edge.getDestiny().addEdge(this, edge.getDistance());
	}
		
	public void removeEdge(Edge edge){
		neighbors.remove(edge);
		edge.getDestiny().removeEdge(this);
		edge.removeDestiny();
	}
	
	public void removeEdge(Node destiny){
		Edge exclude = getEdge(destiny);
		if(exclude!=null)
			removeEdge(exclude);
	}
	
	public void removeEdges(){
		while(neighbors.size() > 0)
			removeEdge(neighbors.get(0));
	}
	
	public String[] getCoordinates(){
		int comma_pos = info.indexOf(",");
		String[] coordinates = new String[2];
		coordinates[0] = info.substring(0, comma_pos);
		coordinates[1] = info.substring(comma_pos+1);
		return coordinates;
	}
	
	public void print(){
		System.out.println("neighbors: "+neighbors.size());
		for(int i=0; i<neighbors.size(); i++){
			Edge current = neighbors.get(i);
			System.out.print("->"+current.getDestiny().getInfo());
			System.out.println(" dist: "+current.getDistance());
		}
	}
}
