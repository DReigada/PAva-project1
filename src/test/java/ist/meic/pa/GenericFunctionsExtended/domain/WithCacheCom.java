package ist.meic.pa.GenericFunctionsExtended.domain;

import ist.meic.pa.GenericFunctionsExtended.GenericFunctionExtended;

import java.util.Vector;

@GenericFunctionExtended(useCache = true)
public interface WithCacheCom {

  static Object bine(Object a, Object b) {
    Vector<Object> v = new Vector<Object>();
    v.add(a);
    v.add(b);
    return v;
  }

  static Integer bine(Integer a, Integer b) {
    return a + b;
  }

  static Object bine(String a, Object b) {
    return a + ", " + b + "!";
  }

  static Object bine(String a, Integer b) {
    return (b == 0) ? "" : a + bine(a, b - 1);
  }
}