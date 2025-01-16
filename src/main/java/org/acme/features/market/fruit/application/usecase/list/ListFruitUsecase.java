package org.acme.features.market.fruit.application.usecase.list;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.features.market.fruit.application.FruitDto;
import org.acme.features.market.fruit.application.service.FruitsVisibilityService;
import org.acme.features.market.fruit.domain.gateway.FruitCached;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class ListFruitUsecase {

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
  public FruitListAllow allow(final Interaction query) {
    FruitListAllow base = FruitListAllow.build(query, true, "Allowed by default");
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
  public CompletionStage<FruitListResult> list(final FruitListQuery query) {
    CompletionStage<FruitCached> future = allow(query).getDetail().thenCompose(detail -> {
      if (!detail.isAllowed()) {
        throw new NotAllowedException(detail.getDescription());
      }
      return visibility.listCachedVisibles(query, query.getFilter(), query.getCursor());
    });
    return future.thenCompose(values -> mapList(query, values));
  }

  /**
   * The slide with some values
   *
   * @autogenerated ListUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @param fruits
   * @return The slide with some values
   */
  private CompletionStage<FruitListResult> mapList(final FruitListQuery query,
      final FruitCached fruits) {
    List<CompletableFuture<FruitDto>> futures =
        fruits.getValue().stream().map(fruit -> visibility.copyWithHidden(query, fruit))
            .map(CompletionStage::toCompletableFuture).toList();
    return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        .thenApply(voidResult -> FruitListResult.builder().query(query)
            .fruits(futures.stream().map(CompletableFuture::join).toList()).since(fruits.getSince())
            .build());
  }
}
