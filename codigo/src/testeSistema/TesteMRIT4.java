package testeSistema;

import java.util.List;

import pathPlanner.PathPlanner;
import pathPlanner.Quadtree;
import pathPlanner.WaveFront;
import util.Path;
import util.Point;
import bestPath.BestPath;
import bestPath.Djikstra;
import controller.PathPlannerController;

public class TesteMRIT4 {

	static float largura_robo = (float)2;
	static int cell_width = 1;
	
	static void teste(PathPlanner path_planner){
		boolean[][] mapa = new boolean[60][60];
		for(int i=0; i<60; i++){
			for(int j=0; j<60; j++)
				mapa[i][j] = false;
		}
		for(int i=19; i<30; i++){
			for(int j=33; j<51; j++)
				mapa[i][j] = true;
		}
		for(int i=32; i<46; i++){
			for(int j=19; j<27; j++)
				mapa[i][j] = true;
		}
		for(int i=46; i<55; i++){
			for(int j=35; j<57; j++)
				mapa[i][j] = true;
		}
		
		BestPath best_path = new Djikstra();		
		PathPlannerController framework = new PathPlannerController(path_planner, best_path);
		framework.defineMap(mapa, false, 39, 12, 39, 32);
		framework.expandObstacles(true);
		Path path = framework.execute(largura_robo, cell_width);
		if(path != null){
			System.out.println("tamanho percorrido: "+path.getSize());
			List<Point> l = path.getRoute();
			for(int i=0; i<l.size(); i++)
				System.out.println(l.get(i).getY()+","+l.get(i).getX());
		}else
			System.out.println("Não foi possivel encontrar o caminho");
	}
	
	public static void main(String[] args) {
		System.out.println("------QUADTREE-----");
		PathPlanner path_planner = new Quadtree(largura_robo, cell_width);
		teste(path_planner);
		System.out.println("------WAVEFRONT-----");
		path_planner = new WaveFront();
		teste(path_planner);
	}

}
