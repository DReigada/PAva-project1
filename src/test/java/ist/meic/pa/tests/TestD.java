package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.Com;

import static junit.framework.TestCase.assertEquals;

public class TestD {
  public static void main(String[] args) {
    Object objects = new Object[]{"Foo", new Integer[]{123, -12}};
    String result1 = Com.bine(objects).toString();
    String expected1 = "Foo111";

    assertEquals(expected1, result1);


    Object numbers = new Object[]{123, new Integer[]{456, 21}};
    String result2 = Com.bine(numbers).toString();
    String expected2 = "123477";

    assertEquals(expected2, result2);
  }
}