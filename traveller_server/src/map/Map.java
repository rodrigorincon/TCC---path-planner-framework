package map;

import java.util.ArrayList;
import java.util.List;

import util.Constants;
import util.Point;

public class Map {
	
	private int[][] data;
	private final int FREE = 0;
	private final int OCCUPIED = 1;
	private int[] initial_point;
	private int[] goal_point;
	private boolean obstacles_expanded = false;
	
	public Map(boolean[][] map_data, boolean free_value){
		 this.data = new int[map_data.length][map_data[0].length];
		 for(int i=0; i<map_data.length; i++){
			 for(int j=0; j<map_data[0].length; j++)
				 this.data[i][j] = map_data[i][j] == free_value ? this.FREE : this.OCCUPIED;
		 }
		 
		 initial_point = new int[2];
		 goal_point = new int[2];
	}
	
	public void setInitialPoint(int init_x, int init_y){
		initial_point[Constants.LINE] = init_y;
		initial_point[Constants.COLUMN] = init_x;
	}
	
	public void setFinalPoint(int final_x, int final_y){
		goal_point[Constants.LINE] = final_y;
		goal_point[Constants.COLUMN] = final_x;
	}
	
	public void expandObstacles(float robotWidth, float cellWidth){
		int size_robot_per_cells = (int) Math.ceil(robotWidth/(cellWidth*2) );
		if(obstacles_expanded){
			ArrayList<Point> to_be_expanded = new ArrayList<Point>(100);
			//mark the cells to be expanded
			for(int line=0; line<data.length; line++){
				for(int col=0; col<data[0].length; col++){
					if(data[line][col] == OCCUPIED){
						//verify the neighbors
						for(int neighbor_line=line-size_robot_per_cells; neighbor_line<=line+size_robot_per_cells; neighbor_line++){
							for(int neighbor_col=col-size_robot_per_cells; neighbor_col<=col+size_robot_per_cells; neighbor_col++){
								if(neighbor_line>=0 && neighbor_col>=0 && neighbor_line<data.length && neighbor_col<data[0].length){
									if(data[neighbor_line][neighbor_col] == FREE)
										to_be_expanded.add(new Point(neighbor_col, neighbor_line));
								}
							}
						}
					}
				}
			}
			//expand the cells marked
			for(int i=0; i<to_be_expanded.size(); i++)
				data[(int)to_be_expanded.get(i).getY()][(int)to_be_expanded.get(i).getX()] = OCCUPIED;
		}
	}
		
	public boolean isObstaclesExpanded(){
		return this.obstacles_expanded;
	}
	public void setObstaclesExpanded(boolean expansion){
		this.obstacles_expanded = expansion;
	}
		
	public void cleanUp(){
		initial_point = null;
		goal_point = null;
		data = null;
	}

	public List<Point> getVertices() {
		ArrayList<Point> vertices = new ArrayList<Point>();
		for(int line=0; line<data.length; line++){
			for(int col=0; col<data[0].length; col++){
				if(data[line][col] == OCCUPIED){
					//if is a extreme point of the matrix
					if( (line==0 && col==0) || (line==0 && col==data[0].length-1) || (line==data.length-1 && col==0) || (line==data.length-1 && col==data[0].length-1) )
						vertices.add(new Point(col, line));
					//if is the firts or last line
					else if( (line==0 || line==data.length-1) ){
						if( data[line][col-1] == FREE || data[line][col+1] == FREE)
							vertices.add(new Point(col, line));
					}
					//if is the first or last collumn
					else if( (col==0 || col==data[0].length-1) ){
						if( data[line-1][col] == FREE || data[line+1][col] == FREE )
							vertices.add(new Point(col, line));
					}
					//if is the middle of the matrix
					else{
						boolean left_free = data[line][col-1] == FREE;
						boolean right_free = data[line][col+1] == FREE;
						boolean bottom_free = data[line+1][col] == FREE;
						boolean up_free = data[line-1][col] == FREE;
						boolean cells_up_are_free = data[line-1][col-1]==FREE && data[line-1][col+1]==FREE;
						boolean cells_bottom_are_free = data[line+1][col-1]==FREE && data[line+1][col+1]==FREE;
						boolean cells_up_are_diff = data[line-1][col-1] != data[line-1][col+1];
						boolean cells_bottom_are_diff = data[line+1][col-1] != data[line+1][col+1];
						//square obstacle
						if( left_free != right_free && up_free != bottom_free && (cells_up_are_free || cells_bottom_are_free) )
							vertices.add(new Point(col, line));
						//triangle obstacle
						else if( (left_free && right_free && bottom_free != up_free) || (up_free && bottom_free && left_free != right_free) )
							vertices.add(new Point(col, line));
						//poligonal obstacle
						else if( left_free != right_free && up_free != bottom_free && cells_up_are_diff && cells_bottom_are_diff )
							vertices.add(new Point(col, line));
					}
				}
			}
		}
		return vertices;
	}
	
