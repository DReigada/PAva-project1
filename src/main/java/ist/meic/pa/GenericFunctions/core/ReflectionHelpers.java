package ist.meic.pa.GenericFunctions.core;

import ist.meic.pa.GenericFunctions.exceptions.GenericFunctionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;


class ReflectionHelpers {
  static <A> A invokeMethod(Method method, Object[] args) {
    method.setAccessible(true);
    try {
      return (A) method.invoke(null, (Object[]) args);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new GenericFunctionException("Failed to call generic method", e);
    }
  }

  static Stream<Class<?>> getSuperClassesOf(Class<?> clazz) {
    Stream.Builder<Class<?>> streamBuilder = Stream.builder();

    do {
      streamBuilder.accept(clazz);
      for (Class<?> interfaze : clazz.getInterfaces()) {
        streamBuilder.accept(interfaze);
      }
      clazz = clazz.getSuperclass();
    } while (clazz != null);

    return streamBuilder.build();
  }
}
