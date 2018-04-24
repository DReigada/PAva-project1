package ist.meic.pa.GenericFunctionsExtended.domain;

import ist.meic.pa.GenericFunctionsExtended.GenericFunctionExtended;

@GenericFunctionExtended(methodComparator = ReversedComparator.class)
public interface Rever {
  static String sed(Object o) {
    return "Object";
  }

  static String sed(MyInterface1 o) {
    return "MyInterface1";
  }

  static String sed(MyClass1 o) {
    return "MyClass1";
  }
  static String sed(MyClass2 o) {
    return "MyClass2";
  }
}
