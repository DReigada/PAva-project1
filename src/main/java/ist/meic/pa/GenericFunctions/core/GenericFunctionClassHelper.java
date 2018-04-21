package ist.meic.pa.GenericFunctions.core;


import ist.meic.pa.GenericFunctions.AfterMethod;
import ist.meic.pa.GenericFunctions.BeforeMethod;
import ist.meic.pa.GenericFunctions.exceptions.NoApplicableGenericFunctionException;
import ist.meic.pa.GenericFunctions.util.*;
import ist.meic.pa.GenericFunctions.util.cache.MethodsCache;
import ist.meic.pa.GenericFunctions.util.cache.MethodsCacheNoOp;
import ist.meic.pa.GenericFunctionsExtended.util.cache.MethodsCacheDefaultImpl;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenericFunctionClassHelper {

  private final Class<?> clazz;
  private final String methodName;
  private final MethodMap[] argumentsToPrimaryFunctionMaps;
  private final MethodMap[] argumentsToBeforeFunctionMaps;
  private final MethodMap[] argumentsToAfterFunctionMaps;

  private final MethodsCache methodsCache;

  public GenericFunctionClassHelper(Class<?> clazz, String methodName, boolean withCache) {
    this.clazz = clazz;
    this.methodName = methodName;

    int argsNumber = filteredMethods(clazz, methodName).findFirst().get().getParameterCount();

    Predicate<Method> beforePredicate = method -> method.isAnnotationPresent(BeforeMethod.class);
    Predicate<Method> afterPredicate = method -> method.isAnnotationPresent(AfterMethod.class);
    Predicate<Method> primaryPredicate = beforePredicate.and(afterPredicate).negate();

    argumentsToBeforeFunctionMaps = createMethodMaps(argsNumber, beforePredicate);
    argumentsToAfterFunctionMaps = createMethodMaps(argsNumber, afterPredicate);
    argumentsToPrimaryFunctionMaps = createMethodMaps(argsNumber, primaryPredicate);

    methodsCache = withCache ? new MethodsCacheDefaultImpl() : new MethodsCacheNoOp();
  }

  private static List<Method> orderMethods(MethodMapWithClass[] arr, Class<?>[] arguments) {
    DefaultMethodComparator comparator = new DefaultMethodComparator(arguments);

    return Arrays.stream(arr)
        .map(mapWithClass -> getMethodsFor(mapWithClass.map, mapWithClass.clazz))
        .reduce((acc, set) -> {
          acc.retainAll(set);
          return acc;
        })
        .filter(set -> set.size() > 0)
        .map(set -> {
          List<Method> list = new ArrayList<>(set);
          list.sort(comparator); // TODO: sort is not necessary since only the first value is needed
          return list;
        })
        .orElse(Collections.emptyList());
  }

  private static LinkedHashSet<Method> getMethodsFor(MethodMap param, Class<?> clazz) {
    return ReflectionHelpers.getSuperClassesOf(clazz)
        .flatMap(superClass -> param.get(superClass).stream())
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  private static MethodMapWithClass[] applicableMethods(MethodMap[] methodsMaps, Class<?>[] paramsTypes) {
    MethodMapWithClass[] map = new MethodMapWithClass[paramsTypes.length];
    for (int i = 0; i < paramsTypes.length; i++) {
      map[i] = new MethodMapWithClass(methodsMaps[i], paramsTypes[i]);
    }
    return map;
  }

  private static Stream<Method> filteredMethods(Class<?> clazz, String methodName) {
    return Arrays.stream(clazz.getDeclaredMethods())
        .filter(m -> m.getName().equals(methodName));
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
    return orderMethods(applicableMethods(argumentsToAfterFunctionMaps, arguments), arguments);
  }

  private List<Method> getBeforeMethodsFor(Class<?>[] arguments) {
    return orderMethods(applicableMethods(argumentsToBeforeFunctionMaps, arguments), arguments);
  }

  private Method getPrimaryMethodFor(Class<?>[] arguments) {
    List<Method> methods = orderMethods(applicableMethods(argumentsToPrimaryFunctionMaps, arguments), arguments);

    if (methods.size() > 0) {
      return methods.get(0);
    } else {
      throw new NoApplicableGenericFunctionException();
    }
  }

  private MethodMap[] createMethodMaps(int size, Predicate<Method> predicate) {
    MethodMap[] methodMaps = Stream.generate(MethodMap::new).limit(size).toArray(MethodMap[]::new);

    filteredMethods(clazz, methodName)
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