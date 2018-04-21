package ist.meic.pa.tests.domain;

import ist.meic.pa.GenericFunctions.GenericFunction;

@GenericFunction
public class Bug {
  public static StringBuilder sb;

  // Test F
  public static void bug(Object o) {
    sb.append("Object\n");
  }

  public static void bug(Foo f) {
    sb.append("Foo\n");
  }

  public static void bug(Bar b) {
    sb.append("Bar\n");
  }
}
