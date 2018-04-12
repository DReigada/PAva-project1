package ist.meic.pa.GenericFunctions;

import ist.meic.pa.GenericFunctions.exceptions.GenericFunctionException;
import ist.meic.pa.GenericFunctions.exceptions.NoApplicableGenericFunctionException;
import ist.meic.pa.GenericFunctions.util.DefaultMethodComparator;
import ist.meic.pa.GenericFunctions.util.GenericFunctionClassHelper;
import ist.meic.pa.GenericFunctions.util.MethodMap;
import ist.meic.pa.GenericFunctions.util.MethodMapWithClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ReflectionHelpers {

  public static Object runFunction(GenericFunctionClassHelper helper, Object[] arguments) {
    Class<?>[] paramsClasses = Arrays.stream(arguments).map(Object::getClass).toArray(Class[]::new);

    Method bestMethod = getBestMethod(helper.validMethods(arguments), paramsClasses);
    return invokeMethod(bestMethod, arguments);
  }

  private static Method getBestMethod(MethodMapWithClass[] arr, Class<?>[] arguments) {
    DefaultMethodComparator comparator = new DefaultMethodComparator(arguments);

    return Arrays.stream(arr)
        .map(mapWithClass -> getMethodsFor(mapWithClass.map, mapWithClass.clazz))
        .reduce((acc, set) -> {
          acc.retainAll(set);
          return acc;
        })
        .filter(set -> set.size() > 0)
        .map(set -> {
          ArrayList<Method> list = new ArrayList<>(set);
          list.sort(comparator); // TODO: sort is not necessary since only the first value is needed
          return list.get(0);
        })
        .orElseThrow(NoApplicableGenericFunctionException::new);
  }

  private static <A> A invokeMethod(Method method, Object[] args) {
    method.setAccessible(true);
    try {
      return (A) method.invoke(null, (Object[]) args);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new GenericFunctionException("Failed to call generic method", e);
    }
  }

  private static LinkedHashSet<Method> getMethodsFor(MethodMap param, Class<?> clazz) {
    return getSuperClassesOf(clazz)
        .flatMap(superClass -> param.get(superClass).stream())
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  public static Stream<Class<?>> getSuperClassesOf(Class<?> clazz) {
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
