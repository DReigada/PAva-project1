package ist.meic.pa.GenericFunctions.core;

import ist.meic.pa.GenericFunctions.GenericFunction;
import ist.meic.pa.GenericFunctions.util.method.order.AbstractMethodComparator;
import ist.meic.pa.GenericFunctions.util.method.order.DefaultMethodComparator;
import ist.meic.pa.GenericFunctionsExtended.GenericFunctionExtended;

public class Config {
  public static final Class<? extends AbstractMethodComparator> DEFAULT_METHOD_COMPARATOR = DefaultMethodComparator.class;

  public final boolean useCache;
  public final Class<? extends AbstractMethodComparator> methodComparator;

  public Config(GenericFunctionExtended annotation) {
    this.useCache = annotation.useCache();
    methodComparator = annotation.methodComparator();
  }

  public Config(GenericFunction annotation) {
    this.useCache = false;
    methodComparator = DEFAULT_METHOD_COMPARATOR;
  }
}