	public boolean NeighborVerticesOfSameObstacle(int col1, int line1, int col2, int line2) {
		boolean response = false;
		if(data[line1][col1] == OCCUPIED && data[line2][col2] == OCCUPIED){
			int diff_line = Math.abs(line1 - line2);
			int diff_col = Math.abs(col1 - col2);
			boolean control = false;
			boolean is_not_the_same_obstacle = false;
			//if they are in the same line
			if(diff_line == 0 && diff_col !=0){
				control = true;
				for(int col=Math.min(col1, col2)+1; col<Math.max(col1, col2); col++)
					if(data[line1][col] == FREE)
						is_not_the_same_obstacle = true;
			}
			//if they are in the same column
			else if(diff_line != 0 && diff_col == 0){
				control = true;
				for(int line=Math.min(line1, line2)+1; line<Math.max(line1, line2); line++)
					if(data[line][col1] == FREE)
						is_not_the_same_obstacle = true;
			}
			//if they are in perfect diagonal
			else if(diff_col!=0 && diff_line!=0 && diff_col==diff_line){
				control = true;
				for(int line=Math.min(line1, line2)+1,col=Math.min(col1, col2)+1; col<Math.max(col1, col2); line++,col++)
					if(data[line][col] == FREE || data[line][col-1]==data[line][col+1])
						is_not_the_same_obstacle = true;
			}
			//if they are in imperfect diagonal near of 90 degrees
			else if(diff_col==1 && diff_line!=0){
				control = true;
				for(int line=Math.min(line1, line2)+1; line<Math.max(line1, line2); line++){
					if(data[line][col1] == data[line][col2] )
						is_not_the_same_obstacle = true;
				}
			}
			//if they are in imperfect diagonal near of 0 degrees
			else if(diff_col!=0 && diff_line==1){
				control = true;
				for(int col=Math.min(col1, col2)+1; col<Math.max(col1, col2); col++){
					if(data[line1][col] == data[line2][col] )
						is_not_the_same_obstacle = true;
				}
			}
			response = control && !is_not_the_same_obstacle;
		}
		return response;
	}
	
	public int getNumLines(){
		return data.length;
	}
	
	public int getNumColumns(){
		return data[0].length;
	}
	/** in relation to x-y axis, where x grows from left to right and y grows from up to down, 
	 * the parameter line represents y and column represents x*/
	public void setValue(int line, int column, int value){
		data[line][column] = value;
	}
	
	public int getPosition(int line, int column){
		return data[line][column];
	}
	public int getPosition(int[] cell){
		return data[cell[Constants.LINE]][cell[Constants.COLUMN]];
	}

	public int getFREE() {
		return FREE;
	}

	public int getOCCUPIED() {
		return OCCUPIED;
	}

	/** returns the line in position 0 and column in position 1*/
	public int[] getInitialPoint() {
		return initial_point;
	}

	/** returns the line in position 0 and column in position 1*/
	public int[] getGoalPoint() {
		return goal_point;
	}

}
