package testeIntegracao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.Path;
import controller.PathPlannerController;

public class TesteMRIT1 {

	float largura_robo = (float)2;
	int cell_width = 1;
	boolean[][] mapa = null;
	
	@Before
	public void setUp() throws Exception {
		mapa = new boolean[60][60];
		for(int i=0; i<60; i++){
			for(int j=0; j<60; j++)
				mapa[i][j] = false;
		}
		for(int i=19; i<=31; i++){
			for(int j=35; j<=51; j++)
				mapa[j][i] = true;
		}
		for(int i=7; i<=15; i++){
			for(int j=15; j<=23; j++)
				mapa[j][i] = true;
		}
		for(int i=34; i<=46; i++){
			for(int j=19; j<=27; j++)
				mapa[j][i] = true;
		}
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
		assertEquals(path.getRoute().size(), 14);
		assertEquals((int)path.getSize(), 68);		
	}
	
	@Test
	public void testeWavefront() {
		String path_planner = "Wavefront";
		Path path = executeFramework(path_planner);
		assertNotNull(path);
		assertEquals(path.getRoute().size(), 52);
		assertEquals((int)path.getSize(), 65);
	}
	
	@Test
	public void testeVisibilityGraph() {
		String path_planner = "VisibilityGraph";
		Path path = executeFramework(path_planner);
		assertNotNull(path);
		assertEquals(path.getRoute().size(), 4);
		assertEquals((int)path.getSize(), 61);
	}
	
	@Test
	public void testeVoronoi() {
		String path_planner = "Voronoi";
		Path path = executeFramework(path_planner);
		assertEquals(path,null);
	}
	
	private Path executeFramework(String path_planner){
		String best_path = "Djikstra";		
		PathPlannerController framework = new PathPlannerController(path_planner, best_path,largura_robo, cell_width);
		framework.defineMap(mapa, false, 2, 2, 40, 50);
		framework.expandObstacles(true);
		return framework.execute(largura_robo, cell_width);
	}
	
}
