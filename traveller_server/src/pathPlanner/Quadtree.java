package pathPlanner;

import java.util.ArrayList;
import java.util.List;

import util.Constants;
import graph.Graph;
import graph.Node;

public class Quadtree extends PathPlanner{

	private int minimumSize;
	private List<Square> squares;
	
	public Quadtree(float carWidth, float cellWidth){
		minimumSize = Math.round(carWidth/cellWidth);
		squares = new ArrayList<Square>();
	}
	
	@Override
	public Graph resolution() {
		verifyAndAddSquare(verifySquare(0,0,map.getNumLines(),map.getNumColumns()));
		graph_returned = new Graph();
		addInitialPoint();
		addFinalPoint();
		addNodes();
		addEdges();
		//if the initial or the goal point isnt inside a bigger square
		verifyInitialAndGoalPoints();
		clearSquares();
		return graph_returned;
	}

	private Square verifySquare(int init_line, int init_column, int linesSquare, int columnsSquare){
		Square new_square = null;
		if(linesSquare < minimumSize || columnsSquare < minimumSize)
			return null;
		if( (linesSquare/2 < minimumSize || columnsSquare/2 < minimumSize) && isAllFree(init_line, init_column, linesSquare, columnsSquare) )
			new_square = new Square(init_line, init_column, linesSquare, columnsSquare);
		else if(linesSquare > minimumSize && columnsSquare > minimumSize){
			Square top_left = verifySquare(init_line, init_column, linesSquare/2, columnsSquare/2);
			Square top_right = verifySquare(init_line, init_column+columnsSquare/2, linesSquare/2, columnsSquare-columnsSquare/2);
			Square down_left = verifySquare(init_line+linesSquare/2, init_column, linesSquare-linesSquare/2, columnsSquare/2);
			Square down_right = verifySquare(init_line+linesSquare/2, init_column+columnsSquare/2, linesSquare-linesSquare/2, columnsSquare-columnsSquare/2);
			
			//if all the four is free, change them for one biggest square
			if(top_left != null && top_right != null && down_left != null && down_right != null)
				new_square = new Square(init_line, init_column, linesSquare, columnsSquare);
			else{
				verifyAndAddSquare(top_left);
				verifyAndAddSquare(top_right);
				verifyAndAddSquare(down_left);
				verifyAndAddSquare(down_right);
			}
		}
		else if(linesSquare == minimumSize && columnsSquare > minimumSize){
			//horizontal passage
			Square left = verifySquare(init_line, init_column, linesSquare, minimumSize);
			Square right = verifySquare(init_line, init_column+minimumSize, linesSquare, columnsSquare-minimumSize);
			if(left != null && right != null ){
				new_square = new Square(init_line, init_column, linesSquare, columnsSquare);
			}else{
				verifyAndAddSquare(left);
				verifyAndAddSquare(right);
			}
		}else if(linesSquare > minimumSize && columnsSquare == minimumSize){
			//vertical passage
			Square up = verifySquare(init_line, init_column, minimumSize, columnsSquare);
			Square down = verifySquare(init_line+minimumSize, init_column, linesSquare-minimumSize, columnsSquare);
			if(up != null && down != null ){
				new_square = new Square(init_line, init_column, linesSquare, columnsSquare);
			}else{
				verifyAndAddSquare(up);
				verifyAndAddSquare(down);
			}
		}
		else{
			//when is the size of robot
			if( isAllFree(init_line, init_column, linesSquare, columnsSquare) )
				new_square = new Square(init_line, init_column, linesSquare, columnsSquare);
		}
		return new_square;
	}	
	
	private void verifyAndAddSquare(Square square){
		if(square != null){
			square.createNode();
			squares.add(square);
		}
	}
	
	private void addInitialPoint(){
		int[] init = map.getInitialPoint();
		Square squareInit = new Square(init[Constants.LINE], init[Constants.COLUMN], 1, 1);
		squareInit.createNode();
		squares.add(squareInit);
	}
	
	private void addFinalPoint(){
		int[] goal = map.getGoalPoint();
		Square squareGoal = new Square(goal[Constants.LINE], goal[Constants.COLUMN], 1, 1);
		squareGoal.createNode();
		squares.add(squareGoal);
	}
	
	private void addNodes(){
		for(int i=0; i<squares.size(); i++){
			Square current = squares.get(i);
			graph_returned.addNode(current.getNode());
		}
	}
	
	private void addEdges(){
		for(int i=0; i<squares.size(); i++){
			Square current = squares.get(i);
			for(int j=i+1; j<squares.size(); j++){
				Square possible_neighbor = squares.get(j);
				if(squareColision(current,possible_neighbor)){
					Node current_node = current.getNode();
					Node neighbor_node = possible_neighbor.getNode();
					float dist = calculateDistance(current_node,neighbor_node);					
					graph_returned.addEdge(current_node, neighbor_node, dist);
				}
			}
		}
	}
	
