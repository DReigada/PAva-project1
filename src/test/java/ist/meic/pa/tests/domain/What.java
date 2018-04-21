package ist.meic.pa.tests.domain;

import ist.meic.pa.GenericFunctions.AfterMethod;
import ist.meic.pa.GenericFunctions.BeforeMethod;
import ist.meic.pa.GenericFunctions.GenericFunction;

@GenericFunction
public class What {

  public static StringBuilder sb;

  public static void is(Black i) {
    sb.append("What is black? ");
  }

  public static void is(Red i) {
    sb.append("What is red? ");
  }

  @BeforeMethod
  public static void is(Blue o) {
    sb.append("Blue ").append("\n");
  }

  @AfterMethod
  public static void is(Object o) {
    sb.append(" Is it an object?");
  }

  @AfterMethod
  public static void is(Color o) {
    sb.append(" Is it a color?");
  }

  @AfterMethod
  public static void is(SuperBlack o) {
    sb.append(" It is all of that and much more...");
  }
}
