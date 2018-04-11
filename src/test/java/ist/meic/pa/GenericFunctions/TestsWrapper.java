package ist.meic.pa.GenericFunctions;

import org.junit.Test;

public class TestsWrapper {
  private static void runTestClass(Class<?> clazz) throws Throwable {
    WithGenericFunctions.main(new String[]{clazz.getName()});
  }

  @Test
  public void test1() throws Throwable {
    runTestClass(Test1.class);
  }

  @Test
  public void test2() throws Throwable {
    runTestClass(Test2.class);
  }

  @Test
  public void shouldWorkWithInterfaceOfSuperClass() throws Throwable {
    runTestClass(ShouldWorkWithInterfaceOfSuperClass.class);
  }

  @Test
  public void shouldSelectClosestInterface() throws Throwable {
    runTestClass(ShouldSelectClosestInterface.class);
  }
}
