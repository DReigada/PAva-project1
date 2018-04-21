package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.ArrayCom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestK {
  public static void main(String[] args) {
    ArrayCom.sb = new StringBuilder();

    List<Object> a = new ArrayList<>();
    a.add("Hello");
    a.add(1);
    a.add('A');

    List<Object> b = new LinkedList<>();
    b.add(2);
    b.add('B');

    String result1 = ArrayCom.bine(a, b).toString();
    String expected1 = "[[[Hello, 2], [Hello, B]], [3, [1, B]], [[A, 2], [A, B]]]";

    String result2 = ArrayCom.sb.toString();
    String expected2 = "List, List\n";


    assertEquals(expected1, result1);
    assertEquals(expected2, result2);
  }
}