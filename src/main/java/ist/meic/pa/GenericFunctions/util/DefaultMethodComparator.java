package ist.meic.pa.GenericFunctions.util;

import com.google.common.collect.Streams;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class DefaultMethodComparator implements Comparator<Method> {

  public final static DefaultMethodComparator instance = new DefaultMethodComparator();

  @Override
  public int compare(Method m1, Method m2) {
    if (m1.equals(m2)) {
      return 0;
    } else {
      Stream<Class<?>> m1Types = Arrays.stream(m1.getParameterTypes());
      Stream<Class<?>> m2Types = Arrays.stream(m2.getParameterTypes());

      ClassComparator comp = new ClassComparator();

      return Streams
          .zip(m1Types, m2Types, comp::compare)
          .filter(i -> i != 0)
          .findFirst()
          .orElse(0);
    }
  }

  private static class ClassComparator implements Comparator<Class<?>> {

    /*
     * c1 is bigger than c2 if it is a superClass/interface of c2
     * Note: Can not compare unrelated types
     */
    @Override
    public int compare(Class<?> c1, Class<?> c2) {
      if (c1.equals(c2)) {
        return 0;
      } else if (c1.isAssignableFrom(c2)) {
        return 1;
      } else if (c2.isAssignableFrom(c1)) {
        return -1;
      } else {
        throw new RuntimeException("Can not compare unrelated types: " + c1.getCanonicalName() + " and " + c2.getCanonicalName());
      }
    }
  }

}
