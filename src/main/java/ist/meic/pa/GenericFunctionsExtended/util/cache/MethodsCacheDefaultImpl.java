package ist.meic.pa.GenericFunctionsExtended.util.cache;

import ist.meic.pa.GenericFunctions.util.cache.MethodsCache;
import ist.meic.pa.GenericFunctions.util.method.MethodsWrapper;
import ist.meic.pa.GenericFunctions.util.ParametersClassWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class MethodsCacheDefaultImpl implements MethodsCache {
  private final Map<ParametersClassWrapper, MethodsWrapper> map;

  public MethodsCacheDefaultImpl() {
    map = new HashMap<>();
  }

  public void put(Class<?>[] paramsClasses, MethodsWrapper methods) {
    ParametersClassWrapper wrappedClasses = new ParametersClassWrapper(paramsClasses);
    map.put(wrappedClasses, methods);
  }

  public Optional<MethodsWrapper> get(Class<?>[] paramsClasses) {
    return Optional.ofNullable(map.get(new ParametersClassWrapper(paramsClasses)));
  }

  @Override
  public MethodsWrapper getOrInsertIfNotExists(Class<?>[] paramsClasses, Supplier<MethodsWrapper> other) {
    Optional<MethodsWrapper> result = get(paramsClasses);
    if (result.isPresent()) {
      return result.get();
    } else {
      MethodsWrapper newValue = other.get();
      put(paramsClasses, newValue);
      return newValue;
    }
  }

}
