package org.acme.features.market.area.domain;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import org.acme.common.action.ParametrizedFuturePipe;
import org.acme.common.action.PriorityComparator;
import org.acme.features.market.area.domain.model.Area;
import org.acme.features.market.area.domain.model.Area.AreaBuilder;
import org.acme.features.market.area.domain.rule.AreaActionType;
import org.acme.features.market.area.domain.rule.AreaBuilderRule;
import org.acme.features.market.area.domain.rule.AreaRule;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Instance;

@RequestScoped
public class Areas {

  /**
   * @autogenerated AggregateGenerator
   */
  private final ParametrizedFuturePipe<AreaBuilder, AreaActionType, Optional<Area>> builderRules;

  /**
   * @autogenerated AggregateGenerator
   */
  private final ParametrizedFuturePipe<Area, AreaActionType, Optional<Area>> rules;

  /**
   * @autogenerated AggregateGenerator
   */
  private Map<String, BiFunction<CompletionStage<AreaBuilder>, Optional<Area>, CompletionStage<AreaBuilder>>> calculatedFields =
      Map.of();

  /**
   * @autogenerated AggregateGenerator
   * @param rules
   * @param builderRules
   */
  public Areas(final Instance<AreaRule> rules, final Instance<AreaBuilderRule> builderRules) {
    this.rules = new ParametrizedFuturePipe<>(AreaActionType.values(), rules.stream().toList(),
        new PriorityComparator<>());
    this.builderRules = new ParametrizedFuturePipe<>(AreaActionType.values(),
        builderRules.stream().toList(), new PriorityComparator<>());
  }

  /**
   * @autogenerated AggregateGenerator
   * @return
   */
  public Set<String> calcultadFields() {
    return calculatedFields.keySet();
  }

  /**
   * The entity
   *
   * @autogenerated AggregateGenerator
   * @param entity a filter to retrieve only matching values
   * @return The entity
   */
  public CompletionStage<Area> clean(final Area entity) {
    return rules.applyCurrent(AreaActionType.DELETE, entity, Optional.of(entity));
  }

  /**
   * The entity
   *
   * @autogenerated AggregateGenerator
   * @param builder a filter to retrieve only matching values
   * @return The entity
   */
  public CompletionStage<Area> initialize(final AreaBuilder builder) {
    return applyModify(AreaActionType.CREATE, Optional.empty(), builder, null);
  }

  /**
   * The entity
   *
   * @autogenerated AggregateGenerator
   * @param base a filter to retrieve only matching values
   * @param builder a filter to retrieve only matching values
   * @return The entity
   */
  public CompletionStage<Area> modify(final Area base, final AreaBuilder builder) {
    return applyModify(AreaActionType.UPDATE, Optional.of(base), builder, null);
  }

  /**
   * The entity
   *
   * @autogenerated AggregateGenerator
   * @param type
   * @param original a filter to retrieve only matching values
   * @param builder a filter to retrieve only matching values
   * @param ignore
   * @return The entity
   */
  private CompletionStage<Area> applyModify(final AreaActionType type,
      final Optional<Area> original, final AreaBuilder builder, final String ignore) {
    CompletionStage<AreaBuilder> ruledBuilder =
        this.builderRules.applyCurrent(type, builder, original);
    for (Entry<String, BiFunction<CompletionStage<AreaBuilder>, Optional<Area>, CompletionStage<AreaBuilder>>> entry : calculatedFields
        .entrySet()) {
      if (!entry.getKey().equals(ignore)) {
        ruledBuilder = entry.getValue().apply(ruledBuilder, original);
      }
    }
    return ruledBuilder.thenCompose(
        resultBuilder -> rules.applyCurrent(type, resultBuilder.buildValid(), original));
  }
}
