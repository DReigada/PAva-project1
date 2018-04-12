package ist.meic.pa.GenericFunctions;

import static org.junit.Assert.assertEquals;

@GenericFunction
interface Explain {
  static String it(Integer i) {
    return i + " is an integer";
  }

  static String it(Double i) {
    return i + " is a double";
  }

  static String it(String s) {
    return s + " is a string";
  }

  @BeforeMethod
  static String it(Number n) {
    return "The number ";
  }

  @AfterMethod
  static String it(Object o) {
    return ".";
  }
}

public class Test3 {

  public static void main(String[] args) {
    StringBuilder sb = new StringBuilder();

    Object[] objs = new Object[]{"Hello", 1, 2.0};
    for (Object o : objs) {
      sb
          .append(Explain.it(o))
          .append("\n");
    }

    String result = sb.toString();

    String expected = "" +
        "Hello is a string.\n" +
        "The number 1 is an integer.\n" +
        "The number 2.0 is a double.\n";

    assertEquals(expected, result);
  }
}
