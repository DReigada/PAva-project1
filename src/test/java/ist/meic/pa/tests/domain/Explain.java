package ist.meic.pa.tests.domain;

import ist.meic.pa.GenericFunctions.AfterMethod;
import ist.meic.pa.GenericFunctions.BeforeMethod;
import ist.meic.pa.GenericFunctions.GenericFunction;

@GenericFunction
public class Explain {

  public static StringBuilder sb;

  public static void it(Integer i) {
    sb.append(i + " is an integer");
  }

  public static void it(Double i) {
    sb.append(i + " is a double");
  }

  public static void it(String s) {
    sb.append(s + " is a string");
  }

  @BeforeMethod
  public static void it(Number n) {
    sb.append("The number");
  }

  @AfterMethod
  public static void it(Object o) {
    sb.append(".").append("\n");
  }
}
