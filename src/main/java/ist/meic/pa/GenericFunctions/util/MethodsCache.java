package ist.meic.pa.GenericFunctions.util;

import java.util.Optional;
import java.util.function.Supplier;

public interface MethodsCache {
  void put(Class<?>[] paramsClasses, MethodsWrapper methods);

  Optional<MethodsWrapper> get(Class<?>[] paramsClasses);

  MethodsWrapper getOrInsertIfNotExists(Class<?>[] paramsClasses, Supplier<MethodsWrapper> other);
}
