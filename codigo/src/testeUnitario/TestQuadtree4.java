package testeUnitario;

import static org.junit.Assert.*;

import java.util.List;

import graph.Graph;

import map.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bestPath.BestPath;
import bestPath.Djikstra;

import pathPlanner.PathPlanner;
import pathPlanner.Quadtree;
import util.Point;

public class TestQuadtree4 {

	Map map;
	PathPlanner quadtree;
	Graph graph;
	
	@Before
	public void setUp() throws Exception {
		boolean[][] mapa = {{true,false,false,false,true,true},
						{false,false,false,true,true,true},
						{false,false,false,true,true,true},
						{false,false,false,true,true,true},
						{false,false,false,true,true,true},
						{false,false,false,true,true,true} };
		Map map = new Map(mapa, false);
		map.setInitialPoint(1, 5);
		map.setFinalPoint(3, 0);
		quadtree = new Quadtree(1,1);
		quadtree.setMap(map);
		graph = quadtree.resolution();
	}

	@After
	public void tearDown() throws Exception {
		graph.remove();
		graph = null;
		quadtree = null;
		map = null;
	}
	
	@Test
	public void testPath(){
		BestPath djikstra = new Djikstra();
		djikstra.setGraph(graph);
		List<Point> list = djikstra.definePath("5,1", "0,3").getRoute();
		assertEquals(list.size(), 4);
		assertEquals((int)list.get(0).getX(),1);
		assertEquals((int)list.get(0).getY(),5);
		assertEquals((int)list.get(1).getX(),1);
		assertEquals((int)list.get(1).getY(),4);
		assertEquals((int)list.get(2).getX(),2);
		assertEquals((int)list.get(2).getY(),2);
		assertEquals((int)list.get(3).getX(),3);
		assertEquals((int)list.get(3).getY(),0);
	}

	@Test
	public void test() {
		assertEquals(graph.getNumNodes(),6);
		assertTrue(graph.nodeAlreadyExists("0,2"));
		assertTrue(graph.nodeAlreadyExists("2,0"));
		assertTrue(graph.nodeAlreadyExists("0,3"));
		assertTrue(graph.nodeAlreadyExists("2,2"));
		assertTrue(graph.nodeAlreadyExists("4,1"));
		//initial and goal point
		assertTrue(graph.nodeAlreadyExists("5,1"));
	}

}
