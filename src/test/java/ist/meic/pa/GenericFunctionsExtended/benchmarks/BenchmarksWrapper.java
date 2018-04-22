package ist.meic.pa.GenericFunctionsExtended.benchmarks;

import ist.meic.pa.GenericFunctions.*;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("These are benchmarks not tests")
public class BenchmarksWrapper {
  private static void runTestClass(Class<?> clazz) throws Throwable {
    WithGenericFunctions.main(new String[]{clazz.getName()});
  }

  @Test
  public void cacheVsNoCacheTimeBenchmark() throws Throwable {
    runTestClass(CacheVsNoCacheTimeBenchmark.class);
  }

  @Test
  public void cacheVsNoGenericTimeBenchmark() throws Throwable {
    runTestClass(CacheVsNoGenericTimeBenchmark.class);
  }

}
