package org.acme.features.market.verify.application.usecase.delete;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.features.market.verify.application.service.VerifysVisibilityService;
import org.acme.features.market.verify.domain.Verifys;
import org.acme.features.market.verify.domain.gateway.VerifyCacheGateway;
import org.acme.features.market.verify.domain.gateway.VerifyWriteRepositoryGateway;
import org.acme.features.market.verify.domain.model.Verify;
import org.acme.features.market.verify.domain.model.VerifyRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class DeleteVerifyUsecase {

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Verifys aggregate;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final VerifyCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<VerifyDeleteAllow> deleteAllow;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final VerifyWriteRepositoryGateway gateway;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final VerifysVisibilityService visibility;

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public VerifyDeleteAllow allow(final Interaction query, final VerifyRef reference) {
    VerifyDeleteAllow base =
        VerifyDeleteAllow.build(query, Optional.of(reference), true, "Allowed by default");
    deleteAllow.fire(base);
    return base;
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @return
   */
  public VerifyDeleteAllow allow(final Interaction query) {
    VerifyDeleteAllow base =
        VerifyDeleteAllow.build(query, Optional.empty(), true, "Allowed by default");
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
  public CompletionStage<VerifyDeleteResult> delete(final VerifyDeleteCommand command) {
    CompletionStage<Optional<Verify>> updated =
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
  private CompletionStage<Optional<Verify>> deleteEntity(final Verify original) {
    return aggregate.clean(original).thenCompose(verify -> gateway.delete(verify))
        .thenCompose(deleted -> cache.remove(deleted).thenApply(_ready -> Optional.of(deleted)));
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param result
   * @return The slide with some values
   */
  private CompletionStage<Optional<Verify>> deleteIfIsPresent(final Optional<Verify> result) {
    return result.map(this::deleteEntity)
        .orElseGet(() -> CompletableFuture.completedFuture(Optional.empty()));
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @param opverify
   * @return The slide with some values
   */
  private CompletionStage<VerifyDeleteResult> mapEntity(final VerifyDeleteCommand command,
      final Optional<Verify> opverify) {
    return opverify
        .map(verify -> visibility.copyWithHidden(command, verify)
            .thenApply(visible -> VerifyDeleteResult.builder().command(command)
                .verify(Optional.of(visible)).build()))
        .orElseGet(() -> CompletableFuture.completedStage(
            VerifyDeleteResult.builder().command(command).verify(Optional.empty()).build()));
  }
}
