package ist.meic.pa.GenericFunctions.util.method;

import java.lang.reflect.Method;
import java.util.*;

public class MethodMap {
  Map<Class<?>, Set<Method>> map;

  public MethodMap() {
    map = new HashMap<>();
  }

  public void put(Class<?> clazz, Method method) {
    Set<Method> methodList = map.get(clazz);

    if (methodList == null) {
      map.put(clazz, new HashSet<>(Arrays.asList(method)));
    } else {
      methodList.add(method);
    }
  }

  public Set<Method> get(Class<?> clazz) {
    return new HashSet<>(Optional.ofNullable(map.get(clazz)).orElse(Collections.emptySet()));
  }
}