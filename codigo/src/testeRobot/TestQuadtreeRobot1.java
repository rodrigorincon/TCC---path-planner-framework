package testeRobot;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import graph.Graph;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.util.PilotProps;
import map.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.PathPlannerController;
import bestPath.BestPath;
import bestPath.Djikstra;
import pathPlanner.PathPlanner;
import pathPlanner.Quadtree;
import util.Path;
import util.Point;

public class TestQuadtreeRobot1 {
	
	public static void main(String[] args) throws IOException{	
		int largura_robo = 18;
		Navigator robot = configurarLejos(largura_robo);
		LinkedList<Point> lista = executeFramework(largura_robo);
		mover(robot,lista);
	}
	
	public static Navigator configurarLejos(int largua_robo) throws IOException{		
		//TODO medida do diametro e distancia entre os centros das rodas, em CM
		float wheelDiameter = 4;
		//TODO verificar os motores		
		RegulatedMotor left_motor = Motor.B;
		RegulatedMotor right_motor = Motor.C;

		DifferentialPilot pilot = new DifferentialPilot(wheelDiameter, largua_robo, left_motor, right_motor);
		OdometryPoseProvider pp = new OdometryPoseProvider(pilot);
		pilot.addMoveListener(pp);
		Navigator nav = new Navigator(pilot);
		return nav;
	}
	
	public static LinkedList<Point> executeFramework(int largura_robo){
		boolean[][] mapa = {{true,  true,  false, false, false, false, false, false},
				            {false, false, false, false, false, false, false, false},
				            {false, false, false, false, false, true,  true,  false},
				            {false, false, false, false, false, false, false, false},
				            {false, false, true,  true,  true,  true,  false, false},
				            {false, false, true,  true,  true,  true,  false, false},
				            {false, false, true,  true,  false, false, false, false},
				            {false, false, false, false, false, false, false, false}	};
		PathPlanner path_planner = new Quadtree(largura_robo, largura_robo);
		BestPath best_path = new Djikstra();
		
		PathPlannerController framework = new PathPlannerController(path_planner, best_path);
		framework.defineMap(mapa, false, 1, 6, 5, 6);
		Path path = framework.execute(largura_robo, largura_robo);
		return path.getRoute();
	}
	
	public static void mover(Navigator robot, LinkedList<Point> lista){
		for(int i=0; i<lista.size(); i++)
			robot.addWaypoint(new Waypoint(lista.get(i).getX(), lista.get(i).getY()));

		robot.followPath();
		while(robot.isMoving()) {}
		// 1,6 -> 2,7 -> 3,7 -> 5,7 -> 5,6
	}
		
}
