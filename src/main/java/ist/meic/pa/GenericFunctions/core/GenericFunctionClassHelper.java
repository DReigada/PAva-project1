package ist.meic.pa.GenericFunctions.core;


import ist.meic.pa.GenericFunctions.AfterMethod;
import ist.meic.pa.GenericFunctions.BeforeMethod;
import ist.meic.pa.GenericFunctions.exceptions.NoApplicableGenericFunctionException;
import ist.meic.pa.GenericFunctions.util.cache.MethodsCache;
import ist.meic.pa.GenericFunctions.util.cache.MethodsCacheNoOp;
import ist.meic.pa.GenericFunctions.util.method.MethodMap;
import ist.meic.pa.GenericFunctions.util.method.MethodMapWithClass;
import ist.meic.pa.GenericFunctions.util.method.MethodsWrapper;
import ist.meic.pa.GenericFunctionsExtended.util.cache.MethodsCacheDefaultImpl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ist.meic.pa.GenericFunctions.core.MethodHelpers.*;

public class GenericFunctionClassHelper {

  private final Map<Integer, MethodMap[]> argumentsToPrimaryFunctionMaps;
  private final Map<Integer, MethodMap[]> argumentsToBeforeFunctionMaps;
  private final Map<Integer, MethodMap[]> argumentsToAfterFunctionMaps;

  private final String methodComparatorClassName;
  private final MethodsCache methodsCache;

  public GenericFunctionClassHelper(Class<?> clazz, String methodName, boolean withCache, String methodComparatorClassName) {
    argumentsToBeforeFunctionMaps = new HashMap<>();
    argumentsToAfterFunctionMaps = new HashMap<>();
    argumentsToPrimaryFunctionMaps = new HashMap<>();

    fillArgumentMaps(clazz, methodName);

    this.methodComparatorClassName = methodComparatorClassName;
    methodsCache = withCache ? new MethodsCacheDefaultImpl() : new MethodsCacheNoOp();
  }

  public Object runFunction(Object[] arguments) {
    Class<?>[] paramsClasses = Arrays.stream(arguments).map(Object::getClass).toArray(Class[]::new);

    MethodsWrapper wrap = methodsCache.getOrInsertIfNotExists(paramsClasses, () -> findMethodsFor(paramsClasses));

    wrap.before.forEach(m -> invokeStaticMethod(m, arguments));
    Object primaryResult = invokeStaticMethod(wrap.primary, arguments);
    wrap.after.forEach(m -> invokeStaticMethod(m, arguments));

    return primaryResult;
  }

  private void fillArgumentMaps(Class<?> clazz, String methodName) {
    Predicate<Method> beforePredicate = method -> method.isAnnotationPresent(BeforeMethod.class);
    Predicate<Method> afterPredicate = method -> method.isAnnotationPresent(AfterMethod.class);
    Predicate<Method> primaryPredicate = beforePredicate.or(afterPredicate).negate();

    filterMethodsByName(clazz, methodName)
        .collect(Collectors.groupingBy(Method::getParameterCount))
        .forEach((argsNum, value) -> {
          argumentsToBeforeFunctionMaps.put(argsNum, createMethodMaps(argsNum, beforePredicate, value.stream()));
          argumentsToAfterFunctionMaps.put(argsNum, createMethodMaps(argsNum, afterPredicate, value.stream()));
          argumentsToPrimaryFunctionMaps.put(argsNum, createMethodMaps(argsNum, primaryPredicate, value.stream()));
        });
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

  private MethodsWrapper findMethodsFor(Class<?>[] paramsClasses) {
    List<Method> beforeMethods = getBeforeMethodsFor(paramsClasses);
    List<Method> afterMethods = getAfterMethodsFor(paramsClasses);
    Method primaryMethod = getPrimaryMethodFor(paramsClasses);

    MethodsWrapper newWraped = new MethodsWrapper(beforeMethods, afterMethods, primaryMethod);

    methodsCache.put(paramsClasses, newWraped);
    return newWraped;
  }

  private List<Method> getAfterMethodsFor(Class<?>[] arguments) {
    MethodMapWithClass[] applicableMethods = filterApplicableMethods(argumentsToAfterFunctionMaps.get(arguments.length), arguments);
    return orderMethods(applicableMethods, arguments, methodComparatorClassName, true);
  }

  private List<Method> getBeforeMethodsFor(Class<?>[] arguments) {
    MethodMapWithClass[] applicableMethods = filterApplicableMethods(argumentsToBeforeFunctionMaps.get(arguments.length), arguments);
    return orderMethods(applicableMethods, arguments, methodComparatorClassName, false);
  }

  private Method getPrimaryMethodFor(Class<?>[] arguments) {
    MethodMapWithClass[] applicableMethods = filterApplicableMethods(argumentsToPrimaryFunctionMaps.get(arguments.length), arguments);
    List<Method> methods = orderMethods(applicableMethods, arguments, methodComparatorClassName, false);

    if (methods.size() > 0) {
      return methods.get(0);
    } else {
      throw new NoApplicableGenericFunctionException();
    }
  }
}