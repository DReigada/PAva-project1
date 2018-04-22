package ist.meic.pa.GenericFunctions.core;

import ist.meic.pa.GenericFunctions.exceptions.GenericFunctionException;
import ist.meic.pa.GenericFunctions.util.method.MethodMap;
import ist.meic.pa.GenericFunctions.util.method.MethodMapWithClass;
import ist.meic.pa.GenericFunctions.util.method.order.AbstractMethodComparator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodHelpers {

  private MethodHelpers() {
  }

  static Object invokeStaticMethod(Method method, Object[] args) {
    method.setAccessible(true);
    try {
      return method.invoke(null, (Object[]) args);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new GenericFunctionException("Failed to call generic method", e);
    }
  }

  static List<Method> orderMethods(MethodMapWithClass[] arr, Class<?>[] arguments, String methodComparatorClass, boolean reverse) {
    Comparator<Method> comparator =
        reverse ?
            instantiateMethodComparator(methodComparatorClass, arguments).reversed() :
            instantiateMethodComparator(methodComparatorClass, arguments);

    return Arrays.stream(arr)
        .map(mapWithClass -> getMethodsFor(mapWithClass.map, mapWithClass.clazz))
        .reduce((acc, set) -> {
          acc.retainAll(set);
          return acc;
        })
        .filter(set -> set.size() > 0)
        .map(set -> {
          List<Method> list = new ArrayList<>(set);
          list.sort(comparator);
          return list;
        })
        .orElse(Collections.emptyList());
  }

  static MethodMapWithClass[] filterApplicableMethods(MethodMap[] methodsMaps, Class<?>[] paramsTypes) {
    if (methodsMaps == null) {
      return new MethodMapWithClass[]{};
    } else {
      MethodMapWithClass[] map = new MethodMapWithClass[paramsTypes.length];
      for (int i = 0; i < paramsTypes.length; i++) {
        map[i] = new MethodMapWithClass(methodsMaps[i], paramsTypes[i]);
      }
      return map;
    }
  }

  static Stream<Method> filterMethodsByName(Class<?> clazz, String methodName) {
    return Arrays.stream(clazz.getDeclaredMethods())
        .filter(m -> m.getName().equals(methodName));
  }

  private static AbstractMethodComparator instantiateMethodComparator(String comparatorClassName, Class<?>[] arguments) {
    try {
      return (AbstractMethodComparator) Class
          .forName(comparatorClassName)
          .getConstructor(Class[].class)
          .newInstance((Object) arguments);
    } catch (ReflectiveOperationException e) {
      throw new GenericFunctionException("Could not instantiate MethodComparator: " + comparatorClassName, e);
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
