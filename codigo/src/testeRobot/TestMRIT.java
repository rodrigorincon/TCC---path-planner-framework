package testeRobot;

import java.io.IOException;
import java.util.LinkedList;
import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import bestPath.BestPath;
import bestPath.Djikstra;
import controller.PathPlannerController;
import pathPlanner.PathPlanner;
import pathPlanner.Quadtree;
import util.Path;
import util.Point;

public class TestMRIT {

	public static void main(String[] args) throws IOException{	
		float largura_robo = (float)2;
		Navigator robot = configurarLejos(largura_robo);
		LinkedList<Point> lista = executeFramework(largura_robo);
		mover(robot,lista);
	}
	
	public static Navigator configurarLejos(float largua_robo) throws IOException{		
		//TODO medida do diametro e distancia entre os centros das rodas, em CM
		float wheelDiameter = (float) 4.3;
		//TODO verificar os motores		
		RegulatedMotor left_motor = Motor.C;
		RegulatedMotor right_motor = Motor.B;

		DifferentialPilot pilot = new DifferentialPilot(wheelDiameter, largua_robo, left_motor, right_motor);
		OdometryPoseProvider pp = new OdometryPoseProvider(pilot);
		pilot.addMoveListener(pp);
		Navigator nav = new Navigator(pilot);
		return nav;
	}
	
	public static LinkedList<Point> executeFramework(float largura_robo){
		int sizeMat = 60;
		boolean[][] mapa = new boolean[sizeMat][sizeMat];
		for(int i=0; i<sizeMat; i++){
			for(int j=0; j<sizeMat; j++){
				mapa[i][j] = false;
			}
		}
		for(int x=17; x<=32; x++){
			for(int y=38; y<=52; y++)
				mapa[sizeMat-x][sizeMat-y] = true;
		}
		for(int x=33; x<=47; x++){
			for(int y=18; y<=22; y++)
				mapa[sizeMat-x][sizeMat-y] = true;
		}
/*		for(int x=5; x<=18; x++){
			for(int y=12; y<=26; y++)
				mapa[sizeMat-x][sizeMat-y] = true;
		}*/
		PathPlanner path_planner = new Quadtree(largura_robo, largura_robo);
		BestPath best_path = new Djikstra();
		
		PathPlannerController framework = new PathPlannerController(path_planner, best_path);
		framework.defineMap(mapa, false, 2, 2, 40, 50);
		Path path = framework.execute(largura_robo, largura_robo);
		return (LinkedList<Point>) path.getRoute();
	}
	
	public static void mover(Navigator robot, LinkedList<Point> lista){
		for(int i=0; i<lista.size(); i++)
			robot.addWaypoint(new Waypoint(lista.get(i).getX(), lista.get(i).getY()));

		robot.followPath();
		while(robot.isMoving());
		
		// 13.8, 82.8 -> 27.6, 96.6 -> 41.4, 96.6 -> 69, 96.6 -> 69, 82.8
	}

}
