package testeUnitario;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import testeIntegracao.*;


@RunWith(Suite.class)
@SuiteClasses({ TestQuadtree.class, TestQuadtree2.class, TestQuadtree3.class,
		TestQuadtree4.class, TestQuadtree5.class, TestQuadtree6.class,
		TestQuadtree7.class, TestWave4neighborhood.class, TestWave2_4neighborhood.class,
		TestGetVertices.class, TestMap.class, TestExpandObstacles.class, TestVisib.class, 
		TestVisib2.class, TesteMRIT1.class, TesteMRIT2.class, TesteMRIT3.class, TesteMRIT4.class, 
		TesteMRIT5.class, TesteMRIT6.class, TesteA.class, TesteB.class})
public class AllTests {

}
