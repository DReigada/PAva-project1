package ist.meic.pa.GenericFunctions;

import ist.meic.pa.GenericFunctions.exceptions.GenericFunctionException;
import ist.meic.pa.GenericFunctions.exceptions.NoApplicableGenericFunctionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Helpers {
  public static <A> A invokeMethod(LinkedHashSet<Method> methods, Object... args) {
    if (methods.iterator().hasNext()) {
      Method method = methods.iterator().next();
      try {
        return (A) method.invoke(null, args);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new GenericFunctionException("Failed to call generic method", e);
      }
    } else {
      throw new NoApplicableGenericFunctionException();
    }
  }

  public static LinkedHashSet<Method> getMethodsFor(Map<Class<?>, Method> param, Class<?> clazz) {
    return getSuperClassesOf(clazz)
        .flatMap(superClass ->
            Optional.ofNullable(param.get(superClass))
                .map(Stream::of)
                .orElseGet(Stream::empty))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  private static Stream<Class<?>> getSuperClassesOf(Class<?> clazz) {
    Stream.Builder<Class<?>> streamBuilder = Stream.builder();

    do {
      streamBuilder.add(clazz);
      clazz = clazz.getSuperclass();
    } while (!clazz.equals(Object.class));

    return streamBuilder.build();
  }
}
