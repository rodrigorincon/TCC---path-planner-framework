package testeUnitario;

import static org.junit.Assert.*;

import java.util.List;

import graph.Graph;

import map.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bestPath.AStar;
import bestPath.BestPath;
import bestPath.Djikstra;

import pathPlanner.PathPlanner;
import pathPlanner.Quadtree;
import util.Path;
import util.Point;

public class TestQuadtree {

	Map map;
	PathPlanner quadtree;
	Graph graph;
	
	@Before
	public void setUp() throws Exception {
		boolean[][] mapa = {{true,true,false,false,false,false,false,false},
				{false,false,false,false,false,false,false,false},
				{false,false,false,false,false,true,true,false},
				{false,false,false,false,false,false,false,false},
				{false,false,true,true,true,true,false,false},
				{false,false,true,true,true,true,false,false},
				{false,false,true,true,false,false,false,false},
				{false,false,false,false,false,false,false,false}	};
		map = new Map(mapa, false);
		map.setInitialPoint(1, 6);
		map.setFinalPoint(5, 6);
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
		List<Point> list = djikstra.definePath("6,1", "6,5").getRoute();
		assertEquals(list.size(), 5);		
		assertEquals((int)list.get(0).getX(),1);
		assertEquals((int)list.get(0).getY(),6);
		assertEquals((int)list.get(1).getX(),2);
		assertEquals((int)list.get(1).getY(),7);
		assertEquals((int)list.get(2).getX(),3);
		assertEquals((int)list.get(2).getY(),7);
		assertEquals((int)list.get(3).getX(),5);
		assertEquals((int)list.get(3).getY(),7);
		assertEquals((int)list.get(4).getX(),5);
		assertEquals((int)list.get(4).getY(),6);
	}
	
	@Test
	public void testeAStar(){
		BestPath astar = new AStar();
		astar.setGraph(graph);
		Path path = astar.definePath("6,1", "6,5");
		assertEquals((int)path.getSize(), 5);
		List<Point> list = path.getRoute();
		assertEquals(list.size(), 5);		
		assertEquals((int)list.get(0).getX(),1);
		assertEquals((int)list.get(0).getY(),6);
		assertEquals((int)list.get(1).getX(),2);
		assertEquals((int)list.get(1).getY(),7);
		assertEquals((int)list.get(2).getX(),3);
		assertEquals((int)list.get(2).getY(),7);
		assertEquals((int)list.get(3).getX(),5);
		assertEquals((int)list.get(3).getY(),7);
		assertEquals((int)list.get(4).getX(),5);
		assertEquals((int)list.get(4).getY(),6);
	}
	
	@Test
	public void test() {
		assertEquals(graph.getNumNodes(),22);
		assertTrue(graph.nodeAlreadyExists("1,3"));
		assertTrue(graph.nodeAlreadyExists("3,3"));
		assertTrue(graph.nodeAlreadyExists("3,1"));
		assertTrue(graph.nodeAlreadyExists("1,0"));
		assertTrue(graph.nodeAlreadyExists("1,1"));
		assertTrue(graph.nodeAlreadyExists("1,5"));
		assertTrue(graph.nodeAlreadyExists("1,7"));
		assertTrue(graph.nodeAlreadyExists("2,4"));
		assertTrue(graph.nodeAlreadyExists("3,4"));
		assertTrue(graph.nodeAlreadyExists("3,5"));
		assertTrue(graph.nodeAlreadyExists("2,7"));
		assertTrue(graph.nodeAlreadyExists("3,6"));
		assertTrue(graph.nodeAlreadyExists("3,7"));
		assertTrue(graph.nodeAlreadyExists("5,1"));
		assertTrue(graph.nodeAlreadyExists("7,1"));
		assertTrue(graph.nodeAlreadyExists("7,2"));		
		assertTrue(graph.nodeAlreadyExists("7,3"));
		assertTrue(graph.nodeAlreadyExists("7,5"));
		assertTrue(graph.nodeAlreadyExists("7,7"));
		assertTrue(graph.nodeAlreadyExists("5,7"));
		//initial and goal points
		assertTrue(graph.nodeAlreadyExists("6,1"));
		assertTrue(graph.nodeAlreadyExists("6,5"));
	}

}
