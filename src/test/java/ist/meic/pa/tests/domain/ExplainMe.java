package ist.meic.pa.tests.domain;

import ist.meic.pa.GenericFunctions.AfterMethod;
import ist.meic.pa.GenericFunctions.BeforeMethod;
import ist.meic.pa.GenericFunctions.GenericFunction;

@GenericFunction
public class ExplainMe {

  public static StringBuilder sb;

  @AfterMethod
  public static void twoThings(Number o1, Number o2) {
    sb.append("Sniff, Sniff! Why am I the last? I'm more specific than Obj-Obj!\n");
  }

  @AfterMethod
  public static void twoThings(Object o1, Object o2) {
    sb.append("Muahaha! I knew I would run after the primary!\n");
  }

  public static void twoThings(Number o1, Integer o2) {
    sb.append("Woho!! I'm the primary!\n");
  }

  @BeforeMethod
  public static void twoThings(Integer o1, Number o2) {
    sb.append("How come Integer-Integer is more specific than me?\n");
  }

  @BeforeMethod
  public static void twoThings(Integer o1, Integer o2) {
    sb.append("Let me be the first!\n");
  }
}
