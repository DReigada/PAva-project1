package ist.meic.pa.GenericFunctionsExtended;

import ist.meic.pa.GenericFunctions.*;
import org.junit.Test;

public class TestsWrapper {
  private static void runTestClass(Class<?> clazz) throws Throwable {
    WithGenericFunctions.main(new String[]{clazz.getName()});
  }

  @Test
  public void test1WithCache() throws Throwable {
    runTestClass(Test1WithCache.class);
  }

}
