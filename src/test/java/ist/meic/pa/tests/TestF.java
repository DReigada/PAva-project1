package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.Bug;
import ist.meic.pa.tests.domain.C1;
import ist.meic.pa.tests.domain.C2;

import static junit.framework.TestCase.assertEquals;

public class TestF {
  public static void main(String[] args) {
    Object c1 = new C1(), c2 = new C2();
    Bug.sb = new StringBuilder();

    Bug.bug(c1);
    Bug.bug(c2);

    String result = Bug.sb.toString();

    String expected = "" +
        "Foo\n" +
        "Bar\n";

    assertEquals(expected, result);
  }
}