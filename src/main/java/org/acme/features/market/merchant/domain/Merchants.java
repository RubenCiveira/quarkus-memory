package org.acme.features.market.merchant.domain;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import org.acme.common.action.ParametrizedFuturePipe;
import org.acme.common.action.PriorityComparator;
import org.acme.features.market.merchant.domain.model.Merchant;
import org.acme.features.market.merchant.domain.model.Merchant.MerchantBuilder;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantEnabledVO;
import org.acme.features.market.merchant.domain.rule.MerchantActionType;
import org.acme.features.market.merchant.domain.rule.MerchantBuilderRule;
import org.acme.features.market.merchant.domain.rule.MerchantRule;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Instance;

@RequestScoped
public class Merchants {

  /**
   * @autogenerated AggregateGenerator
   */
  private final ParametrizedFuturePipe<MerchantBuilder, MerchantActionType, Optional<Merchant>> builderRules;

  /**
   * @autogenerated AggregateGenerator
   */
  private final ParametrizedFuturePipe<Merchant, MerchantActionType, Optional<Merchant>> rules;

  /**
   * @autogenerated AggregateGenerator
   */
  private Map<String, BiFunction<CompletionStage<MerchantBuilder>, Optional<Merchant>, CompletionStage<MerchantBuilder>>> calculatedFields =
      Map.of("enabled", this::calculateEnabled);

  /**
   * @autogenerated AggregateGenerator
   * @param rules
   * @param builderRules
   */
  public Merchants(final Instance<MerchantRule> rules,
      final Instance<MerchantBuilderRule> builderRules) {
    this.rules = new ParametrizedFuturePipe<>(MerchantActionType.values(), rules.stream().toList(),
        new PriorityComparator<>());
    this.builderRules = new ParametrizedFuturePipe<>(MerchantActionType.values(),
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
  public CompletionStage<Merchant> clean(final Merchant entity) {
    return rules.applyCurrent(MerchantActionType.DELETE, entity, Optional.of(entity));
  }

  /**
   * The entity
   *
   * @autogenerated AggregateGenerator
   * @param entity a filter to retrieve only matching values
   * @return The entity
   */
  public CompletionStage<Merchant> disable(final Merchant entity) {
    MerchantBuilder builder = entity.toBuilder();
    return applyModify(MerchantActionType.DISABLE, Optional.of(entity), builder.enabledValue(false),
        "enabled");
  }

  /**
   * The entity
   *
   * @autogenerated AggregateGenerator
   * @param entity a filter to retrieve only matching values
   * @return The entity
   */
  public CompletionStage<Merchant> enable(final Merchant entity) {
    MerchantBuilder builder = entity.toBuilder();
    return applyModify(MerchantActionType.ENABLE, Optional.of(entity), builder.enabledValue(true),
        "enabled");
  }

  /**
   * The entity
   *
   * @autogenerated AggregateGenerator
   * @param builder a filter to retrieve only matching values
   * @return The entity
   */
  public CompletionStage<Merchant> initialize(final MerchantBuilder builder) {
    return applyModify(MerchantActionType.CREATE, Optional.empty(), builder, null);
  }

  /**
   * The entity
   *
   * @autogenerated AggregateGenerator
   * @param base a filter to retrieve only matching values
   * @param builder a filter to retrieve only matching values
   * @return The entity
   */
  public CompletionStage<Merchant> modify(final Merchant base, final MerchantBuilder builder) {
    return applyModify(MerchantActionType.UPDATE, Optional.of(base), builder, null);
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
  private CompletionStage<Merchant> applyModify(final MerchantActionType type,
      final Optional<Merchant> original, final MerchantBuilder builder, final String ignore) {
    CompletionStage<MerchantBuilder> ruledBuilder =
        this.builderRules.applyCurrent(type, builder, original);
    for (Entry<String, BiFunction<CompletionStage<MerchantBuilder>, Optional<Merchant>, CompletionStage<MerchantBuilder>>> entry : calculatedFields
        .entrySet()) {
      if (!entry.getKey().equals(ignore)) {
        ruledBuilder = entry.getValue().apply(ruledBuilder, original);
      }
    }
    return ruledBuilder.thenCompose(
        resultBuilder -> rules.applyCurrent(type, resultBuilder.buildValid(), original));
  }

  /**
   * @autogenerated AggregateGenerator
   * @param promise
   * @param original
   * @return
   */
  private CompletionStage<MerchantBuilder> calculateEnabled(
      final CompletionStage<MerchantBuilder> promise, final Optional<Merchant> original) {
    return promise.thenApply(builder -> builder.enabledValue(
        original.map(Merchant::getEnabled).map(MerchantEnabledVO::getValue).orElse(false)));
  }
}
