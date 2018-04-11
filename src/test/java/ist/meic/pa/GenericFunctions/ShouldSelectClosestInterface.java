package ist.meic.pa.GenericFunctions;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class ShouldSelectClosestInterface {
  public static void main(String[] args) {
    Object testc2 = new TestC2();
    Object num = 1;
    Object result = Gen10.f(testc2, num);

    assertEquals("Interface2 + 1", result);
  }

  @GenericFunction
  interface Gen10 {
    static Object f(Object a, Object b) {
      return "object + object";
    }

    static String f(Interface1 a, Integer b) throws IOException {
      return "Interface1 + " + b.toString();
    }

    static String f(Interface2 a, Integer b) throws IOException {
      return "Interface2 + " + b.toString();
    }
  }

  interface Interface1 {
  }

  interface Interface2 {
  }

  static class TestC1 implements Interface1 {
  }

  static class TestC2 extends TestC1 implements Interface2 {
  }
}
