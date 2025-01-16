package org.acme.features.market.medal.application.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.medal.application.MedalDto;
import org.acme.features.market.medal.application.service.event.MedalFixedFieldsPipelineStageEvent;
import org.acme.features.market.medal.application.service.event.MedalHiddenFieldsPipelineStageEvent;
import org.acme.features.market.medal.application.service.event.MedalVisibilityQueryPipelineStageEvent;
import org.acme.features.market.medal.application.service.event.MedalVisibleContentPipelineStageEvent;
import org.acme.features.market.medal.domain.Medals;
import org.acme.features.market.medal.domain.gateway.MedalCacheGateway;
import org.acme.features.market.medal.domain.gateway.MedalCached;
import org.acme.features.market.medal.domain.gateway.MedalCursor;
import org.acme.features.market.medal.domain.gateway.MedalFilter;
import org.acme.features.market.medal.domain.gateway.MedalReadRepositoryGateway;
import org.acme.features.market.medal.domain.model.Medal;
import org.acme.features.market.medal.domain.model.MedalRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class MedalsVisibilityService {

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final Medals aggregate;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final MedalCacheGateway cache;

  /**
   * Event source for maniputale the fix over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MedalFixedFieldsPipelineStageEvent> fireFix;

  /**
   * Event source for maniputale the hide over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MedalHiddenFieldsPipelineStageEvent> fireHide;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MedalVisibilityQueryPipelineStageEvent> fireVisibleFilter;

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MedalVisibleContentPipelineStageEvent> fireVisibleList;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final MedalsFormulaService formula;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final MedalReadRepositoryGateway medalReadRepositoryGateway;

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
  public CompletionStage<MedalDto> copyWithFixed(Interaction prev, Medal original,
      MedalDto source) {
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
  public CompletionStage<MedalDto> copyWithFixed(Interaction prev, MedalDto source) {
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
   * @param medal
   * @return The input dto with hidden values
   */
  public CompletionStage<MedalDto> copyWithHidden(Interaction prev, Medal medal) {
    return fieldsToHide(prev, medal).getHidden().thenApply(hidden -> {
      MedalDto target = MedalDto.from(medal);
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
  public MedalFixedFieldsPipelineStageEvent fieldsToFix(Interaction prev) {
    CompletionStage<Set<String>> fields = fieldsToHide(prev).getHidden().thenApply(hidden -> {
      Set<String> set = new HashSet<>(aggregate.calcultadFields());
      set.addAll(hidden);
      return set;
    });
    MedalFixedFieldsPipelineStageEvent value =
        MedalFixedFieldsPipelineStageEvent.builder().fixed(fields).interaction(prev).build();
    fireFix.fire(value);
    return value;
  }

  /**
   * initialsFixFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param medal
   * @return initialsFixFields
   */
  public MedalFixedFieldsPipelineStageEvent fieldsToFix(Interaction prev, MedalRef medal) {
    CompletionStage<Set<String>> fields = fieldsToFix(prev).getFixed()
        .thenCombine(fieldsToHide(prev, medal).getHidden(), (set1, set2) -> {
          Set<String> set = new HashSet<>(set1);
          set.addAll(set2);
          return set;
        });
    MedalFixedFieldsPipelineStageEvent value = MedalFixedFieldsPipelineStageEvent.builder()
        .fixed(fields).medal(medal).interaction(prev).build();
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
  public MedalHiddenFieldsPipelineStageEvent fieldsToHide(Interaction prev) {
    CompletionStage<Set<String>> fields = CompletableFuture.completedFuture(Set.of());
    MedalHiddenFieldsPipelineStageEvent value =
        MedalHiddenFieldsPipelineStageEvent.builder().hidden(fields).interaction(prev).build();
    fireHide.fire(value);
    return value;
  }

  /**
   * initialsHideFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param medal
   * @return initialsHideFields
   */
  public MedalHiddenFieldsPipelineStageEvent fieldsToHide(Interaction prev, MedalRef medal) {
    CompletionStage<Set<String>> fields = fieldsToHide(prev).getHidden();
    MedalHiddenFieldsPipelineStageEvent value = MedalHiddenFieldsPipelineStageEvent.builder()
        .hidden(fields).medal(medal).interaction(prev).build();
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
  public CompletionStage<MedalCached> listCachedVisibles(Interaction prev, MedalFilter filter,
      MedalCursor cursor) {
    return applyPreVisibilityFilter(prev, filter)
        .thenCompose(visfilter -> cache.retrieve(visfilter, cursor).thenCompose(cached -> {
          if (cached.isPresent()) {
            return CompletableFuture.completedStage(cached.get());
          } else {
            return queryItems(prev, filter, cursor).thenApply(values -> {
              cache.store(filter, cursor, values);
              return MedalCached.builder().value(values).since(OffsetDateTime.now()).build();
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
  public CompletionStage<List<Medal>> listVisibles(Interaction prev, MedalFilter filter,
      MedalCursor cursor) {
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
  public CompletionStage<MedalCached> retrieveCachedVisible(Interaction prev, String uid) {
    return applyPreVisibilityFilter(prev, MedalFilter.builder().uid(uid).build())
        .thenCompose(filter -> {
          filter.setUid(uid);
          MedalCursor cursor = MedalCursor.builder().limit(1).build();
          return cache.retrieve(filter, cursor).thenCompose(cached -> {
            if (cached.isPresent()) {
              return CompletableFuture.completedStage(cached.get());
            } else {
              return queryItem(prev, uid, filter).thenApply(value -> {
                List<Medal> values = value.map(List::of).orElseGet(List::of);
                cache.store(filter, cursor, values);
                return MedalCached.builder().value(values).since(OffsetDateTime.now()).build();
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
  public CompletionStage<Optional<Medal>> retrieveVisible(Interaction prev, String uid) {
    return applyPreVisibilityFilter(prev, MedalFilter.builder().uid(uid).build())
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
  private CompletionStage<MedalFilter> applyPreVisibilityFilter(Interaction prev,
      MedalFilter filter) {
    MedalVisibilityQueryPipelineStageEvent visible = MedalVisibilityQueryPipelineStageEvent
        .builder().filter(CompletableFuture.completedFuture(filter)).interaction(prev).build();
    fireVisibleFilter.fire(visible);
    return visible.getFilter();
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param medalRefs The source interaction
   * @return The input dto with hidden values
   */
  private CompletionStage<List<Medal>> evaluatePostVisibility(Interaction prev,
      List<Medal> medalRefs) {
    MedalVisibleContentPipelineStageEvent list = MedalVisibleContentPipelineStageEvent.builder()
        .visibles(CompletableFuture.completedFuture(new ArrayList<>(medalRefs))).interaction(prev)
        .build();
    fireVisibleList.fire(list);
    return list.getVisibles();
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param medalRef The source interaction
   * @return The input dto with hidden values
   */
  private CompletionStage<Optional<Medal>> evaluatePostVisibility(Interaction prev,
      Medal medalRef) {
    return evaluatePostVisibility(prev, List.of(medalRef))
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
  private CompletionStage<Optional<Medal>> queryItem(Interaction prev, String uid,
      MedalFilter filter) {
    return medalReadRepositoryGateway.retrieve(uid, Optional.of(filter))
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
  private CompletionStage<List<Medal>> queryItems(Interaction prev, MedalFilter filter,
      MedalCursor cursor) {
    return medalReadRepositoryGateway.list(filter, cursor).thenCompose(
        slide -> slide.filterAndFillAgain(values -> evaluatePostVisibility(prev, values)));
  }
}
