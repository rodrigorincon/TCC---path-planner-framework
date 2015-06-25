package testeUnitario;

import static org.junit.Assert.*;
import map.Map;
import org.junit.Before;
import org.junit.Test;

public class TestExpandObstacles {

	Map map1, map2, map3, map4;
	
	@Before
	public void setUp() throws Exception {
		boolean[][] mapa = {{false, false, false, false, false, false},
							{false, false, false, false, false, false},
							{false, false, true,  true,  false, false},
							{false, false, true,  true,  false, false},
							{false, false, false, false, false, false},
							{false, false, false, false, false, false}};
		map1 = new Map(mapa, false);
		map2 = new Map(mapa, false);
		map3 = new Map(mapa, false);
		map4 = new Map(mapa, false);
		map1.setObstaclesExpanded(true);
		map2.setObstaclesExpanded(true);
		map3.setObstaclesExpanded(true);
		map4.setObstaclesExpanded(true);
		map1.expandObstacles(1, 1);
		map2.expandObstacles(4, 2);
		map3.expandObstacles(1, 2);
		map4.expandObstacles(5, 2);
	}

	@Test
	public void testMap1(){
		assertEquals(map1.getFREE(), map1.getPosition(0, 0) );
		assertEquals(map1.getFREE(), map1.getPosition(0, 2) );
		assertEquals(map1.getFREE(), map1.getPosition(2, 0) );
		assertEquals(map1.getFREE(), map1.getPosition(5, 2) );
		assertEquals(map1.getFREE(), map1.getPosition(2, 5) );
		
		assertEquals(map1.getOCCUPIED(), map1.getPosition(1, 1) );
		assertEquals(map1.getOCCUPIED(), map1.getPosition(1, 2) );
		assertEquals(map1.getOCCUPIED(), map1.getPosition(1, 3) );
		assertEquals(map1.getOCCUPIED(), map1.getPosition(1, 4) );
		assertEquals(map1.getOCCUPIED(), map1.getPosition(2, 1) );
		assertEquals(map1.getOCCUPIED(), map1.getPosition(2, 4) );
		assertEquals(map1.getOCCUPIED(), map1.getPosition(3, 1) );
		assertEquals(map1.getOCCUPIED(), map1.getPosition(3, 4) );
		assertEquals(map1.getOCCUPIED(), map1.getPosition(4, 1) );
		assertEquals(map1.getOCCUPIED(), map1.getPosition(4, 2) );
		assertEquals(map1.getOCCUPIED(), map1.getPosition(4, 3) );
		assertEquals(map1.getOCCUPIED(), map1.getPosition(4, 4) );
	}
	
	@Test
	public void testMap2(){
		assertEquals(map2.getFREE(), map2.getPosition(0, 0) );
		assertEquals(map2.getFREE(), map2.getPosition(0, 2) );
		assertEquals(map2.getFREE(), map2.getPosition(2, 0) );
		assertEquals(map2.getFREE(), map2.getPosition(5, 2) );
		assertEquals(map2.getFREE(), map2.getPosition(2, 5) );
		
		assertEquals(map2.getOCCUPIED(), map2.getPosition(1, 1) );
		assertEquals(map2.getOCCUPIED(), map2.getPosition(1, 2) );
		assertEquals(map2.getOCCUPIED(), map2.getPosition(1, 3) );
		assertEquals(map2.getOCCUPIED(), map2.getPosition(1, 4) );
		assertEquals(map2.getOCCUPIED(), map2.getPosition(2, 1) );
		assertEquals(map2.getOCCUPIED(), map2.getPosition(2, 4) );
		assertEquals(map2.getOCCUPIED(), map2.getPosition(3, 1) );
		assertEquals(map2.getOCCUPIED(), map2.getPosition(3, 4) );
		assertEquals(map2.getOCCUPIED(), map2.getPosition(4, 1) );
		assertEquals(map2.getOCCUPIED(), map2.getPosition(4, 2) );
		assertEquals(map2.getOCCUPIED(), map2.getPosition(4, 3) );
		assertEquals(map2.getOCCUPIED(), map2.getPosition(4, 4) );
	}
	
	@Test
	public void testMap4(){
		assertEquals(map4.getOCCUPIED(), map4.getPosition(0, 0) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(0, 2) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(2, 0) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(5, 2) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(2, 5) );
		
		assertEquals(map4.getOCCUPIED(), map4.getPosition(1, 1) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(1, 2) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(1, 3) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(1, 4) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(2, 1) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(2, 4) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(3, 1) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(3, 4) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(4, 1) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(4, 2) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(4, 3) );
		assertEquals(map4.getOCCUPIED(), map4.getPosition(4, 4) );
	}
	
	@Test
	public void testMap3(){
		assertEquals(map3.getFREE(), map3.getPosition(0, 0) );
		assertEquals(map3.getFREE(), map3.getPosition(0, 2) );
		assertEquals(map3.getFREE(), map3.getPosition(2, 0) );
		assertEquals(map3.getFREE(), map3.getPosition(5, 2) );
		assertEquals(map3.getFREE(), map3.getPosition(2, 5) );
		
		assertEquals(map3.getOCCUPIED(), map3.getPosition(1, 1) );
		assertEquals(map3.getOCCUPIED(), map3.getPosition(1, 2) );
		assertEquals(map3.getOCCUPIED(), map3.getPosition(1, 3) );
		assertEquals(map3.getOCCUPIED(), map3.getPosition(1, 4) );
		assertEquals(map3.getOCCUPIED(), map3.getPosition(2, 1) );
		assertEquals(map3.getOCCUPIED(), map3.getPosition(2, 4) );
		assertEquals(map3.getOCCUPIED(), map3.getPosition(3, 1) );
		assertEquals(map3.getOCCUPIED(), map3.getPosition(3, 4) );
		assertEquals(map3.getOCCUPIED(), map3.getPosition(4, 1) );
		assertEquals(map3.getOCCUPIED(), map3.getPosition(4, 2) );
		assertEquals(map3.getOCCUPIED(), map3.getPosition(4, 3) );
		assertEquals(map3.getOCCUPIED(), map3.getPosition(4, 4) );
	}
	
}
