package ist.meic.pa.GenericFunctionsExtended.domain;

import ist.meic.pa.GenericFunctions.util.method.order.AbstractMethodComparator;
import ist.meic.pa.GenericFunctions.util.method.order.DefaultMethodComparator;

import java.lang.reflect.Method;

public class ReversedComparator extends AbstractMethodComparator {

  private final AbstractMethodComparator defaultComp = new DefaultMethodComparator(referenceArguments);

  public ReversedComparator(Class<?>[] referenceArguments) {
    super(referenceArguments);
  }

  @Override
  public int compare(Method m1, Method m2) {
    boolean m1FirstIsObject = m1.getParameterTypes()[0].equals(Object.class);
    boolean m2FirstIsObject = m2.getParameterTypes()[0].equals(Object.class);

    if (m1FirstIsObject && !m2FirstIsObject) {
      return 1;
    } else if (!m1FirstIsObject && m2FirstIsObject) {
      return -1;
    } else {
      return defaultComp.reversed().compare(m1, m2);
    }
  }
}