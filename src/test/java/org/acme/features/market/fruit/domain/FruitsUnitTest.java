package org.acme.features.market.fruit.domain;

import java.util.List;
import java.util.Optional;

import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.Fruit.FruitBuilder;
import org.acme.features.market.fruit.domain.model.valueobject.FruitNameVO;
import org.acme.features.market.fruit.domain.model.valueobject.FruitUidVO;
import org.acme.features.market.fruit.domain.model.valueobject.FruitVersionVO;
import org.acme.features.market.fruit.domain.rule.FruitActionType;
import org.acme.features.market.fruit.domain.rule.FruitBuilderRule;
import org.acme.features.market.fruit.domain.rule.FruitRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.enterprise.inject.Instance;

class FruitsUnitTest {

  /**
   * @autogenerated AggregateGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_fruits_clean() {
    FruitRule entityRule = Mockito.mock(FruitRule.class);
    FruitBuilderRule builderRule = Mockito.mock(FruitBuilderRule.class);
    @SuppressWarnings("unchecked")
    Instance<FruitRule> entityRules = Mockito.mock(Instance.class);
    @SuppressWarnings("unchecked")
    Instance<FruitBuilderRule> builderRules = Mockito.mock(Instance.class);
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
    Fruits instance = new Fruits(entityRules, builderRules);
    Fruit one = Fruit.builder().uid(FruitUidVO.from("one")).name(FruitNameVO.from("one"))
        .version(FruitVersionVO.from(1)).build();
    instance.clean(one);
    Mockito.verify(entityRule).apply(Mockito.eq(FruitActionType.DELETE), Mockito.eq(one),
        Mockito.any(), Mockito.eq(new Object[] {Optional.of(one)}));
  }

  /**
   * @autogenerated AggregateGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_fruits_initilize() {
    FruitRule entityRule = Mockito.mock(FruitRule.class);
    FruitBuilderRule builderRule = Mockito.mock(FruitBuilderRule.class);
    @SuppressWarnings("unchecked")
    Instance<FruitRule> entityRules = Mockito.mock(Instance.class);
    @SuppressWarnings("unchecked")
    Instance<FruitBuilderRule> builderRules = Mockito.mock(Instance.class);
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
    Fruits instance = new Fruits(entityRules, builderRules);
    Fruit one = Fruit.builder().uid(FruitUidVO.from("one")).name(FruitNameVO.from("one"))
        .version(FruitVersionVO.from(1)).build();
    FruitBuilder oneBuilder = one.toBuilder();
    instance.initialize(oneBuilder);
    Mockito.verify(builderRule).apply(Mockito.eq(FruitActionType.CREATE), Mockito.eq(oneBuilder),
        Mockito.any(), Mockito.eq(new Object[] {Optional.empty()}));
    Mockito.verify(entityRule).apply(Mockito.eq(FruitActionType.CREATE), Mockito.eq(one),
        Mockito.any(), Mockito.eq(new Object[] {Optional.empty()}));
  }

  /**
   * @autogenerated AggregateGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_fruits_modify() {
    FruitRule entityRule = Mockito.mock(FruitRule.class);
    FruitBuilderRule builderRule = Mockito.mock(FruitBuilderRule.class);
    @SuppressWarnings("unchecked")
    Instance<FruitRule> entityRules = Mockito.mock(Instance.class);
    @SuppressWarnings("unchecked")
    Instance<FruitBuilderRule> builderRules = Mockito.mock(Instance.class);
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
    Fruits instance = new Fruits(entityRules, builderRules);
    Fruit one = Fruit.builder().uid(FruitUidVO.from("one")).name(FruitNameVO.from("one"))
        .version(FruitVersionVO.from(1)).build();
    Fruit other = Fruit.builder().uid(FruitUidVO.from("two")).name(FruitNameVO.from("two"))
        .version(FruitVersionVO.from(2)).build();
    FruitBuilder otherBuilder = other.toBuilder();
    instance.modify(one, otherBuilder);
    Mockito.verify(builderRule).apply(Mockito.eq(FruitActionType.UPDATE), Mockito.eq(otherBuilder),
        Mockito.any(), Mockito.eq(new Object[] {Optional.of(one)}));
    Mockito.verify(entityRule).apply(Mockito.eq(FruitActionType.UPDATE), Mockito.eq(other),
        Mockito.any(), Mockito.eq(new Object[] {Optional.of(one)}));
  }
}
