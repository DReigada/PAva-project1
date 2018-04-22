package ist.meic.pa.GenericFunctionsExtended.benchmarks;


import ist.meic.pa.GenericFunctionsExtended.benchmarks.domain.C1;
import ist.meic.pa.GenericFunctionsExtended.benchmarks.domain.TiCache;
import ist.meic.pa.GenericFunctionsExtended.benchmarks.domain.TiNoCache;
import ist.meic.pa.GenericFunctionsExtended.benchmarks.domain.TiNoGeneric;

import java.util.LinkedList;
import java.util.List;
import java.util.OptionalDouble;

public class CacheVsNoCacheTimeBenchmark {

  public static List<Long> cacheTimes = new LinkedList<>();
  public static List<Long> noCacheTimes = new LinkedList<>();
  public static List<Long> noGenericTimes = new LinkedList<>();

  public static void main(String[] args) {
    Object o = new C1();

    for (int i = 0; i < 10; i++) {
      runTest(o, 100000);
    }

    OptionalDouble cacheAverage = cacheTimes.stream().mapToLong(a -> a).average();
    OptionalDouble noCacheAverage = noCacheTimes.stream().mapToLong(a -> a).average();
    OptionalDouble noGenericAverage = noGenericTimes.stream().mapToLong(a -> a).average();

    System.out.println("Cache average " + cacheAverage.getAsDouble() * 1000 + "ns");
    System.out.println("NoCache average " + noCacheAverage.getAsDouble() * 1000 + "ns");
    System.out.println("NoGeneric average " + noGenericAverage.getAsDouble() * 1000 + "ns");
  }

  public static void runTest(Object o, int runs) {
    long time = System.nanoTime();
    for (int i = 0; i < runs; i++) {
      TiCache.me(o);
    }
    time = System.nanoTime() - time;
    System.out.println("Cache: " + time + "ms");
    cacheTimes.add(time);

    time = System.nanoTime();
    for (int i = 0; i < runs; i++) {
      TiNoCache.me(o);
    }
    time = System.nanoTime() - time;
    System.out.println("NoCache: " + time + "ms");
    noCacheTimes.add(time);

    time = System.nanoTime();
    for (int i = 0; i < runs; i++) {
      TiNoGeneric.me((C1) o);
    }
    time = System.nanoTime() - time;
    System.out.println("NoGeneric: " + time + "ms");
    noGenericTimes.add(time);
  }
}
