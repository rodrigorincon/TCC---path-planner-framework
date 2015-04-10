package teste;

import static org.junit.Assert.*;
import graph.Graph;

import map.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pathPlanner.Quadtree;

public class TestQuadtree6 {

	Map map;
	Quadtree quadtree;
	Graph graph;
	
	@Before
	public void setUp() throws Exception {
		boolean[][] mapa = {{true,true,false,false,false,false,false,false,false},
						{false,true,false,false,false,false,false,true,false},
						{false,false,false,false,false,true,true,true,true},
						{false,false,false,false,false,false,true,true,true},
						{false,false,true,true,true,true,false,false,false},
						{false,false,true,true,true,true,false,false,false},
						{false,false,true,true,false,false,false,false,false},
						{false,false,true,true,false,false,false,true,true},
						{false,false,false,false,false,false,false,true,true} };
		Map map = new Map(mapa, false);
		map.setInitialPoint(1, 6);
		map.setFinalPoint(5, 6);
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
		graph.print();
		assertEquals(graph.getNumNodes(),10);
		assertTrue(graph.nodeAlreadyExists("1,3"));
		assertTrue(graph.nodeAlreadyExists("3,1"));
		assertTrue(graph.nodeAlreadyExists("3,3"));
		assertTrue(graph.nodeAlreadyExists("1,5"));
		assertTrue(graph.nodeAlreadyExists("7,1"));
		assertTrue(graph.nodeAlreadyExists("5,1"));		
		assertTrue(graph.nodeAlreadyExists("5,7"));
		assertTrue(graph.nodeAlreadyExists("7,5"));
		//initial and goal points
		assertTrue(graph.nodeAlreadyExists("6,1"));
		assertTrue(graph.nodeAlreadyExists("6,5"));
	}
}
