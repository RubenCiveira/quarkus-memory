package org.acme.features.market.medal.application.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.acme.common.action.Interaction;
import org.acme.features.market.medal.application.MedalDto;
import org.acme.features.market.medal.application.service.proposal.MedalFixedFieldsProposal;
import org.acme.features.market.medal.application.service.proposal.MedalHiddenFieldsProposal;
import org.acme.features.market.medal.application.service.proposal.MedalVisibilityQueryProposal;
import org.acme.features.market.medal.application.service.proposal.MedalVisibleContentProposal;
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
  private final Event<MedalFixedFieldsProposal> fireFix;

  /**
   * Event source for maniputale the hide over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MedalHiddenFieldsProposal> fireHide;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MedalVisibilityQueryProposal> fireVisibleFilter;

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MedalVisibleContentProposal> fireVisibleList;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final MedalsCalculatorService formula;

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
    return listVisibles(prev, MedalFilter.builder().uids(uids).build(),
        MedalCursor.builder().build()).isEmpty();
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
  public MedalDto copyWithFixed(Interaction prev, Medal original, MedalDto source) {
    fieldsToFix(prev, original).forEach(field -> source.fixField(field, original));
    return formula.copyWithFormulas(prev, source, original);
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  public MedalDto copyWithFixed(Interaction prev, MedalDto source) {
    fieldsToFix(prev).forEach(field -> source.fixField(field));
    return formula.copyWithFormulas(prev, source);
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param medal
   * @return The input dto with hidden values
   */
  public MedalDto copyWithHidden(Interaction prev, Medal medal) {
    MedalDto target = MedalDto.from(medal);
    fieldsToHide(prev, medal).forEach(target::hideField);
    return target;
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param filter The filter to retrieve values
   * @return The input entity with the copy values without hidden
   */
  public long countVisibles(Interaction prev, MedalFilter filter) {
    return medalReadRepositoryGateway.count(applyPreVisibilityFilter(prev, filter));
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
    MedalFixedFieldsProposal value =
        MedalFixedFieldsProposal.builder().fields(fields).query(prev).build();
    fireFix.fire(value);
    return value.getFields();
  }

  /**
   * initialsFixFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param medal
   * @return initialsFixFields
   */
  public Set<String> fieldsToFix(Interaction prev, MedalRef medal) {
    Set<String> fields = new HashSet<>(fieldsToHide(prev, medal));
    fields.addAll(aggregate.calcultadFields());
    MedalFixedFieldsProposal value =
        MedalFixedFieldsProposal.builder().fields(fields).medal(medal).query(prev).build();
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
    MedalHiddenFieldsProposal value =
        MedalHiddenFieldsProposal.builder().fields(fields).query(prev).build();
    fireHide.fire(value);
    return value.getFields();
  }

  /**
   * initialsHideFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param medal
   * @return initialsHideFields
   */
  public Set<String> fieldsToHide(Interaction prev, MedalRef medal) {
    Set<String> fields = new HashSet<>();
    MedalHiddenFieldsProposal value =
        MedalHiddenFieldsProposal.builder().fields(fields).medal(medal).query(prev).build();
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
  public MedalCached listCachedVisibles(Interaction prev, MedalFilter filter, MedalCursor cursor) {
    MedalFilter visibleFilter = applyPreVisibilityFilter(prev, filter);
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
  public List<Medal> listVisibles(Interaction prev, MedalFilter filter, MedalCursor cursor) {
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
  public MedalCached retrieveCachedVisible(Interaction prev, String uid) {
    MedalCursor cursor = MedalCursor.builder().limit(1).build();
    MedalFilter visibleFilter =
        applyPreVisibilityFilter(prev, MedalFilter.builder().uid(uid).build());
    return cache.retrieve(visibleFilter, cursor).orElseGet(() -> {
      List<Medal> list = retrieveVisible(prev, uid).<List<Medal>>map(List::of).orElseGet(List::of);
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
  public Optional<Medal> retrieveVisible(Interaction prev, String uid) {
    MedalFilter filter = applyPreVisibilityFilter(prev, MedalFilter.builder().uid(uid).build());
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
  private MedalFilter applyPreVisibilityFilter(Interaction prev, MedalFilter filter) {
    MedalVisibilityQueryProposal visible =
        MedalVisibilityQueryProposal.builder().filter(filter).interaction(prev).build();
    fireVisibleFilter.fire(visible);
    return visible.getFilter();
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param medalRef The source interaction
   * @return The input dto with hidden values
   */
  private boolean evaluatePostVisibility(Interaction prev, Medal medalRef) {
    MedalVisibleContentProposal accesible = MedalVisibleContentProposal.builder().visible(true)
        .entity(medalRef).interaction(prev).build();
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
  private Optional<Medal> queryItem(Interaction prev, String uid, MedalFilter filter) {
    return medalReadRepositoryGateway.retrieve(uid, Optional.of(filter))
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
  private List<Medal> queryItems(Interaction prev, MedalFilter filter, MedalCursor cursor) {
    List<Medal> list = new ArrayList<>();
    Iterator<Medal> slide = medalReadRepositoryGateway.list(filter, cursor)
        .slide(values -> evaluatePostVisibility(prev, values));
    while (slide.hasNext()) {
      list.add(slide.next());
    }
    return list;
  }
}
