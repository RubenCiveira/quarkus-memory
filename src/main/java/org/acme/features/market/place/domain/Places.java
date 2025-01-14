package org.acme.features.market.place.domain;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import org.acme.common.action.ParametrizedFuturePipe;
import org.acme.common.action.PriorityComparator;
import org.acme.features.market.place.domain.model.Place;
import org.acme.features.market.place.domain.model.Place.PlaceBuilder;
import org.acme.features.market.place.domain.rule.PlaceActionType;
import org.acme.features.market.place.domain.rule.PlaceBuilderRule;
import org.acme.features.market.place.domain.rule.PlaceRule;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Instance;

@RequestScoped
public class Places {

  /**
   * @autogenerated AggregateGenerator
   */
  private final ParametrizedFuturePipe<PlaceBuilder, PlaceActionType, Optional<Place>> builderRules;

  /**
   * @autogenerated AggregateGenerator
   */
  private final ParametrizedFuturePipe<Place, PlaceActionType, Optional<Place>> rules;

  /**
   * @autogenerated AggregateGenerator
   */
  private Map<String, BiFunction<CompletionStage<PlaceBuilder>, Optional<Place>, CompletionStage<PlaceBuilder>>> calculatedFields =
      Map.of();

  /**
   * @autogenerated AggregateGenerator
   * @param rules
   * @param builderRules
   */
  public Places(final Instance<PlaceRule> rules, final Instance<PlaceBuilderRule> builderRules) {
    this.rules = new ParametrizedFuturePipe<>(PlaceActionType.values(), rules.stream().toList(),
        new PriorityComparator<>());
    this.builderRules = new ParametrizedFuturePipe<>(PlaceActionType.values(),
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
  public CompletionStage<Place> clean(final Place entity) {
    return rules.applyCurrent(PlaceActionType.DELETE, entity, Optional.of(entity));
  }

  /**
   * The entity
   *
   * @autogenerated AggregateGenerator
   * @param builder a filter to retrieve only matching values
   * @return The entity
   */
  public CompletionStage<Place> initialize(final PlaceBuilder builder) {
    return applyModify(PlaceActionType.CREATE, Optional.empty(), builder, null);
  }

  /**
   * The entity
   *
   * @autogenerated AggregateGenerator
   * @param base a filter to retrieve only matching values
   * @param builder a filter to retrieve only matching values
   * @return The entity
   */
  public CompletionStage<Place> modify(final Place base, final PlaceBuilder builder) {
    return applyModify(PlaceActionType.UPDATE, Optional.of(base), builder, null);
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
  private CompletionStage<Place> applyModify(final PlaceActionType type,
      final Optional<Place> original, final PlaceBuilder builder, final String ignore) {
    CompletionStage<PlaceBuilder> ruledBuilder =
        this.builderRules.applyCurrent(type, builder, original);
    for (Entry<String, BiFunction<CompletionStage<PlaceBuilder>, Optional<Place>, CompletionStage<PlaceBuilder>>> entry : calculatedFields
        .entrySet()) {
      if (!entry.getKey().equals(ignore)) {
        ruledBuilder = entry.getValue().apply(ruledBuilder, original);
      }
    }
    return ruledBuilder.thenCompose(
        resultBuilder -> rules.applyCurrent(type, resultBuilder.buildValid(), original));
  }
}
