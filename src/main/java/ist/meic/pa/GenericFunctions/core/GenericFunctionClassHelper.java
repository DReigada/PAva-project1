package ist.meic.pa.GenericFunctions.core;


import ist.meic.pa.GenericFunctions.core.ReflectionHelpers;
import ist.meic.pa.GenericFunctions.util.MethodMap;
import ist.meic.pa.GenericFunctions.util.MethodMapWithClass;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

public class GenericFunctionClassHelper {

  private final Class<?> clazz;
  private final String methodName;
  private final MethodMap[] argumentsMethodMaps;

  public GenericFunctionClassHelper(Class<?> clazz, String methodName) {
    this.clazz = clazz;
    this.methodName = methodName;

    int argsNumber = filteredMethods().findFirst().get().getParameterCount();
    argumentsMethodMaps = initializeMethodMaps(argsNumber);
  }

  public Object runFunction(Object[] arguments) {
    return ReflectionHelpers.runFunction(this, arguments);
  }

  public MethodMapWithClass[] validMethods(Object[] params) {
    MethodMapWithClass[] map = new MethodMapWithClass[params.length];
    for (int i = 0; i < params.length; i++) {
      map[i] = new MethodMapWithClass(argumentsMethodMaps[i], params[i].getClass());
    }
    return map;
  }

  private Stream<Method> filteredMethods() {
    return Arrays.stream(clazz.getDeclaredMethods())
        .filter(m -> m.getName().equals(methodName));
  }

  private MethodMap[] initializeMethodMaps(int size) {
    MethodMap[] methodMaps = Stream.generate(MethodMap::new).limit(size).toArray(MethodMap[]::new);

    filteredMethods()
        .forEach(m -> {
          Class<?>[] argTypes = m.getParameterTypes();

          for (int i = 0; i < methodMaps.length; i++) {
            methodMaps[i].put(argTypes[i], m);
          }
        });

    return methodMaps;
  }
}
