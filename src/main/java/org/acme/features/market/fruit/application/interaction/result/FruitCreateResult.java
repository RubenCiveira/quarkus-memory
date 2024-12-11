package org.acme.features.market.fruit.application.interaction.result;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.acme.features.market.fruit.application.interaction.FruitDto;
import org.acme.features.market.fruit.application.interaction.command.FruitCreateCommand;

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
public class FruitCreateResult {

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final FruitCreateCommand command;

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final CompletableFuture<Optional<FruitDto>> fruit;
}