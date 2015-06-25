package testeUnitario;

import static org.junit.Assert.*;

import java.util.ArrayList;
import map.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.Point;

public class TestGetVertices {

	Map map1, map2;
	
	@Before
	public void setUp() throws Exception {
		boolean[][] mapa = {{true,  true,  false, false, false, false, false, false, true},
							{true,  true,  false, false, false, false, false, false, false},
							{false, false, false, false, false, true,  true,  true,  false},
							{false, false, false, true,  false, false, false, false, false},
							{false, false, true,  true,  true,  false, false, true,  true},
							{false, true,  true,  true,  true,  true,  false, false, false},
							{false, false, false, false, false, false, false, false, false},	
							{false, false, false, false, false, false, false, false, false},
							{false, false, true,  true,  true,  true,  false, false, false},
							{false, false, true,  true,  true,  true,  false, false, false},
							{false, false, true,  true,  true,  false, false, false, false},
							{false, false, true,  true,  false, false, false, true,  true},
							{true,  false, false, false, false, false, false, true,  true} };
		map1 = new Map(mapa, false);
		//					   0       1       2      3      4       5     6      7      8      9      10     11     12
		boolean[][] mapa2 = {{false,  false,  false, false, false, false, false, false, false, false, false, false, false},//0
				       /*1*/ {false,  true,   true,  true,  true,  true,  true,  true,  false, false, false, false, false},//1
				       /*2*/ {false,  true,   true,  true,  true,  true,  true,  true,  true,  false, false, false, false},//2
				       /*3*/ {false,  true,   true,  true,  false, false, false, true,  true,  true,  false, false, false},//3
				       /*4*/ {false,  true,   true,  false, false, false, false, false, true,  true,  true,  false, false},//4
				       /*5*/ {false,  false,  false, false, false, false, false, false, false, true,  true,  true,  false},//5
				       /*6*/ {false,  false,  false, false, false, false, false, false, false, false, true,  true,  false},//6
				       /*7*/ {false,  false,  true,  false, false, false, true,  false, false, false, false, true,  false},//7
				       /*8*/ {false,  true,   true,  true,  false, true,  true,  true,  false, false, true,  true,  false},//8
				       /*9*/ {false,  true,   true,  true,  true,  true,  true,  true,  false, true,  true,  true,  false},//9
				      /*10*/ {false,  true,   true,  false, false, false, false, false, false, false, false, false, false},//10
				      /*11*/ {false,  true,   true,  false, false, false, false, true,  true,  true,  true,  true,  false},//11
				      /*12*/ {false,  false,  false, false, false, false, false, false, true,  true,  true,  true,  false},//12
				      /*13*/ {false,  false,  false, false, false, false, false, false, false, true,  true,  true,  false},//13
				      /*14*/ {false,  false,  false, false, false, false, false, false, false, false, true,  true,  false},//14
				      /*15*/ {false,  false,  false, false, false, false, false, false, false, true,  true,  true,  false},//15
				      /*16*/ {false,  false,  false, false, false, false, false, false, true,  true,  true,  true,  false},//16
				      /*17*/ {false,  false,  false, false, false, false, false, true,  true,  true,  true,  false, false}};//17
		map2 = new Map(mapa2, false);
	}
	
	@After
	public void tearDown(){
		map1.cleanUp();
		map2.cleanUp();
		map1 = null;
		map2 = null;
	}

	@Test
	public void testGetObstacleVertices1(){
		ArrayList<Point> array = (ArrayList<Point>) map1.getVertices();
		assertEquals(array.size(),22);
		assertEquals((int)array.get(0).getX(), 0);
		assertEquals((int)array.get(0).getY(), 0);
		assertEquals((int)array.get(1).getX(), 1);
		assertEquals((int)array.get(1).getY(), 0);
		assertEquals((int)array.get(2).getX(), 8);
		assertEquals((int)array.get(2).getY(), 0);
		assertEquals((int)array.get(3).getX(), 0);
		assertEquals((int)array.get(3).getY(), 1);
		assertEquals((int)array.get(4).getX(), 1);
		assertEquals((int)array.get(4).getY(), 1);
		assertEquals((int)array.get(5).getX(), 5);
		assertEquals((int)array.get(5).getY(), 2);
		assertEquals((int)array.get(6).getX(), 7);
		assertEquals((int)array.get(6).getY(), 2);
		assertEquals((int)array.get(7).getX(), 3);
		assertEquals((int)array.get(7).getY(), 3);
		assertEquals((int)array.get(8).getX(), 7);
		assertEquals((int)array.get(8).getY(), 4);
		assertEquals((int)array.get(9).getX(), 8);
		assertEquals((int)array.get(9).getY(), 4);
		assertEquals((int)array.get(10).getX(), 1);
		assertEquals((int)array.get(10).getY(), 5);
		assertEquals((int)array.get(11).getX(), 5);
		assertEquals((int)array.get(11).getY(), 5);
		assertEquals((int)array.get(12).getX(), 2);
		assertEquals((int)array.get(12).getY(), 8);
		assertEquals((int)array.get(13).getX(), 5);
		assertEquals((int)array.get(13).getY(), 8);
		assertEquals((int)array.get(14).getX(), 5);
		assertEquals((int)array.get(14).getY(), 9);
		assertEquals((int)array.get(15).getX(), 2);
		assertEquals((int)array.get(15).getY(), 11);
		assertEquals((int)array.get(16).getX(), 3);
		assertEquals((int)array.get(16).getY(), 11);
		assertEquals((int)array.get(17).getX(), 7);
		assertEquals((int)array.get(17).getY(),11);
		assertEquals((int)array.get(18).getX(), 8);
		assertEquals((int)array.get(18).getY(), 11);
		assertEquals((int)array.get(19).getX(), 0);
		assertEquals((int)array.get(19).getY(), 12);
		assertEquals((int)array.get(20).getX(), 7);
		assertEquals((int)array.get(20).getY(), 12);
		assertEquals((int)array.get(21).getX(), 8);
		assertEquals((int)array.get(21).getY(), 12);
	}

	@Test
	public void testGetObstacleVertices2(){
		ArrayList<Point> array = (ArrayList<Point>) map2.getVertices();
		assertEquals(array.size(), 19);
		ArrayList<Point> concavePoints = new ArrayList<Point>();
		concavePoints.add( new Point(2, 9) );
		concavePoints.add( new Point(11, 7) );
		concavePoints.add( new Point(7, 2) );
		concavePoints.add( new Point(4, 9) );
		concavePoints.add( new Point(10, 14) );
		concavePoints.add( new Point(3, 2) );
		for(int i=0; i<array.size(); i++){
			Point p = array.get(i);
			for(int j=0; j<concavePoints.size(); j++){
				Point concave = concavePoints.get(j);
				assertFalse( p.getX()==concave.getX() && p.getY()==concave.getY() );
			}
		}
	}
}
