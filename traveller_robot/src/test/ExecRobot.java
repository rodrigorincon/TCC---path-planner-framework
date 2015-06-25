package test;

import java.util.List;
import traveller.communication.NXTCommunication;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import traveller.communication.EmbeddedCommunication;
import traveller.util.CommunicationException;
import traveller.util.Path;
import traveller.util.Point;

public class ExecRobot {

	
	public static boolean[][] createMap(){
		int size = 60;
	    boolean[][] map = new boolean[size][size];
	    for(int i=0; i<size; i++)
	    	for(int j=0; j<size; j++)
	    		map[i][j] = false;
	    for(int i=19; i<31; i++){
			for(int j=35; j<51; j++)
				map[i][j] = true;
		}
		for(int i=7; i<15; i++){
			for(int j=15; j<23; j++)
				map[i][j] = true;
		}
		for(int i=34; i<46; i++){
			for(int j=19; j<27; j++)
				map[i][j] = true;
		}
		return map;
	}
	
	public static void main(String[] args){
		float robot_width = (float)15.8;
		boolean[][] map = createMap();
		Navigator robot = configureLejos(robot_width);
		Path path = executeFramework(robot_width,map);
		move(robot,path);
	}

	private static void move(Navigator robot, Path path) {
		List<Point> list = path.getRoute();
		for(int i=0; i<list.size(); i++){
			robot.addWaypoint(new Waypoint(list.get(i).getX(), list.get(i).getY()));
			System.out.println(list.get(i).getX()+","+list.get(i).getY());
		}
		robot.followPath();
		while(robot.isMoving());
	}

	private static Path executeFramework(float robot_width, boolean[][] map) {
		String path_planner = "Quadtree";
		String best_path = "Djikstra";
		float cell_width = robot_width/2;
	    int init_pos_x = 2, init_pos_y = 2;
	    int goal_pos_x = 40, goal_pos_y = 50;
	    boolean free_value_map = false;
	    boolean expad_obstacles = true;
		
	    Path path = null;
	    EmbeddedCommunication framework = new NXTCommunication("LENOVO-PC");
		try{
			framework.connect();
			framework.sendData(map,path_planner,best_path,robot_width,cell_width,init_pos_x,
				init_pos_y, goal_pos_x, goal_pos_y,free_value_map,expad_obstacles);
			path = framework.receivePath();
			framework.close();
		}catch(CommunicationException e){
	        LCD.drawString(e.getMessage(), 0, 0);			
		}
		return path;
	}

	private static Navigator configureLejos(float robot_width) {
		float wheelDiameter = (float)3;
		RegulatedMotor left_motor = Motor.C;
		RegulatedMotor right_motor = Motor.A;

		DifferentialPilot pilot = new DifferentialPilot(wheelDiameter, robot_width, left_motor, right_motor);
		OdometryPoseProvider pp = new OdometryPoseProvider(pilot);
		pilot.addMoveListener(pp);
		Navigator nav = new Navigator(pilot);
		return nav;
	}

}
