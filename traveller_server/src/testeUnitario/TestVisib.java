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

public class TestVisib {

	Map map;
	PathPlanner visib;
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
		visib = new VisibilityGraph(1,1);
		visib.setMap(map);
		graph = visib.resolution();
	}

	@After
	public void tearDown() throws Exception {
		graph.remove();
		graph = null;
		visib = null;
		map = null;
	}

	@Test
	public void testGraph(){
		graph.print();
		assertEquals((int)graph.getNumNodes(), 13);
	}
	
	@Test
	public void testNode66() {
		Node node = graph.getNode("6,6");
		assertEquals(node.getNumNeighbors(),4);
		assertEquals(node.getEdge(0).getDestiny().getInfo(),"3,7");
		assertEquals(node.getEdge(1).getDestiny().getInfo(),"7,4");
		assertEquals(node.getEdge(3).getDestiny().getInfo(),"6,5");
	}

	@Test
	public void testNode37() {
		Node node = graph.getNode("3,7");
		assertEquals(node.getNumNeighbors(),3);
		assertEquals(node.getEdge(2).getDestiny().getInfo(),"6,6");
	}

	@Test
	public void testNode31() {
		Node node = graph.getNode("3,1");
		assertEquals(node.getNumNeighbors(),6);
		assertEquals(node.getEdge(3).getDestiny().getInfo(),"3,7");
		assertEquals(node.getEdge(4).getDestiny().getInfo(),"7,1");
		assertEquals(node.getEdge(5).getDestiny().getInfo(),"6,1");
	}

	@Test
	public void testNode17() {
		Node node = graph.getNode("1,7");
		assertEquals(node.getNumNeighbors(),3);
		assertEquals(node.getEdge(1).getDestiny().getInfo(),"1,4");
		assertEquals(node.getEdge(2).getDestiny().getInfo(),"3,7");
	}

	@Test
	public void testNode14() {
		Node node = graph.getNode("1,4");
		assertEquals(node.getNumNeighbors(),5);
		assertEquals(node.getEdge(0).getDestiny().getInfo(),"0,2");
		assertEquals(node.getEdge(1).getDestiny().getInfo(),"1,2");
		assertEquals(node.getEdge(2).getDestiny().getInfo(),"1,7");
		assertEquals(node.getEdge(3).getDestiny().getInfo(),"3,1");
		assertEquals(node.getEdge(4).getDestiny().getInfo(),"7,4");
	}

	@Test
	public void testNode12() {
		Node node = graph.getNode("1,2");
		assertEquals(node.getNumNeighbors(),4);
		assertEquals(node.getEdge(0).getDestiny().getInfo(),"0,2");
		assertEquals(node.getEdge(1).getDestiny().getInfo(),"1,0");
		assertEquals(node.getEdge(2).getDestiny().getInfo(),"1,4");
		assertEquals(node.getEdge(3).getDestiny().getInfo(),"3,1");
	}

	@Test
	public void testNode10() {
		Node node = graph.getNode("1,0");
		assertEquals(node.getNumNeighbors(),5);
		assertEquals(node.getEdge(1).getDestiny().getInfo(),"1,2");
		assertEquals(node.getEdge(2).getDestiny().getInfo(),"3,1");
		assertEquals(node.getEdge(3).getDestiny().getInfo(),"7,1");
		assertEquals(node.getEdge(4).getDestiny().getInfo(),"6,1");
	}

	@Test
	public void testNode02() {
		Node node = graph.getNode("0,2");
		assertEquals(node.getNumNeighbors(),4);
		assertEquals(node.getEdge(0).getDestiny().getInfo(),"0,0");
		assertEquals(node.getEdge(1).getDestiny().getInfo(),"1,2");
		assertEquals(node.getEdge(2).getDestiny().getInfo(),"1,4");
		assertEquals(node.getEdge(3).getDestiny().getInfo(),"1,7");
	}

	@Test
	public void testNode00(){
		Node node = graph.getNode("0,0");
		assertEquals(node.getNumNeighbors(),2);
		assertEquals(node.getEdge(0).getDestiny().getInfo(),"0,2");
		assertEquals(node.getEdge(1).getDestiny().getInfo(),"1,0");
	}
}
