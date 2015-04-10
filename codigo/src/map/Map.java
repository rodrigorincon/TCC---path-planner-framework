package map;

import util.Constants;

public class Map {
	
	private int[][] data;
	private final int FREE = 0;
	private final int OCCUPIED = 1;
	private int[] initial_point;
	private int[] goal_point;
	
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
	
	public void expandObstacles(int robotWidth, int cellWidth){
		//TODO
	}
	
	public void cleanUp(){
		//TODO
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
