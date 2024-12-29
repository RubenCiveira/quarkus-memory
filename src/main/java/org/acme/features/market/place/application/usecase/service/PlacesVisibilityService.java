package org.acme.features.market.place.application.usecase.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotFoundException;
import org.acme.features.market.merchant.application.usecase.service.MerchantsVisibilityService;
import org.acme.features.market.merchant.domain.gateway.MerchantFilter;
import org.acme.features.market.merchant.domain.gateway.MerchantReadRepositoryGateway;
import org.acme.features.market.place.application.interaction.PlaceDto;
import org.acme.features.market.place.application.interaction.visibility.PlaceFixedFields;
import org.acme.features.market.place.application.interaction.visibility.PlaceHiddenFields;
import org.acme.features.market.place.application.interaction.visibility.PlaceListableContent;
import org.acme.features.market.place.application.interaction.visibility.PlaceVisibleContent;
import org.acme.features.market.place.application.interaction.visibility.PlaceVisibleFilter;
import org.acme.features.market.place.domain.Places;
import org.acme.features.market.place.domain.gateway.PlaceFilter;
import org.acme.features.market.place.domain.model.Place;
import org.acme.features.market.place.domain.model.PlaceRef;

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
   * Event source for maniputale the fix over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<PlaceFixedFields> fireFix;

  /**
   * Event source for maniputale the hide over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<PlaceHiddenFields> fireHide;

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<PlaceListableContent> fireListableList;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<PlaceVisibleFilter> fireVisibleFilter;

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<PlaceVisibleContent> fireVisibleList;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final MerchantReadRepositoryGateway merchantReadRepositoryGateway;

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final MerchantsVisibilityService merchantsVisibilityService;

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
    return visiblesReferences(prev, source)
        .thenCompose(dto -> fieldsToFix(prev, original).getFixed().thenApply(fixeds -> {
          fixeds.forEach(field -> dto.fix(field, original));
          return dto;
        }));
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
    return visiblesReferences(prev, source)
        .thenCompose(dto -> fieldsToFix(prev).getFixed().thenApply(fixeds -> {
          fixeds.forEach(field -> dto.fix(field));
          return dto;
        }));
  }

  /**
   * initialsFixFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @return initialsFixFields
   */
  public PlaceFixedFields fieldsToFix(Interaction prev) {
    CompletionStage<Set<String>> fields = fieldsToHide(prev).getHidden().thenApply(hidden -> {
      Set<String> set = new HashSet<>(aggregate.calcultadFields());
      set.addAll(hidden);
      return set;
    });
    PlaceFixedFields value = PlaceFixedFields.builder().fixed(fields).build(prev);
    fireFix.fire(value);
    return value;
  }

  /**
   * initialsFixFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param place
   * @return initialsFixFields
   */
  public PlaceFixedFields fieldsToFix(Interaction prev, PlaceRef place) {
    CompletionStage<Set<String>> fields = fieldsToFix(prev).getFixed()
        .thenCombine(fieldsToHide(prev, place).getHidden(), (set1, set2) -> {
          Set<String> set = new HashSet<>(set1);
          set.addAll(set2);
          return set;
        });
    PlaceFixedFields value = PlaceFixedFields.builder().fixed(fields).place(place).build(prev);
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
  public PlaceHiddenFields fieldsToHide(Interaction prev) {
    CompletionStage<Set<String>> fields = CompletableFuture.completedFuture(Set.of());
    PlaceHiddenFields value = PlaceHiddenFields.builder().hidden(fields).build(prev);
    fireHide.fire(value);
    return value;
  }

  /**
   * initialsHideFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param place
   * @return initialsHideFields
   */
  public PlaceHiddenFields fieldsToHide(Interaction prev, PlaceRef place) {
    CompletionStage<Set<String>> fields = fieldsToHide(prev).getHidden();
    PlaceHiddenFields value = PlaceHiddenFields.builder().hidden(fields).place(place).build(prev);
    fireHide.fire(value);
    return value;
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param place
   * @return The input dto with hidden values
   */
  public CompletionStage<PlaceDto> hide(Interaction prev, Place place) {
    return fieldsToHide(prev, place).getHidden().thenApply(hidden -> {
      PlaceDto target = PlaceDto.from(place);
      hidden.forEach(target::hide);
      return target;
    });
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param placeRefs The source interaction
   * @return The input dto with hidden values
   */
  public CompletionStage<List<Place>> listableFilter(Interaction prev, List<Place> placeRefs) {
    return visibleFilter(prev, placeRefs).thenCompose(visibles -> {
      PlaceListableContent list = PlaceListableContent.builder()
          .listables(CompletableFuture.completedFuture(visibles)).build(prev);
      fireListableList.fire(list);
      return list.getListables();
    });
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param placeRefs The source interaction
   * @return The input dto with hidden values
   */
  public CompletionStage<List<Place>> visibleFilter(Interaction prev, List<Place> placeRefs) {
    PlaceVisibleContent list = PlaceVisibleContent.builder()
        .visibles(CompletableFuture.completedFuture(new ArrayList<>(placeRefs))).build(prev);
    fireVisibleList.fire(list);
    return list.getVisibles();
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param placeRef The source interaction
   * @return The input dto with hidden values
   */
  public CompletionStage<Optional<Place>> visibleFilter(Interaction prev, Place placeRef) {
    return visibleFilter(prev, List.of(placeRef))
        .thenApply(list -> list.isEmpty() ? Optional.empty() : Optional.of(list.get(0)));
  }

  /**
   * The self filter modified with the prepared values.
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param filter The filter to retrieve values
   * @return The self filter modified with the prepared values.
   */
  public CompletionStage<PlaceFilter> visibleFilter(Interaction prev, PlaceFilter filter) {
    PlaceVisibleFilter visible =
        PlaceVisibleFilter.builder().filter(CompletableFuture.completedFuture(filter)).build(prev);
    fireVisibleFilter.fire(visible);
    return visible.getFilter();
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param uid
   * @return The input entity with the copy values without hidden
   */
  private CompletionStage<Boolean> checkVisibilityForMerchant(Interaction prev, String uid) {
    return merchantsVisibilityService.visibleFilter(prev, MerchantFilter.builder().uid(uid).build())
        .thenCompose(filter -> merchantReadRepositoryGateway.exists(uid, Optional.of(filter))
            .thenApply(bool -> {
              if (!bool) {
                throw new NotFoundException("There is no merchant with uid " + uid);
              }
              return bool;
            }));
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
    List<CompletionStage<Boolean>> related = new ArrayList<>();
    if (null != source.getMerchant()) {
      related.add(checkVisibilityForMerchant(prev, source.getMerchant()));
    }
    return CompletableFuture.allOf(related.toArray(new CompletableFuture[0]))
        .thenApply(_void -> source);
  }
}
