package org.acme.features.market.fruit.application.interaction.result;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.acme.features.market.fruit.application.interaction.FruitDto;
import org.acme.features.market.fruit.application.interaction.query.FruitListQuery;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RegisterForReflection
@With
public class FruitListResult {

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final CompletableFuture<List<FruitDto>> fruits;

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  private final FruitListQuery query;

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public Optional<FruitListQuery> getQuery() {
    return Optional.ofNullable(query);
  }
}