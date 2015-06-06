package testeSistema;

import pathPlanner.PathPlanner;
import pathPlanner.Quadtree;
import pathPlanner.WaveFront;
import util.Path;
import bestPath.BestPath;
import bestPath.Djikstra;
import controller.PathPlannerController;

public class TesteMRIT3 {

	static float largura_robo = (float)2;
	static int cell_width = 1;
	
	static void teste(PathPlanner path_planner){
		boolean[][] mapa = new boolean[60][60];
		for(int i=0; i<60; i++){
			for(int j=0; j<60; j++)
				mapa[i][j] = false;
		}
		for(int i=34; i<46; i++){
			for(int j=19; j<27; j++)
				mapa[i][j] = true;
		}
		for(int i=46; i<55; i++){
			for(int j=33; j<57; j++)
				mapa[i][j] = true;
		}
		for(int i=19; i<31; i++){
			for(int j=35; j<51; j++)
				mapa[i][j] = true;
		}
		for(int i=2; i<12; i++){
			for(int j=43; j<55; j++)
				mapa[i][j] = true;
		}
		for(int i=7; i<16; i++){
			for(int j=7; j<29; j++)
				mapa[i][j] = true;
		}
		
		BestPath best_path = new Djikstra();		
		PathPlannerController framework = new PathPlannerController(path_planner, best_path);
		framework.defineMap(mapa, false, 52, 24, 5, 20);
		framework.expandObstacles(true);
		Path path = framework.execute(largura_robo, cell_width);
		if(path != null)
			System.out.println("tamanho percorrido: "+path.getSize());
		else
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
