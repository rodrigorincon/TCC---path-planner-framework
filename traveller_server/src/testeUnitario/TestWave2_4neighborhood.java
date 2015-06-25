package testeUnitario;

import static org.junit.Assert.*;
import java.util.List;
import map.Map;
import org.junit.Before;
import org.junit.Test;
import controller.PathPlannerController;
import util.Path;
import util.Point;

public class TestWave2_4neighborhood {

	Map map;
	String wavefront;
	Path path;
	
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
		wavefront = "Wavefront";
		String best_path = "Djikstra";		
		PathPlannerController framework = new PathPlannerController(wavefront, best_path,1, 1);
		framework.defineMap(mapa, false, 1, 6, 5, 6);
		path = framework.execute(1,1);
	}

	@Test
	public void testPath(){
		assertEquals((int)path.getSize(), 8);
		List<Point> list = path.getRoute();
		assertEquals((int)list.get(0).getX(), 1);
		assertEquals((int)list.get(0).getY(), 6);
		assertEquals((int)list.get(1).getX(), 1);
		assertEquals((int)list.get(1).getY(), 7);
		assertEquals((int)list.get(2).getX(), 1);
		assertEquals((int)list.get(2).getY(), 8);
		assertEquals((int)list.get(3).getX(), 2);
		assertEquals((int)list.get(3).getY(), 8);
		assertEquals((int)list.get(4).getX(), 3);
		assertEquals((int)list.get(4).getY(), 8);
		assertEquals((int)list.get(5).getX(), 4);
		assertEquals((int)list.get(5).getY(), 8);
		assertEquals((int)list.get(6).getX(), 4);
		assertEquals((int)list.get(6).getY(), 7);
		assertEquals((int)list.get(7).getX(), 4);
		assertEquals((int)list.get(7).getY(), 6);
	}

}
