package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.Identify;

import static junit.framework.TestCase.assertEquals;

public class TestE {
  public static void main(String[] args) {
    Object objects = new Object[]{123, "Foo", 1.2};
    String result = Identify.it(objects);
    String expected = "IntegerStringObject";

    assertEquals(expected, result);
  }
}