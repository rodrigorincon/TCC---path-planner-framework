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

public class TestQuadtree3 {

	Map map;
	PathPlanner quadtree;
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
		List<Point> list = djikstra.definePath("3,0", "2,6").getRoute();
		assertEquals(list.size(), 6);
		assertEquals((int)list.get(0).getX(),0);
		assertEquals((int)list.get(0).getY(),3);
		assertEquals((int)list.get(1).getX(),0);
		assertEquals((int)list.get(1).getY(),2);
		assertEquals((int)list.get(2).getX(),2);
		assertEquals((int)list.get(2).getY(),1);
		assertEquals((int)list.get(3).getX(),5);
		assertEquals((int)list.get(3).getY(),0);
		assertEquals((int)list.get(4).getX(),7);
		assertEquals((int)list.get(4).getY(),1);
		assertEquals((int)list.get(5).getX(),6);
		assertEquals((int)list.get(5).getY(),2);
	}

	@Test
	public void test() {
		assertEquals(graph.getNumNodes(),9);
		assertTrue(graph.nodeAlreadyExists("1,2"));
		assertTrue(graph.nodeAlreadyExists("2,0"));
		assertTrue(graph.nodeAlreadyExists("3,0"));
		assertTrue(graph.nodeAlreadyExists("0,5"));
		assertTrue(graph.nodeAlreadyExists("0,7"));
		assertTrue(graph.nodeAlreadyExists("1,7"));
		assertTrue(graph.nodeAlreadyExists("2,7"));
		assertTrue(graph.nodeAlreadyExists("3,6"));
		//initial and goal points
		assertTrue(graph.nodeAlreadyExists("2,6"));
	}

}
