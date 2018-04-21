package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.Blue;
import ist.meic.pa.tests.domain.Color;
import ist.meic.pa.tests.domain.Red;
import ist.meic.pa.tests.domain.Yellow;

import static junit.framework.TestCase.assertEquals;

public class TestM {
  public static void main(String[] args) {
    Color[] colors = new Color[]{new Red(), new Blue(), new Yellow()};
    StringBuilder sb = new StringBuilder();

    for (Color c1 : colors)
      for (Color c2 : colors)
        sb.append(Color.mix(c1, c2)).append("\n");

    String result = sb.toString();

    String expected = "" +
        "More red\n" +
        "Magenta\n" +
        "Orange\n" +
        "Magenta\n" +
        "More blue\n" +
        "Green\n" +
        "Orange\n" +
        "Green\n" +
        "More yellow\n";

    assertEquals(expected, result);
  }
}