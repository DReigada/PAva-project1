package ist.meic.pa.GenericFunctionsExtended;

import java.util.Vector;

import static org.junit.Assert.assertEquals;

@GenericFunctionExtended(useCache = true)
interface WithCacheCom {

  public static Object bine(Object a, Object b) {
    Vector<Object> v = new Vector<Object>();
    v.add(a);
    v.add(b);
    return v;
  }

  public static Integer bine(Integer a, Integer b) {
    return a + b;
  }

  public static Object bine(String a, Object b) {
    return a + ", " + b + "!";
  }

  public static Object bine(String a, Integer b) {
    return (b == 0) ? "" : a + bine(a, b - 1);
  }
}

public class Test1WithCache {

  public static void main(String[] args) {
    StringBuilder sb = new StringBuilder();

    Object[] objs1 = new Object[]{"Hello", 1, 'A'};
    Object[] objs2 = new Object[]{"World", 2, 'B'};
    for (Object o1 : objs1) {
      for (Object o2 : objs2) {
        sb
            .append("Combine(" + o1 + ", " + o2 + ") -> " + WithCacheCom.bine(o1, o2))
            .append("\n");
      }
    }

    String result = sb.toString();

    String expected = "Combine(Hello, World) -> Hello, World!\n" +
        "Combine(Hello, 2) -> HelloHello\n" +
        "Combine(Hello, B) -> Hello, B!\n" +
        "Combine(1, World) -> [1, World]\n" +
        "Combine(1, 2) -> 3\n" +
        "Combine(1, B) -> [1, B]\n" +
        "Combine(A, World) -> [A, World]\n" +
        "Combine(A, 2) -> [A, 2]\n" +
        "Combine(A, B) -> [A, B]\n";

    assertEquals(expected, result);

  }
}