package org.acme.features.market.fruit.application.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.acme.common.action.Interaction;
import org.acme.features.market.fruit.application.FruitDto;
import org.acme.features.market.fruit.application.service.event.FruitFixedFieldsPipelineEvent;
import org.acme.features.market.fruit.application.service.event.FruitHiddenFieldsPipelineEvent;
import org.acme.features.market.fruit.application.service.event.FruitVisibilityQueryPipelineEvent;
import org.acme.features.market.fruit.application.service.event.FruitVisibleContentPipelineEvent;
import org.acme.features.market.fruit.domain.Fruits;
import org.acme.features.market.fruit.domain.gateway.FruitCacheGateway;
import org.acme.features.market.fruit.domain.gateway.FruitCached;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.features.market.fruit.domain.gateway.FruitReadRepositoryGateway;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.FruitRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class FruitsVisibilityService {

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final Fruits aggregate;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final FruitCacheGateway cache;

  /**
   * Event source for maniputale the fix over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<FruitFixedFieldsPipelineEvent> fireFix;

  /**
   * Event source for maniputale the hide over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<FruitHiddenFieldsPipelineEvent> fireHide;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<FruitVisibilityQueryPipelineEvent> fireVisibleFilter;

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<FruitVisibleContentPipelineEvent> fireVisibleList;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final FruitsFormulaService formula;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final FruitReadRepositoryGateway fruitReadRepositoryGateway;

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param uid
   * @return The input entity with the copy values without hidden
   */
  public boolean checkVisibility(Interaction prev, String uid) {
    return retrieveVisible(prev, uid).isPresent();
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param uids
   * @return The input entity with the copy values without hidden
   */
  public boolean checkVisibility(Interaction prev, List<String> uids) {
    return listVisibles(prev, FruitFilter.builder().uids(uids).build(),
        FruitCursor.builder().build()).isEmpty();
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
  public FruitDto copyWithFixed(Interaction prev, Fruit original, FruitDto source) {
    fieldsToFix(prev, original).forEach(field -> source.fixField(field, original));
    return formula.copyWithFormulas(prev, original, source);
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  public FruitDto copyWithFixed(Interaction prev, FruitDto source) {
    fieldsToFix(prev).forEach(field -> source.fixField(field));
    return formula.copyWithFormulas(prev, source);
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param fruit
   * @return The input dto with hidden values
   */
  public FruitDto copyWithHidden(Interaction prev, Fruit fruit) {
    FruitDto target = FruitDto.from(fruit);
    fieldsToHide(prev, fruit).forEach(target::hideField);
    return target;
  }

  /**
   * initialsFixFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @return initialsFixFields
   */
  public Set<String> fieldsToFix(Interaction prev) {
    Set<String> fields = new HashSet<>(fieldsToHide(prev));
    fields.addAll(aggregate.calcultadFields());
    FruitFixedFieldsPipelineEvent value =
        FruitFixedFieldsPipelineEvent.builder().fields(fields).query(prev).build();
    fireFix.fire(value);
    return value.getFields();
  }

  /**
   * initialsFixFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param fruit
   * @return initialsFixFields
   */
  public Set<String> fieldsToFix(Interaction prev, FruitRef fruit) {
    Set<String> fields = new HashSet<>(fieldsToHide(prev, fruit));
    fields.addAll(aggregate.calcultadFields());
    FruitFixedFieldsPipelineEvent value =
        FruitFixedFieldsPipelineEvent.builder().fields(fields).fruit(fruit).query(prev).build();
    fireFix.fire(value);
    return value.getFields();
  }

  /**
   * initialsHideFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @return initialsHideFields
   */
  public Set<String> fieldsToHide(Interaction prev) {
    Set<String> fields = new HashSet<>();
    FruitHiddenFieldsPipelineEvent value =
        FruitHiddenFieldsPipelineEvent.builder().fields(fields).query(prev).build();
    fireHide.fire(value);
    return value.getFields();
  }

  /**
   * initialsHideFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param fruit
   * @return initialsHideFields
   */
  public Set<String> fieldsToHide(Interaction prev, FruitRef fruit) {
    Set<String> fields = new HashSet<>();
    FruitHiddenFieldsPipelineEvent value =
        FruitHiddenFieldsPipelineEvent.builder().fields(fields).fruit(fruit).query(prev).build();
    fireHide.fire(value);
    return value.getFields();
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
  public FruitCached listCachedVisibles(Interaction prev, FruitFilter filter, FruitCursor cursor) {
    FruitFilter visibleFilter = applyPreVisibilityFilter(prev, filter);
    return cache.retrieve(visibleFilter, cursor)
        .orElseGet(() -> cache.store(visibleFilter, cursor, listVisibles(prev, filter, cursor)));
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
  public List<Fruit> listVisibles(Interaction prev, FruitFilter filter, FruitCursor cursor) {
    return queryItems(prev, applyPreVisibilityFilter(prev, filter), cursor);
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param uid
   * @return The input entity with the copy values without hidden
   */
  public FruitCached retrieveCachedVisible(Interaction prev, String uid) {
    FruitCursor cursor = FruitCursor.builder().limit(1).build();
    FruitFilter visibleFilter =
        applyPreVisibilityFilter(prev, FruitFilter.builder().uid(uid).build());
    return cache.retrieve(visibleFilter, cursor).orElseGet(() -> {
      List<Fruit> list = retrieveVisible(prev, uid).<List<Fruit>>map(List::of).orElseGet(List::of);
      return cache.store(visibleFilter, cursor, list);
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
  public Optional<Fruit> retrieveVisible(Interaction prev, String uid) {
    FruitFilter filter = applyPreVisibilityFilter(prev, FruitFilter.builder().uid(uid).build());
    return queryItem(prev, uid, filter);
  }

  /**
   * The self filter modified with the prepared values.
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param filter The filter to retrieve values
   * @return The self filter modified with the prepared values.
   */
  private FruitFilter applyPreVisibilityFilter(Interaction prev, FruitFilter filter) {
    FruitVisibilityQueryPipelineEvent visible =
        FruitVisibilityQueryPipelineEvent.builder().filter(filter).interaction(prev).build();
    fireVisibleFilter.fire(visible);
    return visible.getFilter();
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param fruitRef The source interaction
   * @return The input dto with hidden values
   */
  private boolean evaluatePostVisibility(Interaction prev, Fruit fruitRef) {
    FruitVisibleContentPipelineEvent accesible = FruitVisibleContentPipelineEvent.builder()
        .visible(true).entity(fruitRef).interaction(prev).build();
    fireVisibleList.fire(accesible);
    return accesible.getVisible();
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
  private Optional<Fruit> queryItem(Interaction prev, String uid, FruitFilter filter) {
    return fruitReadRepositoryGateway.retrieve(uid, Optional.of(filter))
        .filter(values -> evaluatePostVisibility(prev, values));
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
  private List<Fruit> queryItems(Interaction prev, FruitFilter filter, FruitCursor cursor) {
    List<Fruit> list = new ArrayList<>();
    Iterator<Fruit> slide = fruitReadRepositoryGateway.list(filter, cursor)
        .slide(values -> evaluatePostVisibility(prev, values));
    while (slide.hasNext()) {
      list.add(slide.next());
    }
    return list;
  }
}
