package org.acme.common.sql;

import java.util.List;
import java.util.Optional;

public interface SqlResult<T> {

  Optional<T> one();

  List<T> limit(int max);

  List<T> all();
}
