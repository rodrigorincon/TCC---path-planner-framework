package testeIntegracao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Path;
import controller.PathPlannerController;

public class TesteMRIT3 {

	float largura_robo = (float)2;
	int cell_width = 1;
	boolean[][] mapa = null;
	
	@Before
	public void setUp() throws Exception {
		mapa = new boolean[60][60];
		for(int i=0; i<60; i++){
			for(int j=0; j<60; j++)
				mapa[j][i] = false;
		}
		for(int i=34; i<=46; i++){
			for(int j=19; j<=27; j++)
				mapa[j][i] = true;
		}
		for(int i=46; i<=55; i++){
			for(int j=33; j<=57; j++)
				mapa[j][i] = true;
		}
		for(int i=19; i<=31; i++){
			for(int j=35; j<=51; j++)
				mapa[j][i] = true;
		}
		for(int i=2; i<=12; i++){
			for(int j=43; j<=55; j++)
				mapa[j][i] = true;
		}
		for(int i=7; i<=16; i++){
			for(int j=7; j<=29; j++)
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
		assertEquals(path.getRoute().size(), 18);
		assertEquals((int)path.getSize(), 77);
	}
	
	@Test
	public void testeWavefront() {
		String path_planner = "Wavefront";
		Path path = executeFramework(path_planner);
		assertNotNull(path);
		assertEquals(path.getRoute().size(), 58);
		assertEquals((int)path.getSize(), 60);
	}
	
	@Test
	public void testeVisibilityGraph() {
		String path_planner = "VisibilityGraph";
		Path path = executeFramework(path_planner);
		assertNotNull(path);
		assertEquals(path.getRoute().size(), 5);
		assertEquals((int)path.getSize(), 57);
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
		framework.defineMap(mapa, false, 52, 24, 5, 20);
		framework.expandObstacles(true);
		return framework.execute(largura_robo, cell_width);
	}
}
