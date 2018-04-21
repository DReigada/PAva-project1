package ist.meic.pa.GenericFunctions.util.cache;

import ist.meic.pa.GenericFunctions.util.MethodsWrapper;

import java.util.Optional;
import java.util.function.Supplier;

public class MethodsCacheNoOp implements MethodsCache {
  @Override
  public void put(Class<?>[] paramsClasses, MethodsWrapper methods) {
  }

  @Override
  public Optional<MethodsWrapper> get(Class<?>[] paramsClasses) {
    return Optional.empty();
  }

  @Override
  public MethodsWrapper getOrInsertIfNotExists(Class<?>[] paramsClasses, Supplier<MethodsWrapper> other) {
    return other.get();
  }
}
