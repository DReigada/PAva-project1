package ist.meic.pa.GenericFunctionsExtended.benchmarks;


import ist.meic.pa.GenericFunctions.GenericFunction;
import ist.meic.pa.GenericFunctionsExtended.GenericFunctionExtended;

abstract class I1 {
}

abstract class I2 extends I1 {
}

abstract class I3 extends I2 {
}

abstract class I4 extends I3 {
}

abstract class I5 extends I4 {
}

class C1 extends I5 {
}

@GenericFunctionExtended(useCache = true)
interface TiCache {
  static void me(Object a) {
  }

  static void me(I1 a) {
  }

  static void me(I2 a) {
  }

  static void me(I3 a) {
  }

  static void me(I4 a) {
  }

  static void me(I5 a) {
  }

  static void me(C1 a) {
  }
}

@GenericFunction
interface TiNoCache {
  static void me(Object a) {
  }

  static void me(I1 a) {
  }

  static void me(I2 a) {
  }

  static void me(I3 a) {
  }

  static void me(I4 a) {
  }

  static void me(I5 a) {
  }

  static void me(C1 a) {
  }
}

public class CacheVsNoCacheTimeBenchmark {

  public static void main(String[] args) {
    Object o = new C1();

    for (int i = 0; i < 10; i++) {
      runTest(o, 100000);
    }
  }

  public static void runTest(Object o, int runs) {
    long time = System.currentTimeMillis();
    for (int i = 0; i < runs; i++) {
      TiCache.me(o);
    }
    System.out.println("Cache: " + (System.currentTimeMillis() - time) + "ms");

    time = System.currentTimeMillis();
    for (int i = 0; i < runs; i++) {
      TiNoCache.me(o);
    }
    System.out.println("NoCache: " + (System.currentTimeMillis() - time) + "ms");
  }
}
