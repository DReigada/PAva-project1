package ist.meic.pa.GenericFunctions.util;

import java.util.Arrays;

public class ParametersClassWrapper {
  public final Class<?>[] paramsClasses;

  public ParametersClassWrapper(Class<?>[] paramsClasses) {
    this.paramsClasses = paramsClasses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ParametersClassWrapper that = (ParametersClassWrapper) o;
    return Arrays.equals(paramsClasses, that.paramsClasses);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(paramsClasses);
  }
}
