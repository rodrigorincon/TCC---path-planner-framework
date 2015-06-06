package testeRobot;

import java.io.IOException;
import java.util.LinkedList;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import controller.PathPlannerController;
import bestPath.BestPath;
import bestPath.Djikstra;
import pathPlanner.PathPlanner;
import pathPlanner.Quadtree;
import util.Path;
import util.Point;

public class ExtressQuadRobot {
	
	//DA EXCEÇÃO DE FALTA DE MEMÓRIA
	static OdometryPoseProvider pp;
	
	public static void main(String[] args) throws IOException{	
		float largura_robo = (float)13.8;
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
		pp = new OdometryPoseProvider(pilot);
		pilot.addMoveListener(pp);
		Navigator nav = new Navigator(pilot);
		return nav;
	}
	
	public static LinkedList<Point> executeFramework(float largura_robo){
		int tamanhoMatriz = 20;
		boolean[][] mapa = new boolean[tamanhoMatriz][tamanhoMatriz];
		for(int i=0; i<tamanhoMatriz; i++){
			for(int j=0; j<tamanhoMatriz; j++){
				if(i%3==2)
					mapa[i][j] = false;
				else if(j%3!=2)
					mapa[i][j] = true;
				else
					mapa[i][j] = false;
			}
		}
		PathPlanner path_planner = new Quadtree(largura_robo, largura_robo);
		BestPath best_path = new Djikstra();
		
		PathPlannerController framework = new PathPlannerController(path_planner, best_path);
		framework.defineMap(mapa, false, 1, 6, 5, 6);
		Path path = framework.execute(largura_robo, largura_robo);
		return (LinkedList<Point>) path.getRoute();
	}
	
	public static void mover(Navigator robot, LinkedList<Point> lista){
		for(int i=0; i<lista.size(); i++)
			robot.addWaypoint(new Waypoint(lista.get(i).getX(), lista.get(i).getY()));

		LinkedList<Waypoint> listaPercorrida = new LinkedList<Waypoint>();
		Waypoint anterior = null;
		robot.followPath();
		while(robot.isMoving()) {
			Waypoint wp = robot.getWaypoint();
			if( anterior !=wp ){
				anterior = wp;
				listaPercorrida.add(wp);
			}
		}
		
		LCD.clear();
		for(int i=0; i<listaPercorrida.size(); i++){
			Waypoint val = listaPercorrida.get(i);
			System.out.println(val.getX()+","+val.getY());
		}
		// 13.8, 82.8 -> 27.6, 96.6 -> 41.4, 96.6 -> 69, 96.6 -> 69, 82.8
		Button.waitForAnyPress();
	}
		
}
