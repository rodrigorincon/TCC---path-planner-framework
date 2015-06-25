package bestPath;

import graph.Node;

public class PriorityNode {
	protected Node node;
	private float size_path;
	protected PriorityNode prev;
	protected PriorityNode next;
	
	public PriorityNode(Node node, PriorityNode prev, float size_path){
		this.node = node;
		this.prev = prev;
		if(prev!=null)
			this.size_path = prev.getSizePath()+size_path;
		else
			this.size_path = size_path;
		this.next = null;
	}
		
	public float getSizePath(){
		return size_path;
	}
	public void setSizePath(float size_path){
		this.size_path = size_path;
	}
	public Node getNode(){
		return node;
	}
	public PriorityNode getPrev(){
		return prev;
	}
	public void setPrev(PriorityNode prev_new){
		prev = prev_new;
	}
	public PriorityNode getNext() {
		return next;
	}
	public void setNext(PriorityNode next) {
		this.next = next;
	}
}
