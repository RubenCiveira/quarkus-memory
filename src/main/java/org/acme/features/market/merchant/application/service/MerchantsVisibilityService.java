package org.acme.features.market.merchant.application.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.application.MerchantDto;
import org.acme.features.market.merchant.application.service.proposal.MerchantFixedFieldsProposal;
import org.acme.features.market.merchant.application.service.proposal.MerchantHiddenFieldsProposal;
import org.acme.features.market.merchant.application.service.proposal.MerchantVisibilityQueryProposal;
import org.acme.features.market.merchant.application.service.proposal.MerchantVisibleContentProposal;
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
  private final Event<MerchantFixedFieldsProposal> fireFix;

  /**
   * Event source for maniputale the hide over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MerchantHiddenFieldsProposal> fireHide;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MerchantVisibilityQueryProposal> fireVisibleFilter;

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<MerchantVisibleContentProposal> fireVisibleList;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final MerchantsCalculatorService formula;

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
    return listVisibles(prev, MerchantFilter.builder().uids(uids).build(),
        MerchantCursor.builder().build()).isEmpty();
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
  public MerchantDto copyWithFixed(Interaction prev, Merchant original, MerchantDto source) {
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
  public MerchantDto copyWithFixed(Interaction prev, MerchantDto source) {
    fieldsToFix(prev).forEach(field -> source.fixField(field));
    return formula.copyWithFormulas(prev, source);
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param merchant
   * @return The input dto with hidden values
   */
  public MerchantDto copyWithHidden(Interaction prev, Merchant merchant) {
    MerchantDto target = MerchantDto.from(merchant);
    fieldsToHide(prev, merchant).forEach(target::hideField);
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
    MerchantFixedFieldsProposal value =
        MerchantFixedFieldsProposal.builder().fields(fields).query(prev).build();
    fireFix.fire(value);
    return value.getFields();
  }

  /**
   * initialsFixFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param merchant
   * @return initialsFixFields
   */
  public Set<String> fieldsToFix(Interaction prev, MerchantRef merchant) {
    Set<String> fields = new HashSet<>(fieldsToHide(prev, merchant));
    fields.addAll(aggregate.calcultadFields());
    MerchantFixedFieldsProposal value =
        MerchantFixedFieldsProposal.builder().fields(fields).merchant(merchant).query(prev).build();
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
    MerchantHiddenFieldsProposal value =
        MerchantHiddenFieldsProposal.builder().fields(fields).query(prev).build();
    fireHide.fire(value);
    return value.getFields();
  }

  /**
   * initialsHideFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param merchant
   * @return initialsHideFields
   */
  public Set<String> fieldsToHide(Interaction prev, MerchantRef merchant) {
    Set<String> fields = new HashSet<>();
    MerchantHiddenFieldsProposal value = MerchantHiddenFieldsProposal.builder().fields(fields)
        .merchant(merchant).query(prev).build();
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
  public MerchantCached listCachedVisibles(Interaction prev, MerchantFilter filter,
      MerchantCursor cursor) {
    MerchantFilter visibleFilter = applyPreVisibilityFilter(prev, filter);
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
  public List<Merchant> listVisibles(Interaction prev, MerchantFilter filter,
      MerchantCursor cursor) {
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
  public MerchantCached retrieveCachedVisible(Interaction prev, String uid) {
    MerchantCursor cursor = MerchantCursor.builder().limit(1).build();
    MerchantFilter visibleFilter =
        applyPreVisibilityFilter(prev, MerchantFilter.builder().uid(uid).build());
    return cache.retrieve(visibleFilter, cursor).orElseGet(() -> {
      List<Merchant> list =
          retrieveVisible(prev, uid).<List<Merchant>>map(List::of).orElseGet(List::of);
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
  public Optional<Merchant> retrieveVisible(Interaction prev, String uid) {
    MerchantFilter filter =
        applyPreVisibilityFilter(prev, MerchantFilter.builder().uid(uid).build());
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
  private MerchantFilter applyPreVisibilityFilter(Interaction prev, MerchantFilter filter) {
    MerchantVisibilityQueryProposal visible =
        MerchantVisibilityQueryProposal.builder().filter(filter).interaction(prev).build();
    fireVisibleFilter.fire(visible);
    return visible.getFilter();
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param merchantRef The source interaction
   * @return The input dto with hidden values
   */
  private boolean evaluatePostVisibility(Interaction prev, Merchant merchantRef) {
    MerchantVisibleContentProposal accesible = MerchantVisibleContentProposal.builder()
        .visible(true).entity(merchantRef).interaction(prev).build();
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
  private Optional<Merchant> queryItem(Interaction prev, String uid, MerchantFilter filter) {
    return merchantReadRepositoryGateway.retrieve(uid, Optional.of(filter))
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
  private List<Merchant> queryItems(Interaction prev, MerchantFilter filter,
      MerchantCursor cursor) {
    List<Merchant> list = new ArrayList<>();
    Iterator<Merchant> slide = merchantReadRepositoryGateway.list(filter, cursor)
        .slide(values -> evaluatePostVisibility(prev, values));
    while (slide.hasNext()) {
      list.add(slide.next());
    }
    return list;
  }
}
