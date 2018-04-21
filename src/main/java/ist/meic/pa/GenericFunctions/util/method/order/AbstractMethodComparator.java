package ist.meic.pa.GenericFunctions.util.method.order;

import java.lang.reflect.Method;
import java.util.Comparator;

public abstract class AbstractMethodComparator implements Comparator<Method> {
  protected Class<?>[] referenceArguments;

  public AbstractMethodComparator(Class<?>[] referenceArguments) {
    this.referenceArguments = referenceArguments;
  }

  @Override
  abstract public int compare(Method m1, Method m2);
}
