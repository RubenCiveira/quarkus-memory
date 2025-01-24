package org.acme.common.sql;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface SqlResult<T> {

  CompletionStage<Optional<T>> one();

  CompletionStage<List<T>> limit(Optional<Integer> max);

  CompletionStage<List<T>> limit(int max);

  CompletionStage<List<T>> all();
}
