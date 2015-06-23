package bestPath;

import graph.Node;

public class PriorityList {
	PriorityNode head;
	
	public PriorityList(){
		head = null;
	}
	
	public void addNode(PriorityNode prior_node, PriorityNode father){
		//if the queue is empty
		if(head==null)
			head = prior_node;
		else{
			PriorityNode current, prev = null;
			for(current=head; current != null; current=current.getNext()){
				if(current.getSizePath() > prior_node.getSizePath()){
					prior_node.setNext(current);
					if(prev==null)//if it's 1º
						head = prior_node;
					else
						prev.setNext(prior_node);
					return;
				}
				prev = current;
			}
			//if the current value is bigger then all the other values, insert in the end
			prev.setNext(prior_node);
		}
	}
	
	public void addNode(Node node, PriorityNode father, float size_path){
		PriorityNode prior_node = new PriorityNode(node, father, size_path);
		addNode(prior_node, father);
	}
	
	public PriorityNode popNode(){
		//if the queue is empty
		if(head==null)
			return null;
		PriorityNode first_node = head;
		head = first_node.getNext();	
		return first_node;
	}
		
	public PriorityNode researchNode(Node node){
		//if the queue is empty
		if(head==null)
			return null;
		for(PriorityNode temp=head; temp!=null; temp=temp.getNext())
			if(temp.getNode().equals(node))
				return temp;
		return null;
	}
	
	public boolean contains(Node node){
		return researchNode(node)!=null;
	}
	
	public boolean isEmpty(){
		return head==null;
	}
	
	public void clear(){
		PriorityNode prev=null;
		for(PriorityNode temp=head; temp!=null; temp=temp.getNext()){
			if(prev!=null)
				prev = null;
			prev = temp;
		}
		prev = null;
	}
	
}
