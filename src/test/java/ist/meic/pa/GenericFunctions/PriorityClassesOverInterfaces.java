package ist.meic.pa.GenericFunctions;

import static junit.framework.TestCase.assertEquals;

@GenericFunction
interface Hello {
  static String f(Object o) {
    return "O";
  }

  static String f(I i) {
    return "I";
  }

  static String f(A a) {
    return "A";
  }
}

interface I {
}

abstract class A {
}

class C extends A implements I {
}

public class PriorityClassesOverInterfaces {
  public static void main(String[] args) {
    assertEquals("A", Hello.f((Object) new C()));
  }
}
