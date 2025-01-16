package org.acme.features.market.fruit.application.usecase.delete;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.features.market.fruit.application.service.FruitsVisibilityService;
import org.acme.features.market.fruit.domain.Fruits;
import org.acme.features.market.fruit.domain.gateway.FruitCacheGateway;
import org.acme.features.market.fruit.domain.gateway.FruitWriteRepositoryGateway;
import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.FruitRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class DeleteFruitUsecase {

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Fruits aggregate;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final FruitCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<FruitDeleteAllow> deleteAllow;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final FruitWriteRepositoryGateway gateway;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final FruitsVisibilityService visibility;

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public FruitDeleteAllow allow(final Interaction query, final FruitRef reference) {
    FruitDeleteAllow base =
        FruitDeleteAllow.build(query, Optional.of(reference), true, "Allowed by default");
    deleteAllow.fire(base);
    return base;
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @return
   */
  public FruitDeleteAllow allow(final Interaction query) {
    FruitDeleteAllow base =
        FruitDeleteAllow.build(query, Optional.empty(), true, "Allowed by default");
    deleteAllow.fire(base);
    return base;
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public CompletionStage<FruitDeleteResult> delete(final FruitDeleteCommand command) {
    CompletionStage<Optional<Fruit>> updated =
        allow(command, command.getReference()).getDetail().thenCompose(detail -> {
          if (!detail.isAllowed()) {
            throw new NotAllowedException(detail.getDescription());
          }
          return visibility.retrieveVisible(command, command.getReference().getUidValue())
              .thenCompose(this::deleteIfIsPresent);
        });
    return updated.thenCompose(entity -> mapEntity(command, entity));
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param original
   * @return The slide with some values
   */
  private CompletionStage<Optional<Fruit>> deleteEntity(final Fruit original) {
    return aggregate.clean(original).thenCompose(fruit -> gateway.delete(fruit))
        .thenCompose(deleted -> cache.remove(deleted).thenApply(_ready -> Optional.of(deleted)));
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param result
   * @return The slide with some values
   */
  private CompletionStage<Optional<Fruit>> deleteIfIsPresent(final Optional<Fruit> result) {
    return result.map(this::deleteEntity)
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param opfruit
   * @return The slide with some values
   */
  private CompletionStage<FruitDeleteResult> mapEntity(final FruitDeleteCommand command,
      final Optional<Fruit> opfruit) {
    return opfruit
        .map(fruit -> visibility.copyWithHidden(command, fruit)
            .thenApply(visible -> FruitDeleteResult.builder().command(command)
                .fruit(Optional.of(visible)).build()))
        .orElseGet(() -> CompletableFuture.completedStage(
            FruitDeleteResult.builder().command(command).fruit(Optional.empty()).build()));
  }
}
