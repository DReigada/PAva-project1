package ist.meic.pa.tests;

import ist.meic.pa.GenericFunctions.WithGenericFunctions;
import org.junit.Test;

public class TestsWrapper {
  private static void runTestClass(Class<?> clazz) throws Throwable {
    WithGenericFunctions.main(new String[]{clazz.getName()});
  }

  @Test
  public void testA() throws Throwable {
    runTestClass(TestA.class);
  }

  @Test
  public void testB() throws Throwable {
    runTestClass(TestB.class);
  }

  @Test
  public void testC() throws Throwable {
    runTestClass(TestC.class);
  }

  @Test
  public void testD() throws Throwable {
    runTestClass(TestD.class);
  }

  @Test
  public void testE() throws Throwable {
    runTestClass(TestE.class);
  }

  @Test
  public void testF() throws Throwable {
    runTestClass(TestF.class);
  }

  @Test
  public void testG() throws Throwable {
    runTestClass(TestG.class);
  }

  @Test
  public void testH() throws Throwable {
    runTestClass(TestH.class);
  }

  @Test
  public void testI() throws Throwable {
    runTestClass(TestI.class);
  }

  @Test
  public void testJ() throws Throwable {
    runTestClass(TestJ.class);
  }

  @Test
  public void testK() throws Throwable {
    runTestClass(TestK.class);
  }

  @Test
  public void testL() throws Throwable {
    runTestClass(TestL.class);
  }

  @Test
  public void testM() throws Throwable {
    runTestClass(TestM.class);
  }

  @Test
  public void testN() throws Throwable {
    runTestClass(TestN.class);
  }

  @Test
  public void testO() throws Throwable {
    runTestClass(TestO.class);
  }

  @Test
  public void testP() throws Throwable {
    runTestClass(TestP.class);
  }


}
