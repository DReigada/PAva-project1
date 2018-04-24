package ist.meic.pa.GenericFunctionsExtended.benchmarks;


import ist.meic.pa.GenericFunctionsExtended.benchmarks.domain.C1;
import ist.meic.pa.GenericFunctionsExtended.benchmarks.domain.TiCache;
import ist.meic.pa.GenericFunctionsExtended.benchmarks.domain.TiNoCache;

import java.util.LinkedList;
import java.util.List;
import java.util.OptionalDouble;

public class CacheVsNoCacheTimeBenchmark {

  public static List<Long> cacheTimes = new LinkedList<>();
  public static List<Long> noCacheTimes = new LinkedList<>();

  public static void main(String[] args) {
    Object o = new C1();

    for (int i = 0; i < 10; i++) {
      runTest(o, 100000);
    }

    OptionalDouble cacheAverage = cacheTimes.stream().mapToLong(a -> a).average();
    OptionalDouble noCacheAverage = noCacheTimes.stream().mapToLong(a -> a).average();

    System.out.println("Cache average " + cacheAverage.getAsDouble() + "ms");
    System.out.println("NoCache average " + noCacheAverage.getAsDouble() + "ms");
  }

  public static void runTest(Object o, int runs) {
    long time = System.currentTimeMillis();
    for (int i = 0; i < runs; i++) {
      TiCache.me(o);
    }
    time = System.currentTimeMillis() - time;
    System.out.println("Cache: " + time + "ms");
    cacheTimes.add(time);

    time = System.currentTimeMillis();
    for (int i = 0; i < runs; i++) {
      TiNoCache.me(o);
    }
    time = System.currentTimeMillis() - time;
    System.out.println("NoCache: " + time + "ms");
    noCacheTimes.add(time);
  }
}
