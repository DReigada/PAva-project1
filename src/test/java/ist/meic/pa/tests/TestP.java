package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.ExplainMe;

import static org.junit.Assert.assertEquals;

public class TestP {
  public static void main(String[] args) {
    Object o1 = 2, o2 = 91;
    ExplainMe.sb = new StringBuilder();

    ExplainMe.twoThings(o1, o2);

    String result = ExplainMe.sb.toString();

    String expected = "" +
        "Let me be the first!\n" +
        "How come Integer-Integer is more specific than me?\n" +
        "Woho!! I'm the primary!\n" +
        "Muahaha! I knew I would run after the primary!\n" +
        "Sniff, Sniff! Why am I the last? I'm more specific than Obj-Obj!\n";

    assertEquals(expected, result);
  }
}
