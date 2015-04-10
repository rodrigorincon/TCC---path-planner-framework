package util;

import util.Point;

import java.util.LinkedList;

public class Path {

	private LinkedList<Point> route;
	private int size;
	
	public Path(LinkedList<Point> percurso, int tamanho_percurso) {
		this.route = percurso;
		this.size = tamanho_percurso;
	}

	public LinkedList<Point> getRoute() {
		return route;
	}
	/** returns the size of the shortest path (size of the path, not the number of nodes!!) */
	public int getSize() {
		return size;
	}
	
	public void invert(){
		int size_route = route.size();
		for(int i=0; i<size_route/2; i++){
			Point p_end = route.remove(size_route-1-i);
			Point p_init = route.remove(i);
			route.add(i, p_end);
			route.add(size_route-1-i, p_init);
		}
	}
	
}
