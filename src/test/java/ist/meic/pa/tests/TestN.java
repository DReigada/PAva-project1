package ist.meic.pa.tests;

import ist.meic.pa.tests.domain.Color;
import ist.meic.pa.tests.domain.Red;
import ist.meic.pa.tests.domain.SuperBlack;

import static junit.framework.TestCase.assertEquals;

public class TestN {
  public static void main(String[] args) {
    Object red1 = new Red(), red2 = new Red(), black = new SuperBlack();

    assertEquals("Red-Color-Red", Color.mix(red1, black, red2));
    assertEquals("SuperBlack-Black-Color", Color.mix(black, black, red2));
    assertEquals("SuperBlack-Black-SuperBlack", Color.mix(black, black, black));
  }
}
