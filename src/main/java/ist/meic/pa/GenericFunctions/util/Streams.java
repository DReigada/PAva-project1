package ist.meic.pa.GenericFunctions.util;

import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {
  public static <A, B, R> Stream<R> zip(A[] a, B[] b, BiFunction<A, B, R> function) {
    return IntStream
        .range(0, Math.min(a.length, b.length))
        .mapToObj(i -> function.apply(a[i], b[i]));
  }
}
