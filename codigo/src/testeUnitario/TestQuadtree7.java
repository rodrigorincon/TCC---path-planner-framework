package testeUnitario;

import static org.junit.Assert.*;
import graph.Graph;

import map.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pathPlanner.Quadtree;

public class TestQuadtree7 {

	Map map;
	Quadtree quadtree;
	Graph graph;
	
	@Before
	public void setUp() throws Exception {
		boolean[][] mapa = {{false,false,false,false,false,false,false,false,false},
						{false,false,false,false,true,true,false,false,false},
						{false,true,true,true,true,true,false,false,false},
						{false,true,true,true,true,true,false,true,true} };
		Map map = new Map(mapa, false);
		map.setInitialPoint(0, 3);
		map.setFinalPoint(6, 2);
		quadtree = new Quadtree(2,1);
		quadtree.setMap(map);
	}

	@After
	public void tearDown() throws Exception {
		graph.remove();
		graph = null;
		quadtree = null;
		map = null;
	}

	@Test
	public void test() {
		graph = quadtree.resolution();
		assertEquals(graph.getNumNodes(),4);
		assertTrue(graph.nodeAlreadyExists("1,2"));
		assertTrue(graph.nodeAlreadyExists("1,7"));
		//initial and goal point
		assertTrue(graph.nodeAlreadyExists("3,0"));
		assertTrue(graph.nodeAlreadyExists("2,6"));
	}

}
