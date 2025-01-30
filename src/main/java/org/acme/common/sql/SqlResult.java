package org.acme.common.sql;

import java.util.Optional;
import org.acme.common.reactive.Stream;

public interface SqlResult<T> {

  Stream<T> one();

  Stream<T> limit(Optional<Integer> max);

  Stream<T> limit(int max);

  Stream<T> all();
}
