package ist.meic.pa.GenericFunctions.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MethodsCache {
  final Map<ParametersClassWrapper, MethodsWrapper> map;

  public MethodsCache() {
    map = new HashMap<>();
  }

  public void put(Class<?>[] paramsClasses, MethodsWrapper methods) {
    ParametersClassWrapper wrappedClasses = new ParametersClassWrapper(paramsClasses);
    map.put(wrappedClasses, methods);
  }

  public Optional<MethodsWrapper> get(Class<?>[] paramsClasses) {
    return Optional.ofNullable(map.get(new ParametersClassWrapper(paramsClasses)));
  }

}
