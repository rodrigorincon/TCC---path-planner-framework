package graph;

public class Edge {

	int distance;
	Node destiny;
	
	public Edge(Node destiny, int distance){
		this.distance = distance;
		this.destiny = destiny;
	}
	public Node getDestiny(){
		return this.destiny;
	}
	public int getDistance(){
		return this.distance;
	}
	
	public void removeDestiny(){
		destiny = null;
	}
	
}
