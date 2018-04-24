package ist.meic.pa.GenericFunctionsExtended;

import ist.meic.pa.GenericFunctionsExtended.domain.MyClass1;
import ist.meic.pa.GenericFunctionsExtended.domain.MyClass2;
import ist.meic.pa.GenericFunctionsExtended.domain.Rever;

import static org.junit.Assert.assertEquals;

public class ReversedOrder {

  public static void main(String[] args) {
    assertEquals("MyInterface1", Rever.sed((Object) new MyClass1()));
    assertEquals("MyClass2", Rever.sed((Object) new MyClass2()));
  }
}

