package pathPlanner;

import java.util.ArrayList;
import java.util.List;

import util.Constants;
import util.Point;
import graph.Graph;
import graph.Node;

public class Voronoi extends PathPlanner{
	
	private List<Point> points;
	private ArrayList<Triangle> triangle_list;
	
	@Override
	public Graph resolution() {
		points = defineInitialPoints();
		delauneyTriangulation();
		graph_returned = new Graph();
		ArrayList<Node> nodes = createNodesAndEdges();
		addInitialAndFinalPoints(nodes);
		addNodesInGraph(nodes);
		return graph_returned;
	}

	private void addInitialAndFinalPoints(ArrayList<Node> nodes) {
		Point init = new Point(map.getInitialPoint()[Constants.COLUMN], map.getInitialPoint()[Constants.LINE]);
		Point goal = new Point(map.getGoalPoint()[Constants.COLUMN], map.getGoalPoint()[Constants.LINE]);
		
		for(int i=0; i<triangle_list.size(); i++){
			Triangle triangle = triangle_list.get(i);
			if( triangle.isPointInside(init) || triangle.isOverEdge(init) )
				addNodeInsideOfATriangle(triangle, nodes, init);
			else if( triangle.isPointInside(goal) || triangle.isOverEdge(goal) )
				addNodeInsideOfATriangle(triangle, nodes, goal);
		}
	}

	private void addNodeInsideOfATriangle(Triangle triangle, ArrayList<Node> nodes, Point point_inside){
		Node point_inside_node = new Node(point_inside.toString());
		nodes.add(point_inside_node);
		
		Point[] center_edges = getCenterEdges(triangle);	
		Node[] node_edges = addOrGetCenterEdgesToList(center_edges, nodes);
		
		if(freeRoute(center_edges[0],point_inside))
			point_inside_node.addEdge(node_edges[0], point_inside.dist(center_edges[0]));
		if(freeRoute(center_edges[1],point_inside))
			point_inside_node.addEdge(node_edges[1], point_inside.dist(center_edges[1]));
		if(freeRoute(center_edges[2],point_inside))
			point_inside_node.addEdge(node_edges[2], point_inside.dist(center_edges[2]));
	}
	
	private void addNodesInGraph(ArrayList<Node> nodes) {
		for(int i=0; i<nodes.size(); i++)
			graph_returned.addNode(nodes.get(i));
	}

