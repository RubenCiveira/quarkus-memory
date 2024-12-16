package org.acme.features.market.color.domain;

import java.util.List;
import java.util.Optional;

import org.acme.features.market.color.domain.model.Color;
import org.acme.features.market.color.domain.model.Color.ColorBuilder;
import org.acme.features.market.color.domain.model.valueobject.ColorNameVO;
import org.acme.features.market.color.domain.model.valueobject.ColorUidVO;
import org.acme.features.market.color.domain.model.valueobject.ColorVersionVO;
import org.acme.features.market.color.domain.rule.ColorActionType;
import org.acme.features.market.color.domain.rule.ColorBuilderRule;
import org.acme.features.market.color.domain.rule.ColorRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.enterprise.inject.Instance;

class ColorsUnitTest {

  /**
   * @autogenerated AggregateGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_colors_clean() {
    ColorRule entityRule = Mockito.mock(ColorRule.class);
    ColorBuilderRule builderRule = Mockito.mock(ColorBuilderRule.class);
    @SuppressWarnings("unchecked")
    Instance<ColorRule> entityRules = Mockito.mock(Instance.class);
    @SuppressWarnings("unchecked")
    Instance<ColorBuilderRule> builderRules = Mockito.mock(Instance.class);
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
    Colors instance = new Colors(entityRules, builderRules);
    Color one = Color.builder().uid(ColorUidVO.from("one")).name(ColorNameVO.from("one"))
        .version(ColorVersionVO.from(1)).build();
    instance.clean(one);
    Mockito.verify(entityRule).apply(Mockito.eq(ColorActionType.DELETE), Mockito.any(),
        Mockito.any(), Mockito.eq(new Object[] {Optional.of(one)}));
  }

  /**
   * @autogenerated AggregateGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_colors_initilize() {
    ColorRule entityRule = Mockito.mock(ColorRule.class);
    ColorBuilderRule builderRule = Mockito.mock(ColorBuilderRule.class);
    @SuppressWarnings("unchecked")
    Instance<ColorRule> entityRules = Mockito.mock(Instance.class);
    @SuppressWarnings("unchecked")
    Instance<ColorBuilderRule> builderRules = Mockito.mock(Instance.class);
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
    Colors instance = new Colors(entityRules, builderRules);
    Color one = Color.builder().uid(ColorUidVO.from("one")).name(ColorNameVO.from("one"))
        .version(ColorVersionVO.from(1)).build();
    ColorBuilder oneBuilder = one.toBuilder();
    instance.initialize(oneBuilder);
    Mockito.verify(builderRule).apply(Mockito.eq(ColorActionType.CREATE), Mockito.any(),
        Mockito.any(), Mockito.eq(new Object[] {Optional.empty()}));
    Mockito.verify(entityRule).apply(Mockito.eq(ColorActionType.CREATE), Mockito.any(),
        Mockito.any(), Mockito.eq(new Object[] {Optional.empty()}));
  }

  /**
   * @autogenerated AggregateGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_colors_modify() {
    ColorRule entityRule = Mockito.mock(ColorRule.class);
    ColorBuilderRule builderRule = Mockito.mock(ColorBuilderRule.class);
    @SuppressWarnings("unchecked")
    Instance<ColorRule> entityRules = Mockito.mock(Instance.class);
    @SuppressWarnings("unchecked")
    Instance<ColorBuilderRule> builderRules = Mockito.mock(Instance.class);
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
    Colors instance = new Colors(entityRules, builderRules);
    Color one = Color.builder().uid(ColorUidVO.from("one")).name(ColorNameVO.from("one"))
        .version(ColorVersionVO.from(1)).build();
    Color other = Color.builder().uid(ColorUidVO.from("two")).name(ColorNameVO.from("two"))
        .version(ColorVersionVO.from(2)).build();
    ColorBuilder otherBuilder = other.toBuilder();
    instance.modify(one, otherBuilder);
    Mockito.verify(builderRule).apply(Mockito.eq(ColorActionType.UPDATE), Mockito.any(),
        Mockito.any(), Mockito.eq(new Object[] {Optional.of(one)}));
    Mockito.verify(entityRule).apply(Mockito.eq(ColorActionType.UPDATE), Mockito.any(),
        Mockito.any(), Mockito.eq(new Object[] {Optional.of(one)}));
  }
}
