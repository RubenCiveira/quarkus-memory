package org.acme.features.market.place.domain;

import java.util.List;
import java.util.Optional;

import org.acme.features.market.place.domain.model.Place;
import org.acme.features.market.place.domain.model.Place.PlaceBuilder;
import org.acme.features.market.place.domain.model.valueobject.PlaceMerchantVO;
import org.acme.features.market.place.domain.model.valueobject.PlaceNameVO;
import org.acme.features.market.place.domain.model.valueobject.PlaceOpeningDateVO;
import org.acme.features.market.place.domain.model.valueobject.PlacePhotoVO;
import org.acme.features.market.place.domain.model.valueobject.PlaceUidVO;
import org.acme.features.market.place.domain.model.valueobject.PlaceVersionVO;
import org.acme.features.market.place.domain.rule.PlaceActionType;
import org.acme.features.market.place.domain.rule.PlaceBuilderRule;
import org.acme.features.market.place.domain.rule.PlaceRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.enterprise.inject.Instance;

class PlacesUnitTest {

  /**
   * @autogenerated AggregateGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_places_clean() {
    PlaceRule entityRule = Mockito.mock(PlaceRule.class);
    PlaceBuilderRule builderRule = Mockito.mock(PlaceBuilderRule.class);
    @SuppressWarnings("unchecked")
    Instance<PlaceRule> entityRules = Mockito.mock(Instance.class);
    @SuppressWarnings("unchecked")
    Instance<PlaceBuilderRule> builderRules = Mockito.mock(Instance.class);
    Mockito.when(entityRules.stream()).thenReturn(List.of(entityRule).stream());
    Mockito.when(builderRules.stream()).thenReturn(List.of(builderRule).stream());
    Mockito.when(entityRule.supports(Mockito.any())).thenReturn(true);
    Mockito.when(builderRule.supports(Mockito.any())).thenReturn(true);
    Mockito
        .when(
            entityRule.apply(Mockito.any(), Mockito.any(), Mockito.any(), (Object[]) Mockito.any()))
        .then(call -> call.getArgument(1));
    Mockito.when(
        builderRule.apply(Mockito.any(), Mockito.any(), Mockito.any(), (Object[]) Mockito.any()))
        .then(call -> call.getArgument(1));
    Places instance = new Places(entityRules, builderRules);
    Place one = Place.builder().uid(PlaceUidVO.from("one")).name(PlaceNameVO.from("one"))
        .merchant(PlaceMerchantVO.from(null)).photo(PlacePhotoVO.from(null))
        .openingDate(PlaceOpeningDateVO.from(null)).version(PlaceVersionVO.from(1)).build();
    instance.clean(one);
    Mockito.verify(entityRule).apply(Mockito.eq(PlaceActionType.DELETE), Mockito.any(),
        Mockito.any(), Mockito.eq(new Object[] {Optional.of(one)}));
  }

  /**
   * @autogenerated AggregateGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_places_initilize() {
    PlaceRule entityRule = Mockito.mock(PlaceRule.class);
    PlaceBuilderRule builderRule = Mockito.mock(PlaceBuilderRule.class);
    @SuppressWarnings("unchecked")
    Instance<PlaceRule> entityRules = Mockito.mock(Instance.class);
    @SuppressWarnings("unchecked")
    Instance<PlaceBuilderRule> builderRules = Mockito.mock(Instance.class);
    Mockito.when(entityRules.stream()).thenReturn(List.of(entityRule).stream());
    Mockito.when(builderRules.stream()).thenReturn(List.of(builderRule).stream());
    Mockito.when(entityRule.supports(Mockito.any())).thenReturn(true);
    Mockito.when(builderRule.supports(Mockito.any())).thenReturn(true);
    Mockito
        .when(
            entityRule.apply(Mockito.any(), Mockito.any(), Mockito.any(), (Object[]) Mockito.any()))
        .then(call -> call.getArgument(1));
    Mockito.when(
        builderRule.apply(Mockito.any(), Mockito.any(), Mockito.any(), (Object[]) Mockito.any()))
        .then(call -> call.getArgument(1));
    Places instance = new Places(entityRules, builderRules);
    Place one = Place.builder().uid(PlaceUidVO.from("one")).name(PlaceNameVO.from("one"))
        .merchant(PlaceMerchantVO.from(null)).photo(PlacePhotoVO.from(null))
        .openingDate(PlaceOpeningDateVO.from(null)).version(PlaceVersionVO.from(1)).build();
    PlaceBuilder oneBuilder = one.toBuilder();
    instance.initialize(oneBuilder);
    Mockito.verify(builderRule).apply(Mockito.eq(PlaceActionType.CREATE), Mockito.any(),
        Mockito.any(), Mockito.eq(new Object[] {Optional.empty()}));
    Mockito.verify(entityRule).apply(Mockito.eq(PlaceActionType.CREATE), Mockito.any(),
        Mockito.any(), Mockito.eq(new Object[] {Optional.empty()}));
  }

  /**
   * @autogenerated AggregateGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_places_modify() {
    PlaceRule entityRule = Mockito.mock(PlaceRule.class);
    PlaceBuilderRule builderRule = Mockito.mock(PlaceBuilderRule.class);
    @SuppressWarnings("unchecked")
    Instance<PlaceRule> entityRules = Mockito.mock(Instance.class);
    @SuppressWarnings("unchecked")
    Instance<PlaceBuilderRule> builderRules = Mockito.mock(Instance.class);
    Mockito.when(entityRules.stream()).thenReturn(List.of(entityRule).stream());
    Mockito.when(builderRules.stream()).thenReturn(List.of(builderRule).stream());
    Mockito.when(entityRule.supports(Mockito.any())).thenReturn(true);
    Mockito.when(builderRule.supports(Mockito.any())).thenReturn(true);
    Mockito
        .when(
            entityRule.apply(Mockito.any(), Mockito.any(), Mockito.any(), (Object[]) Mockito.any()))
        .then(call -> call.getArgument(1));
    Mockito.when(
        builderRule.apply(Mockito.any(), Mockito.any(), Mockito.any(), (Object[]) Mockito.any()))
        .then(call -> call.getArgument(1));
    Places instance = new Places(entityRules, builderRules);
    Place one = Place.builder().uid(PlaceUidVO.from("one")).name(PlaceNameVO.from("one"))
        .merchant(PlaceMerchantVO.from(null)).photo(PlacePhotoVO.from(null))
        .openingDate(PlaceOpeningDateVO.from(null)).version(PlaceVersionVO.from(1)).build();
    Place other = Place.builder().uid(PlaceUidVO.from("two")).name(PlaceNameVO.from("two"))
        .merchant(PlaceMerchantVO.from(null)).photo(PlacePhotoVO.from(null))
        .openingDate(PlaceOpeningDateVO.from(null)).version(PlaceVersionVO.from(2)).build();
    PlaceBuilder otherBuilder = other.toBuilder();
    instance.modify(one, otherBuilder);
    Mockito.verify(builderRule).apply(Mockito.eq(PlaceActionType.UPDATE), Mockito.any(),
        Mockito.any(), Mockito.eq(new Object[] {Optional.of(one)}));
    Mockito.verify(entityRule).apply(Mockito.eq(PlaceActionType.UPDATE), Mockito.any(),
        Mockito.any(), Mockito.eq(new Object[] {Optional.of(one)}));
  }
}