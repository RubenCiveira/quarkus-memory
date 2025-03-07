/* @autogenerated */
package org.acme.common.infrastructure.sql;

import java.util.List;
import java.util.Optional;

public interface SqlResult<T> {

  Optional<T> one();

  List<T> limit(Optional<Integer> max);

  List<T> limit(int max);

  List<T> all();
}
