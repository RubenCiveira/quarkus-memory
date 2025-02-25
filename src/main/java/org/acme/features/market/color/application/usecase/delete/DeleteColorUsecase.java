package org.acme.features.market.color.application.usecase.delete;

import java.time.Duration;
import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.batch.BatchIdentificator;
import org.acme.common.batch.BatchProgress;
import org.acme.common.batch.BatchService;
import org.acme.common.batch.ExecutorByDeferSteps;
import org.acme.common.batch.ExecutorPlan;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.exception.NotFoundException;
import org.acme.common.security.Allow;
import org.acme.features.market.color.application.ColorDto;
import org.acme.features.market.color.application.service.ColorsVisibilityService;
import org.acme.features.market.color.domain.Colors;
import org.acme.features.market.color.domain.gateway.ColorCacheGateway;
import org.acme.features.market.color.domain.gateway.ColorWriteRepositoryGateway;
import org.acme.features.market.color.domain.model.Color;
import org.acme.features.market.color.domain.model.ColorRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class DeleteColorUsecase {

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Colors aggregate;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final BatchService batch;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final ColorCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<ColorDeleteAllowProposal> deleteAllow;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<ColorDeleteEvent> eventEmitter;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final ColorWriteRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<ColorDeleteProposal> proposalEmitter;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final ColorsVisibilityService visibility;

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public Allow allow(final Interaction query, final ColorRef reference) {
    return ColorDeleteAllowProposal.resolveWith(deleteAllow, query, Optional.of(reference), true,
        "Allowed by default");
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @return
   */
  public Allow allow(final Interaction query) {
    return ColorDeleteAllowProposal.resolveWith(deleteAllow, query, Optional.empty(), true,
        "Allowed by default");
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public BatchProgress checkProgress(final ColorCheckBatchDeleteStatusQuery query) {
    return query.getActor().getName()
        .flatMap(name -> batch.retrieve(query.getTaskId(), query.getConnection().getLocale(), name))
        .orElseThrow(() -> new NotFoundException());
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public ColorDto delete(final ColorDeleteCommand command) {
    Allow detail = allow(command, command.getReference());
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    Color original = visibility.retrieveVisible(command, command.getReference().getUidValue())
        .orElseThrow(() -> new NotFoundException(""));
    Color deleted = delete(command, original);
    flush();
    return visibility.copyWithHidden(command, deleted);
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public BatchIdentificator delete(final ColorDeleteAllInBatchCommand command) {
    Allow detail = allow(command);
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    return batch.start(command.getActor().getName().orElse("-"), Duration.ofHours(6), ExecutorPlan
        .<ColorDeleteAllInBatchCommand>builder().params(command).name("delete-color")
        .executor(
            ExecutorByDeferSteps.<Color, Color, ColorDeleteAllInBatchCommand, DeleteColorsInBatchExecutor.ColorPaginableBatch>builder()
                .initializer(DeleteColorsInBatchExecutor.class)
                .counter(DeleteColorsInBatchExecutor.class)
                .descriptor(DeleteColorsInBatchExecutor.class)
                .reader(DeleteColorsInBatchExecutor.class)
                .processor(DeleteColorsInBatchExecutor.class)
                .writer(DeleteColorsInBatchExecutor.class)
                .finalizer(DeleteColorsInBatchExecutor.class).build())
        .build());
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param interaction
   * @param original
   * @return
   */
  Color delete(final Interaction interaction, final Color original) {
    Color clean = aggregate.clean(original);
    ColorRef modified = ColorDeleteProposal.resolveWith(proposalEmitter, interaction, clean);
    Color result = gateway.delete(gateway.enrich(modified));
    ColorDeleteEvent.notifyWith(eventEmitter, interaction, result);
    return result;
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  void flush() {
    cache.evict();
  }
}
