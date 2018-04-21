package ist.meic.pa.GenericFunctions.util.method.order;

import ist.meic.pa.GenericFunctions.core.ReflectionHelpers;
import ist.meic.pa.GenericFunctions.util.Pair;
import ist.meic.pa.GenericFunctions.util.Streams;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
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
      Pair<Class<?>, Class<?>>[] zipped =
          Streams.zip(m1.getParameterTypes(), m2.getParameterTypes(), (BiFunction<Class<?>, Class<?>, Pair>) Pair::new)
              .toArray((IntFunction<Pair<Class<?>, Class<?>>[]>) Pair[]::new);

      return Streams.zip(zipped, referenceArguments, (pair, arg) -> new ClassComparator(arg).compare(pair.fst, pair.snd))
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

    /**
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