	private ArrayList<Node> createNodesAndEdges() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int i=0; i<triangle_list.size(); i++){
			Triangle actual_triangle = triangle_list.get(i);
			addNodesAndEdgesOfTriangle(actual_triangle, nodes);
		}
		return nodes;
	}
	
	private void addNodesAndEdgesOfTriangle(Triangle triangle, ArrayList<Node> nodes) {
		Point[] center_edges = getCenterEdges(triangle);	
		Node[] node_edges = addOrGetCenterEdgesToList(center_edges, nodes);
		if(freeRoute(center_edges[0],center_edges[1]))
			node_edges[0].addEdge(node_edges[1], center_edges[0].dist(center_edges[1]));
		if(freeRoute(center_edges[0],center_edges[2]))
			node_edges[0].addEdge(node_edges[2], center_edges[0].dist(center_edges[2]));
		if(freeRoute(center_edges[1],center_edges[2]))
			node_edges[1].addEdge(node_edges[2], center_edges[1].dist(center_edges[2]));
		
	}
	
	/** if exists a node with a point info in the list, get it. Otherwise create a new node and add it in the list
	 * @return the nodes with the points info */
	private Node[] addOrGetCenterEdgesToList(Point[] center_edges,ArrayList<Node> nodes) {
		Node node_edge1 = createOrGetNode(nodes, center_edges[0]);
		Node node_edge2 = createOrGetNode(nodes, center_edges[1]);
		Node node_edge3 = createOrGetNode(nodes, center_edges[2]);
		return new Node[] {node_edge1,node_edge2,node_edge3};
	}

	private Point[] getCenterEdges(Triangle triangle){
		Point p1 = triangle.getP1();
		Point p2 = triangle.getP2();
		Point p3 = triangle.getP3();
		Point center_edge1 = new Point( (p1.getX()+p2.getX())/2, (p1.getY()+p2.getY())/2);
		Point center_edge2 = new Point( (p1.getX()+p3.getX())/2, (p1.getY()+p3.getY())/2);
		Point center_edge3 = new Point( (p3.getX()+p2.getX())/2, (p3.getY()+p2.getY())/2);
		return new Point[] {center_edge1,center_edge2,center_edge3};
	}

	private boolean freeRoute(Point center_edge1, Point center_edge2) {
		float x1 = center_edge1.getX();
		float x2 = center_edge2.getX();
		float y1 = center_edge1.getY();
		float y2 = center_edge2.getY();
		return haveFreeRouteLine(x1, y1, x2, y2);
	}
	
	private boolean haveFreeRouteLine(float x1, float y1, float x2, float y2){
		if(Math.abs(x1 - x2) <= 1 && Math.abs(y1 - y2) <= 1){
			int line = y2 >= map.getNumLines()-1 ? map.getNumLines()-1 : (int)y2;
			int col = x2 >= map.getNumColumns()-1 ? map.getNumColumns()-1 : (int)x2;
			boolean a = map.getPosition(line, col) == map.getFREE();
			return a;
		}
		float diff_x = Math.abs(x1 - x2); 
		float middle_x = (float)(diff_x/2 + Math.min(x1, x2));
		float diff_y = (float)Math.abs(y1 - y2);
		float middle_y = (float)( diff_y/2 + Math.min(y1, y2) );
		boolean b= haveFreeRouteLine(x1, y1, middle_x, middle_y) ? haveFreeRouteLine(middle_x, middle_y, x2, y2) : false;
		return b;
	}

	/** if exists a node with a point info, get it, otherwise create a new node and add it in the list
	 * @return a node with the point info */
	private Node createOrGetNode(ArrayList<Node> nodes, Point point){
		for(int i=0; i<nodes.size(); i++){
			if(nodes.get(i).getInfo().equals(point.toFloatString()))
				return nodes.get(i); 
		}
		Node node = new Node(point.toFloatString());
		nodes.add(node);
		return node;
	}

	private List<Point> defineInitialPoints() {
		List<Point> points = map.getVertices();	
		boolean sup_left_exist = false;
		boolean sup_middle_exist = false;
		boolean sup_right_exist = false;
		boolean middle_left_exist = false;
		boolean middle_right_exist = false;
		boolean sub_left_exist = false;
		boolean sub_middle_exist = false;
		boolean sub_right_exist = false;
		//verify if border points already exists for dont repeat the points
		for(int i=0; i<points.size(); i++){
			Point actual = points.get(i);
			if(actual.getX() == 0 && actual.getY() == 0)
				sup_left_exist = true;
			else if(actual.getX() == map.getNumColumns()/2 && actual.getY() == 0)
				sup_middle_exist = true;
			else if(actual.getX() == map.getNumColumns()-1 && actual.getY() == 0)
				sup_right_exist = true;
			else if(actual.getX() == 0 && actual.getY() == map.getNumLines()/2)
				middle_left_exist = true;
			else if(actual.getX() == map.getNumColumns()-1 && actual.getY() == map.getNumLines()/2)
				middle_right_exist = true;
			else if(actual.getX() == 0 && actual.getY() == map.getNumLines()-1)
				sub_left_exist = true;
			else if(actual.getX() == map.getNumColumns()/2 && actual.getY() == map.getNumLines()-1)
				sub_middle_exist = true;
			else if(actual.getX() == map.getNumColumns()-1 && actual.getY() == map.getNumLines()-1)
				sub_right_exist = true;
		}
		//add the border points
		if(!sup_left_exist)points.add(new Point(0, 0));
		if(!sup_middle_exist)points.add(new Point(map.getNumColumns()/2, 0));
		if(!sup_right_exist)points.add(new Point(map.getNumColumns(), 0));
		if(!middle_left_exist)points.add(new Point(0, map.getNumLines()/2));
		if(!middle_right_exist)points.add(new Point(map.getNumColumns(), map.getNumLines()/2));
		if(!sub_left_exist)points.add(new Point(0, map.getNumLines()));
		if(!sub_middle_exist)points.add(new Point(map.getNumLines()/2, map.getNumLines()));
		if(!sub_right_exist)points.add(new Point(map.getNumLines(), map.getNumLines()));
		return points;
	}

	private Point[] initial_triangulation() {
		int[] position_points = {0,0,0,0};
		for(int i=0; i<points.size(); i++){
			Point actual = points.get(i);
			if(actual.getX() == 0 && actual.getY() == 0){
				position_points[0] = i;
			}else if(actual.getX() == 0 && (actual.getY() == map.getNumLines()-1 || actual.getY() == map.getNumLines()) )
				position_points[1] = i;
			else if( (actual.getX() == map.getNumColumns()-1 || actual.getX() == map.getNumColumns()) && actual.getY() == 0)
				position_points[2] = i;
			else if( (actual.getX() == map.getNumColumns()-1 || actual.getX() == map.getNumColumns()) && (actual.getY() == map.getNumLines()-1 || actual.getY() == map.getNumLines()) )
				position_points[3] = i;
		}
		//sort the 4 positions
		for(int i=0; i<position_points.length-1; i++){
			for(int j=i+1; j<position_points.length; j++){
				if(position_points[i] > position_points[j]){
					int temp = position_points[i];
					position_points[i] = position_points[j];
					position_points[j] = temp;
				}
			}
		}
		//remove the points and return them
		Point init_point1 = points.remove(position_points[0]);
		Point init_point2 = points.remove(position_points[1]-1);
		Point init_point3 = points.remove(position_points[2]-2);
		Point init_point4 = points.remove(position_points[3]-3);
		return new Point[]{init_point1,init_point2,init_point3,init_point4};
	}
	
	
	private void delauneyTriangulation() {
		Point[] initial_points = initial_triangulation();
		triangle_list = new ArrayList<Triangle>();
		Triangle initial_triang = new Triangle(initial_points[0],initial_points[1], initial_points[2]);
		triangle_list.add(initial_triang);
		initial_triang = new Triangle(initial_points[1],initial_points[2], initial_points[3]);
		triangle_list.add(initial_triang);
		
		for(int index_point=0; index_point<points.size(); index_point++){
			Point new_point = points.get(index_point);
			for(int index_triangle=0; index_triangle<triangle_list.size(); index_triangle++){
				Triangle actual_triangle = triangle_list.get(index_triangle);
				if(actual_triangle.isPointInside(new_point) && actual_triangle.isNotAPoint(new_point)){
					Point triang_point1 = actual_triangle.getP1();
					Point triang_point2 = actual_triangle.getP2();
					Point triang_point3 = actual_triangle.getP3();
					triangle_list.remove(index_triangle);
					legalizeEdge(new_point, triang_point1, triang_point2);
					legalizeEdge(new_point, triang_point1, triang_point3);
					legalizeEdge(new_point, triang_point2, triang_point3);
					break;
				}else if(actual_triangle.isOverEdge(new_point) && actual_triangle.isNotAPoint(new_point)){
					Point[] edge = actual_triangle.getEdge(new_point);
					Triangle neighbor_triangle = getNeighborTriangle(actual_triangle, edge[0], edge[1]);
					triangle_list.remove(index_triangle);
					Point third_point = actual_triangle.getAnotherPoint(edge[0], edge[1]);
					if(neighbor_triangle != null){
						Point neighbor_point = neighbor_triangle.getAnotherPoint(edge[0], edge[1]);
						legalizeEdge(new_point,edge[0],neighbor_point);
						legalizeEdge(new_point,edge[1],neighbor_point);
						legalizeEdge(new_point,edge[0],third_point);
						legalizeEdge(new_point,edge[1],third_point);
					}else{
						Triangle t1 = new Triangle(new_point, edge[0], third_point);
						Triangle t2 = new Triangle(new_point, edge[1], third_point);
						triangle_list.add(t1);
						triangle_list.add(t2);
					}
					break;
				}
			}
		}
	}

	private Triangle getNeighborTriangle(Triangle triangle, Point triang_point1, Point triang_point2){
		for(int count=0; count<triangle_list.size(); count++){
			Triangle possible_neighbor_triangle = triangle_list.get(count);
			if(possible_neighbor_triangle == triangle)
				continue;
			if(possible_neighbor_triangle.hasEdge(triang_point1, triang_point2))
				return possible_neighbor_triangle;
		}
		return null;
	}


	private void legalizeEdge(Point new_point, Point triang_point1, Point triang_point2) {
		Triangle triangle = new Triangle(new_point, triang_point1, triang_point2);
		Triangle neighbor_triangle = getNeighborTriangle(triangle, triang_point1, triang_point2);
		if(neighbor_triangle!=null && neighbor_triangle.isPointInside(new_point)){
			Point neighbor_point = neighbor_triangle.getAnotherPoint(triang_point1, triang_point2);
			triangle_list.remove(neighbor_triangle);
			Triangle new_triang1 = new Triangle(triang_point1, neighbor_point, new_point);
			Triangle new_triang2 = new Triangle(triang_point2, neighbor_point, new_point);
			triangle_list.add(new_triang1);
			triangle_list.add(new_triang2);
		}else{
			triangle_list.add(triangle);
		}
	}

	public class Triangle{
		private Point p1, p2, p3;

		public Triangle(Point p1, Point p2, Point p3){
			this.p1 = p1;
			this.p2 = p2;
			this.p3 = p3;
		}
		
		public boolean isNotAPoint(Point new_point) {
			return p1!=new_point && p2!=new_point && p3!= new_point;
		}
		public Point getAnotherPoint(Point pA, Point pB) {
			if( (pA==p1 && p2==pB) || (pA==p2 && pB==p1) )
				return p3;
			if( (pA==p1 && p3==pB) || (pA==p3 && pB==p1) )
				return p2;
			if( (pA==p3 && p2==pB) || (pA==p2 && pB==p3) )
				return p1;
			return null;
		}
		public boolean hasEdge(Point point, Point point2) {
			return (p1==point && p2==point2) || (p1==point2 && p2==point) || 
					(p1==point && p3==point2) || (p1==point2 && p3==point) || 
					(p2==point && p3==point2) || (p2==point2 && p3==point);
		}
		public Point[] getEdge(Point pt) {
			if(p1.getX() == p2.getX() && p1.getX() == pt.getX() )
				return new Point[]{p1,p2};
			if(p1.getX() == p3.getX() && p1.getX() == pt.getX() )
				return new Point[]{p1,p3};
			if(p2.getX() == p3.getX() && p2.getX() == pt.getX() )
				return new Point[]{p2,p3};
			
			float angular_coef_p1_p2 = Math.abs(p1.getY()-p2.getY())/(Math.abs(p1.getX()-p2.getX()));
			float linear_coef_p1_p2 = p1.getY() - angular_coef_p1_p2*p1.getX();
			float angular_coef_p1_p3 = Math.abs(p1.getY()-p3.getY())/(Math.abs(p1.getX()-p3.getX()));
			float linear_coef_p1_p3 = p1.getY() - angular_coef_p1_p3*p1.getX();
			float angular_coef_p3_p2 = Math.abs(p3.getY()-p2.getY())/(Math.abs(p3.getX()-p2.getX()));
			float linear_coef_p3_p2 = p3.getY() - angular_coef_p3_p2*p3.getX();
			
			float angular_coef_pt_p1 = Math.abs(p1.getY()-pt.getY())/(Math.abs(p1.getX()-pt.getX()));
			float linear_coef_pt_p1 = p1.getY() - angular_coef_pt_p1*p1.getX();
			float angular_coef_pt_p2 = Math.abs(p2.getY()-pt.getY())/(Math.abs(p2.getX()-pt.getX()));
			float linear_coef_pt_p2 = p2.getY() - angular_coef_pt_p2*p2.getX();
			if(angular_coef_p1_p2 == angular_coef_pt_p1 && linear_coef_p1_p2 == linear_coef_pt_p1)
				return new Point[]{p1,p2};
			if(angular_coef_p1_p3 == angular_coef_pt_p1 && linear_coef_p1_p3 == linear_coef_pt_p1)
				return new Point[]{p1,p3};
			if(angular_coef_p3_p2 == angular_coef_pt_p2 && linear_coef_p3_p2 == linear_coef_pt_p2)
				return new Point[]{p2,p3};
			return null;
		}
		public boolean isOverEdge(Point new_point) {
			return getEdge(new_point) != null;
		}
		
		private float determinante(Point new_point){
			Point[] points = counterClockwiseOrder(new_point);
			float[][] matrix = { {points[0].getX(), points[0].getY(), (float)(Math.pow(points[0].getX(),2)+Math.pow(points[0].getY(),2)), 1},
								 {points[1].getX(), points[1].getY(), (float)(Math.pow(points[1].getX(),2)+Math.pow(points[1].getY(),2)), 1},
								 {points[2].getX(), points[2].getY(), (float)(Math.pow(points[2].getX(),2)+Math.pow(points[2].getY(),2)), 1},
								 {points[3].getX(), points[3].getY(), (float)(Math.pow(points[3].getX(),2)+Math.pow(points[3].getY(),2)), 1}};
			return -matrix[0][3]*(matrix[1][0]*matrix[2][1]*matrix[3][2]+matrix[1][1]*matrix[2][2]*matrix[3][0]+matrix[2][0]*matrix[3][1]*matrix[1][2]-matrix[1][2]*matrix[2][1]*matrix[3][0]-matrix[1][1]*matrix[2][0]*matrix[3][2]-matrix[1][0]*matrix[2][2]*matrix[3][1]) +matrix[1][3]*(matrix[0][0]*matrix[2][1]*matrix[3][2]+matrix[0][1]*matrix[2][2]*matrix[3][0]+matrix[2][0]*matrix[3][1]*matrix[0][2]-matrix[0][2]*matrix[2][1]*matrix[3][0]-matrix[0][1]*matrix[2][0]*matrix[3][2]-matrix[0][0]*matrix[2][2]*matrix[3][1]) -matrix[2][3]*(matrix[0][0]*matrix[1][1]*matrix[3][2]+matrix[0][1]*matrix[1][2]*matrix[3][0]+matrix[1][0]*matrix[3][1]*matrix[0][2]-matrix[0][2]*matrix[1][1]*matrix[3][0]-matrix[0][1]*matrix[1][0]*matrix[3][2]-matrix[0][0]*matrix[1][2]*matrix[3][1]) +matrix[3][3]*(matrix[0][0]*matrix[1][1]*matrix[2][2]+matrix[0][1]*matrix[1][2]*matrix[2][0]+matrix[1][0]*matrix[2][1]*matrix[0][2]-matrix[0][2]*matrix[1][1]*matrix[2][0]-matrix[0][1]*matrix[1][0]*matrix[2][2]-matrix[0][0]*matrix[1][2]*matrix[2][1]);
		}
		public boolean isPointInside(Point new_point) {
			return determinante(new_point)>0;
		}
		
		private Point centerCicle(){
			double center_p1p3_x = (p1.getX()+p3.getX())/2;
			double center_p1p3_y = (p1.getY()+p3.getY())/2;
			double center_p2p3_x = (p2.getX()+p3.getX())/2;
			double center_p2p3_y = (p2.getY()+p3.getY())/2;
			
			double angular_coef_p1_p3 = (p1.getY()-p3.getY())/(p1.getX()-p3.getX());
			double angular_coef_mediatriz_p1_p3 = -1.0/angular_coef_p1_p3;
			double angular_coef_p2_p3 = (p2.getY()-p3.getY())/(p2.getX()-p3.getX());
			double angular_coef_mediatriz_p2_p3 = -1.0/angular_coef_p2_p3;
			
			float center_x = (float) ((-angular_coef_mediatriz_p1_p3*center_p1p3_x+center_p1p3_y+angular_coef_mediatriz_p2_p3*center_p2p3_x-center_p2p3_y)/(angular_coef_mediatriz_p2_p3-angular_coef_mediatriz_p1_p3));
			float center_y = (float) (angular_coef_mediatriz_p1_p3*(center_x-center_p1p3_x)+center_p1p3_y);
			return new Point(center_x, center_y);
		}
		
		private Point[] counterClockwiseOrder(Point p4){
			Point center = centerCicle();
			float angular_coef_p1_center = (p1.getY()-center.getY())/(p1.getX()-center.getX());
			float angular_coef_p2_center = (p2.getY()-center.getY())/(p2.getX()-center.getX());
			float angular_coef_p3_center = (p3.getY()-center.getY())/(p3.getX()-center.getX());
			float angular_coef_p4_center = (p4.getY()-center.getY())/(p4.getX()-center.getX());
			float[] angulars = {angular_coef_p1_center, angular_coef_p2_center, angular_coef_p3_center, angular_coef_p4_center};
			//sort
			Point[] points = {p1,p2,p3,p4};
			for(int i=0; i<points.length-1; i++){
				for(int j=i+1; j<points.length; j++){
					if(points[i].getX() > center.getX() && points[j].getX() < center.getX()){
						Point temp = points[i];
						points[i] = points[j];
						points[j] = temp;
						float temp_ang = angulars[i];
						angulars[i] = angulars[j];
						angulars[j] = temp_ang;
					}else if( (points[i].getX() < center.getX() && points[j].getX() < center.getX() ) || (points[i].getX() > center.getX() && points[j].getX() > center.getX()) ){
						if(angulars[i] > angulars[j]){
							Point temp = points[i];
							points[i] = points[j];
							points[j] = temp;
							float temp_ang = angulars[i];
							angulars[i] = angulars[j];
							angulars[j] = temp_ang;
						}
					}
				}
			}
			return points;
		}
		
		public String toString(){
			return "A: "+p1.toString()+", B: "+p2.toString()+", C: "+p3.toString();
		}
		
		public Point getP1() {
			return p1;
		}
		public Point getP3() {
			return p3;
		}
		public Point getP2() {
			return p2;
		}
	}
	
}
