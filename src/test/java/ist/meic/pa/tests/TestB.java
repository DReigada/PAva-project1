package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.Com;

import static junit.framework.TestCase.assertEquals;

public class TestB {
  public static void main(String[] args) {
    Object[] objects = new Object[]{new Object(), "Foo", 123};
    StringBuilder sb = new StringBuilder();

    for (Object c : objects) sb.append(Com.bine(c)).append("\n");

    String result = sb.toString();

    String expected = "" +
        "Object\n" +
        "Foo\n" +
        "123\n";

    assertEquals(expected, result);
  }
}