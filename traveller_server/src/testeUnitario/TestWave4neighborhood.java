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
import pathPlanner.WaveFront;
import util.Point;

public class TestWave4neighborhood {

	Map map;
	PathPlanner wavefront;
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
		map.setInitialPoint(1, 5);
		map.setFinalPoint(5, 6);
		wavefront = new WaveFront();
		wavefront.setMap(map);
		graph = wavefront.resolution();
	}

	@After
	public void tearDown() throws Exception {
		graph.remove();
		graph = null;
		wavefront = null;
		map = null;
	}

	@Test
	public void testPath(){
		BestPath djikstra = new Djikstra();
		djikstra.setGraph(graph);
		List<Point> list = djikstra.definePath("5,1", "6,5").getRoute();
		assertEquals(list.size(), 8);
		assertEquals((int)list.get(0).getX(),1);
		assertEquals((int)list.get(0).getY(),5);
		assertEquals((int)list.get(1).getX(),1);
		assertEquals((int)list.get(1).getY(),6);
		assertEquals((int)list.get(2).getX(),1);
		assertEquals((int)list.get(2).getY(),7);
		assertEquals((int)list.get(3).getX(),2);
		assertEquals((int)list.get(3).getY(),7);
		assertEquals((int)list.get(4).getX(),3);
		assertEquals((int)list.get(4).getY(),7);
		assertEquals((int)list.get(5).getX(),4);
		assertEquals((int)list.get(5).getY(),7);
		assertEquals((int)list.get(6).getX(),4);
		assertEquals((int)list.get(6).getY(),6);
		assertEquals((int)list.get(7).getX(),5);
		assertEquals((int)list.get(7).getY(),6);
	}
	
	@Test
	public void testeGraph(){
		assertEquals(graph.getNumNodes(), 9);
		assertEquals(graph.getNode(0).getInfo(),"5,1");
		assertEquals(graph.getNode(1).getInfo(),"6,1");
		assertEquals(graph.getNode(2).getInfo(),"7,1");
		assertEquals(graph.getNode(3).getInfo(),"7,2");
		assertEquals(graph.getNode(4).getInfo(),"7,3");
		assertEquals(graph.getNode(5).getInfo(),"7,4");
		assertEquals(graph.getNode(6).getInfo(),"6,4");
		assertEquals(graph.getNode(7).getInfo(),"7,5");
		assertEquals(graph.getNode(8).getInfo(),"6,5");
	}
	
	@Test
	public void testMap() {
		assertEquals(map.getPosition(0,0),1);
		assertEquals(map.getPosition(0,1),1);
		assertEquals(map.getPosition(0,2),13);
		assertEquals(map.getPosition(0,3),12);
		assertEquals(map.getPosition(0,4),11);
		assertEquals(map.getPosition(0,5),12);
		assertEquals(map.getPosition(0,6),11);
		assertEquals(map.getPosition(0,7),10);
		assertEquals(map.getPosition(1,0),14);
		assertEquals(map.getPosition(1,1),13);
		assertEquals(map.getPosition(1,2),12);
		assertEquals(map.getPosition(1,3),11);
		assertEquals(map.getPosition(1,4),10);
		assertEquals(map.getPosition(1,5),11);
		assertEquals(map.getPosition(1,6),10);
		assertEquals(map.getPosition(1,7),9);
		assertEquals(map.getPosition(2,0),13);
		assertEquals(map.getPosition(2,1),12);
		assertEquals(map.getPosition(2,2),11);
		assertEquals(map.getPosition(2,3),10);
		assertEquals(map.getPosition(2,4),9);
		assertEquals(map.getPosition(2,5),1);
		assertEquals(map.getPosition(2,6),1);
		assertEquals(map.getPosition(2,7),8);
		assertEquals(map.getPosition(3,0),12);
		assertEquals(map.getPosition(3,1),11);
		assertEquals(map.getPosition(3,2),10);
		assertEquals(map.getPosition(3,3),9);
		assertEquals(map.getPosition(3,4),8);
		assertEquals(map.getPosition(3,5),7);
		assertEquals(map.getPosition(3,6),6);
		assertEquals(map.getPosition(3,7),7);
		assertEquals(map.getPosition(4,0),11);
		assertEquals(map.getPosition(4,1),10);
		assertEquals(map.getPosition(4,2),1);
		assertEquals(map.getPosition(4,3),1);
		assertEquals(map.getPosition(4,4),1);
		assertEquals(map.getPosition(4,5),1);
		assertEquals(map.getPosition(4,6),5);
		assertEquals(map.getPosition(4,7),6);
		assertEquals(map.getPosition(5,0),10);
		assertEquals(map.getPosition(5,1),9);
		assertEquals(map.getPosition(5,2),1);
		assertEquals(map.getPosition(5,3),1);
		assertEquals(map.getPosition(5,4),1);
		assertEquals(map.getPosition(5,5),1);
		assertEquals(map.getPosition(5,6),4);
		assertEquals(map.getPosition(5,7),5);
		assertEquals(map.getPosition(6,0),9);
		assertEquals(map.getPosition(6,1),8);
		assertEquals(map.getPosition(6,2),1);
		assertEquals(map.getPosition(6,3),1);
		assertEquals(map.getPosition(6,4),3);
		assertEquals(map.getPosition(6,5),2);
		assertEquals(map.getPosition(6,6),3);
		assertEquals(map.getPosition(6,7),4);
		assertEquals(map.getPosition(7,0),8);
		assertEquals(map.getPosition(7,1),7);
		assertEquals(map.getPosition(7,2),6);
		assertEquals(map.getPosition(7,3),5);
		assertEquals(map.getPosition(7,4),4);
		assertEquals(map.getPosition(7,5),3);
		assertEquals(map.getPosition(7,6),4);
		assertEquals(map.getPosition(7,7),5);
	}

}
