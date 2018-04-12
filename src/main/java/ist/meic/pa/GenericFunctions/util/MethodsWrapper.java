package ist.meic.pa.GenericFunctions.util;

import java.lang.reflect.Method;
import java.util.List;

public class MethodsWrapper {
  public final List<Method> before;
  public final List<Method> after;
  public final Method primary;

  public MethodsWrapper(List<Method> before, List<Method> after, Method primary) {
    this.before = before;
    this.after = after;
    this.primary = primary;
  }
}
