package org.acme.features.market.fruit.application.interaction.result;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.acme.common.action.Interaction;
import org.acme.features.market.fruit.application.interaction.FruitDto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@With
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RegisterForReflection
public class FruitDeleteResult {

  /**
   * The source interaction
   *
   * @autogenerated EntityGenerator
   */
  private final Interaction command;

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final CompletableFuture<Optional<FruitDto>> fruit;

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public Optional<Interaction> getCommand() {
    return Optional.ofNullable(command);
  }
}