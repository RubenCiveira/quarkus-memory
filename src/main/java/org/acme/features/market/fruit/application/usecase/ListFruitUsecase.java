package org.acme.features.market.fruit.application.usecase;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.acme.common.action.Slide;
import org.acme.common.exception.NotAllowedException;
import org.acme.features.market.fruit.application.allow.FruitListAllow;
import org.acme.features.market.fruit.application.interaction.FruitDto;
import org.acme.features.market.fruit.application.interaction.query.FruitAllowQuery;
import org.acme.features.market.fruit.application.interaction.query.FruitListQuery;
import org.acme.features.market.fruit.application.interaction.result.FruitListResult;
import org.acme.features.market.fruit.application.usecase.service.FruitsVisibilityService;
import org.acme.features.market.fruit.domain.gateway.FruitReadRepositoryGateway;
import org.acme.features.market.fruit.domain.model.Fruit;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class ListFruitUsecase {

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final FruitReadRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ListUsecaseGenerator
   */
  private final Event<FruitListAllow> listAllow;

  /**
   * @autogenerated ListUsecaseGenerator
   */
  private final FruitsVisibilityService visibility;

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @return
   */
  public FruitListAllow allow(final FruitAllowQuery query) {
    FruitListAllow base = FruitListAllow.build(true, "Allowed by default");
    listAllow.fire(base);
    return base;
  }

  /**
   * The slide with some values
   *
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public FruitListResult list(final FruitListQuery query) {
    CompletableFuture<List<Fruit>> future = allow(query).getDetail().thenCompose(detail -> {
      if (!detail.isAllowed()) {
        throw new NotAllowedException(detail.getDescription());
      }
      Slide<Fruit> slide =
          gateway.list(visibility.visibleFilter(query, query.getFilter()), query.getCursor());
      return slide.filterUnitLimit(values -> filterUnitLimit(query, values));
    });
    return FruitListResult.builder().query(query)
        .fruits(future.thenApply(values -> mapList(query, values))).build();
  }

  /**
   * @autogenerated ListUsecaseGenerator
   * @param query
   * @return
   */
  private FruitListAllow allow(final FruitListQuery query) {
    return allow(FruitAllowQuery.builder().build(query));
  }

  /**
   * The slide with some values
   *
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @param fruits
   * @return The slide with some values
   */
  private List<Fruit> filterUnitLimit(final FruitListQuery query, final List<Fruit> fruits) {
    return visibility.listableFilter(query, fruits);
  }

  /**
   * The slide with some values
   *
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @param fruit
   * @return The slide with some values
   */
  private FruitDto mapEntity(final FruitListQuery query, final Fruit fruit) {
    return visibility.hide(query, fruit);
  }

  /**
   * The slide with some values
   *
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @param fruits
   * @return The slide with some values
   */
  private List<FruitDto> mapList(final FruitListQuery query, final List<Fruit> fruits) {
    return fruits.stream().map(fruit -> mapEntity(query, fruit)).toList();
  }
}
