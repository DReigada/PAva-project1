package ist.meic.pa.tests.domain;

import ist.meic.pa.GenericFunctions.GenericFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@GenericFunction
public class ArrayCom {

  public static StringBuilder sb;

  public static Object bine(Object a, Object b) {
    return new Object[]{a, b};
  }

  public static Object bine(Object[] a, Object[] b) {
    Object[] r = new Object[a.length];
    for (int i = 0; i < a.length; i++) {
      r[i] = ArrayCom.bine(a[i], b[i]);
    }
    return r;
  }

  private static Object bine(String a, String b) {
    sb.append(a + b).append("\n");
    return a + b;
  }

  private static Object bine(String a, Float b) {
    return a + "-(float): " + b;
  }

  public static Integer bine(Integer a, Integer b) {
    return a + b;
  }

  // Test K
  public static Object bine(List<Object> a, List<Object> b) {
    sb.append("List, List").append("\n");
    Object[][] r = new Object[a.size()][b.size()];
    for (int i = 0; i < a.size(); i++) {
      for (int j = 0; j < b.size(); j++)
        r[i][j] = ArrayCom.bine(a.get(i), b.get(j));
    }
    return Arrays.deepToString(r);
  }

  public static Object bine(ArrayList<Object> a, ArrayList<Object> b) {
    sb.append("ArrayList").append("\n");
    Object[][] r = new Object[a.size()][b.size()];
    for (int i = 0; i < a.size(); i++) {
      for (int j = 0; j < b.size(); j++)
        r[i][j] = ArrayCom.bine(a.get(i), b.get(j));
    }
    return Arrays.deepToString(r);
  }

}
