package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.Color;
import ist.meic.pa.tests.domain.Red;
import ist.meic.pa.tests.domain.SuperBlack;
import ist.meic.pa.tests.domain.What;

import static junit.framework.TestCase.assertEquals;

public class TestI {
  public static void main(String[] args) {
    Object[] colors = new Color[]{new SuperBlack(), new Red()};

    What.sb = new StringBuilder();
    for (Object o : colors) What.is(o);

    String result = What.sb.toString();
    String expected = "What is black?  Is it an object? Is it a color? It is all of that and much more...What is red?  Is it an object? Is it a color?";

    assertEquals(expected, result);
  }
}
