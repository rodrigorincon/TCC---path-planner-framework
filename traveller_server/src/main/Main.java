package main;

import java.io.IOException;
import controller.PathPlannerController;
import robotCommunication.BluetoothCommunicator;
import util.Path;

public class Main {
	
	private static void attendRequest(BluetoothCommunicator communicator){
		
		int[] init_point = null;
		int[] goal_point = null;
		String path_planner = null;
		String best_path = null;
		boolean[][] map = null;
		boolean free_value = false;
		float carWidth = 0;
		float cellWidth = 0;
		boolean expand_obtacles = false;
		try {
			init_point = communicator.getPoint();
			goal_point = communicator.getPoint();
			path_planner = communicator.getString();
			best_path = communicator.getString();
			map = communicator.getMap();
			free_value = communicator.getBoolean();
			carWidth = communicator.getFloat();
			cellWidth = communicator.getFloat();
			expand_obtacles = communicator.getBoolean();
		} catch (IOException e) {
			System.out.println("data receivement failed: "+e.getMessage());
			return;
		}
		PathPlannerController controller = new PathPlannerController(path_planner, best_path, carWidth, cellWidth);
		controller.defineMap(map, free_value, init_point[0], init_point[1], goal_point[0], goal_point[1]);
		controller.expandObstacles(expand_obtacles);
		Path path = controller.execute(carWidth, cellWidth);
		try {
			if(path==null)
				communicator.sendFail();
			else{
				communicator.sendPathSize(path.getSize());
				communicator.sendPath(path);
				communicator.sendPathSize(path.getSize());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		BluetoothCommunicator communicator = new BluetoothCommunicator();
		try {
			communicator.open();
			while(true){
				try{
					communicator.connect();
					attendRequest(communicator);					
				}catch (IOException e) {
					System.out.println("execution failed: "+e.getMessage());
				}finally{
					communicator.close();					
				}
			}
		} catch (IOException e) {
			System.out.println("openning failed: "+e.getMessage());
		}		
	}
}
