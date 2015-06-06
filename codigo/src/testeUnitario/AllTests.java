package testeUnitario;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ TestQuadtree.class, TestQuadtree2.class, TestQuadtree3.class,
		TestQuadtree4.class, TestQuadtree5.class, TestQuadtree6.class,
		TestQuadtree7.class, TestWave4neighborhood.class, TestWave2_4neighborhood.class })
public class AllTests {

}
