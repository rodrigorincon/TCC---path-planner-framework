package testeUnitario;

import static org.junit.Assert.assertEquals;
import map.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMap {

	Map map1;
	
	@Before
	public void setUp() throws Exception {
		boolean[][] mapa = {{true,  true,  false, false, false, false, false, false, true},
							{true,  true,  false, false, false, false, false, false, false},
							{false, false, false, false, false, true,  true,  true,  false},
							{false, false, false, false, false, false, false, false, false},
							{false, false, false, true,  false, false, false, false, false},
							{false, false, true,  true,  true,  false, false, false, false},
							{false, true,  true,  true,  true,  true,  false, false, false},
							{false, false, false, false, false, false, false, false, false},
							{false, false, false, false, false, false, false, false, false},
							{false, false, true,  true,  true,  true,  false, false, false},
							{false, false, true,  true,  true,  true,  false, false, false},
							{false, false, true,  true,  false, false, false, false, false},
							{false, false, false, false, false, false, false, false, false} };
		map1 = new Map(mapa, false);
		map1.setObstaclesExpanded(true);
	}
	
	@After
	public void tearDown(){
		map1.cleanUp();
		map1 = null;
	}

	@Test
	public void testExpand1(){
		map1.expandObstacles(1, 1);
		testExpand1Cell();
	}
	
	@Test
	public void testExpand2(){
		map1.expandObstacles(2, 1);
		testExpand1Cell();
	}
	
	@Test
	public void testExpand3(){
		map1.expandObstacles(2, 3);
		testExpand1Cell();
	}
	
	@Test
	public void testExpand4(){
		map1.expandObstacles(3, 1);
		testExpand2Cell();
	}
	
	private void testExpand2Cell() {
		assertEquals(map1.getPosition(2,0), map1.getOCCUPIED());
		assertEquals(map1.getPosition(2,2), map1.getOCCUPIED());
		assertEquals(map1.getPosition(0,2), map1.getOCCUPIED());
		assertEquals(map1.getPosition(2,3), map1.getOCCUPIED());
		assertEquals(map1.getPosition(2,4), map1.getOCCUPIED());
		assertEquals(map1.getPosition(1,4), map1.getOCCUPIED());
		assertEquals(map1.getPosition(3,4), map1.getOCCUPIED());
		assertEquals(map1.getPosition(1,6), map1.getOCCUPIED());
		assertEquals(map1.getPosition(1,8), map1.getOCCUPIED());
		assertEquals(map1.getPosition(3,7), map1.getOCCUPIED());
		assertEquals(map1.getPosition(3,3), map1.getOCCUPIED());
		assertEquals(map1.getPosition(3,2), map1.getOCCUPIED());
		assertEquals(map1.getPosition(4,4), map1.getOCCUPIED());
		assertEquals(map1.getPosition(4,2), map1.getOCCUPIED());
		assertEquals(map1.getPosition(4,1), map1.getOCCUPIED());
		assertEquals(map1.getPosition(5,1), map1.getOCCUPIED());
		assertEquals(map1.getPosition(5,0), map1.getOCCUPIED());
		assertEquals(map1.getPosition(6,0), map1.getOCCUPIED());
		assertEquals(map1.getPosition(7,0), map1.getOCCUPIED());
		assertEquals(map1.getPosition(7,3), map1.getOCCUPIED());
		assertEquals(map1.getPosition(7,6), map1.getOCCUPIED());
		assertEquals(map1.getPosition(6,6), map1.getOCCUPIED());
		assertEquals(map1.getPosition(5,6), map1.getOCCUPIED());
		assertEquals(map1.getPosition(5,5), map1.getOCCUPIED());
		assertEquals(map1.getPosition(4,5), map1.getOCCUPIED());
		assertEquals(map1.getPosition(3,1), map1.getOCCUPIED());
		assertEquals(map1.getPosition(4,0), map1.getOCCUPIED());
		assertEquals(map1.getPosition(4,6), map1.getOCCUPIED());
		assertEquals(map1.getPosition(11,6), map1.getOCCUPIED());
		assertEquals(map1.getPosition(9,6), map1.getOCCUPIED());
		assertEquals(map1.getPosition(10,6), map1.getOCCUPIED());
	}

	private void testExpand1Cell(){
		assertEquals(map1.getPosition(2,0), map1.getOCCUPIED());
		assertEquals(map1.getPosition(2,2), map1.getOCCUPIED());
		assertEquals(map1.getPosition(0,2), map1.getOCCUPIED());
		assertEquals(map1.getPosition(2,3), map1.getFREE());
		assertEquals(map1.getPosition(2,4), map1.getOCCUPIED());
		assertEquals(map1.getPosition(1,4), map1.getOCCUPIED());
		assertEquals(map1.getPosition(3,4), map1.getOCCUPIED());
		assertEquals(map1.getPosition(1,6), map1.getOCCUPIED());
		assertEquals(map1.getPosition(1,8), map1.getOCCUPIED());
		assertEquals(map1.getPosition(3,7), map1.getOCCUPIED());
		assertEquals(map1.getPosition(3,3), map1.getOCCUPIED());
		assertEquals(map1.getPosition(3,2), map1.getOCCUPIED());
		assertEquals(map1.getPosition(4,4), map1.getOCCUPIED());
		assertEquals(map1.getPosition(4,2), map1.getOCCUPIED());
		assertEquals(map1.getPosition(4,1), map1.getOCCUPIED());
		assertEquals(map1.getPosition(5,1), map1.getOCCUPIED());
		assertEquals(map1.getPosition(5,0), map1.getOCCUPIED());
		assertEquals(map1.getPosition(6,0), map1.getOCCUPIED());
		assertEquals(map1.getPosition(7,0), map1.getOCCUPIED());
		assertEquals(map1.getPosition(7,3), map1.getOCCUPIED());
		assertEquals(map1.getPosition(7,6), map1.getOCCUPIED());
		assertEquals(map1.getPosition(6,6), map1.getOCCUPIED());
		assertEquals(map1.getPosition(5,6), map1.getOCCUPIED());
		assertEquals(map1.getPosition(5,5), map1.getOCCUPIED());
		assertEquals(map1.getPosition(4,5), map1.getOCCUPIED());
		assertEquals(map1.getPosition(3,1), map1.getFREE());
		assertEquals(map1.getPosition(4,0), map1.getFREE());
		assertEquals(map1.getPosition(4,6), map1.getFREE());
		assertEquals(map1.getPosition(11,6), map1.getOCCUPIED());
		assertEquals(map1.getPosition(9,6), map1.getOCCUPIED());
		assertEquals(map1.getPosition(10,6), map1.getOCCUPIED());
	}
	
}
