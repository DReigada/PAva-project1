package ist.meic.pa.tests;

import ist.meic.pa.GenericFunctions.exceptions.NoApplicableGenericFunctionException;
import ist.meic.pa.tests.domain.Blue;
import ist.meic.pa.tests.domain.Color;
import ist.meic.pa.tests.domain.What;

import static junit.framework.TestCase.fail;

public class TestJ {
  public static void main(String[] args) {
    Color blue = new Blue();
    What.sb = new StringBuilder();

    try {
      What.is(blue);
      fail("Should throw NoApplicableGenericFunctionException");
    } catch (NoApplicableGenericFunctionException ignored) {
    }
  }

}