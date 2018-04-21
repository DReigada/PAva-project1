package ist.meic.pa.GenericFunctions;

import static org.junit.Assert.assertEquals;

@GenericFunction
abstract class Explain {
  static StringBuilder stringBuilder;

  static void it(Integer i) {
    stringBuilder.append(i + " is an integer");
  }

  static void it(Double i) {
    stringBuilder.append(i + " is a double");
  }

  static void it(String s) {
    stringBuilder.append(s + " is a string");
  }

  @BeforeMethod
  static void it(Number n) {
    stringBuilder.append("The number ");
  }

  @AfterMethod
  static void it(Object o) {
    stringBuilder.append(".\n");
  }
}

public class Test3 {

  public static void main(String[] args) throws Exception {
    Object[] objs = new Object[]{"Hello", 1, 2.0};
    Explain.stringBuilder = new StringBuilder();

    for (Object o : objs) {
      Explain.it(o);
    }

    String result = Explain.stringBuilder.toString();

    String expected = "" +
        "Hello is a string.\n" +
        "The number 1 is an integer.\n" +
        "The number 2.0 is a double.\n";

    assertEquals(expected, result);
  }
}
