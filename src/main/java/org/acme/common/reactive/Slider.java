package org.acme.common.reactive;

import java.util.function.Function;

// Un slide es un stream, que permite avanzar más datos...
public interface Slider<T> extends Stream<T> {

  Stream<T> slide(Function<T, Stream<Boolean>> filter);
}
