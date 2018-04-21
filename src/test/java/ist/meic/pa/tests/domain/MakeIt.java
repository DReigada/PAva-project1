package ist.meic.pa.tests.domain;

import ist.meic.pa.GenericFunctions.AfterMethod;
import ist.meic.pa.GenericFunctions.BeforeMethod;
import ist.meic.pa.GenericFunctions.GenericFunction;

@GenericFunction
public class MakeIt {
  public static StringBuilder sb;

  public static void ddouble(C1 c) {
    sb.append("C1").append("\n");
  }

  @BeforeMethod
  @AfterMethod
  public static void ddouble(Object c) {
    sb.append("Object").append("\n");
  }

  @BeforeMethod
  @AfterMethod
  public static void ddouble(Foo c) {
    sb.append("Foo").append("\n");
  }

}

