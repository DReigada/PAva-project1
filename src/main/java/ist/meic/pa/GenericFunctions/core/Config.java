package ist.meic.pa.GenericFunctions.core;

import ist.meic.pa.GenericFunctions.GenericFunction;
import ist.meic.pa.GenericFunctions.util.method.order.DefaultMethodComparator;
import ist.meic.pa.GenericFunctionsExtended.GenericFunctionExtended;

class Config {
  private static final String DEFAULT_METHOD_COMPARATOR = DefaultMethodComparator.class.getName();

  public final boolean useCache;
  public final String methodComparator;

  public Config(GenericFunctionExtended annotation) {
    this.useCache = annotation.useCache();
    methodComparator =
        annotation.methodComparator().isEmpty() ?
            DEFAULT_METHOD_COMPARATOR :
            annotation.methodComparator();
  }

  public Config(GenericFunction annotation) {
    this.useCache = false;
    methodComparator = DEFAULT_METHOD_COMPARATOR;
  }
}