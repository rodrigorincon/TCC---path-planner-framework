package bestPath;

import graph.Node;

public class PriorityNode {
	private Node node;
	private int size_path;
	private PriorityNode prev;
	private PriorityNode next;
	
	public PriorityNode(Node node, PriorityNode prev, int size_path){
		this.node = node;
		this.prev = prev;
		if(prev!=null)
			this.size_path = prev.getSizePath()+size_path;
		else
			this.size_path = size_path;
		this.next = null;
	}
		
	public int getSizePath(){
		return size_path;
	}
	public void setSizePath(int size_path){
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
