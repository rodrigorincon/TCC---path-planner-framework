package pathPlanner;
import java.util.ArrayList;
import java.util.List;
import util.Constants;
import graph.Graph;
import graph.Node;

public class WaveFront extends PathPlanner{

	private final int INITIAL_VALUE = 2;
	private List<int[]> boundary;
	
	/** returns if the cell in line,col position must be insert in boundary list */
	private boolean evaluateCell(int[] center_cell, int line, int col){
		boolean return_value = false;
		if( map.getPosition(line, col) != map.getOCCUPIED() ){
			if( map.getPosition(line, col) == map.getFREE() ||
				map.getPosition(line, col)>map.getPosition(center_cell)+1 )
				return_value = true;
		}
		return return_value;
	}
	
	private boolean is8Neighborhood(int[] cell, int line, int col){
		if(diagonalNeighbors(cell, line, col) && map.isObstaclesExpanded())
			return true;
		return false;
	}
	
	/** insert the value in the neighbor cells and add them in the list */
	private void insertNeighborhood(int[] cell){
		for(int line=cell[Constants.LINE]-1; line<=cell[Constants.LINE]+1; line++){
			for(int col=cell[Constants.COLUMN]-1; col<=cell[Constants.COLUMN]+1; col++){
				if(line==cell[Constants.LINE] && col==cell[Constants.COLUMN])
					continue;
				if(line>=0 && col>=0 && line<map.getNumLines() && col<map.getNumColumns()){
					if(is8Neighborhood(cell,line,col) || diagonalNeighbors(cell, line, col)==false){
						if( evaluateCell(cell,line,col) ){
							map.setValue(line, col, map.getPosition(cell)+1);
							boundary.add(new int[] {line,col});
						}
					}
				}
			}
		}
	}
	
	private void fillCells(){
		int[] goal = map.getGoalPoint();
		map.setValue(goal[Constants.LINE], goal[Constants.COLUMN], INITIAL_VALUE);
		
		boundary = new ArrayList<int[]>();
		insertNeighborhood(goal);
		
		while(boundary.isEmpty() == false){
			int[] cell = boundary.remove(0);
			insertNeighborhood(cell);
		}
	}
	
	private List<Node> listSmallerNeighbors(Node node){
		int[] cell = node.getIntCoordinates();
		List<Node> returned_list = new ArrayList<Node>();
		int minimal_value = map.getPosition(cell);
		//define the smaller value of the neighbor cells		
		for(int line=cell[Constants.LINE]-1; line<=cell[Constants.LINE]+1; line++){
			for(int col=cell[Constants.COLUMN]-1; col<=cell[Constants.COLUMN]+1; col++){
				if(line==cell[Constants.LINE] && col==cell[Constants.COLUMN])
					continue;
				if(line>=0 && col>=0 && line<map.getNumLines() && col<map.getNumColumns()){					
					if(is8Neighborhood(cell,line,col) || diagonalNeighbors(cell, line, col)==false){
						if( map.getPosition(line, col) != map.getOCCUPIED() )
							minimal_value = map.getPosition(line, col) < minimal_value ? map.getPosition(line, col) : minimal_value;
					}
				}
			}
		}
		//add the neighbor cells with the smaller value
		for(int line=cell[Constants.LINE]-1; line<=cell[Constants.LINE]+1; line++){
			for(int col=cell[Constants.COLUMN]-1; col<=cell[Constants.COLUMN]+1; col++){
				if(line==cell[Constants.LINE] && col==cell[Constants.COLUMN])
					continue;
				if(line>=0 && col>=0 && line<map.getNumLines() && col<map.getNumColumns()){
					if(is8Neighborhood(cell,line,col) || diagonalNeighbors(cell, line, col)==false){						
						if(map.getPosition(line, col) == minimal_value)
							returned_list.add(new Node(line+","+col));
					}
				}
			}
		}
		return returned_list;
	}
		
	private Graph createGraph(){
		graph_returned = new Graph();
		int[] init = map.getInitialPoint();
		
		Node central_node = new Node(init[Constants.LINE]+","+init[Constants.COLUMN]);
		graph_returned.addNode(central_node);
		
		for(int i=0; i<graph_returned.getNumNodes(); i++){			
			Node cell = graph_returned.getNode(i);
			List<Node> smaller_neighbors = listSmallerNeighbors(cell);
			while(smaller_neighbors.isEmpty()==false){
				Node neighbor = smaller_neighbors.remove(0);
				if( !graph_returned.nodeAlreadyExists(neighbor.getInfo()) )
					graph_returned.addNode(neighbor);
				else
					neighbor = graph_returned.getNode(neighbor.getInfo());
				
				if(diagonalNeighbors(cell,neighbor))
					cell.addEdge(neighbor, (float)1.4);
				else
					cell.addEdge(neighbor, 1);
			}
		}		
		return graph_returned;
	}
	
	private boolean diagonalNeighbors(int[] cell, int line, int col){
		if(cell[Constants.LINE]!=line && cell[Constants.COLUMN]!=col)
			return true;
		return false;
	}
	
	private boolean diagonalNeighbors(Node cell, Node neighbor) {
		int[] values_cell = cell.getIntCoordinates();
		int[] values_neighbor = neighbor.getIntCoordinates();
		if(values_cell[Constants.LINE]!=values_neighbor[Constants.LINE] && values_cell[Constants.COLUMN]!=values_neighbor[Constants.COLUMN])
			return true;
		return false;
	}

	@Override
	public Graph resolution() {
		fillCells();
		return createGraph();
	}

}
