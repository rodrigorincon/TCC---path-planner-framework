package testeUnitario;

import static org.junit.Assert.*;
import graph.Graph;
import graph.Node;
import map.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pathPlanner.PathPlanner;
import pathPlanner.VisibilityGraph;

public class TestVisib2 {

	Map map1;
	Graph graph;
	PathPlanner visib;
	
	@Before
	public void setUp() throws Exception {
		boolean[][] mapa = {{false, false, false, false, false, false, false, false, false},
							{false, false, false, false, false, false, false, false, false},
							{false, true,  true,  false, false, false, false, false, false},
							{false, true,  true,  true, false, false, false, false, false},
							{false, true,  true,  true, true,  false, false, false, false},
							{false, true,  true,  true, false, false, false, false, false},
							{false, true,  true,  false, false, false, false, false, false},	
							{false, false, false, false, false, false, false, false, false},
							{false, false, false, false, false, false, false, false, false} };
		map1 = new Map(mapa, false);
		visib = new VisibilityGraph(1, 1);
		visib.setMap(map1);
		graph = visib.resolution();
	}
	
	@After
	public void tearDown(){
		map1.cleanUp();
		map1 = null;
		graph = null;
	}

	@Test
	public void testGetObstacleVertices1(){
		assertEquals((map1.NeighborVerticesOfSameObstacle(2, 2, 2, 6)),false);
		Node node = graph.getNode("1,3");
		Node node2 = graph.getNode("7,3");
		assertEquals(node.edgeAlreadyExists(node2),false);
	}

}
