package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.Black;
import ist.meic.pa.tests.domain.Blue;
import ist.meic.pa.tests.domain.Color;
import ist.meic.pa.tests.domain.Red;

import static junit.framework.TestCase.assertEquals;

public class TestA {
  public static void main(String[] args) {
    Color[] colors = new Color[]{new Red(), new Blue(), new Black()};

    StringBuilder sb = new StringBuilder();

    for (Color c : colors) sb.append(Color.mix(c)).append("\n");

    String result = sb.toString();

    String expected = "" +
        "Red\n" +
        "What color am I?\n" +
        "What color am I?\n";

    assertEquals(expected, result);
  }

}