	private float calculateDistance(Node current, Node neighbor){
		int current_coordinate_x = current.getIntCoordinates()[Constants.COLUMN];
		int current_coordinate_y = current.getIntCoordinates()[Constants.LINE];
		int neighbor_coordinate_x = neighbor.getIntCoordinates()[Constants.COLUMN];
		int neighbor_coordinate_y = neighbor.getIntCoordinates()[Constants.LINE];
		
		int diff_x = Math.abs(current_coordinate_x-neighbor_coordinate_x);
		int diff_y = Math.abs(current_coordinate_y-neighbor_coordinate_y);
		return (float)Math.sqrt( Math.pow(diff_x,2) + Math.pow(diff_y,2) );
	}
	
	private boolean squareColision(Square sqr1, Square sqr2){
		boolean verify_lines_sqr1_bigger = sqr2.getLine() >= sqr1.getLine() && sqr2.getLine() <= sqr1.getLine()+sqr1.getHeight();
		boolean verify_lines_sqr2_bigger = sqr1.getLine() >= sqr2.getLine() && sqr1.getLine() <= sqr2.getLine()+sqr2.getHeight();
		boolean same_lines = verify_lines_sqr1_bigger || verify_lines_sqr2_bigger;
		
		boolean verify_collumns_sqr1_bigger = sqr2.getColumn() >= sqr1.getColumn() && sqr2.getColumn() <= sqr1.getColumn()+sqr1.getWidth();
		boolean verify_collumns_sqr2_bigger = sqr1.getColumn() >= sqr2.getColumn() && sqr1.getColumn() <= sqr2.getColumn()+sqr2.getWidth();
		boolean same_collumns = verify_collumns_sqr1_bigger || verify_collumns_sqr2_bigger;
		
		return same_lines && same_collumns;
	}
	
	private void verifyInitialAndGoalPoints() {
		Node initial = graph_returned.getNode(graph_returned.getNumNodes()-2);
		if(initial.getNumNeighbors()==0 && map.getPosition(initial.getIntCoordinates())==map.getFREE())
			addEdgeToNearest(initial);
		Node goal = graph_returned.getNode(graph_returned.getNumNodes()-1);
		if(goal.getNumNeighbors()==0 && map.getPosition(goal.getIntCoordinates())==map.getFREE())
			addEdgeToNearest(goal);
	}
	
	private void addEdgeToNearest(Node node_to_add) {
		Node nearest = null;
		float smaller_dist = 9999;
		//find the nearest node and the distance, if exist
		for(int i=0; i<graph_returned.getNumNodes(); i++){
			Node possible_neighbor = graph_returned.getNode(i);
			if(node_to_add == possible_neighbor)
				continue;
			if( haveFreeRouteLine(node_to_add.getIntCoordinates()[Constants.COLUMN], node_to_add.getIntCoordinates()[Constants.LINE], 
					possible_neighbor.getIntCoordinates()[Constants.COLUMN], possible_neighbor.getIntCoordinates()[Constants.LINE]) ){
				if( nearest == null ){
					nearest = possible_neighbor;
					smaller_dist = calculateDistance(node_to_add, possible_neighbor);
				}else{
					float temp_dist = calculateDistance(node_to_add, possible_neighbor);
					if(temp_dist < smaller_dist){
						nearest = possible_neighbor;
						smaller_dist = temp_dist;
					}
				}
			}
		}
		//add the nodes
		if(nearest != null)
			node_to_add.addEdge(nearest, smaller_dist);
	}
	
	private boolean haveFreeRouteLine(int x1, int y1, int x2, int y2){
		if(Math.abs(x1 - x2) <= 1 && Math.abs(y1 - y2) <= 1){
			boolean a = map.getPosition(y2, x2) == map.getFREE();
			return a;
		}
		int diff_x = Math.abs(x1 - x2); 
		int middle_x = diff_x/2 + Math.min(x1, x2);
		int diff_y = (int)Math.abs(y1 - y2);
		int middle_y = diff_y/2 + Math.min(y1, y2);
		boolean b= haveFreeRouteLine(x1, y1, middle_x, middle_y) ? haveFreeRouteLine(middle_x, middle_y, x2, y2) : false;
		return b;
	}

	private void clearSquares(){
		while(!squares.isEmpty())
			squares.remove(0);
	}
	
	private boolean isAllFree(int init_line,int init_column,int linesSquare, int columnsSquare){
		boolean is_free = true;
		for(int line=init_line; line<init_line+linesSquare; line++){
			for(int column=init_column; column<init_column+columnsSquare; column++){
				if(map.getPosition(line, column) == map.getOCCUPIED()){
					is_free = false;
					break;
				}
			}
			if(!is_free)
				break;
		}
		return is_free;
	}
	
	private class Square{
		
		private int line;
		private int column;
		private int height;
		private int width;
		private Node node;
		
		public Square(int line, int column, int height, int width){
			this.line = line;
			this.column = column;
			this.height = height;
			this.width = width;
		}

		public void createNode(){
			int center_line = line+height/2;
			int center_column = column+width/2;
			this.node = new Node(center_line+","+center_column);
		}
		
		public Node getNode(){
			return node;
		}
		public int getLine() {
			return line;
		}
		public int getColumn() {
			return column;
		}
		public int getHeight() {
			return height;
		}
		public int getWidth() {
			return width;
		}		
	}

}
