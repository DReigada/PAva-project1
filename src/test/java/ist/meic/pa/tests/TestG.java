package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.ArrayCom;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class TestG {
  public static void main(String[] args) {
    String result1 = toString(ArrayCom.bine(1, 3));
    String result2 = toString(ArrayCom.bine(new Object[]{1, 2, 3}, new Object[]{4, 5, 6}));
    String result3 = toString(ArrayCom.bine(new Object[]{new Object[]{1, 2}, 3},
        new Object[]{new Object[]{3, 4}, 5}));

    String expected1 = "4";
    String expected2 = "[5, 7, 9]";
    String expected3 = "[[4, 6], 8]";

    assertEquals(expected1, result1);
    assertEquals(expected2, result2);
    assertEquals(expected3, result3);
  }

  private static String toString(Object obj) {
    if (obj instanceof Object[]) {
      return Arrays.deepToString((Object[]) obj);
    } else {
      return obj.toString();
    }
  }
}
