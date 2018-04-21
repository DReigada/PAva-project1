package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.Explain;

import static org.junit.Assert.assertEquals;

public class TestH {
  public static void main(String[] args) {
    Object[] objs = new Object[]{"Hello", 1, 2.0};
    Explain.sb = new StringBuilder();

    for (Object o : objs) Explain.it(o);

    String result = Explain.sb.toString();

    String expected = "" +
        "Hello is a string.\n" +
        "The number1 is an integer.\n" +
        "The number2.0 is a double.\n";

    assertEquals(expected, result);
  }
}
