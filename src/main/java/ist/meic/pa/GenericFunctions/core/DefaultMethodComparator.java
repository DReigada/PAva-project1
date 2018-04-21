package ist.meic.pa.GenericFunctions.core;

import com.google.common.collect.Streams;
import ist.meic.pa.GenericFunctions.util.Pair;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class DefaultMethodComparator extends AbstractMethodComparator {

  public DefaultMethodComparator(Class<?>[] referenceArguments) {
    super(referenceArguments);
  }

  @Override
  public int compare(Method m1, Method m2) {
    if (m1.equals(m2)) {
      return 0;
    } else {
      Stream<Class<?>> m1Types = Arrays.stream(m1.getParameterTypes());
      Stream<Class<?>> m2Types = Arrays.stream(m2.getParameterTypes());

      Stream<Pair<Class<?>, Class<?>>> zipped = Streams
          .zip(m1Types, m2Types, (a, b) -> new Pair(a, b));

      Stream<Class<?>> argsStream = Arrays.stream(referenceArguments);

      return Streams.zip(zipped, argsStream, (pair, arg) -> new ClassComparator(arg).compare(pair.fst, pair.snd))
          .filter(i -> i != 0)
          .findFirst()
          .orElse(0);
    }
  }

  private static class ClassComparator implements Comparator<Class<?>> {

    private Class<?> referenceClass;

    public ClassComparator(Class<?> referenceClass) {
      this.referenceClass = referenceClass;
    }

    /*
     * c1 is bigger than c2 if it is a superClass/interface of c2,
     * unless c1 or c2 are interfaces in that case the reference class is used to compare
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
        Stream<Class<?>> superClasses = ReflectionHelpers.getSuperClassesOf(referenceClass);

        Class<?> first = superClasses
            .filter(c -> c.equals(c1) || c.equals(c2))
            .limit(2)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Can not compare unrelated types: " + c1.getName() + " and " + c2.getName() + " given reference: " + referenceClass.getName()));

        if (first.equals(c1)) {
          return -1;
        } else {
          return 1;
        }
      }
    }
  }

}
