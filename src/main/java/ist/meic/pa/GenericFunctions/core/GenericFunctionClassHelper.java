package ist.meic.pa.GenericFunctions.core;


import ist.meic.pa.GenericFunctions.AfterMethod;
import ist.meic.pa.GenericFunctions.BeforeMethod;
import ist.meic.pa.GenericFunctions.exceptions.GenericFunctionException;
import ist.meic.pa.GenericFunctions.exceptions.NoApplicableGenericFunctionException;
import ist.meic.pa.GenericFunctions.util.cache.MethodsCache;
import ist.meic.pa.GenericFunctions.util.cache.MethodsCacheNoOp;
import ist.meic.pa.GenericFunctions.util.method.MethodMap;
import ist.meic.pa.GenericFunctions.util.method.MethodMapWithClass;
import ist.meic.pa.GenericFunctions.util.method.MethodsWrapper;
import ist.meic.pa.GenericFunctions.util.method.order.AbstractMethodComparator;
import ist.meic.pa.GenericFunctionsExtended.util.cache.MethodsCacheDefaultImpl;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenericFunctionClassHelper {

  private final Map<Integer, MethodMap[]> argumentsToPrimaryFunctionMaps;
  private final Map<Integer, MethodMap[]> argumentsToBeforeFunctionMaps;
  private final Map<Integer, MethodMap[]> argumentsToAfterFunctionMaps;

  private final String methodComparator;
  private final MethodsCache methodsCache;

  public GenericFunctionClassHelper(Class<?> clazz, String methodName, boolean withCache, String methodComparator) {
    argumentsToBeforeFunctionMaps = new HashMap<>();
    argumentsToAfterFunctionMaps = new HashMap<>();
    argumentsToPrimaryFunctionMaps = new HashMap<>();

    fillArgumentMaps(clazz, methodName);

    this.methodComparator = methodComparator;
    methodsCache = withCache ? new MethodsCacheDefaultImpl() : new MethodsCacheNoOp();
  }

  private static List<Method> orderMethods(MethodMapWithClass[] arr, Class<?>[] arguments, String methodComparatorClass, boolean reverse) {
    Comparator<Method> comparator =
        reverse ?
            getMethodComparator(methodComparatorClass, arguments).reversed() :
            getMethodComparator(methodComparatorClass, arguments);

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

  private static AbstractMethodComparator getMethodComparator(String comparator, Class<?>[] arguments) {
    try {
      return (AbstractMethodComparator) Class
          .forName(comparator)
          .getConstructor(Class[].class)
          .newInstance((Object) arguments);
    } catch (ReflectiveOperationException e) {
      throw new GenericFunctionException("Could not instantiate MethodComparator: " + comparator, e);
    }
  }

  private static LinkedHashSet<Method> getMethodsFor(MethodMap param, Class<?> clazz) {
    return ReflectionHelpers.getSuperClassesOf(clazz)
        .flatMap(superClass -> param.get(superClass).stream())
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  private static MethodMapWithClass[] applicableMethods(MethodMap[] methodsMaps, Class<?>[] paramsTypes) {
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

  private static Stream<Method> filteredMethods(Class<?> clazz, String methodName) {
    return Arrays.stream(clazz.getDeclaredMethods())
        .filter(m -> m.getName().equals(methodName));
  }

  private void fillArgumentMaps(Class<?> clazz, String methodName) {
    Predicate<Method> beforePredicate = method -> method.isAnnotationPresent(BeforeMethod.class);
    Predicate<Method> afterPredicate = method -> method.isAnnotationPresent(AfterMethod.class);
    Predicate<Method> primaryPredicate = beforePredicate.or(afterPredicate).negate();

    filteredMethods(clazz, methodName)
        .collect(Collectors.groupingBy(Method::getParameterCount))
        .forEach((argsNum, value) -> {
          argumentsToBeforeFunctionMaps.put(argsNum, createMethodMaps(argsNum, beforePredicate, value.stream()));
          argumentsToAfterFunctionMaps.put(argsNum, createMethodMaps(argsNum, afterPredicate, value.stream()));
          argumentsToPrimaryFunctionMaps.put(argsNum, createMethodMaps(argsNum, primaryPredicate, value.stream()));
        });
  }

  public Object runFunction(Object[] arguments) {
    Class<?>[] paramsClasses = Arrays.stream(arguments).map(Object::getClass).toArray(Class[]::new);

    MethodsWrapper wrap = methodsCache.getOrInsertIfNotExists(paramsClasses, () -> calculateMethodsFor(paramsClasses));

    wrap.before.forEach(m -> ReflectionHelpers.invokeMethod(m, arguments));
    Object primaryResult = ReflectionHelpers.invokeMethod(wrap.primary, arguments);
    wrap.after.forEach(m -> ReflectionHelpers.invokeMethod(m, arguments));

    return primaryResult;
  }

  private MethodsWrapper calculateMethodsFor(Class<?>[] paramsClasses) {
    List<Method> beforeMethods = getBeforeMethodsFor(paramsClasses);
    List<Method> afterMethods = getAfterMethodsFor(paramsClasses);
    Method primaryMethod = getPrimaryMethodFor(paramsClasses);

    MethodsWrapper newWraped = new MethodsWrapper(beforeMethods, afterMethods, primaryMethod);

    methodsCache.put(paramsClasses, newWraped);
    return newWraped;
  }

  private List<Method> getAfterMethodsFor(Class<?>[] arguments) {
    MethodMapWithClass[] applicableMethods = applicableMethods(argumentsToAfterFunctionMaps.get(arguments.length), arguments);
    return orderMethods(applicableMethods, arguments, methodComparator, true);
  }

  private List<Method> getBeforeMethodsFor(Class<?>[] arguments) {
    MethodMapWithClass[] applicableMethods = applicableMethods(argumentsToBeforeFunctionMaps.get(arguments.length), arguments);
    return orderMethods(applicableMethods, arguments, methodComparator, false);
  }

  private Method getPrimaryMethodFor(Class<?>[] arguments) {
    MethodMapWithClass[] applicableMethods = applicableMethods(argumentsToPrimaryFunctionMaps.get(arguments.length), arguments);
    List<Method> methods = orderMethods(applicableMethods, arguments, methodComparator, false);

    if (methods.size() > 0) {
      return methods.get(0);
    } else {
      throw new NoApplicableGenericFunctionException();
    }
  }

  private MethodMap[] createMethodMaps(int size, Predicate<Method> predicate, Stream<Method> methods) {
    MethodMap[] methodMaps = Stream.generate(MethodMap::new).limit(size).toArray(MethodMap[]::new);

    methods
        .filter(predicate)
        .forEach(m -> {
          Class<?>[] argTypes = m.getParameterTypes();

          for (int i = 0; i < methodMaps.length; i++) {
            methodMaps[i].put(argTypes[i], m);
          }
        });

    return methodMaps;
  }
}