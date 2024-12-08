package org.acme.features.market.fruit.domain.interaction.result;

import java.util.Optional;

import org.acme.features.market.fruit.domain.interaction.FruitDto;
import org.acme.features.market.fruit.domain.interaction.command.FruitUpdateCommand;
import org.acme.features.market.fruit.domain.model.Fruit;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@RegisterForReflection
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FruitUpdateResult {

  /**
   * A new dto with a result list
   *
   * @autogenerated EntityGenerator
   * @param prev The source interaction responsible of generating the dto
   * @param fruit The list with values
   * @return A new dto with a result list
   */
  public static FruitUpdateResult from(final FruitUpdateCommand prev, final Optional<Fruit> fruit) {
    return FruitUpdateResult.builder().fruit(fruit.map(FruitDto::from)).command(prev).build();
  }

  /**
   * A new dto with a result list
   *
   * @autogenerated EntityGenerator
   * @param prev The source interaction responsible of generating the dto
   * @param fruit The list with values
   * @return A new dto with a result list
   */
  public static FruitUpdateResult fromDto(final FruitUpdateCommand prev,
      final Optional<FruitDto> fruit) {
    return FruitUpdateResult.builder().fruit(fruit).command(prev).build();
  }

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final FruitUpdateCommand command;

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  private final Optional<FruitDto> fruit;
}
