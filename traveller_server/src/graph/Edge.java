package graph;

public class Edge {

	float distance;
	Node destiny;
	
	public Edge(Node destiny, float distance){
		this.distance = distance;
		this.destiny = destiny;
	}
	public Node getDestiny(){
		return this.destiny;
	}
	public float getDistance(){
		return this.distance;
	}
	
	public void removeDestiny(){
		destiny = null;
	}
	
}
