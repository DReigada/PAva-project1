package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.Black;
import ist.meic.pa.tests.domain.Color;
import ist.meic.pa.tests.domain.Red;

import static junit.framework.TestCase.assertEquals;

public class TestC {
  public static void main(String[] args) {
    Object colors = new Object[]{new Red(), 2.9, new Black(), "Holla!" };

    String result = Color.mix(colors);

    String expected = "RedI'm just an object.What color am I?I'm just an object.";

    assertEquals(expected, result);
  }
}