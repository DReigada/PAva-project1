package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.C1;
import ist.meic.pa.tests.domain.MakeIt;

import static junit.framework.TestCase.assertEquals;

public class TestO {
  public static void main(String[] args) {
    Object c = new C1();

    MakeIt.sb = new StringBuilder();
    MakeIt.ddouble(c);

    String result = MakeIt.sb.toString();
    String expected = "" +
        "Foo\n" +
        "Object\n" +
        "C1\n" +
        "Object\n" +
        "Foo\n";

    assertEquals(expected, result);
  }
}