package org.acme.features.market.place.application.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.application.service.MerchantsVisibilityService;
import org.acme.features.market.place.application.PlaceDto;
import org.acme.features.market.place.application.service.event.PlaceFixedFieldsPipelineStageEvent;
import org.acme.features.market.place.application.service.event.PlaceHiddenFieldsPipelineStageEvent;
import org.acme.features.market.place.application.service.event.PlaceVisibilityQueryPipelineStageEvent;
import org.acme.features.market.place.application.service.event.PlaceVisibleContentPipelineStageEvent;
import org.acme.features.market.place.domain.Places;
import org.acme.features.market.place.domain.gateway.PlaceCacheGateway;
import org.acme.features.market.place.domain.gateway.PlaceCached;
import org.acme.features.market.place.domain.gateway.PlaceCursor;
import org.acme.features.market.place.domain.gateway.PlaceFilter;
import org.acme.features.market.place.domain.gateway.PlaceReadRepositoryGateway;
import org.acme.features.market.place.domain.model.Place;
import org.acme.features.market.place.domain.model.PlaceRef;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class PlacesVisibilityService {

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final Places aggregate;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final PlaceCacheGateway cache;

  /**
   * Event source for maniputale the fix over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<PlaceFixedFieldsPipelineStageEvent> fireFix;

  /**
   * Event source for maniputale the hide over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<PlaceHiddenFieldsPipelineStageEvent> fireHide;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<PlaceVisibilityQueryPipelineStageEvent> fireVisibleFilter;

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<PlaceVisibleContentPipelineStageEvent> fireVisibleList;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final PlacesFormulaService formula;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final MerchantsVisibilityService merchantsVisibilityService;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final PlaceReadRepositoryGateway placeReadRepositoryGateway;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final Tracer tracer;

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param uid
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<Boolean> checkVisibility(Interaction prev, String uid) {
    Span startSpan = tracer.spanBuilder("place-check-item-visbility").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return retrieveVisible(prev, uid).thenApply(Optional::isPresent).whenComplete((val, ex) -> {
        if (null == ex) {
          startSpan.setStatus(StatusCode.OK);
        } else {
          startSpan.recordException(ex).setStatus(StatusCode.ERROR);
        }
        startSpan.end();
      });
    }
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
  public CompletionStage<PlaceDto> copyWithFixed(Interaction prev, Place original,
      PlaceDto source) {
    Span startSpan = tracer.spanBuilder("place-copy-existent-with-fixed").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return visiblesReferences(prev, source)
          .thenCompose(dto -> fieldsToFix(prev, original).thenApply(fixeds -> {
            fixeds.forEach(field -> dto.fixField(field, original));
            return dto;
          })).thenCompose(fixed -> formula.copyWithFormulas(prev, original, fixed))
          .whenComplete((val, ex) -> {
            if (null == ex) {
              startSpan.setStatus(StatusCode.OK);
            } else {
              startSpan.recordException(ex).setStatus(StatusCode.ERROR);
            }
            startSpan.end();
          });
    }
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<PlaceDto> copyWithFixed(Interaction prev, PlaceDto source) {
    Span startSpan = tracer.spanBuilder("place-copy-new-with-fixed").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return visiblesReferences(prev, source)
          .thenCompose(dto -> fieldsToFix(prev).thenApply(fixeds -> {
            fixeds.forEach(field -> dto.fixField(field));
            return dto;
          })).thenCompose(fixed -> formula.copyWithFormulas(prev, fixed))
          .whenComplete((val, ex) -> {
            if (null == ex) {
              startSpan.setStatus(StatusCode.OK);
            } else {
              startSpan.recordException(ex).setStatus(StatusCode.ERROR);
            }
            startSpan.end();
          });
    }
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param place
   * @return The input dto with hidden values
   */
  public CompletionStage<PlaceDto> copyWithHidden(Interaction prev, Place place) {
    Span startSpan = tracer.spanBuilder("place-copy-with-hidden").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return fieldsToHide(prev, place).thenApply(hidden -> {
        PlaceDto target = PlaceDto.from(place);
        hidden.forEach(target::hideField);
        return target;
      }).whenComplete((val, ex) -> {
        if (null == ex) {
          startSpan.setStatus(StatusCode.OK);
        } else {
          startSpan.recordException(ex).setStatus(StatusCode.ERROR);
        }
        startSpan.end();
      });
    }
  }

  /**
   * initialsFixFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @return initialsFixFields
   */
  public CompletionStage<Set<String>> fieldsToFix(Interaction prev) {
    Span startSpan = tracer.spanBuilder("place-field-to-fix-for-new").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      CompletionStage<Set<String>> fields = fieldsToHide(prev).thenApply(hidden -> {
        Set<String> set = new HashSet<>(aggregate.calcultadFields());
        set.addAll(hidden);
        return set;
      });
      PlaceFixedFieldsPipelineStageEvent value =
          PlaceFixedFieldsPipelineStageEvent.builder().fixed(fields).interaction(prev).build();
      fireFix.fire(value);
      return value.getFixed().whenComplete((val, ex) -> {
        if (null == ex) {
          startSpan.setAttribute("fieds", String.join(",", val));
          startSpan.setStatus(StatusCode.OK);
        } else {
          startSpan.recordException(ex).setStatus(StatusCode.ERROR);
        }
        startSpan.end();
      });
    }
  }

  /**
   * initialsFixFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param place
   * @return initialsFixFields
   */
  public CompletionStage<Set<String>> fieldsToFix(Interaction prev, PlaceRef place) {
    Span startSpan = tracer.spanBuilder("place-field-to-fix-for-existent").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      CompletionStage<Set<String>> fields =
          fieldsToFix(prev).thenCombine(fieldsToHide(prev, place), (set1, set2) -> {
            Set<String> set = new HashSet<>(set1);
            set.addAll(set2);
            return set;
          });
      PlaceFixedFieldsPipelineStageEvent value = PlaceFixedFieldsPipelineStageEvent.builder()
          .fixed(fields).place(place).interaction(prev).build();
      fireFix.fire(value);
      return value.getFixed().whenComplete((val, ex) -> {
        if (null == ex) {
          startSpan.setAttribute("fieds", String.join(",", val));
          startSpan.setStatus(StatusCode.OK);
        } else {
          startSpan.recordException(ex).setStatus(StatusCode.ERROR);
        }
        startSpan.end();
      });
    }
  }

  /**
   * initialsHideFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @return initialsHideFields
   */
  public CompletionStage<Set<String>> fieldsToHide(Interaction prev) {
    Span startSpan = tracer.spanBuilder("place-field-to-hide-for-new").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      CompletionStage<Set<String>> fields = CompletableFuture.completedFuture(Set.of());
      PlaceHiddenFieldsPipelineStageEvent value =
          PlaceHiddenFieldsPipelineStageEvent.builder().hidden(fields).interaction(prev).build();
      fireHide.fire(value);
      return value.getHidden().whenComplete((val, ex) -> {
        if (null == ex) {
          startSpan.setAttribute("fieds", String.join(",", val));
          startSpan.setStatus(StatusCode.OK);
        } else {
          startSpan.recordException(ex).setStatus(StatusCode.ERROR);
        }
        startSpan.end();
      });
    }
  }

  /**
   * initialsHideFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param place
   * @return initialsHideFields
   */
  public CompletionStage<Set<String>> fieldsToHide(Interaction prev, PlaceRef place) {
    Span startSpan = tracer.spanBuilder("place-field-to-hide-for-existent").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      CompletionStage<Set<String>> fields = fieldsToHide(prev);
      PlaceHiddenFieldsPipelineStageEvent value = PlaceHiddenFieldsPipelineStageEvent.builder()
          .hidden(fields).place(place).interaction(prev).build();
      fireHide.fire(value);
      return value.getHidden().whenComplete((val, ex) -> {
        if (null == ex) {
          startSpan.setAttribute("fieds", String.join(",", val));
          startSpan.setStatus(StatusCode.OK);
        } else {
          startSpan.recordException(ex).setStatus(StatusCode.ERROR);
        }
        startSpan.end();
      });
    }
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
  public CompletionStage<PlaceCached> listCachedVisibles(Interaction prev, PlaceFilter filter,
      PlaceCursor cursor) {
    Span startSpan = tracer.spanBuilder("place-list-cached-visible").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return applyPreVisibilityFilter(prev, filter)
          .thenCompose(visfilter -> cache.retrieve(visfilter, cursor).thenCompose(cached -> {
            if (cached.isPresent()) {
              startSpan.setAttribute("source", "cache");
              return CompletableFuture.completedStage(cached.get());
            } else {
              startSpan.setAttribute("source", "gateway");
              return queryItems(prev, filter, cursor).thenApply(values -> {
                cache.store(filter, cursor, values);
                return PlaceCached.builder().value(values).since(OffsetDateTime.now()).build();
              });
            }
          })).whenComplete((val, ex) -> {
            if (null == ex) {
              startSpan.setStatus(StatusCode.OK);
            } else {
              startSpan.recordException(ex).setStatus(StatusCode.ERROR);
            }
            startSpan.end();
          });
    }
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
  public CompletionStage<List<Place>> listVisibles(Interaction prev, PlaceFilter filter,
      PlaceCursor cursor) {
    Span startSpan = tracer.spanBuilder("place-list-visibles").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return applyPreVisibilityFilter(prev, filter)
          .thenCompose(visfilter -> queryItems(prev, visfilter, cursor)).whenComplete((val, ex) -> {
            if (null == ex) {
              startSpan.setStatus(StatusCode.OK);
            } else {
              startSpan.recordException(ex).setStatus(StatusCode.ERROR);
            }
            startSpan.end();
          });
    }
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param uid
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<PlaceCached> retrieveCachedVisible(Interaction prev, String uid) {
    Span startSpan = tracer.spanBuilder("place-retrieve-cached-visible").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return applyPreVisibilityFilter(prev, PlaceFilter.builder().uid(uid).build())
          .thenCompose(filter -> {
            filter.setUid(uid);
            PlaceCursor cursor = PlaceCursor.builder().limit(1).build();
            return cache.retrieve(filter, cursor).thenCompose(cached -> {
              if (cached.isPresent()) {
                startSpan.setAttribute("source", "cache");
                return CompletableFuture.completedStage(cached.get());
              } else {
                startSpan.setAttribute("source", "gateway");
                return queryItem(prev, uid, filter).thenApply(value -> {
                  List<Place> values = value.map(List::of).orElseGet(List::of);
                  cache.store(filter, cursor, values);
                  return PlaceCached.builder().value(values).since(OffsetDateTime.now()).build();
                });
              }
            });
          }).whenComplete((val, ex) -> {
            if (null == ex) {
              startSpan.setStatus(StatusCode.OK);
            } else {
              startSpan.recordException(ex).setStatus(StatusCode.ERROR);
            }
            startSpan.end();
          });
    }
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param uid
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<Optional<Place>> retrieveVisible(Interaction prev, String uid) {
    Span startSpan = tracer.spanBuilder("place-retrieve-visible").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return applyPreVisibilityFilter(prev, PlaceFilter.builder().uid(uid).build())
          .thenCompose(filter -> queryItem(prev, uid, filter)).whenComplete((val, ex) -> {
            if (null == ex) {
              startSpan.setStatus(StatusCode.OK);
            } else {
              startSpan.recordException(ex).setStatus(StatusCode.ERROR);
            }
            startSpan.end();
          });
    }
  }

  /**
   * The self filter modified with the prepared values.
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param filter The filter to retrieve values
   * @return The self filter modified with the prepared values.
   */
  private CompletionStage<PlaceFilter> applyPreVisibilityFilter(Interaction prev,
      PlaceFilter filter) {
    Span startSpan = tracer.spanBuilder("place-calculate-visible-filter").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      PlaceVisibilityQueryPipelineStageEvent visible = PlaceVisibilityQueryPipelineStageEvent
          .builder().filter(CompletableFuture.completedFuture(filter)).interaction(prev).build();
      fireVisibleFilter.fire(visible);
      return visible.getFilter().whenComplete((val, ex) -> {
        if (null == ex) {
          startSpan.setStatus(StatusCode.OK);
        } else {
          startSpan.recordException(ex).setStatus(StatusCode.ERROR);
        }
        startSpan.end();
      });
    }
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param placeRefs The source interaction
   * @return The input dto with hidden values
   */
  private CompletionStage<List<Place>> evaluatePostVisibility(Interaction prev,
      List<Place> placeRefs) {
    Span startSpan = tracer.spanBuilder("place-evaluate-liste-post-visibility").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      PlaceVisibleContentPipelineStageEvent list = PlaceVisibleContentPipelineStageEvent.builder()
          .visibles(CompletableFuture.completedFuture(new ArrayList<>(placeRefs))).interaction(prev)
          .build();
      fireVisibleList.fire(list);
      return list.getVisibles().whenComplete((val, ex) -> {
        if (null == ex) {
          startSpan.setStatus(StatusCode.OK);
        } else {
          startSpan.recordException(ex).setStatus(StatusCode.ERROR);
        }
        startSpan.end();
      });
    }
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param placeRef The source interaction
   * @return The input dto with hidden values
   */
  private CompletionStage<Optional<Place>> evaluatePostVisibility(Interaction prev,
      Place placeRef) {
    Span startSpan = tracer.spanBuilder("place-evaluate-item-post-visibility").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return evaluatePostVisibility(prev, List.of(placeRef))
          .thenApply(list -> list.isEmpty() ? Optional.<Place>empty() : Optional.of(list.get(0)))
          .whenComplete((val, ex) -> {
            if (null == ex) {
              startSpan.setStatus(StatusCode.OK);
            } else {
              startSpan.recordException(ex).setStatus(StatusCode.ERROR);
            }
            startSpan.end();
          });
    }
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
  private CompletionStage<Optional<Place>> queryItem(Interaction prev, String uid,
      PlaceFilter filter) {
    Span startSpan = tracer.spanBuilder("place-query-item").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return placeReadRepositoryGateway.retrieve(uid, Optional.of(filter))
          .thenCompose(stored -> stored.map(retrieved -> evaluatePostVisibility(prev, retrieved))
              .orElseGet(() -> CompletableFuture.completedStage(Optional.empty())))
          .whenComplete((val, ex) -> {
            if (null == ex) {
              startSpan.setStatus(StatusCode.OK);
            } else {
              startSpan.recordException(ex).setStatus(StatusCode.ERROR);
            }
            startSpan.end();
          });
    }
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
  private CompletionStage<List<Place>> queryItems(Interaction prev, PlaceFilter filter,
      PlaceCursor cursor) {
    Span startSpan = tracer.spanBuilder("place-query-items").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      return placeReadRepositoryGateway.list(filter, cursor)
          .thenCompose(
              slide -> slide.filterAndFillAgain(values -> evaluatePostVisibility(prev, values)))
          .whenComplete((val, ex) -> {
            if (null == ex) {
              startSpan.setStatus(StatusCode.OK);
            } else {
              startSpan.recordException(ex).setStatus(StatusCode.ERROR);
            }
            startSpan.end();
          });
    }
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  private CompletionStage<PlaceDto> visiblesReferences(Interaction prev, PlaceDto source) {
    Span startSpan = tracer.spanBuilder("place-visibles-reference").startSpan();
    try (Scope scope = startSpan.makeCurrent()) {
      List<CompletionStage<Boolean>> related = new ArrayList<>();
      if (null != source.getMerchant()) {
        related.add(merchantsVisibilityService.checkVisibility(prev,
            source.getMerchant().getReferenceValue()));
      }
      return CompletableFuture.allOf(related.toArray(new CompletableFuture[0]))
          .thenApply(_void -> source).whenComplete((val, ex) -> {
            if (null == ex) {
              startSpan.setStatus(StatusCode.OK);
            } else {
              startSpan.recordException(ex).setStatus(StatusCode.ERROR);
            }
            startSpan.end();
          });
    }
  }
}
