package ist.meic.pa.GenericFunctionsExtended;

import ist.meic.pa.GenericFunctions.util.method.order.AbstractMethodComparator;
import ist.meic.pa.GenericFunctions.util.method.order.DefaultMethodComparator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface GenericFunctionExtended {
  boolean useCache() default true;

  Class<? extends AbstractMethodComparator> methodComparator() default DefaultMethodComparator.class;
}