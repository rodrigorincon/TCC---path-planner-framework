package util;

import util.Point;
import java.util.List;

public class Path {

	private List<Point> route;
	private float size;
	
	public Path(List<Point> percurso, float tamanho_percurso) {
		this.route = percurso;
		this.size = tamanho_percurso;
	}

	public List<Point> getRoute() {
		return route;
	}
	/** returns the size of the shortest path (size of the path, not the number of nodes!!) */
	public float getSize() {
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
