package ist.meic.pa.GenericFunctionsExtended;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface GenericFunctionExtended {
  boolean useCache() default true;
}