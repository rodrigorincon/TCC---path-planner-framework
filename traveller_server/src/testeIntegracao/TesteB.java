package testeIntegracao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.Path;
import controller.PathPlannerController;

public class TesteB {

	float largura_robo = (float)14.8;
	int cell_width = 1;
	boolean[][] mapa = null;
	
	@Before
	public void setUp() throws Exception {
		mapa = new boolean[200][150];
		for(int i=0; i<200; i++){
			for(int j=0; j<150; j++)
				mapa[i][j] = false;
		}
		for(int linha=35; linha<=193; linha++)
			for(int col=50; col<=60; col++)
				mapa[linha][col] = true;
		for(int linha=35; linha<=45; linha++)
			for(int col=70; col<=80; col++)
				mapa[linha][col] = true;
		for(int linha=135; linha<=145; linha++)
			for(int col=70; col<=80; col++)
				mapa[linha][col] = true;
		for(int linha=85; linha<=95; linha++)
			for(int col=115; col<=125; col++)
				mapa[linha][col] = true;
	}
	
	@After
	public void tearDown(){
		mapa = null;
	}
	
	@Test
	public void testeQuadtree() {
		String path_planner = "Quadtree";
		Path path = executeFramework(path_planner);
		assertNotNull(path);
		assertEquals(path.getRoute().size(), 13);
		assertEquals((int)path.getSize(), 298);
	}
	
	
	@Test
	public void testeVisibilityGraph() {
		String path_planner = "VisibilityGraph";
		Path path = executeFramework(path_planner);
		assertNotNull(path);
		assertEquals(path.getRoute().size(), 6);
		assertEquals((int)path.getSize(), 210);
	}
		
	private Path executeFramework(String path_planner){
		String best_path = "Djikstra";		
		PathPlannerController framework = new PathPlannerController(path_planner, best_path,largura_robo, cell_width);
		framework.defineMap(mapa, false, 20, 20, 75, 163);
		framework.expandObstacles(true);
		return framework.execute(largura_robo, cell_width);
	}
	
}
