package org.acme.features.market.merchant.application.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.application.MerchantDto;
import org.acme.features.market.merchant.application.service.event.MerchantFixedFieldsPipelineStageEvent;
import org.acme.features.market.merchant.application.service.event.MerchantHiddenFieldsPipelineStageEvent;
import org.acme.features.market.merchant.application.service.event.MerchantVisibilityQueryPipelineStageEvent;
import org.acme.features.market.merchant.application.service.event.MerchantVisibleContentPipelineStageEvent;
import org.acme.features.market.merchant.domain.Merchants;
import org.acme.features.market.merchant.domain.gateway.MerchantCacheGateway;
import org.acme.features.market.merchant.domain.gateway.MerchantCached;
import org.acme.features.market.merchant.domain.gateway.MerchantCursor;
import org.acme.features.market.merchant.domain.gateway.MerchantFilter;
import org.acme.features.market.merchant.domain.gateway.MerchantReadRepositoryGateway;
import org.acme.features.market.merchant.domain.model.Merchant;
import org.acme.features.market.merchant.domain.model.MerchantRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class MerchantsVisibilityService {

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final Merchants aggregate;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final MerchantCacheGateway cache;

  /**
   * Event source for maniputale the fix over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MerchantFixedFieldsPipelineStageEvent> fireFix;

  /**
   * Event source for maniputale the hide over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MerchantHiddenFieldsPipelineStageEvent> fireHide;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MerchantVisibilityQueryPipelineStageEvent> fireVisibleFilter;

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MerchantVisibleContentPipelineStageEvent> fireVisibleList;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final MerchantsFormulaService formula;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final MerchantReadRepositoryGateway merchantReadRepositoryGateway;

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param uid
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<Boolean> checkVisibility(Interaction prev, String uid) {
    return retrieveVisible(prev, uid).thenApply(Optional::isPresent);
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param original The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<MerchantDto> copyWithFixed(Interaction prev, Merchant original,
      MerchantDto source) {
    return fieldsToFix(prev, original).getFixed().thenApply(fixeds -> {
      fixeds.forEach(field -> source.fixField(field, original));
      return source;
    }).thenCompose(fixed -> formula.copyWithFormulas(prev, original, fixed));
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<MerchantDto> copyWithFixed(Interaction prev, MerchantDto source) {
    return fieldsToFix(prev).getFixed().thenApply(fixeds -> {
      fixeds.forEach(field -> source.fixField(field));
      return source;
    }).thenCompose(fixed -> formula.copyWithFormulas(prev, fixed));
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param merchant
   * @return The input dto with hidden values
   */
  public CompletionStage<MerchantDto> copyWithHidden(Interaction prev, Merchant merchant) {
    return fieldsToHide(prev, merchant).getHidden().thenApply(hidden -> {
      MerchantDto target = MerchantDto.from(merchant);
      hidden.forEach(target::hideField);
      return target;
    });
  }

  /**
   * initialsFixFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @return initialsFixFields
   */
  public MerchantFixedFieldsPipelineStageEvent fieldsToFix(Interaction prev) {
    CompletionStage<Set<String>> fields = fieldsToHide(prev).getHidden().thenApply(hidden -> {
      Set<String> set = new HashSet<>(aggregate.calcultadFields());
      set.addAll(hidden);
      return set;
    });
    MerchantFixedFieldsPipelineStageEvent value =
        MerchantFixedFieldsPipelineStageEvent.builder().fixed(fields).interaction(prev).build();
    fireFix.fire(value);
    return value;
  }

  /**
   * initialsFixFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param merchant
   * @return initialsFixFields
   */
  public MerchantFixedFieldsPipelineStageEvent fieldsToFix(Interaction prev, MerchantRef merchant) {
    CompletionStage<Set<String>> fields = fieldsToFix(prev).getFixed()
        .thenCombine(fieldsToHide(prev, merchant).getHidden(), (set1, set2) -> {
          Set<String> set = new HashSet<>(set1);
          set.addAll(set2);
          return set;
        });
    MerchantFixedFieldsPipelineStageEvent value = MerchantFixedFieldsPipelineStageEvent.builder()
        .fixed(fields).merchant(merchant).interaction(prev).build();
    fireFix.fire(value);
    return value;
  }

  /**
   * initialsHideFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @return initialsHideFields
   */
  public MerchantHiddenFieldsPipelineStageEvent fieldsToHide(Interaction prev) {
    CompletionStage<Set<String>> fields = CompletableFuture.completedFuture(Set.of());
    MerchantHiddenFieldsPipelineStageEvent value =
        MerchantHiddenFieldsPipelineStageEvent.builder().hidden(fields).interaction(prev).build();
    fireHide.fire(value);
    return value;
  }

  /**
   * initialsHideFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param merchant
   * @return initialsHideFields
   */
  public MerchantHiddenFieldsPipelineStageEvent fieldsToHide(Interaction prev,
      MerchantRef merchant) {
    CompletionStage<Set<String>> fields = fieldsToHide(prev).getHidden();
    MerchantHiddenFieldsPipelineStageEvent value = MerchantHiddenFieldsPipelineStageEvent.builder()
        .hidden(fields).merchant(merchant).interaction(prev).build();
    fireHide.fire(value);
    return value;
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param filter The filter to retrieve values
   * @param cursor The filter to retrieve values
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<MerchantCached> listCachedVisibles(Interaction prev, MerchantFilter filter,
      MerchantCursor cursor) {
    return applyPreVisibilityFilter(prev, filter)
        .thenCompose(visfilter -> cache.retrieve(visfilter, cursor).thenCompose(cached -> {
          if (cached.isPresent()) {
            return CompletableFuture.completedStage(cached.get());
          } else {
            return queryItems(prev, filter, cursor).thenApply(values -> {
              cache.store(filter, cursor, values);
              return MerchantCached.builder().value(values).since(OffsetDateTime.now()).build();
            });
          }
        }));
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param filter The filter to retrieve values
   * @param cursor The filter to retrieve values
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<List<Merchant>> listVisibles(Interaction prev, MerchantFilter filter,
      MerchantCursor cursor) {
    return applyPreVisibilityFilter(prev, filter)
        .thenCompose(visfilter -> queryItems(prev, visfilter, cursor));
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param uid
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<MerchantCached> retrieveCachedVisible(Interaction prev, String uid) {
    return applyPreVisibilityFilter(prev, MerchantFilter.builder().uid(uid).build())
        .thenCompose(filter -> {
          filter.setUid(uid);
          MerchantCursor cursor = MerchantCursor.builder().limit(1).build();
          return cache.retrieve(filter, cursor).thenCompose(cached -> {
            if (cached.isPresent()) {
              return CompletableFuture.completedStage(cached.get());
            } else {
              return queryItem(prev, uid, filter).thenApply(value -> {
                List<Merchant> values = value.map(List::of).orElseGet(List::of);
                cache.store(filter, cursor, values);
                return MerchantCached.builder().value(values).since(OffsetDateTime.now()).build();
              });
            }
          });
        });
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param uid
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<Optional<Merchant>> retrieveVisible(Interaction prev, String uid) {
    return applyPreVisibilityFilter(prev, MerchantFilter.builder().uid(uid).build())
        .thenCompose(filter -> queryItem(prev, uid, filter));
  }

  /**
   * The self filter modified with the prepared values.
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param filter The filter to retrieve values
   * @return The self filter modified with the prepared values.
   */
  private CompletionStage<MerchantFilter> applyPreVisibilityFilter(Interaction prev,
      MerchantFilter filter) {
    MerchantVisibilityQueryPipelineStageEvent visible = MerchantVisibilityQueryPipelineStageEvent
        .builder().filter(CompletableFuture.completedFuture(filter)).interaction(prev).build();
    fireVisibleFilter.fire(visible);
    return visible.getFilter();
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param merchantRefs The source interaction
   * @return The input dto with hidden values
   */
  private CompletionStage<List<Merchant>> evaluatePostVisibility(Interaction prev,
      List<Merchant> merchantRefs) {
    MerchantVisibleContentPipelineStageEvent list = MerchantVisibleContentPipelineStageEvent
        .builder().visibles(CompletableFuture.completedFuture(new ArrayList<>(merchantRefs)))
        .interaction(prev).build();
    fireVisibleList.fire(list);
    return list.getVisibles();
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param merchantRef The source interaction
   * @return The input dto with hidden values
   */
  private CompletionStage<Optional<Merchant>> evaluatePostVisibility(Interaction prev,
      Merchant merchantRef) {
    return evaluatePostVisibility(prev, List.of(merchantRef))
        .thenApply(list -> list.isEmpty() ? Optional.empty() : Optional.of(list.get(0)));
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param uid
   * @param filter The filter to retrieve values
   * @return The input entity with the copy values without hidden
   */
  private CompletionStage<Optional<Merchant>> queryItem(Interaction prev, String uid,
      MerchantFilter filter) {
    return merchantReadRepositoryGateway.retrieve(uid, Optional.of(filter))
        .thenCompose(stored -> stored.map(retrieved -> evaluatePostVisibility(prev, retrieved))
            .orElseGet(() -> CompletableFuture.completedStage(Optional.empty())));
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param filter The filter to retrieve values
   * @param cursor The filter to retrieve values
   * @return The input entity with the copy values without hidden
   */
  private CompletionStage<List<Merchant>> queryItems(Interaction prev, MerchantFilter filter,
      MerchantCursor cursor) {
    return merchantReadRepositoryGateway.list(filter, cursor).thenCompose(
        slide -> slide.filterAndFillAgain(values -> evaluatePostVisibility(prev, values)));
  }
}